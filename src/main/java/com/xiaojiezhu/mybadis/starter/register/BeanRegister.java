package com.xiaojiezhu.mybadis.starter.register;

/**
 * @author zxj<br>
 * 时间 2017/11/4 12:59
 * 说明 spring的bean注册器
 */
public interface BeanRegister {

    /**
     * 注册一个对象到spring，按照spring规范生成对象名字，并且是单例的对象
     * @param object 对象
     * @throws Exception
     */
    void register(Object object)throws Exception;

    /**
     * 注册一个对象到spring，单例
     * @param object 对象
     * @param beanName 在spring中的对象名称
     * @throws Exception
     */
    void register(Object object,String beanName)throws Exception;

    /**
     * 注册一个对象到spring中
     * @param object 对象
     * @param beanName 在spring中的对象名称
     * @param singleton 是否单例
     * @throws Exception
     */
    void register(Object object,String beanName,boolean singleton)throws Exception;
}
