# jql

一、简介

    jql是一个通过调用方法从而实现操作数据库的java库。

    它并不是大框架，只是在其他orm的基础上封装了一次，目前有两个实现：mybatis-boot 、 jdbc。

    使用的时候，需要导入两个依赖，其一是jql-api，它定义了操作数据库的方法，从而使不同的实现的使用体验是一样的，更换实现不会再有影响。其二就是不同的实现依赖了，依赖哪个实现，他的底层就会变成哪种实现，也可以自己开发一个实现。（对实现依赖深度调整的话，可能就没那么丝滑切换实现了，还得调整）



二、和mybatisplus有什么区别

    目前的想法是这样的，项目不再有pojo和dao结构。

    因为调用数据库使用的是静态方法调用，所以dao就没必要存在了，一个简单的查询是这样的：``Curd.select("user").where("id",1).executeGetOne()``

    返回一个``Map <String,Object>``的结构，查多个则返回list，里面包map，返回的时候直接返回map或者list，也就不需要pojo了。

    当然这样也是会有一些问题的，但对我来说是益大于弊的。比如某些工具类，他可能默认的参数传递是用pojo的，要用还需要改造，更麻烦了些。

    他的好处是什么呢？ 更关注逻辑吧，更清晰明了。这样一个小模块就只需要控制器和服务了，参数传递，需要什么传什么。

    不然直接传个pojo，根本不知道这个方法他需要什么不需要什么，只是一股脑扔进去，有时候还各种new只有几个参数的pojo。因为一个模块有时候有一些不属于数据库的参数，于是有的给pojo加字段，有的用vo，加的时候好加，可是删除的时候，谁知道某个角落还在调用呢，项目大了，就非常乱了。

    光是建模块的时候，新的结构只需要控制器和服务，而之前呢，还需要pojo，dao，xml。模块多了，都是一些重复的工作，很是难受，干这些事的时间，可以干点别的事了。

    相比MP，改的更彻底了

三、用法展示

    增加：`Curd.insert("表名").add("name","张三").add("age","15").execute()`

    删除：`Curd.delete("表名").where("id",1).execute()`

    修改：`Curd.update("表名").set("name","李四").where("id",12).execute()`

    查询：`Curd.select("表名").where("id",13).execute()`

    说明： 
        1、表名后面有一个可选参数，是空处理参数，对于null值，是查还是不查，默认忽略空(null和空字符串) 
        2、现在基本上sql的大部分操作都实现了

    直接sql语句： Sql.select("select * from user where id = ? ",1)  说明： 增删改查有不同的方法，参数是预编译的，以？当作参数占位符


四、实现

    mybatis-boot :  https://github.com/dawnflyc/JqlMybatisBoot

    jdbc: https://github.com/dawnflyc/JqlJdbc

五、在springboot环境下如何开始使用

    1、pom导入此包以及mybatis-boot实现包
    2、组件扫描包(ComponentScan) com.dawnflyc.jqlmb
    3、数据库涉及时间需要在配置文件里面加上时间格式化
        spring:      
            jackson:
                date-format: yyyy-MM-dd HH:mm:ss
                time-zone: Asia/Shanghai
    4、(可选)打印sql语句以方便调试
        在Main方法中修改配置文件 ConfigManage.getConfig().setPrintSql(true);
        然后在配置文件中增加日志输出
        logging:
            com.dawnflyc: debug
    5、(可选)打印执行时间
        在Main方法中修改配置文件 ConfigManage.getConfig().setPrintRuntime(true);

六、如何导入Maven

    因为不会搞maven仓库，所以只能用本地导入
    1、将jar包放到项目的一个地方 比如根目录下的libs目录
    2、pom增加以下内容
        <dependency>
        <groupId>com.dawnflyc</groupId>
        <artifactId>JqlApi</artifactId>
        <version>0.1.1.20</version>
        <scope>system</scope>
        <systemPath>${project.basedir}/libs/JqlApi-0.1.1.20.jar</systemPath>
        </dependency>
    3、实现包也是这样导入
    4、在spring插件 spring-boot-maven-plugin 添加一句
        <includeSystemScope>true</includeSystemScope>
        可以让本地导入的包在打包的时候打进包里，不然部署的时候会找不到

七、说明

    1、版本对应
        jql的版本示例：0.1.1.20  0.1.1是api的兼容版本 20则api下的版本
        实现包的版本示例 0.1.0-api0.1.1  0.1.0则是实现包自己的版本 后面的是兼容jql的版本

作者是一介菜鸟，有什么问题、瑕疵、没考虑到的地方、更好的方案，联系作者一起交流，可以为Jql贡献属于自己的一份力。

