package com.xiaojiezhu.mybadis.starter.beanfactory;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.FactoryBean;

/**
 * 作者 zxj<br>
 * 时间 2017/9/26 20:04
 * 说明 ...
 */
public class SqlSessionFactoryBeanFactory implements FactoryBean<SqlSessionFactory> {

    private SqlSessionFactory sqlSessionFactory;

    public SqlSessionFactoryBeanFactory(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public SqlSessionFactory getObject() throws Exception {
        return sqlSessionFactory;
    }

    @Override
    public Class<?> getObjectType() {
        return SqlSessionFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
