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
     * 包名
     */
    private String packageName;

    public LoaderMateData() {
    }

    public LoaderMateData(String dataSourceName, String packageName) {
        this.dataSourceName = dataSourceName;
        this.packageName = packageName;
    }

    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
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
