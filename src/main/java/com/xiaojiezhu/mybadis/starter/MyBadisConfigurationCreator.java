package com.xiaojiezhu.mybadis.starter;

import com.xiaojiezhu.mybadis.starter.core.DataSourceConfig;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * 作者 zxj<br>
 * 时间 2017/9/26 20:00
 * 说明 ...
 */
public class MyBadisConfigurationCreator {
    public final static Logger LOG = LoggerFactory.getLogger(MyBadisConfigurationCreator.class);

    public SqlSessionFactory createSqlSessionFactory(DataSource ds) {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(ds);

        //分页插件
/*        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        properties.setProperty("reasonable", "false");
        properties.setProperty("pageSizeZero", "true");
        pageHelper.setProperties(properties);*/
        //bean.setPlugins(new Interceptor[]{pageHelper});

        try {
            //指定mapper xml目录
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            //设置配置项
            org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
            //自动把下划线转成驼峰
            configuration.setMapUnderscoreToCamelCase(true);
            bean.setConfiguration(configuration);
            return bean.getObject();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * - 设置SqlSessionFactory；
     * - 设置dao所在的package路径，路径可以以逗号或者分号进行分隔设置多个
     * - 关联注解在dao类上的Annotation名字；
     * - 上面的注解可以不需要
     */
    public MapperScannerConfigurer createMapperScannerConfiguer(DataSourceConfig dataSourceConfig) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName(createSqlSessionFactoryBeanName(dataSourceConfig.getDataSourceName()));
        //mapperScannerConfigurer.setSqlSessionFactory(sqlSessionFactory);
        mapperScannerConfigurer.setBasePackage(dataSourceConfig.getPackageName());
        //mapperScannerConfigurer.setAnnotationClass((Class<? extends Annotation>) Class.forName("com.zxj.ds.mybatis.annotation.SaasDao"));
        return mapperScannerConfigurer;
    }

    public String createSqlSessionFactoryBeanName(String dataSourceName){
        return "sqlSessionFactory_" + dataSourceName;
    }


    public String createMapperScannerConfigurerBeanName(String dataSourceName){
        return "mapperScannerConfigurer_" + dataSourceName;
    }
}
