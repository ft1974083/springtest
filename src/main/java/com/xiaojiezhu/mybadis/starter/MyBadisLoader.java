package com.xiaojiezhu.mybadis.starter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 作者 zxj<br>
 * 时间 2017/9/26 16:40
 * 说明 ...
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Import({AnnotationLoader.class})
public @interface MyBadisLoader {

    /**
     * 对应所有的数据源<br>
     * 示例如下:    dataSourceName : packageNae : mappnigPath                 数据源名 : 包名 : mapping文件路件<br>
     * 如: saas : com.xxx.dao : classpath:mapper/*.xml,classpath:mapper/user/*.xml,      就意味着saas的数据源，全部在com.xxx.dao这个包下，会到classpath/mapper和 classpath/mapper/user下查找xml映射
     * @return
     */
    String[] value();
}
