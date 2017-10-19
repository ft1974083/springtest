package com.xiaojiezhu.mybadis.starter.core;

/**
 * 作者 zxj<br>
 * 时间 2017/9/26 16:56
 * 说明 ...
 */
public class LoaderMateData {

    /**
     * 数据源名
     */
    private String dataSourceName;
    /**
     * 接口包名，spring boot在本工程中可不配置
     */
    private String packageName;
    /**
     * mapping配置文件的位置
     */
    private String[] mappingPath;

    public LoaderMateData() {
    }

    public LoaderMateData(String dataSourceName, String packageName, String[] mappingPath) {
        this.dataSourceName = dataSourceName;
        this.packageName = packageName;
        this.mappingPath = mappingPath;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    public String[] getMappingPath() {
        return mappingPath;
    }

    public void setMappingPath(String[] mappingPath) {
        this.mappingPath = mappingPath;
    }

    public String getPackageName() {
        return packageName;
    }


    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String toString() {
        return "LoaderMateData{" +
                "dataSourceName='" + dataSourceName + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }
}
