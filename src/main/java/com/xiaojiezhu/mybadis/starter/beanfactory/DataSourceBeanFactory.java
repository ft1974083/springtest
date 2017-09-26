package com.xiaojiezhu.mybadis.starter.beanfactory;

import org.springframework.beans.factory.FactoryBean;

import javax.sql.DataSource;

/**
 * 作者 zxj<br>
 * 时间 2017/9/26 19:51
 * 说明 ...
 */
public class DataSourceBeanFactory implements FactoryBean<DataSource>{
    private DataSource ds;

    public DataSourceBeanFactory(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public DataSource getObject() throws Exception {
        return ds;
    }

    @Override
    public Class<?> getObjectType() {
        return DataSource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
