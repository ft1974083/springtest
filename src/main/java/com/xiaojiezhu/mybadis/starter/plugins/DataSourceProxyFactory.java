package com.xiaojiezhu.mybadis.starter.plugins;

import javax.sql.DataSource;

/**
 * @author zxj<br>
 * 时间 2017/11/1 14:48
 * 说明 插件，数据源的创建，允许创建者返回一个代理层
 */
public interface DataSourceProxyFactory {

    /**
     * 允许通过这个方法来重新构建一个数据源
     * @param dataSource
     * @return
     */
    DataSource buildDataSource(DataSource dataSource);
}
