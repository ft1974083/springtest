# 只需要一行注解，就可以配置mybadis的多数据源
### 这是一个基于spring boot 的mybadis的starter
> 支持多个数据源

使用方法很简单，先定义一些配置文件，放到application.yml中
````
mysql:
  server:
    saas:
      url: "jdbc:mysql://localhost:3306/saas?useUnicode=true&characterEncoding=utf8"
      username: root
      password: 123
      driverClassName: com.mysql.jdbc.Driver
      initialSize: 1  #初始化大小
      minIdle: 1  #空闲连接池的大小
      maxActive: 50 #最大激活数量
    saas2:
      url: "jdbc:mysql://localhost:3306/saas2?useUnicode=true&characterEncoding=utf8"
      username: root
      password: 123
      driverClassName: com.mysql.jdbc.Driver
      initialSize: 1  #初始化大小
      minIdle: 1  #空闲连接池的大小
      maxActive: 50 #最大激活数量
````


使用方法如下
```java
@MyBadisLoader({"saas : com.llc.admin.web.dao.saas" , "saas2 : com.llc.admin.web.dao.saas2"})
@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);
    }
}
```
> 上面的注解中 saas是上方配置文件，数据源的名称，冒号后面的是分号
> 注解中接收的是一个数组，所以支持多个数据源，除此不需要任何代码就可以使用