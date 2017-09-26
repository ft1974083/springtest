package com.xiaojiezhu.mybadis.starter.beanfactory;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.FactoryBean;

/**
 * 作者 zxj<br>
 * 时间 2017/9/26 20:13
 * 说明 ...
 */
public class MapperScannerConfigurerBeanFactory implements FactoryBean<MapperScannerConfigurer> {
    private MapperScannerConfigurer mapperScannerConfigurer;

    public MapperScannerConfigurerBeanFactory(MapperScannerConfigurer mapperScannerConfigurer) {
        this.mapperScannerConfigurer = mapperScannerConfigurer;
    }

    @Override
    public MapperScannerConfigurer getObject() throws Exception {
        return mapperScannerConfigurer;
    }

    @Override
    public Class<?> getObjectType() {
        return MapperScannerConfigurer.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
