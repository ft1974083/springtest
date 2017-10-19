package com.xiaojiezhu.mybadis.starter.core;

/**
 * 作者 zxj<br>
 * 时间 2017/9/26 19:33
 * 说明 ...
 */
public class DataSourceConfig {
    private String dataSourceName;
    private String packageName;
    private String[] xmlMappingPath;
    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private int initialSize;

    private int minIdle;

    private int maxActive;

    public DataSourceConfig(String dataSourceName,String packageName,String[] xmlMappingPath,String url, String username, String password, String driverClassName, int initialSize, int minIdle, int maxActive) {
        this.dataSourceName = dataSourceName;
        this.packageName = packageName;
        this.xmlMappingPath = xmlMappingPath;
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
        this.initialSize = initialSize;
        this.minIdle = minIdle;
        this.maxActive = maxActive;
    }



    public DataSourceConfig(String dataSourceName,String packageName,String[] xmlMappingPath,String url, String username, String password, String driverClassName) {
        this.dataSourceName = dataSourceName;
        this.packageName = packageName;
        this.xmlMappingPath = xmlMappingPath;
        this.url = url;
        this.username = username;
        this.password = password;
        this.driverClassName = driverClassName;
    }

    public DataSourceConfig() {
    }

    public String[] getXmlMappingPath() {
        return xmlMappingPath;
    }

    public void setXmlMappingPath(String[] xmlMappingPath) {
        this.xmlMappingPath = xmlMappingPath;
    }

    public String getUrl() {
        return url;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public int getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(int initialSize) {
        this.initialSize = initialSize;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }


}
