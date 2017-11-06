package com.xiaojiezhu.mybadis.starter.register;

/**
 * @author zxj<br>
 * 时间 2017/11/4 13:12
 * 说明 ...
 */
public class ObjectNameCreator {

    public static String genBeanName(Object object){
        String simpleName = object.getClass().getSimpleName();
        String first = simpleName.substring(0, 1);
        first = first.toLowerCase();
        return first + simpleName.substring(1);
    }

}
