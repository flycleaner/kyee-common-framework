## Kyee-framework使用指南


1. 模块介绍		
2.	快速使用		
3.	模块详解	
	3.1	 @KyeeApplication		
	3.2  @EnableDataSource 数据源自动配置		
	3.3 @EnableCORS 开启跨域访问过滤器		
	3.4 @EnableKyeeSecurity 启用security模块		
	3.5 @EnableRabbitConfig 启用rabbitmq自动配置

###1.模块介绍
kyee-framework对spring-security,数据源(多数据源配置，flyway,事务)，Cors,RabitMq,redis配置进行了封装，引入了该包以后可以更方便的使用这些功能。   
**框架定义了以下8个注解：**	
1.`@KyeeApplication`	因为kyee-framework的一些配置会与springboot冲突，所以排除springBoot的一些自动配置，使用这个注解取代`@SpringBootApplication`.Intelij配置运行的时候，右上角会有一个叉说不是springboot程序，忽略之。
			
2.`@EnableExceptionManagement`	异常处理，将会自动处理异常并返回给请求。
	
3.`@EnableDataSource`	自动配置数据源（一个或多个），同时会自动配置mybatis,事务处理，仅需在配置文件中按照格式声明即可。如果配置了[flyway](http://flywaydb.org/)参数，会自动迁移同步数据库。
	
4.`@EnableSingleDb`		自动配置单个数据源	
		
5.`@EnableRabbitConfig`	
			
6.`@EnableCORS`		跨域访问控制
			
7.`@EnableKyeeSecurity`		对spring-security进行封装，实现类似于微信的access_token功能。
		
8.`@EnableBaiduPush`	对百度推送进行了简单的配置


###2.快速使用
1.引入依赖。

```xml
<dependency>
    <groupId>com.kyee</groupId>
    <artifactId>framework-core</artifactId>
    <version>0.0.4-SNAPSHOT</version>
</dependency>
```
2.由于框架替代了springboot的部分自动配置，所以应该使用`@KyeeApplication`代替`@SpringBootApplication`
示例:

```java
@KyeeApplicaiton
@EnableKyeeSecurity	#开启security
@EnableDataSource	#开启自动配置数据源
@EnableCORS	#开启跨域访问功能
@ComponentScan("com.kyee.framework.demo.security")
public class SecurityDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SecurityDemoApplication.class, args);
    }

}
```
3.配置
开启了相应的功能以后，需要在`application.yml`(或者`application.properties`)中进行配置。
具体配置见下面详解。

###3.模块详解
本节将围绕定义的8个注解分模块进行讲解
####3.1 @KyeeApplication
这个注解是整个kyee-framework的核心，等价于`@SpringBootApplication`，它会禁用spring-boot的部分自动配置功能，被禁用的springBoot自动配置如下:	
>DataSourceAutoConfiguration.class		
>XADataSourceAutoConfiguration.class		
>DataSourceTransactionManagerAutoConfiguration.class	
>JtaAutoConfiguration.class		
>SecurityAutoConfiguration.class		
>FlywayAutoConfiguration.class	
>RabbitAutoConfiguration.class

示例:

```java
@KyeeApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```
####3.2 @EnableDataSource 数据源自动配置
这个注解会开启数据源自动配置。同时对mybatis,事务，flyway进行了配置。这意味着，第一个数据源将会被配置成首选数据源，第一个数据源的事务也会是首选事务。		
详细配置：

```yaml
kyee:
  datasource:
    config:   #可以写一个或任意多个
      - dbName: hcrm_wechat   #名字必须不同， 第一个为首选数据源
        url: jdbc:mysql://202.120.40.105:9003/hcrm_wechat
        username: kyee
        password: kyee
        basePackage: com.kyee.framework.demo.mutidb.repository.source1  #mybatis扫描路径
        txManager: hcrmManager  #事务管理器  使用事务时必须加上这个名字: @Transactional("hcrmManager")
        flywaySqlLocation: db/hcrm_wechat  #sql脚本路径
      - dbName: localhost
        url: jdbc:mysql://localhost:3306/hcrm_test
        username: root
        password: root
        basePackage: com.kyee.framework.demo.mutidb.repository.source2
        txManager: localManager
        flywaySqlLocation: db/local
#      - 更多继续写下去
```

1.	kyee.datasource.config[*].flywaySqlLocation 为sql脚本路径,sql脚本文件格式为`(DATE)_(MODULE)_(Table)_(ACTION).SQL`, 如`201509171646_USER_t_user_authorities_CREATE.SQL`
设置次参数则启动flyway,注意首次启动flyway必须保证数据库为空，否则会报错。flyway会将自动将对应目录下的sql脚本在对应数据库上执行。DDL语句不能失败不能回滚，DML可以回滚。
2. kyee.datasource.config[*].txManager  为对应数据源的事务管理器，对该数据源操作需要事务时使用注解@Transactional("txManager"), txManager为该属性的值
3. kyee.datasource.config[*].basePackage 为mybatis扫描路径，该路径下的dao的数据源为对应数据源。例如包`com.kyee.framework.demo.mutidb.repository.source2` 下的dao的数据源为 localhost

**注意:**

* `@Autowire private DataSource dataSource;` 将会自动注入第一个数据源
* @Transactional   默认会使用第一个数据源的事务。即操作第一个数据源的函数事务可以直接使用@Transactional
* 如果想要注入其它数据源或者事务，根据配置中写的名字注入。例如上面配置，如想要使用localhost数据源的事务，使用@Transactional("localManager")

***如果想要使用分布式事务，引入包:***

```xml
<dependency>
    <groupId>com.kyee</groupId>
    <artifactId>framework-mutidb-with-jta-transaction</artifactId>
    <version>0.0.4-SNAPSHOT</version>
</dependency>
```
使用注解`@EnableJtaMutiDb`,配置与非分布式多数据源(@EnableDataSource)类似:

```yaml
kyee:
  datasource:
    config:   #可以写一个或任意多个
      - uniqueResourceName: hcrm_wechat   #名字必须不同
        url: jdbc:mysql://202.120.40.105:9003/hcrm_wechat
        dataSourceType: mysql
        username: kyee
        password: kyee
        basePackage: com.kyee.framework.demo.mutidb.jta.repository.source1  #mybatis扫描路径
#        flywaySqlLocation: db/hcrm_wechat  #设置次参数则启动flyway,注意首次启动flyway必须保证数据库为空，否则报错
      - uniqueResourceName: localhost
        dataSourceType: mysql
        url: jdbc:mysql://localhost:3306/hcrm_test
        username: root
        password: root
        basePackage: com.kyee.framework.demo.mutidb.jta.repository.source2
        flywaySqlLocation: db/local
#      - 更多继续写下去
```
示例:[demo](https://github.com/flycleaner/kyee-common-framework/tree/master/framework-demo/framework-demo-mutidb)
###3.3 @EnableCORS 开启跨域访问过滤器

 js会有跨域限制，开启了该过滤器后，默认会允许所有跨域请求。
 
 如要进行配置，示例如下:
 
 ```yaml
 kyee:
 	cors:
 		Access-Control-Allow-Origin:
 			- /api/*
 			- /user/login	
# 			- 更多继续写下去，如果为 * ,则允许所有
 		Access-Control-Allow-Credentials: true
 		Access-Control-Allow-Methods: "POST, GET, OPTIONS, DELETE, PUT"
 		Access-Control-Allow-Headers: "text/html,application/xhtml+xml,application/xml"
 ```


###3.4 @EnableKyeeSecurity 启用security模块

security模块对spring-security进行了个性化定制。采用类似于微信的access_token的认证方式

[==登陆==]：首先需要访问它的`/authToken`接口，发送get请求。如`http://host_ip/authToken?credentials=username/password@domain`。变量credentials即为认证凭证，凭证格式为username/password@domain,返回值

```json
{
  	"success" : true,
   "value" : "ZXlKMWMyVnlibUZ0WlNJNkltdDVaV1VpTENKaGRYUm9iM0pwZEdsbGN5STZXM3NpWVhWM",
   "state" : 0,
   "message" : ""
}
```
value即为token。		

[==访问==]访问其它需要授权的接口需要添加url变量token。如向`xxx_Interface` 发送post请求: `http://host_ip/xxx_Interface?token= ZXlKMWMyVnlibUZ0WlNJNkltdDVaV1VpTENKaGRYUm9iM0pwZEdsbGN5STZXM3NpWVhWM `

[==登出==] token默认有效期7200s，如果未过期想登出访问接口`authToken/logout`，需要附带url变量token。如`http://host_ip/authToken/logout?token= ZXlKMWMyVnlibUZ0WlNJNkltdDVaV1VpTENKaGRYUm9iM0pwZEdsbGN5STZXM3NpWVhWM `

kyee-security提供了会话管理功能，依赖于redis（需要一个redis服务器）。提供会话管理策略:
同一个账号最多可以同时登陆几次，先来的优先(后面的无法登陆)还是后来的优先(前面的被挤出去)，均可以在配置文件中配置

**3.4.1 security详细配置:**

```yaml
kyee:
  security:
    expire: 7200  #token有效期
    token:
      secretKey: fsnsdijnirf987957BHJkkbjdkUI8B&YGIHHUGYIH87guiv&8fovi8ofv86fV  #生成token的私钥，若无此属性每次开启项目会随机生成一个32byte的密钥
    session:  #session管理
      prior: 1 #  挤掉前面的:1; 不让后面的登陆:0
      keyPrefix: "SESSION:"
      maxNum: 1    #同一个账号最多允许1次登陆
    userDetailsService: #如果项目实现了UserDetailsService，则会以项目的为准，否则采用默认的UserDetailsService. 采用默认的UserDetailsService需要配置下面两个参数
      authoritiesByUsernameQuery: "select username,role from t_security_authority where username = ?"    #根据用户名在表t_security_authority中查找用户权限  数据源为首选数据源
      usersByUsernameQuery: "select username,password,enabled from t_security_user where username = ?" #根据用户名在表t_security_user中查找用户信息 数据源为首选数据源
  redis:  #session保存在redis中，配置redis服务器
    host: localhost
#    password: mypassword
    port: 6379
    pool:
      maxIdle: 200
      minIdle: 10
      maxWait: 1000
```

**3.4.2 配置http拦截规则**

继承抽象类`AbstractSecurityConfig`,可以配置拦截规则。		

***示例***

```java
@Configuration
@EnableKyeeSecurity
public class SecurityConfig extends AbstractSecurityConfig{
    @Override
    protected void configureHttpProxy(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                        .antMatchers("/api/*").permitAll()
                .antMatchers("/admin/*").hasRole("ADMIN")
                .antMatchers("/user/*").hasAnyRole("ADMIN","USER")
                //here add whiteList which can be accessed without token
                .anyRequest().authenticated()   //any request will be authenticated
        ;
    }

}
```


**3.4.3 定制UserDetailsService:**		
在验证用户的凭证是否有效的时候，用户可以自己定义验证方式,传入参数为credentials(format: `username/password@domain`,但是不保证用户输入格式是正确的)。默认提供了解析credentials的工具:[PharseUtil](https://github.com/flycleaner/kyee-common-framework/blob/master/framework-core/src/main/java/com/kyee/framework/core/web/security/utils/PharseUtil.java)可以用来解析username,password,domain以及一些可能有用的功能，解析失败返回null，所以可以根据函数返回值是否为null来判断传入参数格式是否正确。

UserDetailsService的函数`loadUserByUsername`返回的UserDetail需要有***正确的用户名，密码，权限***。

kyee-seurity提供了UserDetials的一个实现:[com.kyee.framework.core.web.security.user.User](https://github.com/flycleaner/kyee-common-framework/blob/master/framework-core/src/main/java/com/kyee/framework/core/web/security/user/User.java),用户权限GrantedAuthority的实现:[com.kyee.framework.core.web.security.user.UserAuthority](https://github.com/flycleaner/kyee-common-framework/blob/master/framework-core/src/main/java/com/kyee/framework/core/web/security/user/UserAuthority.java)

***示例***

```java
@Service
public class CloudUserDetailService implements UserDetailsService{

    @Autowired
    private IUserDao userDao;
    @Autowired
    private IHospitalUserService hospitalUserService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user;
        String [] userInfo = PharseUtil.pharseUserInfo(name);
        if(userInfo == null){
            throw new UsernameNotFoundException("用户名密码错误");
        }
        String username = userInfo[0];
        String password = userInfo[1];
        int hospitalId = Integer.parseInt(userInfo[2]);
        if(hospitalUserService.hospitalLogin(username,password,hospitalId)){
            user = userDao.queryUserByName(username,hospitalId);
            if(user!= null){    //如果cloud数据库中有该用户信息
                user.setPassword(password);
                return user;
            }else{  //向cloud中添加该用户，并返回该用户
                ArrayList<UserAuthority> authorities = new ArrayList<>();
                authorities.add(new UserAuthority("ROLE_USER"));
                user = new User(username,password,authorities,new Date());
                user.setDomain(String.valueOf(hospitalId));
                insertUser(user);
                return user;
            }
        }else {
            return null;
        }
    }

    @Transactional
    private void insertUser(User user){
        //插入t_user中
        userDao.insertUser(user);
        //插入t_user_authorities中
        userDao.insertAuthorities(user);
        //插入t_user_config中
        userDao.insertUserConfig(user);
    }
}
```
###3.5 @EnableRabbitMq 开启rabbitmq 自动配置

RabbitMq转发消息的方式是把消息推送到exchange上，queue调用binding的方法绑定到exchange，由exchange来判断如何转发消息。

**Exchange**

Exchange类型有三种：

`Direct`: exchange与queue一对一对应，消息只会发送到指定队列

`Fanout`: exchange采取广播方式，所有与该exchange绑定的队列都会收到消息

`Topic`: exchange会根据routing key来选择符合key的队列来转发消息

`EnableRabbitMq`中采用的是`Fanout`形式，配置文件中采用`FanoutExchange`定义exchange,如果需要采取别的形式的exchange，可以对应使用`DirectExchange`、`FanoutExchange`

####使用方法
在应用程序启动类中添加`@EnableRabbitMq`启用RabbitMq，配置文件中需要添加相关配置信息

```yaml
rabbitMq:
  url: localhost
  port: 5672
  userName: test
  password: test
  exchange: exchange_name
  queueName: cloud
```

**发送端使用方法**

```java
@Autowired
RabbitTemplate rabbitTemplate;

rabbitTemplate.convertAndSend(exchange_name, "", "Hello World");
```

**说明:**
`convertAndSend`方法，`rabbitTemplate`会自动将`Object`序列化后进行发送。第一个参数填写`Exchange`的名称，第二个参数填写`Routing Key`,本注解中使用`Fanout`模式，`Routing Key`为空，第三个参数为消息内容。

**接收端使用方法**

```java
@RabbitListener(queues = "cloud")
public void receiveMessage(Message message)
```

**说明：**
Message为spring.amqp中定义的类，由两部分组成（Body，MessageProperties)获取数据时调用`getBody`方法获得 Byte[]格式消息，反序列化获得`Object`
