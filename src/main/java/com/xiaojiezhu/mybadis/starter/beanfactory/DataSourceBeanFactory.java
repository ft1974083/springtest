package com.xiaojiezhu.mybadis.starter.beanfactory;

import org.springframework.beans.factory.FactoryBean;

import javax.sql.DataSource;

/**
 * 时间 2017/9/26 19:51
 * 说明 ...
 * @author zxj
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
