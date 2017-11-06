package com.xiaojiezhu.mybadis.starter.register;

import org.springframework.beans.factory.FactoryBean;


/**
 * @author zxj<br>
 * 时间 2017/11/4 12:56
 * 说明 ...
 */
public class CommonFactoryBean implements FactoryBean<Object> {
    /**
     * 要注入到spring中的对象
     */
    private Object object;
    /**
     * 是否单例，默认为是单例
     */
    private boolean singleton = true;


    public CommonFactoryBean(Object object) {
        this.object = object;
    }

    public CommonFactoryBean(Object object, boolean singleton) {
        this.object = object;
        this.singleton = singleton;
    }

    @Override
    public Object getObject() throws Exception {
        return this.object;
    }

    @Override
    public Class<?> getObjectType() {
        return object.getClass();
    }

    @Override
    public boolean isSingleton() {
        return singleton;
    }
}
