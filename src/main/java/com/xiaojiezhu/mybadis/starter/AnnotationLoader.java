package com.xiaojiezhu.mybadis.starter;

import com.alibaba.druid.pool.DruidDataSource;
import com.xiaojiezhu.mybadis.starter.beanfactory.DataSourceBeanFactory;
import com.xiaojiezhu.mybadis.starter.beanfactory.SqlSessionFactoryBeanFactory;
import com.xiaojiezhu.mybadis.starter.core.DataSourceConfig;
import com.xiaojiezhu.mybadis.starter.core.LoaderMateData;
import com.xiaojiezhu.mybadis.starter.plugins.DataSourceProxyFactory;
import com.xiaojiezhu.mybadis.starter.register.BeanRegister;
import com.xiaojiezhu.mybadis.starter.register.DefaultBeanRegister;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 作者 zxj<br>
 * 时间 2017/9/26 16:48
 * 说明 ...
 */
public class AnnotationLoader implements ApplicationContextAware,BeanDefinitionRegistryPostProcessor {
    public final static Logger LOG = LoggerFactory.getLogger(AnnotationLoader.class);

    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
    private MyBadisConfigurationCreator myBadisConfigurationCreator = new MyBadisConfigurationCreator();
    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    private ApplicationContext context;
    private Set<LoaderMateData> mateDatas;

