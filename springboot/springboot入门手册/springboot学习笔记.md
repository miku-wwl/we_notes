springboot 学习总结一下。《SpringBoot 篇》01.Springboot 超详细入门（基础篇）

读取全部数据
​ 读取单一数据可以解决读取数据的问题，但是如果定义的数据量过大，这么一个一个书写肯定会累死人的，SpringBoot 提供了一个对象，能够把所有的数据都封装到这一个对象中，这个对象叫做 Environment，使用自动装配注解可以将所有的 yaml 数据封装到这个对象中

当前使用的 SpringBoot 版本是 2.5.4，对应的坐标设置中 Mysql 驱动使用的是 8x 版本。当 SpringBoot2.4.3（不含）版本之前会出现一个小 BUG，就是 MySQL 驱动升级到 8 以后要求强制配置时区，如果不设置会出问题。解决方案很简单，驱动 url 上面添加上对应设置就行了

而这个坐标的名字书写比较特殊，是第三方技术名称在前，boot 和 starter 在后。此处简单提一下命名规范，后期原理篇会再详细讲解

《SpringBoot 篇》03.超详细属性配置介绍
​ SpringBoot 提供了灵活的配置方式，如果你发现你的项目中有个别属性需要重新配置，可以使用临时属性的方式快速修改某些配置。方法也特别简单，在启动的时候添加上对应参数就可以了。’

(2.)开发环境中使用临时属性
​ 临时使用目前是有了，但是上线的时候通过命令行输入的临时属性必须是正确的啊，那这些属性配置值我们必须在开发环境中测试好才行。下面说一下开发环境中如何使用临时属性，其实就是 Idea 界面下如何操作了。

​a.第一种方式：

2.配置文件分类
​ SpringBoot 提供了配置文件和临时属性的方式来对程序进行配置。前面一直说的是临时属性，这一节要说说配置文件了。其实这个配置文件我们一直在使用，只不过我们用的是 SpringBoot 提供的 4 级配置文件中的其中一个级别。4 个级别分别是：

《SpringBoot 篇》04.超详细多环境开发介绍

2.多环境开发（yaml 多文件版）
​ 将所有的配置都放在一个配置文件中，尤其是每一

是上面的设置也有一个问题，比如我要切换 dev 环境为 pro 时，include 也要修改。因为 include 属性只能使用一次，这就比较麻烦了。SpringBoot 从 2.4 版开始使用 group 属性替代 includ

以将日志记录下来了，但是面对线上的复杂情况，一个文件记录肯定是不能够满足运维要求的。
通常会每天记录日志文件，同时为了便于维护，还要限制每个日志

《SpringBoot 篇》06.超详细热部署教学

《SpringBoot 篇》07.@ConfigurationProperties 注解实现第三方 bean 加载属性

3.使用@EnableConfigurationProperties 注解，简便开发

Spring Boot 常用注解（一） - 声明 Bean 的注解
https://blog.csdn.net/lipinganq/article/details/79155072#:~:text=Spring%E6%8F%90%E4%BE%9BXM#:~:text=Spring%E6%8F%90%E4%BE%9BXM

《SpringBoot 篇》08.属性绑定规则及计量单位绑定
实际上是 springboot 进行编程时人性化设计的一种体现，即配置文件中的命名格式与变量名的命名格式可以进行格式上的最大化兼容

《SpringBoot 篇》09.Spring Data JPA 简介与 SpringBoot 整合超详细教学
说明： 在 application.properties 中填写一下配置,这里数据库，用户名，密码 换成自己的

《SpringBoot 篇》10.JPQL 超详细介绍与 JPA 命名规则

配置 Schema Update
确保你的 Spring Boot 应用配置正确，以便在启动时更新数据库模式：

properties
深色版本
spring.jpa.hibernate.ddl-auto=update

（3）原生 SQL
说明： 如果不习惯 JQPL 可以使用原生 SQL，这就和平时写 SQL 一样就不多介绍了

2.命名规则表
说明： 命名规则按下面这个表就可以了

《SpringBoot 篇》11.JPA 常用注解只需一个表
