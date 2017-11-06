package com.xiaojiezhu.mybadis.starter.register;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.AnnotationScopeMetadataResolver;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;

/**
 * @author zxj<br>
 * 时间 2017/11/4 13:02
 * 说明 默认实现的spring对象注册器
 */
public class DefaultBeanRegister implements BeanRegister {
    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();

    private BeanDefinitionRegistry registry;

    public DefaultBeanRegister(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void register(Object object) throws Exception {
        String beanName = ObjectNameCreator.genBeanName(object);
        register(object,beanName,true);
    }

    @Override
    public void register(Object object, String beanName) throws Exception {
        register(object,beanName,true);

    }

    @Override
    public void register(Object object, String beanName, boolean singleton) throws Exception {
        RootBeanDefinition abd = new RootBeanDefinition(CommonFactoryBean.class);
        //=================================================================================
        //这里定义了对象生成的规则，在构造方法中添加了一个参数，在创建相应的bean时，会自动传递参数来构建对象
        ConstructorArgumentValues cons = abd.getConstructorArgumentValues();
        cons.addGenericArgumentValue(object);
        cons.addGenericArgumentValue(singleton);
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

    public static void main(String[] args) {
        String simpleName = DefaultBeanRegister.class.getSimpleName();
        System.out.println(simpleName);
    }
}