    private Environment env;
    private Set<DataSourceConfig> dataSourceConfigs;
    /**
     * 数据源创建的代理插件
     */
    private DataSourceProxyFactory dataSourceProxyFactory;



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        env = context.getBean(Environment.class);
        try{
            this.dataSourceProxyFactory = context.getBean(DataSourceProxyFactory.class);
        }catch (Exception e){

        }
        loaderMateData();
        loadProperties(env);
    }

    /**
     * 加载元数据
     */
    private void loaderMateData(){
        Map<String, Object> annotationBean = context.getBeansWithAnnotation(MyBadisLoader.class);
        if(annotationBean == null || annotationBean.size() == 0){
            throw new NullPointerException("找不到相应的" + MyBadisLoader.class + " bean");
        }else if(annotationBean.size()  >  1){
            throw new RuntimeException("只能有1 个" + MyBadisLoader.class + "的bean，但是找到" + annotationBean.size() + "个");
        }else{
            Object value = annotationBean.entrySet().iterator().next().getValue();
            MyBadisLoader annotation = value.getClass().getAnnotation(MyBadisLoader.class);
            String[] loaders = annotation.value();
            mateDatas = new HashSet<LoaderMateData>(loaders.length);
            dataSourceConfigs = new HashSet<DataSourceConfig>(mateDatas.size());

            for(String loader : loaders){
                String[] split = loader.split("=");
                if(split.length != 3){
                    throw new RuntimeException(MyBadisLoader.class + " 注解的值不合法:" + loader + "   示例    saas : com.xxx.dao : classpath:/mapper/*.xml");
                }else{
                    String[] xmlMappingPath = split[2].split(",");
                    for(int i = 0 ; i < xmlMappingPath.length ; i ++){
                        xmlMappingPath[i] = xmlMappingPath[i].trim();
                    }
                    mateDatas.add(new LoaderMateData(split[0].trim(),split[1].trim(),xmlMappingPath));
                }
            }
            LOG.debug("mybadis元数据加载完成:" + mateDatas);
        }
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        for(DataSourceConfig config : dataSourceConfigs){
            DataSource dataSource = createDataSource(config);
            BeanRegister beanRegister = new DefaultBeanRegister(registry);
            try {
                beanRegister.register(dataSource,config.getDataSourceName());
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }

            //registerBean(registry,config.getDataSourceName(), DataSourceBeanFactory.class,new Object[]{dataSource});

            SqlSessionFactory sqlSessionFactory = myBadisConfigurationCreator.createSqlSessionFactory(dataSource,config.getXmlMappingPath());
            registerBean(registry,myBadisConfigurationCreator.createSqlSessionFactoryBeanName(config.getDataSourceName()), SqlSessionFactoryBeanFactory.class,new Object[]{sqlSessionFactory});

            try {
                MapperScannerConfigurer mapperScannerConfigure = myBadisConfigurationCreator.createMapperScannerConfiguer(config);
                //registerBean(registry,myBadisConfigurationCreator.createMapperScannerConfigurerBeanName(config.getDataSourceName()), MapperScannerConfigurerBeanFactory.class,new Object[]{mapperScannerConfigure});
                mapperScannerConfigure.postProcessBeanDefinitionRegistry(registry);

            } catch (Exception e) {
                e.printStackTrace();
                LOG.error("创建mybadis实例失败，失败原因:" + e.getMessage());
                throw new RuntimeException("实例化mybadis失败");
            }
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //
    }

    private void registerBean(BeanDefinitionRegistry registry, String beanName, Class<? extends FactoryBean> beanClass, Object[] args){
        RootBeanDefinition abd = new RootBeanDefinition(beanClass);
        //=================================================================================
        //这里定义了对象生成的规则，在构造方法中添加了两个参数，分别是name与age，在创建相应的bean时，会自动传递参数来构建对象
        ConstructorArgumentValues cons = abd.getConstructorArgumentValues();
        for (Object arg : args){
            cons.addGenericArgumentValue(arg);
        }
        //=================================================================================

        //而后面的代码就会把MyBeanFactory这个对象传递到Spring中，但是BeanFactory的这个接口是经过特殊处理的，会调用getObject()方法来获取
        //相应的对象，所以实际注入的对象，就是getObject()中返回的Person对象
        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
        abd.setScope(scopeMetadata.getScopeName());
        // 按照spring的名称规范来生成一个bean名字

        //AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);

        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
    }

    private void loadProperties(Environment environment) {
        for(LoaderMateData mateData : mateDatas){
            String url = environment.getProperty("mysql.server." + mateData.getDataSourceName() + ".url");
            String username = environment.getProperty("mysql.server." + mateData.getDataSourceName() + ".username");
            String password = environment.getProperty("mysql.server." + mateData.getDataSourceName() + ".password");
            String driverClassName = environment.getProperty("mysql.server." + mateData.getDataSourceName() + ".driverClassName");
            String initialSize = environment.getProperty("mysql.server." + mateData.getDataSourceName() + ".initialSize");
            String minIdle = environment.getProperty("mysql.server." + mateData.getDataSourceName() + ".minIdle");
            String maxActive = environment.getProperty("mysql.server." + mateData.getDataSourceName() + ".maxActive");
            DataSourceConfig config = new DataSourceConfig(mateData.getDataSourceName(),mateData.getPackageName(),mateData.getMappingPath(),url, username, password, driverClassName);
            if(initialSize != null){
                config.setInitialSize(Integer.parseInt(initialSize));
            }
            if(minIdle != null){
                config.setMinIdle(Integer.parseInt(minIdle));
            }
            if(maxActive != null){
                config.setMaxActive(Integer.parseInt(maxActive));
            }
            dataSourceConfigs.add(config);
        }
    }

    private DataSource createDataSource(DataSourceConfig config){
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(config.getUrl());
        ds.setUsername(config.getUsername());
        ds.setPassword(config.getPassword());
        ds.setDriverClassName(config.getDriverClassName());
        if(config.getInitialSize() != 0){
            ds.setInitialSize(config.getInitialSize());
        }
        if(config.getMinIdle() != 0){
            ds.setInitialSize(config.getMinIdle());
        }
        if(config.getMaxActive() != 0){
            ds.setMaxActive(config.getMaxActive());
        }
        if(this.dataSourceProxyFactory != null){
            //创建代理数据源
            DataSource dataSource = this.dataSourceProxyFactory.buildDataSource(ds);
            return dataSource;
        }else{
            return ds;
        }
    }


}
