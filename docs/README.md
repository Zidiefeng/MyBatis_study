# 【狂】MyBatis

- [ ]  [P11、什么是MyBatis26:55](https://www.bilibili.com/video/BV1NE411Q7Nx?p=1)

## 开篇

### 环境

- JDK1.8
- MySQL 5.7(8.0)
- maven 3.6.1
- IDEA

### 需要了解的知识点

- JDBC
- MySQL
- Java基础，封装继承，工具类
- Maven 架构，模块
- junit

### 框架

- MyBatis是SSM的M
- 框架设置配置文件
- 最好的方式，看官网文档

    [mybatis - MyBatis 3 | Introduction](https://mybatis.org/mybatis-3/)

## MyBatis简介

### 什么是MyBatis

- MyBatis is a first class **persistence framework（持久层框架）**
- with support for **custom** SQL, stored procedures and advanced mappings.
- MyBatis **eliminates almost all of the JDBC** code and **manual setting** of parameters and retrieval of results.
- MyBatis **can use simple XML or Annotations for configuration** and map primitives, Map interfaces and Java POJOs (Plain Old Java Objects) to database records.
- MyBatis是iBatis开源项目，2010，由apache software foundation迁移到了google code，改名MyBatis，2013.11迁移到了github

### 如何获得MyBatis

- maven仓库

    ```xml
    <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.6</version>
    </dependency>
    ```

- github：MyBatis其实就是一个maven项目，可以从github下载源码

    [mybatis/mybatis-3](https://github.com/mybatis/mybatis-3)

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled.png)

### 持久化

- 数据持久化：是将程序的data在持久状态和瞬时状态转化的过程
- 持久状态：
    - 存到数据库里
    - io（存到文件中，但是这个很浪费资源）
- 瞬时状态：内存断电即失(运行的data都在内存里)

### 为什么需要持久化

- 有一些对象不能丢失，存储起来
- 内存比较贵，比硬盘贵很多

### 持久层

- 层：
    - DAO层-持久化层
    - service层：业务
    - controller层：接受用户请求，转成业务去做
    - 界限明显
- MyBatis是持久层，完成持久化工作的代码块
- 持久化是一个动作，持久层是一个职能层概念

### 为什么需要MyBatis

- 帮助程序员将data存到db中
- 因为传统的JDBC代码太复杂了，除了MyBatis框架简化代码
- 框架帮助自动化
- 思想
    - 不用MyBatis也可以，因为只是一个框架，学他就是为了容易上手
    - 技术没有高低之分，使用人有高低之分
- 优点：
    - 简单易学
    - 灵活
    - sql和代码分离，提供可维护性
    - 提供mapping标签，支持与DB的orm字段关系映射
    - 提供对象关系mapping标签，支持对象关系组建维护
    - 提供xml标签，支持编写动态sql
- 最重要的：用的人多

- [ ]  [P22、第一个Mybatis程序1:09:46](https://www.bilibili.com/video/BV1NE411Q7Nx?p=2)

## 第一个MyBatis程序

### 第一个MyBatis程序-思路

- 环境搭建
- 导入MyBatis
- 编写代码
- 测试

### 第一步：搭建环境

- 搭建DB

    ```sql
    CREATE DATABASE `mybatis`;
    use `mybatis`;

    create table `user`(
    	`id` int(20) not null primary key,
        `name` varchar(30) default null,
        `pwd` varchar(30) default null
    )engine = InnoDB default charset=utf8;

    insert into `user` (`id`,`name`,`pwd`) values 
    (1,'kaitan','123456'),
    (2,'zhangsan','123456'),
    (3,'lisi','123456');
    ```

- 新建项目
    1. 普通maven项目
    2. check一下maven的版本 （可能idea自动切换回默认版本）

        ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%201.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%201.png)

    3. 删除src dir

        相当于一个parent program，中间可以建module

    4. 导入dependencies （pom.xml文件）

        ```sql
        <?xml version="1.0" encoding="UTF-8"?>
        <project xmlns="http://maven.apache.org/POM/4.0.0"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
            <modelVersion>4.0.0</modelVersion>

            <!--parent project-->
            <groupId>org.example</groupId>
            <artifactId>MyBatis_study</artifactId>
            <version>1.0-SNAPSHOT</version>

            <!--导入dependencies-->
            <dependencies>
                <!--mysql-->
                <dependency>
                    <groupId>mysql</groupId>
                    <artifactId>mysql-connector-java</artifactId>
                    <version>5.1.47</version>
                </dependency>
                <!--mybatis-->
                <dependency>
                    <groupId>org.mybatis</groupId>
                    <artifactId>mybatis</artifactId>
                    <version>3.5.6</version>
                </dependency>
                <!--junit-->
                <dependency>
                    <groupId>junit</groupId>
                    <artifactId>junit</artifactId>
                    <version>4.11</version>
                </dependency>
            </dependencies>

        </project>
        ```

### 第二步-创建一个module

- 普通maven项目，子项目不用导包了，因为parent project中已经导了
    - 建立的module会自动更新在pom.xml中

        ```xml
        <!--parent project-->
            <groupId>org.example</groupId>
            <artifactId>MyBatis_study</artifactId>
            <packaging>pom</packaging>
            <version>1.0-SNAPSHOT</version>
            <modules>
                <module>mybatis-01</module>
            </modules>
        ```

- **编写mybatis核心配置文件**

    [mybatis - MyBatis 3 | Getting started](https://mybatis.org/mybatis-3/getting-started.html)

    - 在resources中创建mybatis-config.xml文件，从官网例子复制过来

        ```xml
        <?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE configuration
                PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
                "http://mybatis.org/dtd/mybatis-3-config.dtd">
        <configuration>
            <environments default="development">
                <environment id="development">
                    <transactionManager type="JDBC"/>
                    <dataSource type="POOLED">
                        <property name="driver" value="${driver}"/>
                        <property name="url" value="${url}"/>
                        <property name="username" value="${username}"/>
                        <property name="password" value="${password}"/>
                    </dataSource>
                </environment>
            </environments>
        </configuration>
        ```

    - 这里可以配置多套环境，然后设置默认环境

        ```xml
        <!--核心配置文件-->
        <configuration>
            <environments default="development">
                <environment id="development">
                    <transactionManager type="JDBC"/>
                    <dataSource type="POOLED">
                        <property name="driver" value="${driver}"/>
                        <property name="url" value="${url}"/>
                        <property name="username" value="${username}"/>
                        <property name="password" value="${password}"/>
                    </dataSource>
                </environment>
                <environment id="test">
                    <transactionManager type="JDBC"/>
                    <dataSource type="POOLED">
                        <property name="driver" value="${driver}"/>
                        <property name="url" value="${url}"/>
                        <property name="username" value="${username}"/>
                        <property name="password" value="${password}"/>
                    </dataSource>
                </environment>
            </environments>
        </configuration>
        ```

    - 填写config，可以依照intellij DB
        - url:
            - 指定schema: mybatis
            - 安全连接SSL
            - xml中的`&`是`&amp;`
            - useUnicode和characterEncoding帮助输出中文
            - 之后可以再指定时区

            ```xml
            jdbc:mysql://localhost:3306/mybatis?userSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8
            ```

            ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%202.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%202.png)

        - 完整版

            ```xml
            <?xml version="1.0" encoding="UTF-8" ?>
            <!DOCTYPE configuration
                    PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
                    "http://mybatis.org/dtd/mybatis-3-config.dtd">
            <!--核心配置文件-->
            <configuration>
                <environments default="development">
                    <environment id="development">
                        <transactionManager type="JDBC"/>
                        <dataSource type="POOLED">
                            <property name="driver" value="com.mysql.jdbc.Driver"/>
                            <property name="url" value="jdbc:mysql://localhost:3306/mybatis?userSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
                            <property name="username" value="root"/>
                            <property name="password" value="root"/>
                        </dataSource>
                    </environment>
                </environments>
            </configuration>
            ```

- **编写mybatis工具类**
    - 理解
        - 每个基于MyBatis的app都是以一个SqlSessionFactory的instance为核心的（工厂模式）
        - SqlSessionFactory可以通过SqlSessionFactoryBuilder（建造者模式）获得，SqlSessionFactoryBuilder可以通过xml config file中构建出来SqlSessionFactory instance
        - 我们可以从SqlSessionFactory中获得SqlSession, SqlSession包含全部 面向DB执行SQL命令所需的所有方法，可以同过SqlSession instance来执行已mapping 的SQL语句
        - SqlSession类似PreparedStatement
    - 官网给了三行代码来实现

        [mybatis - MyBatis 3 | Getting started](https://mybatis.org/mybatis-3/getting-started.html)

        ```java
        String resource = "org/mybatis/example/mybatis-config.xml"; //config file
        InputStream inputStream = Resources.getResourceAsStream(resource); //通过config file
        SqlSessionFactory sqlSessionFactory =
          new SqlSessionFactoryBuilder().build(inputStream);
        ```

    - 读取resource文件，占用io资源，我们可以把它写成工具类

        package com.kaitan.utils;

        ```java
        public class MybatisUtils {
            private static SqlSessionFactory sqlSessionFactory;
            static {
                //获取sqlSessionFactory对象
                try {
                    String resource = "mybatis-config.xml"; //config file
                    InputStream inputStream = Resources.getResourceAsStream(resource); //通过config file
                    sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public static SqlSession getSqlSession(){
        /*        SqlSession sqlSession = sqlSessionFactory.openSession();
                return sqlSession;*/
                return sqlSessionFactory.openSession();
            }
        }
        ```

### 第三步：编写代码

- POJO实体类

    ```java
    package com.kaitan.pojo;

    public class User {
        private int id;
        private String name;
        private String pwd;

        public User() {
        }

        public User(int id, String name, String pwd) {
            this.id = id;
            this.name = name;
            this.pwd = pwd;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", pwd='" + pwd + '\'' +
                    '}';
        }
    }
    ```

- 原本的操作方法是写Dao接口，Dao实现类

    ```java
    package com.kaitan.dao;
    import com.kaitan.pojo.User;
    import java.util.List;

    public interface UserDao {
        List<User> getUserList();
    }
    ```

    ```java
    package com.kaitan.dao;
    import com.kaitan.pojo.User;
    import java.util.List;

    public class UserDaoImp implements UserDao {
        @Override
        public List<User> getUserList() {
            return null;
        }
    }
    ```

- MyBatis去掉了这种手动配置，只需要conifg文件即可
    - 如官网上的例子

        [mybatis - MyBatis 3 | Getting started](https://mybatis.org/mybatis-3/getting-started.html)

        ```xml
        <?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
        <mapper namespace="org.mybatis.example.BlogMapper">
          <select id="selectBlog" resultType="Blog">
            select * from Blog where id = #{id}
          </select>
        </mapper>
        ```

    - 把这个代码放到UserMapper.xml中作为模板
        - 上面部分是header
        - 下面可以来写sql代码了
    - 不用UserDaoImp了，直接用Mapper配置文件（UserMapper.xml）

        ```xml
        <?xml version="1.0" encoding="UTF-8" ?>
        <!DOCTYPE mapper
                PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
                "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

        <!--namespace-绑定一个对应的Dao接口或者称为Mapper接口，即实现了这个接口-->
        <mapper namespace="com.kaitan.dao.UserDao">
            <select id="getUserList" resultType="com.kaitan.pojo.User">
                select * from mybatis.user
            </select>
        </mapper>
        ```

    - 理解：
        - 之前使用实体类UserDaoImp继承UserDao接口，现在是在config中的namespace，指定到一个UserDao接口（这里称为Mapper接口）

            ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%203.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%203.png)

        - 之前是重写接口中的方法，这里是用不同方法对应的标签，比如select，进行书写
        - 之前重写的方法返回结果集，MyBatis则是通过添加标签里的label来确认返回内容，如`resultType="com.kaitan.pojo.User"` （写全名），虽然这里应该返回`List<User>` ,但是只填泛型`User` !
            - resultMap 返回多个
            - resultType 返回一个，常用

                ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%204.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%204.png)

### 第四步：测试

- 一般在test/java中建立测试文件，一般test的结构和主文件相互对应

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%205.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%205.png)

- 测试代码
    - 两种方法对比（官网）

        ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%206.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%206.png)

    - 第一种方法

        ```java
        public class UserDaoTest {
            @Test
            public void test(){
                //获得sqlSession对象
                SqlSession sqlSession = MybatisUtils.getSqlSession();

                //执行sql

                /*第一种方法*/
                //这里，我们的mapper配置文件绑定了UserDao接口，那么调用这个接口的class，
                    //即UserDao.class,则可以获得mapper,来执行里面的方法了
                    //理解，UserMapper.xml只是UserDao接口的实现类
                    //拿到UserDao类就好了
                UserDao mapper = sqlSession.getMapper(UserDao.class);
                List<User> userList = mapper.getUserList();
                for (User user : userList) {
                    System.out.println(user);
                }

                sqlSession.close();

            }
        }
        ```

    - 第二种方法
        - 通过sqlSession中的selectList,然后指定对应方法
        - 如果返回的是一个，对应selectOne方法，（根据方法的返回值去返回）
        - 过时了，不推荐

        ```java
        public class UserDaoTest {
            @Test
            public void test(){
                //获得sqlSession对象
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                /*第二种方法*/
                //过时了,不推荐
                //
                List<User> userList = sqlSession.selectList("com.kaitan.dao.UserDao.getUserList");

                for (User user : userList) {
                    System.out.println(user);
                }

                sqlSession.close();

            }
        }
        ```

- 常见报错
    1. 忘记注册mapper

        ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%207.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%207.png)

        - 解决方法
            - 在核心配置文件mybatis-config.xml中要配置所有mapper

                ```xml
                <mappers>
                    <mapper resource="com/kaitan/dao/UserMapper.xml"></mapper>
                </mappers>
                ```

            - 完整版mybatis-config.xml

                每一个mapper.xml文件都需要在myBatis核心配置文件中注册

                ```xml
                <?xml version="1.0" encoding="UTF-8" ?>
                <!DOCTYPE configuration
                        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
                        "http://mybatis.org/dtd/mybatis-3-config.dtd">
                <!--核心配置文件-->
                <configuration>
                    <environments default="development">
                        <environment id="development">
                            <transactionManager type="JDBC"/>
                            <dataSource type="POOLED">
                                <property name="driver" value="com.mysql.jdbc.Driver"/>
                                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?userSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
                                <property name="username" value="root"/>
                                <property name="password" value="root"/>
                            </dataSource>
                        </environment>
                    </environments>

                    <!--每一个mapper.xml文件都需要在myBatis核心配置文件中注册-->
                    <mappers>
                        <mapper resource="com/kaitan/dao/UserMapper.xml"></mapper>
                    </mappers>
                </configuration>
                ```

    2. 找不到resource下的mapper config xml文件
        - 如：Could not find resource com/kaitan/dao/UserMapper.xml
        - 问题所在
            - 因为maven默认是把配置文件放进resources，但现在放到了java目录中，需要手动在maven中配置资源过滤
            - 因为目前target文件中的 xml文件没有自动生成过来（一次次手动复制过来不可取）

                ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%208.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%208.png)

        - 解决办法
            - 将这部分代码放到主project中的 pom.xml文件中（project标签中）

                ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%209.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%209.png)

                如果不行，可以把第一个resource删掉，因为已经有了

                ```xml
                <!--（pom.xml）在build中配置resources,来防止我们资源导出失败的问题-->
                <build>
                    <resources>
                        <resource>
                            <directory>src/main/resources</directory>
                            <excludes>
                                <exclude>**/*.properties</exclude>
                                <exclude>**/*.xml</exclude>
                            </excludes>
                            <filtering>false</filtering>
                        </resource>
                        <resource>
                            <directory>src/main/java</directory>
                            <includes>
                                <include>**/*.properties</include>
                                <include>**/*.xml</include>
                            </includes>
                            <filtering>false</filtering>
                        </resource>
                    </resources>
                </build>
                ```

            - 注意，一定要重新加载maven dependencies

### 测试阶段错误总结

- 配置文件mapper没有注册进mybatis主要配置文件
- 接口绑定错误（mapper文件配置时候连接了错误的dao或称mapper接口）
- 方法名称不对
- 返回类型不对
- Maven导出资源问题

### 第一个示例mybatis项目回顾

- 导入包mysql, mybatis
- 为了实现SqlSessionFactory对象，将那3行代码做成工具类，对应官网指导
- （根据官网指导）设定mybatis主配置文件，配置数据库连接，可以先删掉mapper
- 对照DB写实体类pojo
- 写Dao接口，或者称Mapper接口
- 写mapper config xml配置文件（相当于之前的实体类）
    - namespace指定接口
    - select标签代替重写方法
    - id代表方法
    - body就是sql语句
    - 返回值类型resultType写泛型全称类
- 最后test

### 几个对象的理解

- SqlSessionFactoryBuilder只用来生成 SqlSessionFactory

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2010.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2010.png)

- SqlSession

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2011.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2011.png)

    - 官方建议放进finally千万不要忘记关闭资源，类似

        ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2012.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2012.png)

- [ ]  [P33、增删改查实现24:29](https://www.bilibili.com/video/BV1NE411Q7Nx?p=3)

## CRUD

### namespace

- 将UserDao改成UserMapper（interface）
- 做UserMapperTest
- 必须在UserMapper.xml中注册mapper,否则运行不了，其中mapper的namespace必须和interface名称一致

### select标签

- UserMapper.xml中的标签select对应select sql query
- 重点的 label
    - id：对应namespace中的方法名
    - resultType：sql语句执行的返回值，除了class以外，都是基本类型
    - parameterType：code如果涉及参数，需要设置

### 添加一个select方法的流程（example）

1. 在interface中增加方法`getUserById`

    ```java
    public interface UserMapper {
        //查询全部用户
        List<User> getUserList();

        //根据id查询用户
        User getUserById(int id);
    }
    ```

2. 在UserMapper.xml中添加mapper的方法
    - `parameterType="int"` 指代id这个参数
    - `#{id}` 表示input parameter id
    - `id="getUserById"` 表示方法
    - `resultType="com.kaitan.pojo.User"` 表示返回值

    ```xml
    <mapper namespace="com.kaitan.dao.UserMapper">
    <select id="getUserById" parameterType="int" resultType="com.kaitan.pojo.User">
        select * from mybatis.user where id=#{id}
    </select>
    </mapper>
    ```

3. 写test case
    - 下面两行是所有db都需要的，固定代码

        `SqlSession sqlSession = MybatisUtils.getSqlSession();
        sqlSession.close();`

    ```java
    public class UserMapperTest {
        @Test
        public void getUserById(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();//固定代码
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user = mapper.getUserById(1);
            System.out.println(user);

            sqlSession.close();//固定代码
        }
    }
    ```

### 添加一个insert方法的流程（example）

1. 在interface中增加方法

    ```java
    public interface UserMapper {
    		int addUser(User user);
    }
    ```

2. 在UserMapper.xml中添加mapper的方法
    - parameterType如果是class，class对象中的属性可以直接取出来!
        - 这里，`com.kaitan.pojo.User` 的属性id,name,pwd直接用`#{attribute}`形式取出，因为在label绑定了input parameter class
    - `parameterType="com.kaitan.pojo.User"` 表示input parameter User，要写全称

    ```xml
    <mapper namespace="com.kaitan.dao.UserMapper">

    <!--对象中的属性可以直接取出来，User的属性id,name,pwd直接可用，因为绑定了pojo的class-->
    <insert id="addUser" parameterType="com.kaitan.pojo.User">
        insert into mybatis.user (id,name,pwd) values(#{id},#{name},#{pwd});
    </insert>
    </mapper>
    ```

3. 写test case
    - 增删改必须commit 提交transaction！否则无法生效（但也不会报错）
    - 这里，使用`sqlSession.commit();` 提交transaction

    ```java
    public class UserMapperTest {
    		@Test
    		public void addUser(){
    		        SqlSession sqlSession = MybatisUtils.getSqlSession();//固定代码
    		        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
    		        int result = mapper.addUser(new User(4,"妮妮","123123123"));
    		        sqlSession.commit();
    		        sqlSession.close();//固定代码
    		    }
    }
    ```

### 添加一个update方法的流程（example）

1. 在interface中增加方法

    ```java
    public interface UserMapper {
    		//update一个用户
        int updateUser(User user);
    }
    ```

2. 在UserMapper.xml中添加mapper的方法
    - parameterType如果是class，class对象中的属性可以直接取出来!

    ```xml
    <mapper namespace="com.kaitan.dao.UserMapper">
    		<update id="updateUser" parameterType="com.kaitan.pojo.User">
            update mybatis.user set name=#{name}, pwd=#{pwd} where id = #{id};
        </update>
    </mapper>
    ```

3. 写test case
    - 增删改必须commit 提交transaction！否则无法生效（但也不会报错）
    - 这里，使用`sqlSession.commit();` 提交transaction

    ```java
    public class UserMapperTest {
    		@Test
        public void updateUser(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();//固定代码
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int result = mapper.updateUser(new User(4,"妮妮","000000"));
            sqlSession.commit();
            sqlSession.close();//固定代码
        }
    }
    ```

### 添加一个delete方法的流程（example）

1. 在interface中增加方法

    ```java
    public interface UserMapper {
    		//delete一个用户
        int deleteUser(int id);
    }
    ```

2. 在UserMapper.xml中添加mapper的方法
    - parameterType如果是单变量，比如int id，这个变量在标签内也可以

    ```xml
    <mapper namespace="com.kaitan.dao.UserMapper">
    		<delete id="deleteUser" parameterType="int">
            delete from mybatis.user where id = #{id};
        </delete>
    </mapper>
    ```

3. 写test case
    - 增删改必须commit 提交transaction！否则无法生效（但也不会报错）

    ```java
    public class UserMapperTest {
    		@Test
        public void deleteUser(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();//固定代码
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            int result = mapper.deleteUser(4);
            sqlSession.commit();
            sqlSession.close();//固定代码
        }
    }
    ```

- [ ]  [P44、错误排查指导08:57](https://www.bilibili.com/video/BV1NE411Q7Nx?p=4)

### 常见错误排查

读java报错从下往上读

1. namespace是`.`不是`/`
2. 标签匹配错误：比如insert的标签写成了select，报错

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2013.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2013.png)

3. 初始config文件写错了

    这里的resource只能用`/` 不能用`.`

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2014.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2014.png)

4. 主config文件的 db配置错误
5. 空指针报错-多处声明同一个变量，导致return的变量其实是外面的第一个，null

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2015.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2015.png)

6. xml文件（UserMapper.xml）中存在中文乱码问题
7. maven资源没有导出（记得在pom.xml设置 ：java下的dir中的xml，properties可以识别）

- [ ]  [P55、Map和模糊查询拓展23:49](https://www.bilibili.com/video/BV1NE411Q7Nx?p=5)

## Map

### 使用map进行insert（example）

1. 在interface中增加方法

    ```java
    public interface UserMapper {
    		//使用map add用户
        int addUser2(Map<String,Object> map);		
    }
    ```

2. 在UserMapper.xml中添加mapper的方法
    - map作为parameter,其中的key直接可以用`#{}` 导入

    ```xml
    <mapper namespace="com.kaitan.dao.UserMapper">
    		<!--map只需要跟key对应就好了-->
        <insert id="addUser2" parameterType="map">
            insert into mybatis.user (id,name,pwd) values(#{user_id},#{user_name},#{user_pwd});
        </insert>
    </mapper>
    ```

3. 写test case

    ```java
    public class UserMapperTest {
    		@Test
        public void addUser2(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            Map<String, Object> map = new HashMap<>();
            map.put("user_id",5);
            map.put("user_name","hello");
            map.put("user_pwd",111111);

            mapper.addUser2(map);
            sqlSession.commit();
            sqlSession.close();
        }
    }
    ```

### map和实体类class作为Input parameter的差别

- 使用dojo class来传参，在new class 的时候，比如new User(id, name ,...)，需要把所有的参数都写上，非常麻烦
- 使用map传参，只需要在new map的时候，传想改变更的column即可，简单很多（比如update一条数据的时候）
- 结论：
    - 若实体类中的attribute（table中的column）过多，我们应该使用map而不是实体类，但这不是正规方法（野路子）
    - 甚至可以全部用map去做，不用实体类
    - 有人说：
        - map不太好控制map的值的范围，不好
        - 控制层传map会被打哈哈
        - xml里的名称也要跟key对应
        - 传输数据非法会报错

### 小tip

- code block 全部收起来：`control`+`shift`+`-`
- code block 全部展开：`control`+`shift`+`+`

### 使用map进行select（example）

1. 在interface中增加方法

    ```java
    public interface UserMapper {
    		User getUserById2(Map<String, Object> map);
    }
    ```

2. 在UserMapper.xml中添加mapper的方法
    - map作为parameter,其中的key直接可以用`#{}` 导入

    ```xml
    <mapper namespace="com.kaitan.dao.UserMapper">
    		<select id="getUserById2" parameterType="map" resultType="com.kaitan.pojo.User">
            select * from mybatis.user where id=#{user_id}
        </select>
    </mapper>
    ```

3. 写test case

    ```java
    public class UserMapperTest {
    		@Test
        public void getUserById2(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            Map<String, Object> map = new HashMap<>();
            map.put("user_id",2);
            User user = mapper.getUserById2(map);
            System.out.println(user);
            sqlSession.close();
        }
    }
    ```

### parameter总结

- map作input parameter,直接在sql中取key即可

    `parameterType="map"` 

- class传参（比如实体类），直接在sql取class的attribute就好

    `parameterType="Object"`

- 只有一个基本type的参数，可以直接在sql中取到
- 多个参数，可以用Map，或者注解

## 模糊查询如何写

### 解决思路

- 传递通配符`% %`
- 安全方式：`mapper.getUserLike("%李%");`
- 危险方式：可能存在sql注入

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2016.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2016.png)

### 模糊查询（example）

1. 在interface中增加方法

    ```java
    public interface UserMapper {
    		List<User> getUserLike(String value);
    }
    ```

2. 在UserMapper.xml中添加mapper的方法
    - map作为parameter,其中的key直接可以用`#{}` 导入

    ```xml
    <mapper namespace="com.kaitan.dao.UserMapper">
    		<select id="getUserLike" resultType="com.kaitan.pojo.User">
            select * from mybatis.user where name like #{value}
        </select>
    </mapper>
    ```

3. 写test case

    ```java
    public class UserMapperTest {
    		@Test
        public void getUserLike(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();//固定代码
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = mapper.getUserLike("%李%");
            for (User user : userList) {
                System.out.println(user);
            }
            sqlSession.close();//固定代码
        }
    }
    ```

## 配置解析

### 核心配置文件

- mybatis-config.xml (这是官方名字，也可以使用其他名称)
- 需要重点掌握

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2017.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2017.png)

### 新建一个module

1. 创建普通maven module
2. resources中写mybatis-config.xml配置文件
3. 在java.com.kaitan.utils中建立MybatisUtils.java工具类，包含通过sqlSessionFactory创建sqlSession的v方法
4. 在java.com.kaitan.pojo中建立实体类
5. 在java.com.kaitan.dao中建立接口和配置文件`UserMapper.java`和`UserMapper.xml`
    - 注意两个文件同名
6. test文件：`src/test/java/dao/UserDaoTest.java`

### 环境配置environments

- `mybatis-config.xml`中尽管可以配置多个environments,但是每个SqlSessionFactory实例只能选择一个environment

    ```python
    <environments default="development">
      <environment id="development">
          <transactionManager type="JDBC"/>
          <dataSource type="POOLED">
              <property name="driver" value="com.mysql.jdbc.Driver"/>
              <property name="url" value="jdbc:mysql://localhost:3306/mybatis?userSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
              <property name="username" value="root"/>
              <property name="password" value="root"/>
          </dataSource>
      </environment>
    </environments>
    ```

- 要学会配置多套Environment：所以每次切换环境的时候，直接用default `<environments default="development">` 来指定environment id 就好了
- environment配置的要点

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2018.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2018.png)

    1. transactionMangaer：包含两种，默认是JDBC
        - JDBC：可以使用JDBC的commit和rollback，依赖于从data source得到的connection来管理transaction 作用域
        - MANAGED（没什么用，适用于某老一套的体系的，如果用Spring+MyBatis，就不用考虑这个）

            ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2019.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2019.png)

    2. dataSource
        - 包含：dbcp, c3p0, druid (alibaba)，...
        - 功能都是连接数据库
        - 默认的是Pooled，连接池
        - 包括三种选择：
            - pooled（有连接池，默认，用完可以回收，因为connection占资源，连完放在那儿等另外一个人连）
            - unpooled（无连接池）
            - jndi(正常连接，老版)

            ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2020.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2020.png)

            ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2021.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2021.png)

### Properties

- 我们可以通过`properties` 实行来引用config file
    - 比如db.properties
- 属性都是可以externally configured 且可以动态切换
- 可以再java属性文件中配置，也可以通过properties的子元素来传递

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2022.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2022.png)

- 【实操】
    - 建立properties文件 `src/main/resources/db.properties`

        ```java
        driver=com.mysql.jdbc.Driver
        url=jdbc:mysql://localhost:3306/mybatis?userSSL=true&useUnicode=true&characterEncoding=UTF-8
        username=root
        password=root
        ```

        (注意这里不需要转移&，用`&amp;`会出错)

    - 在核心配置（mybatis-config.xml）中可以引入这个外部配置文件，也可以用properties标签添加attribute
        - 注意，在核心配置xml中，所有的标签都要按照规定顺序配置！

            ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2023.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2023.png)

        - properties必须排在第一个标签！
        - 注意，如果不在配置子attribute,可以直接指定properties文件，然后自闭合，这里db.properties放在同一路径下了，所以只写了相对路径

            ```java
            <configuration>
                <properties resource="db.properties"/>
            </configuration>
            ```

        - 也可以包含新的attribute

            ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2024.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2024.png)

        - 如果用db.properties和外部标签配置了同一个属性，优先外部标签中的配置

### 别名typeAliases

- **方式一：**可以在配置文件mybatis-config.xml中，给长长的java类配置一个别名alias，各个地方就可以直接用alias了

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2025.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2025.png)

    - 【实操】
        1. 在mybatis-config.xml中配置alias

            ```xml
            <typeAliases>
                <typeAlias type="com.kaitan.pojo.User" alias="User"/>
            </typeAliases>
            ```

        2. 在UserMapper.xml中，直接可以用了

            ```xml
            <select id="getUserList" resultType="User">
                select * from mybatis.user
            </select>
            ```

- **方式二：**也可以指定package名

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2026.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2026.png)

    - 【实操】
        1. 在mybatis-config.xml中配置package，alias为这个package的class的首字母小写，比如添加了`com.kaitan.pojo`之后，`com.kaitan.pojo.User`这个class的alias是`user`

            ```xml
            <typeAliases>
                <package name="com.kaitan.pojo"/>
            </typeAliases>
            ```

        2. 在UserMapper.xml中，直接可以用了

            ```xml
            <select id="getUserList" resultType="user">
                select * from mybatis.user
            </select>
            ```

- 方式选择
    1. entity实体类比较少的时候，使用第一种（alias）
    2. entity实体类非常多，使用第二种（package）
    3. 区别：第一种（alias）可以DIY别名，但是第二种不行不过可以直接在class上用注解`@Alias("example")` 来变更默认别名

        ```java
        @Alias("user")
        public class User {}
        ```

### java内置类在mybatis中的alias

- There are many built-in type aliases for common Java types. They are all case insensitive, note the special handling of primitives due to the overloaded names.

    [mybatis - MyBatis 3 | Configuration](https://mybatis.org/mybatis-3/configuration.html#typeAliases)

    [MyBatis Alias](https://www.notion.so/daacfa9db50c4667aa7788d181b4dca3)

- 写_开头一般是基本类型，否则是包装类
    - 比如返回值是int，可以写_int(代表八大基本类型)
    - 直接写int，其实代表integer，即包装类
- 其他的，比如Map的别名是小写map

### Settings

- Settings在MyBatis中非常重要，会改变MyBatis的running behavior
- 重要的Setting

    [mybatis - MyBatis 3 | Configuration](https://mybatis.org/mybatis-3/configuration.html#settings)

    - cacheEnabled: true/false
    - lazyLoadingEnabled: true/false

        ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2027.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2027.png)

    - useGeneratedKeys: true/false

        ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2028.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2028.png)

    - mapUnderscoreToCamelCase
        - 比如DB中一般写last_name，但是java中一般是lastName，无法转换，这个setting开启驼峰命名规则的mapping
    - loglmpl
        - 指定MyBatis所用log的实现，未指定时自动查找

        ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2029.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2029.png)

- 完整版设置example

    ```xml
    <settings>
      <setting name="cacheEnabled" value="true"/>
      <setting name="lazyLoadingEnabled" value="true"/>
      <setting name="multipleResultSetsEnabled" value="true"/>
      <setting name="useColumnLabel" value="true"/>
      <setting name="useGeneratedKeys" value="false"/>
      <setting name="autoMappingBehavior" value="PARTIAL"/>
      <setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
      <setting name="defaultExecutorType" value="SIMPLE"/>
      <setting name="defaultStatementTimeout" value="25"/>
      <setting name="defaultFetchSize" value="100"/>
      <setting name="safeRowBoundsEnabled" value="false"/>
      <setting name="mapUnderscoreToCamelCase" value="false"/>
      <setting name="localCacheScope" value="SESSION"/>
      <setting name="jdbcTypeForNull" value="OTHER"/>
      <setting name="lazyLoadTriggerMethods"
        value="equals,clone,hashCode,toString"/>
    </settings>
    ```

### 其他Settings

- [typeHandlers](https://mybatis.org/mybatis-3/configuration.html#typeHandlers) 类型处理器
- [objectFactory](https://mybatis.org/mybatis-3/configuration.html#objectFactory) 对象工厂
- [plugins](https://mybatis.org/mybatis-3/configuration.html#plugins) 插件

### plugin 插件-examples

- 比如MyBatis Generator Core可以根据DB自动写出来MyBatis SQL增删改查部分

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2030.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2030.png)

- MyBatis Plus 第三方插件,跟MyBatis搭配使用，增删改查不用自己写，只需要focus在复杂代码上即可

    [MyBatis-Plus](https://baomidou.com/en/)

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2031.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2031.png)

### 映射器mappers

- MapperRegistry: 注册绑定我们的Mapper文件
- 在mybatis-config.xml中注册mappers的几种方式

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2032.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2032.png)

- 方式1`推荐`：用mapper xml文件路径注册（resource）

    ```xml
    <mappers>
        <mapper resource="com/kaitan/dao/UserMapper.xml"></mapper>
    </mappers>
    ```

- 方式2：用class文件注册（class）
    - interface和其Mapper配置文件必须同名
    - interface和其Mapper配置文件必须在同一个包下

        ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2033.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2033.png)

    ```xml
    <mappers>
        <mapper class="com.kaitan.dao.UserMapper"></mapper>
    </mappers>
    ```

- 方式3：通过package注册
    - interface和其Mapper配置文件必须同名
    - interface和其Mapper配置文件必须在同一个包下

    ```xml
    <mappers>
        <package name="com.kaitan.dao"></package>
    </mappers>
    ```

### 生命周期和作用域

- 生命周期和作用域是至关重要的，错误的话会导致并发问题
- 流程
    1. mybatis-config.xml文件配置好，用于生成SqlSessionFactoryBuilder(建造者模式)
    2. 这个SqlSessionFactoryBuilder用于建造工厂SqlSessionFactory（工厂模式）
        - 一旦创建SqlSessionFactory，就不需要SqlSessionFactoryBuilder了→ 局部变量
        - SqlSessionFactory可以理解为DB连接池，创建后一直存在，不需要丢弃它或重新建立，否则浪费内存资源
        - SqlSessionFactory随程序开始即开始，程序结束即结束
        - SqlSessionFactory最佳作用域是Application应用作用域，最简单的是使用单例模式/static单例模式（全局只有一个变量）
    3. 工厂SqlSessionFactory建立SqlSession
        - SqlSession相当于数据库连接的一次请求(连接到 连接池的一次request)，请求后要关闭
        - SqlSession需要开始和关闭，用完不关闭会占用资源
        - SqlSession的instance不是线程安全的，每个SqlSession自己一个线程，不能被共享
        - 最佳作用域是放到方法/request里，理解为每次http request都开一个SqlSession，response后就完毕这个SqlSession
        - 每个线程都应该有其自己的SqlSession instance

        ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2034.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2034.png)

    4. SqlSession拿到Sql Mapper
    5. 执行程序，结束 （还有一种废弃的路径）

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2035.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2035.png)

- 对于SqlSession的理解
    - SqlSessionFactory一直都在，作为pool管理各种SqlSession,每一个http request即对应一个SqlSession，而每个SqlSession都可以连接一个或者多个Mapper
    - 每个Mapper都代表一个具体的业务(比如getUserList)

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2036.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2036.png)

- [ ]  [P66、配置之属性优化22:42](https://www.bilibili.com/video/BV1NE411Q7Nx?p=6)
- [ ]  [P77、配置之别名优化14:03](https://www.bilibili.com/video/BV1NE411Q7Nx?p=7)
- [ ]  [P88、配置之映射器说明12:26](https://www.bilibili.com/video/BV1NE411Q7Nx?p=8)
- [ ]  [P99、生命周期和作用域11:10](https://www.bilibili.com/video/BV1NE411Q7Nx?p=9)

## ResultMap（解决attribute与column名称不同的问题）

### Project: 测试entity实体类column不一致的情况

- 新建一个普通maven项目mybatis-03
- 将mybatis-02拷过来
    1. 先把resources中的db.properties和mybatis-config.xml拷贝过来
    2. 把java里的文件copy过来就好了
    3. 把test中的文件copy过来
- 修改`pojo/User.java`文件，故意造成entity user 的attribute和db中User table中的column名不同（db中column是pwd，entity class用的是password）

    ```java
    package com.kaitan.pojo;

    public class User {
        private int id;
        private String name;
        private String password; //db中是pwd

        ...
    ```

- 如果不做处理，则会出现password取不出来的情况（如下，为null）

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2037.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2037.png)

### column与attribute名称不同的解决方法

- 方法1：起别名（笨，粗暴的解决方法）
    - 原本

        ```xml
        <mapper namespace="com.kaitan.dao.UserMapper">

            <select id="getUserById" parameterType="int" resultType="com.kaitan.pojo.User">
                select * from mybatis.user where id=#{id}
            </select>

        </mapper>
        ```

    - 改成 `select id, name, pwd as password` 就可以识别了

        ```xml
        <mapper namespace="com.kaitan.dao.UserMapper">

            <select id="getUserById" parameterType="int" resultType="com.kaitan.pojo.User">
                select id, name, pwd as password from mybatis.user where id=#{id}
            </select>

        </mapper>
        ```

- 方法2：不改sql，使用resultMap
    - 小tip

        ```xml
        <typeAliases>
            <typeAlias type="com.kaitan.pojo.User" alias="User"/>
        </typeAliases>
        ```

        就可以写 `resultType="User"` 了

    - resultMap
        - 不要使用resultType了，换成resultMap，随便起一个resultMap的名字，比如这里的`"UserMap"`

            ```xml
            <select id="getUserById" resultMap="UserMap">
                select * from mybatis.user where id=#{id}
            </select>
            ```

        - 新建一个resultMap标签
            - id就对应resultMap的名字，比如这里的`"UserMap"`
            - type是想要map的类型
            - 增加result标签用来map 映射DB column和class attribute的关系

            ```xml
            <mapper namespace="com.kaitan.dao.UserMapper">
              <resultMap id="UserMap" type="User">
                  <result column="id" property="id"/>
                  <result column="name" property="name"/>
                  <result column="pwd" property="password"/>
              </resultMap>

              <select id="getUserById" resultMap="UserMap">
                  select * from mybatis.user where id=#{id}
              </select>

            </mapper>
            ```

            ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2038.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2038.png)

### 对于resultMap 的理解

- 官网描述

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2039.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2039.png)

- 如果复杂关系，需要复杂设置
- [ ]  [P1010、ResultMap结果集映射21:55](https://www.bilibili.com/video/BV1NE411Q7Nx?p=10)

## 日志

### 日志工厂

- 比如假设UserMapper.xml文件中的sql写错了，如何查看？可以在日志中
- 如果一个DB操作出现了异常，需要排错，那日志是最好的帮手
    - 曾经: 使用print, debug模式来找错
    - 现在: 用日志工厂实现
- MyBatis内置了很多日志工厂

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2040.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2040.png)

    - SLF4J
    - LOG4J `要掌握`
    - LOG4J2：LOG4J升级版
    - JDK_LOGGING：Java自带的一个logging输出
    - COMMONS_LOGGING：一个工具包
    - STDOUT_LOGGING `要掌握` ：标准日志输出
    - NO_LOGGING：无输出
- 在MyBatis中具体使用哪一个logging实现，在settings中设定（无默认值）

### 在Settings中设置logging

- 在mybatix-config.xml中配置
    - name就是配置的内容，这里是log工厂 `"logImpl"`
    - 后面value中填写具体的类型 （千万注意大小写，空格）

    ```xml
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
    ```

- 注意，settings在configuration中必须放在properties和typeAliases中间（注意顺序）

    ```xml
    <configuration>
        <properties resource="db.properties"/>
        <settings>
            <setting name="logImpl" value="STDOUT_LOGGING"/>
        </settings>

        <typeAliases>
            <typeAlias type="com.kaitan.pojo.User" alias="User"/>
        </typeAliases>

        <environments default="development">
            <environment id="development">
                <transactionManager type="JDBC"/>
                <dataSource type="POOLED">
                    <property name="driver" value="${driver}"/>
                    <property name="url" value="${url}"/>
                    <property name="username" value="${username}"/>
                    <property name="password" value="${password}"/>
                </dataSource>
            </environment>
        </environments>

        <mappers>
            <mapper resource="com/kaitan/dao/UserMapper.xml"></mapper>
        </mappers>
    </configuration
    ```

- 查看logging结果
    - 表示使用`'class org.apache.ibatis.logging.stdout.StdOutImpl'` 日志工厂
    - 一些准备工作
    - 重点是：
        - Opening JDBC Connection
        - Created connection 1666607455.
        - Setting `autocommit` to false
        - 查询内容

            ```prolog
            ==>  Preparing: select * from mybatis.user where id=?
            ==> Parameters: 1(Integer)
            <==    Columns: id, name, pwd
            <==        Row: 1, kaitan, 123456
            <==      Total: 1
            ```

        - 查询结果

            ```prolog
            User{id=1, name='kaitan', password='123456'}
            ```

        - Resetting `autocommit` to true
        - Closing JDBC Connection
        - Returned connection 1666607455 to pool.
    - 完整版logging

        ```prolog
        ...
        Logging initialized using 'class org.apache.ibatis.logging.stdout.StdOutImpl' adapter.
        PooledDataSource forcefully closed/removed all connections.
        PooledDataSource forcefully closed/removed all connections.
        PooledDataSource forcefully closed/removed all connections.
        PooledDataSource forcefully closed/removed all connections.
        Opening JDBC Connection
        Mon Jul 26 01:58:03 EDT 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
        Created connection 1666607455.
        Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@6356695f]
        ==>  Preparing: select * from mybatis.user where id=?
        ==> Parameters: 1(Integer)
        <==    Columns: id, name, pwd
        <==        Row: 1, kaitan, 123456
        <==      Total: 1
        User{id=1, name='kaitan', password='123456'}
        Resetting autocommit to true on JDBC Connection [com.mysql.jdbc.JDBC4Connection@6356695f]
        Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@6356695f]
        Returned connection 1666607455 to pool.

        Process finished with exit code 0
        ```

### 使用LOG4J

- 什么是LOG4J
    - LOG4J是apache下的开源项目
    - 可以输入logging到console、file、GUI组件
    - 可以控制logging的输出格式
    - 可以控制logging级别
    - 可以通过配置文件配置，无需修改密码
- STDOUT_LOGGING 可以直接用，LOG4J的话不行，会报错

    (mybatis-config.xml)

    ```xml
    <settings>
        <setting name="logImpl" value="LOG4J"/>
    </settings>
    ```

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2041.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2041.png)

- 需要导包log4j包

    pom.xml

    ```xml
    <dependencies>
        <!-- https://mvnrepository.com/artifact/log4j/log4j -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
    </dependencies>
    ```

- 在resources中配置properties `log4j.properties`

    ```prolog
    #将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码
    log4j.rootLogger=DEBUG,console,file

    #控制台输出的相关设置
    log4j.appender.console = org.apache.log4j.ConsoleAppender
    log4j.appender.console.Target = System.out
    log4j.appender.console.Threshold=DEBUG
    log4j.appender.console.layout = org.apache.log4j.PatternLayout
    log4j.appender.console.layout.ConversionPattern=[%c]-%m%n

    #文件输出的相关设置
    log4j.appender.file = org.apache.log4j.RollingFileAppender
    log4j.appender.file.File=./log/kuang.log
    log4j.appender.file.MaxFileSize=10mb #超过会生成新文件
    log4j.appender.file.Threshold=DEBUG
    log4j.appender.file.layout=org.apache.log4j.PatternLayout
    log4j.appender.file.layout.ConversionPattern=[%p][%d{yy-MM-dd}][%c]%m%n

    #日志输出级别， debug才会输出
    log4j.logger.org.mybatis=DEBUG
    log4j.logger.java.sql=DEBUG
    log4j.logger.java.sql.Statement=DEBUG
    log4j.logger.java.sql.ResultSet=DEBUG
    log4j.logger.java.sql.PreparedStatement=DEBUG
    ```

- 直接测试运行，发现差不多

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2042.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2042.png)

### log4j简单使用

- 写个测试类，注意method不要跟class重名
- 在要使用的class中导log4j包
    - 注意导的包是`import org.apache.log4j.Logger;`
- logging的input对象是当前类的class（用哪个类运行logging，就输入哪个class）

    ```java
    Logger logger = Logger.getLogger(UserDaoTest.class);
    ```

- 完整示例

    ```java
    ...
    import org.apache.log4j.Logger;
    import org.junit.Test;

    public class UserDaoTest {

        Logger logger = Logger.getLogger(UserDaoTest.class);

        @Test
        public void testLog4j(){
            logger.info("info: 进入testLog4j");
            logger.debug("debug: 进入testLog4j");
            logger.error("error: 进入testLog4j");

        }
    }
    ```

    - 根据配置log4j.properties，在目录下生成log文件（当前目录）log/kaitan.log，append

        ```prolog
        [INFO][21-07-26][com.kaitan.dao.UserDaoTest]info: 进入testLog4j
        [DEBUG][21-07-26][com.kaitan.dao.UserDaoTest]debug: 进入testLog4j
        [ERROR][21-07-26][com.kaitan.dao.UserDaoTest]error: 进入testLog4j
        ```

- [ ]  [P1111、日志工厂14:54](https://www.bilibili.com/video/BV1NE411Q7Nx?p=11)
- [ ]  [P1212、Log4j讲解23:41](https://www.bilibili.com/video/BV1NE411Q7Nx?p=12)

## 分页

### 为什么要分页

- 减少数据处理量

### 回顾limit

![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2043.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2043.png)

### 使用MyBatis实现分页

- 核心就是使用SQL
- 步骤
    - interface（java/com/kaitan/dao/UserMapper.java）

        ```prolog
        public interface UserMapper {
            //分页
            List<User> getUserByLimit(Map<String,Integer> map);
        }
        ```

    - 写mapper.xml中的sql

        ```xml
        <mapper namespace="com.kaitan.dao.UserMapper">

            <!--分页-->
            <select id="getUserByLimit" parameterType="map" resultType="user">
                select * from mybatis.user limit #{startIndex},#{pageSize}
            </select>
        </mapper>
        ```

        - 在官网找发现Map的别名是map
    - test类

        ```java
        @Test
        public void getUserByLimit(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);

            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("startIndex",0);
            map.put("pageSize",2);
            List<User> userList = mapper.getUserByLimit(map);
            for (User user : userList) {
                System.out.println(user);
            }
            sqlSession.close();
        }
        ```

        result

        ```prolog
        [com.kaitan.dao.UserMapper.getUserByLimit]-==>  Preparing: select * from mybatis.user limit ?, ?
        [com.kaitan.dao.UserMapper.getUserByLimit]-==> Parameters: 0(Integer), 2(Integer)
        [com.kaitan.dao.UserMapper.getUserByLimit]-<==      Total: 2
        User{id=1, name='kaitan', password='123456'}
        User{id=2, name='zhangsan', password='123456'}
        ```

### RowBounds分页（面向对象）

- interface（java/com/kaitan/dao/UserMapper.java）

    ```prolog
    public interface UserMapper {
        //用面向对象 分页
        List<User> getUserByRowBounds();
    }
    ```

- 写mapper.xml中的sql

    ```xml
    <mapper namespace="com.kaitan.dao.UserMapper">

    	<select id="getUserByRowBounds" resultMap="UserMap">
          select * from mybatis.user 
      </select>
    </mapper>
    ```

    - 在官网找发现Map的别名是map
- 有两种实现方法
    1. 常用 sqlSession.getMapper(UserMapper.class);
    2. 不推荐 过时，返回什么，select什么： 如sqlSession.selectList("com.kaitan.dao.UserMapper.getUserByRowBounds");

        ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2044.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2044.png)

- Test类
    - 用上述第二种方式
    - 可以查看RowBounds是啥，其中input 1为start index, input 2 为size

    ```java
    @Test
    public void getUserByRowBounds(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        RowBounds rowBounds = new RowBounds(1, 2);

        //通过Java代码实现分页，但是Mybatis官网都不推荐了
        List<User> userList = sqlSession.selectList("com.kaitan.dao.UserMapper.getUserByRowBounds",
                null,
                rowBounds);
        for (User user : userList) {
            System.out.println(user);
        }
        sqlSession.close();
    }
    ```

    结果

    ```prolog
    [org.apache.ibatis.transaction.jdbc.JdbcTransaction]-Setting autocommit to false on JDBC Connection [com.mysql.jdbc.JDBC4Connection@31304f14]
    [com.kaitan.dao.UserMapper.getUserByRowBounds]-==>  Preparing: select * from mybatis.user
    [com.kaitan.dao.UserMapper.getUserByRowBounds]-==> Parameters: 
    User{id=2, name='zhangsan', password='123456'}
    User{id=3, name='lisi', password='123456'}
    ```

### 分页插件

- PageHelper插件，使MyBatis更简单
- 了解可以，万一以后公司架构师说要使用

    [MyBatis 分页插件 PageHelper](https://pagehelper.github.io/)

- [ ]  [P1313、Limit实现分页16:21](https://www.bilibili.com/video/BV1NE411Q7Nx?p=13)
- [ ]  [P1414、RowBounds分页11:40](https://www.bilibili.com/video/BV1NE411Q7Nx?p=14)

## 注解开发

### 什么是interface

- 目的
    - 为了解耦，可拓展，提高复用
    - 上层不用管细节实现，遵循共同的标准，便于开发，规范
- 理解：definition和implementation的分离
    - 架构师提出需求，写接口（大网站可能成嵌）
    - developer开发
    - 接口是system design人员对系统的抽象理解
- 两类interface
    - abstract class 对个体的抽象，即抽象体，一个抽象体可能有多个抽象面
    - Interface 对一个个体某一方面的抽象, 即抽象面

### 三个面向

- 面向对象：思考问题，以object为单位，考虑ta的attribute和method
- 面向过程：考虑问题的时候，以一个具体流程（事务过程）为单位，考虑实现
- 接口设计与非接口设计 是针对复用技术而言，与面向对象（过程）不是一个问题，更多的体现的事对系统整体的架构

### 使用注解开发

- 直接在interface上配一个sql query注解，就可以了（dao/UserMapper.java）

    ```java
    public interface UserMapper {
        @Select("select * from user")
        List<User> getUsers();
    }
    ```

- 其实不用配xml了
    - 将mybatis-config.xml中的mappers配置到interface而不是xml配置文件了

        ```xml
        <!--不绑定xml了，绑定接口-->
        <mappers>
            <mapper class="com.kaitan.dao.UserMapper"/>
        </mappers>
        ```

    - 也不需要dao/UserMapper.xml的xml 文件了
- 测试

    ```java
    import java.util.List;

    public class UserMapperTest {

        @Test
        public void test(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> users = mapper.getUsers();
            for (User user : users) {
                System.out.println(user);
            }
            sqlSession.close();
        }
    }
    ```

    - 这时需要复杂的ResultMap 的功能就不能实现了（注解对简单语句使用很多，好像可以@resultmap?）

        ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2045.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2045.png)

### 注解-底层实现

- 注解的底层主要使用反射机制，反射可以获取这个类的method，attribute，还可以创建对象，执行方法
    - 这里通过UserMapper.class，能得到其所有的东西（比如方法，注解）
    - 注解就是给class加注释，然后元注解 规定运行环境在runtime，那么注解信息就可以在运行时获取，而反射需要class路径去instance实例对象
    - 反射：

        > Java的反射（reflection）机制是指在程序的运行状态中，可以构造任意一个类的对象，可以了解任意一个对象所属的类，可以了解任意一个类的成员变量和方法，可以调用任意一个对象的属性和方法。 这种动态获取程序信息以及动态调用对象的功能称为Java语言的反射机制。 反射被视为动态语言的关键。

- 动态代理（代理模式-设计模式）
    - 代理-查看笔记：[https://github.com/Zidiefeng/java_thread_study/tree/master](https://github.com/Zidiefeng/java_thread_study/tree/master)
    - 即 agent 把 受理对象的方法执行了，顺带执行自己的额外的method，agent和受理对象都继承于同一个Interface，都重写了interface中的同一款方法

        ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2046.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2046.png)

### MyBatis执行流程

这个部分对照test的代码流程，依次点进class（源码）顺序看下来就能明白了（配合debug模式，依次往下点击查看var）

1. 通过resource加载全局配置文件mybatis-config.xml

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2047.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2047.png)

2. 实例化SqlSessionFactoryBuilder
3. 通过XMLConfigBuilder解析文件流（要读取文件）
    - 返回configuration-所有配置信息（以下是源码）
    - 展开上面.build(inputStream)部分

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2048.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2048.png)

4. SqlSessionFactory实例化
5. 创建执行器executor（mybatis能运行的核心）
    - 包含transactional事务管理器

        ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2049.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2049.png)

6. 创建sqlSession，实现CRUD
    - 查看是否执行成功，成功即提交事务，失败即回滚
    - 用的话，就是使用execute执行mapper
    - mapper已经通过反射加载出来了class中的信息

        ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2050.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2050.png)

7. 图

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2051.png)

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2052.png)

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2053.png)

### autocommit问题

- 我们可以在工具类创建的时候实现自动提交事务autocommit
    - 如下 `return sqlSessionFactory.openSession(true);`

        ```java
        public class MybatisUtils {
            private static SqlSessionFactory sqlSessionFactory;
            static {
                //获取sqlSessionFactory对象
                try {
                    String resource = "mybatis-config.xml"; //config file
                    InputStream inputStream = Resources.getResourceAsStream(resource); //通过config file
                    sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            public static SqlSession getSqlSession(){
        /*        SqlSession sqlSession = sqlSessionFactory.openSession();
                return sqlSession;*/
                return sqlSessionFactory.openSession(true);
            }
        }
        ```

    - 如下，在SqlSessionFactory中定义的SqlSession，如果输入参数T，则表示创建的SqlSession开启autocommit

        ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2054.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2054.png)

    - 开启autocommit的便捷之处在于，不需要Sql
- 测试
    - 亲测autocommit有效！如果没有写 `return sqlSessionFactory.openSession(true);` 因为没有 commit，提示说发生了rollback
    - 写了这个comment，直接commit作用在db啦！

    ```java
    public class UserMapperTest {

        @Test
        public void test(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.addUser(new User(13,"Hell0","123321"));
            sqlSession.close();
            /*
            List<User> users = mapper.getUsers();
            for (User user : users) {
                System.out.println(user);
            }
            sqlSession.close();
            */

        }
    }
    ```

### CRUD-注解

- 为了让注解中的sql识别params，当method存在多params时，需要给每一个param加上`@Param` 标注
- 如下，为了让上面`@Select`注解中能识别attribute `#{id}`，需要标注出来，那个参数对应`id`（`@Param("id") int id`）

    ```java
    @Select("select * from user where id = #{id} and #{name}")
    User getUserById(@Param("id") int id, @Param("name") String name);
    ```

- 即使只有一个参数，如果用`@Param` 指成了别的attribute，`@Select` 就识别不出来原有param了，会报错

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2055.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2055.png)

- 注意事项
    1. 基本类型的参数or String类型，需要加上
    2. 引用类型不需要加
    3. 如果只有一个基本类型的话，可以忽略，但是也建议加上
- `${}` 和 `#{}` 的区别
    - 尽量使用# 可以防止注入，$不行
    - 对应prepared...那些

### CRUD例子

- 增

    ```java
    @Insert("insert into user(id,name,pwd) values (#{id},#{name},#{password} )")
    int addUser(User user);
    ```

    ```java
    @Test
    public void test(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.addUser(new User(13,"Hell0","123321"));
        sqlSession.close();
    }
    ```

- 删

    ```java
    @Delete("delete from user where id = #{uid}")
    int deleteUser(@Param("uid") int id);
    ```

    ```java
    @Test
    public void test(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.deleteUser(13);
        sqlSession.close();
    }
    ```

- 改

    ```java
    @Update("update user set name = #{name}, pwd=#{password} where id = #{id}")
    int updateUser(User user);
    ```

    ```java
    @Test
    public void test(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.updateUser(new User(2,"Kakaka","131313"));
        sqlSession.close();
    }
    ```

- 查

    ```java
    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id") int id);
    ```

    ```java
    @Test
    public void test(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.getUsers();
        for (User user : users) {
            System.out.println(user);
        }
        sqlSession.close();
    }
    ```

- [ ]  [P1515、使用注解开发20:23](https://www.bilibili.com/video/BV1NE411Q7Nx?p=15)
- [ ]  [P1616、Mybatis执行流程剖析12:30](https://www.bilibili.com/video/BV1NE411Q7Nx?p=16)
- [ ]  [P1717、注解增删改查19:19](https://www.bilibili.com/video/BV1NE411Q7Nx?p=17)

## Lombok

- [ ]  [P1818、Lombok的使用15:56](https://www.bilibili.com/video/BV1NE411Q7Nx?p=18)

### 什么是Lombok

- 定义

    Project Lombok is a java library that automatically plugs into your editor and builds tools, spicing up your java.

    Never write another getter or equals method again, with one annotation your class has a fully-featured builder, Automate your logging variables, and much more.

    [Project Lombok](https://projectlombok.org/)

### 如何在idea种安装lombok

- file settings plugins 搜索lombok （找不到点击browse repo）
- 在项目中导入jar包 (pom.xml)

    ```xml
    <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.20</version>
        <scope>provided</scope>
    </dependency>
    ```

- 加注解

    A plugin that adds first-class support for Project Lombok Features

    - @Getter and @Setter
    - @FieldNameConstants
    - @ToString
    - @EqualsAndHashCode
    - @AllArgsConstructor, @RequiredArgsConstructor and @NoArgsConstructor
    - @Log, @Log4j, @Log4j2, @Slf4j, @XSlf4j, @CommonsLog, @JBossLog, @Flogger,
    - @CustomLog
    - @Data
    - @Builder
    - @SuperBuilder
    - @Singular
    - @Delegate
    - @Value
    - @Accessors
    - @Wither
    - @With
    - @SneakyThrows
    - @val
    - @var
    - experimental @var
    - @UtilityClass

### @Data使用

- 加上`@Data` 可以实现各种方法，只定义attribute即可自动添加下面的方法
    - 无参构造
    - get, set
    - toString
    - hashcode
    - equals

    ```java
    package com.kaitan.pojo;
    import lombok.Data;

    @Data
    public class User {
        private int id;
        private String name;
        private String password; 

    }
    ```

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2056.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2056.png)

- 可以指定添加有参`@AllArgsConstructor`或无参构造`@NoArgsConstructor`

    ```java
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class User {
        private int id;
        private String name;
        private String password;//db中是pwd
    }

    ```

- 可以在attribute和类上增加@getter（查看源码）

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2057.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2057.png)

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2058.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2058.png)

### 辩证看待

![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2059.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2059.png)

![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2060.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2060.png)

## 多对一

### 多对一处理

- 多对一：多个学生**关联**一个老师 **association**
- 一对多：一个老师有很多学生，**集合 collection**
- 结果映射resultMap

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2061.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2061.png)

### 例子

- SQL-创建数据库
    - 设置tid作为foreign key连接到teacher表中的id  `KEY `fktid`(`tid`)
    CONSTRAINT `fktid` FOREIGN KEY (`tid`) REFERENCES `teacher` (`id`)`

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2062.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2062.png)

    ```sql
    -- CREATE DATABASE `mybatis`;
    -- use `mybatis`;

    CREATE TABLE `teacher`(
    	`id` INT NOT NULL,
    	`name` VARCHAR(30) DEFAULT NULL,
    	PRIMARY KEY(`id`)
    )ENGINE=INNODB DEFAULT CHARSET=utf8;

    INSERT INTO teacher(`id`,`name`)VALUES (1,"秦老师");

    CREATE TABLE `student`(
    	`id` INT NOT NULL,
    	`name` VARCHAR(30) DEFAULT NULL,
    	`tid` INT DEFAULT NULL,
    	PRIMARY KEY(`id`),
    	KEY `fktid`(`tid`),
    	CONSTRAINT `fktid` FOREIGN KEY (`tid`) REFERENCES `teacher` (`id`)
    )ENGINE=INNODB DEFAULT CHARSET=utf8;
    -- DROP TABLE student;

    INSERT INTO `student`(`id`,`name`,`tid`) VALUES(1,"小明",1);
    INSERT INTO `student`(`id`,`name`,`tid`) VALUES(2,"小红",1);
    INSERT INTO `student`(`id`,`name`,`tid`) VALUES(3,"小张",1);
    INSERT INTO `student`(`id`,`name`,`tid`) VALUES(4,"小李",1);
    INSERT INTO `student`(`id`,`name`,`tid`) VALUES(5,"小王",1);
    ```

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2063.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2063.png)

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2064.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2064.png)

- 两种查询
    - 连表查询
    - 子查询

### 项目准备

- 导入lombok(pom.xml)

    ```xml
    <dependencies>
        <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.20</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
    ```

- 架构
    - 将mapper xml文件放到resources对应路径中
    - 实体类与interface与xml一一对应

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2065.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2065.png)

- 建立实体类
    - Teacher.java

        ```java
        package com.kaitan.pojo;

        public class Teacher {
            private int id;
            private String name;
        }
        ```

    - Student.java
        - 通过直接增加Teacher属性的方式来关联老师

        ```java
        package com.kaitan.pojo;
        import lombok.Data;

        @Data
        public class Student {
            private int id;
            private String name;

            //学生需要关联一个老师
            private Teacher teacher;
        }
        ```

- 建立Mapper接口
    - StudentMapper.java interface
    - TeacherMapper.java interface

        ```java
        package com.kaitan.dao;

        import com.kaitan.pojo.Teacher;
        import org.apache.ibatis.annotations.Param;
        import org.apache.ibatis.annotations.Select;

        public interface TeacherMapper {
            @Select("select * from teacher where id = #{tid}")
            Teacher getTeacher(@Param("tid") int id);
        }
        ```

- 建立Mapper xml
    - StudentMapper.xml 初始模板

        ```xml
        <?xml version="1.0" encoding="UTF8" ?>
        <!DOCTYPE mapper
                PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
                "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

        <mapper namespace="com.kaitan.dao.StudentMapper">
        </mapper>
        ```

    - TeacjerMapper.xml 初始模板

        ```xml
        <?xml version="1.0" encoding="UTF8" ?>
        <!DOCTYPE mapper
                PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
                "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

        <mapper namespace="com.kaitan.dao.TeacherMapper">
        </mapper>
        ```

- 在mybatis-config.xml中添加mapper(核心配置中注册Mapper接口或者文件，方式多，随便选)
    - 因为Mapper xml文件 都在resource中的某一个相对目录中，直接用resource注册mapper

        ```xml
        <!--这个报错了-->
        <mappers>
            <mapper resource="com/kaitan/dao/*Mapper.xml">
        </mappers>

        <!--这个可以work-->
        <mappers>
            <mapper class="com.kaitan.dao.TeacherMapper"/>
            <mapper class="com.kaitan.dao.StudentMapper"/>
        </mappers>
        ```

- Test 测试成功

    ```java
    import com.kaitan.dao.TeacherMapper;
    import com.kaitan.pojo.Teacher;
    import com.kaitan.utils.MybatisUtils;
    import org.apache.ibatis.session.SqlSession;

    public class MyTest {
        public static void main(String[] args) {
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            Teacher teacher = mapper.getTeacher(1);
            System.out.println(teacher);
            sqlSession.close();
        }
    }
    ```

### 例子：查询所有学生，以及对应老师的信息

- SQL

    ```sql
    select s.id, s.name, t.name from student s, teacher t where s.tid = t.id;
    ```

- StudentMapper.java

    ```java
    public interface StudentMapper {
        //查询所有学生信息，以及对应的老师信息
        public List<Student> getStudentInfo();
    }
    ```

- 需要根据查询出来的学生的tid，寻找对应的老师

### 报错tip

- bounding说没有com.kaitan.dao.StudentMapper.getStudent
- 原因是：在resource目录下还原的com.kaitan.dao连在一起了，并没有解读为com/kaitan/dao

    解决方案：重新创建一遍dir就好了

    ![%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2066.png](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2066.png)

- 还有其他错因，比如mybatis-config.xml中注册错了

    ```xml
    <!--不绑定xml了，绑定接口-->
    <mappers>
        <mapper class="com.kaitan.dao.TeacherMapper"/>
        <mapper class="com.kaitan.dao.StudentMapper"/>
    <!--        <mapper resource="com/kaitan/dao/StudentMapper.xml"></mapper>-->
    </mappers>
    ```

- 注意需要添加无参构造

    ```java
    @Data
    @NoArgsConstructor
    public class Student {
        private int id;
        private String name;

        //学生需要关联一个老师
        private Teacher teacher;
    }
    ```

### 多对一 `方法1`

- 方法1：类似子查询，先查出来所有学生（每一行是一个学生），然后再通过每一个学生的tid把对应的老师查出来
- StudentMapper.xml
    - 首先查出学生，虽然这里是*，实际上查到id, name, tid
    - 然后通过resultMap，将id, name, id 转换成Student类的attribute
        - 查出来的id 赋值为 Student的id
        - 查出来的name 赋值为 Student的name
        - 查出来的tid 要转换为Teacher类赋值到Student的teacher，这涉及到了对象，需要使用association，association的property标签依旧表示attribute，但此时要配合javaType表示这个attribute的java class（这里是Teacher）
    - association，将查出来的tid作为input params，通过select标签，传入getTeacher(自定义的)，返回对应的Teacher 赋值回Student 的teacher attribute
        - getTeacher的resultType是Teacher（即将查出来的id, name自动赋值到Teacher的attribute中）
        - 在getTeacher(自定义的)中，因为只有一个参数tid，不用对照var name写，任何代参都可以识别，比如#{id}
        - 通过getTeacher返回的Teacher instance会赋值到Student 的teacher attribute（Teacher class）

    ```xml
    <select id="getStudent" resultMap="StudentTeacher">
        select * from student;
    </select>

    <!--按照查询 嵌套-->
    <resultMap id="StudentTeacher" type="Student">
        <result property="id" column="id"/>
        <result property="name" column="name"/>
        <!--复杂属性，单独处理
        如果是对象用 association， 如果是集合使用collection
        要获取teacher对象，需要通过tid，select
        association中的javaType代表这个对象的类型，select代表如何通过传入的column
        -->
        <association property="teacher" column="tid" javaType="Teacher" select="getTeacher"></association>

    </resultMap>

    <select id="getTeacher" resultType="Teacher">
        select * from teacher where id = #{id}
    </select>
    ```

### 多对一 `方法2`

- 方法2：类似链表查询，根据学生tid和老师id的关系连表查询
- StudentMapper.xml
    - 直接连表查出来 sid,  sname, tname
    - 然后通过resultMap分别存到Student attribute中
        - 查出来的sid 赋值为 Student的id
        - 查出来的sname 赋值为 Student的name
        - 查出来的tid 要转换为Teacher类赋值到Student的teacher，这涉及到了对象，需要使用association，association的property标签依旧表示attribute，但此时要配合javaType表示这个attribute的java class（这里是Teacher）
    - association里面的标签将tname存到Teacher object的name attribute中，这个Teacher object 会作为Student 的teacher attribute

    ```xml
    <select id="getStudentAlt" resultMap="StudentTeacherAlt">
        select s.id sid, s.name sname, t.name tname
        from teacher t, student s
        where s.tid = t.id
    </select>
    <resultMap id="StudentTeacherAlt" type="Student">
        <result property="id" column="sid"/>
        <result property="name" column="sname"/>
        <association property="teacher" javaType="Teacher">
            <result property="name" column="tname"></result>
        </association>
    </resultMap>
    ```

## 一对多

### 一对多概念理解

- 一个老师有多个学生，对于老师而言，就是一对多

### 环境准备

- pojo.Teacher.java
    - 使用collection表示多个学生

    ```java
    @Data
    public class Teacher {
        private int id;
        private String name;
        private List<Student> students;
    }
    ```

- pojo.Student.java

    ```java
    @Data
    @NoArgsConstructor
    public class Student {
        private int id;
        private String name;
        private int tid;

    }
    ```

- 简单测试环境

    ```xml
    <select id="getTeachers" resultType="Teacher">
        select * from mybatis.teacher;
    </select>
    ```

    ```java
    public class MyTest {
        @Test
        public void test(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            List<Teacher> teachers = mapper.getTeachers();
            for (Teacher teacher : teachers) {
                System.out.println(teacher);
            }
        }
    }
    ```

    `Teacher(id=1, name=秦老师, students=null)`

### 一对多 `方法1`

- 方法1：类似子查询，先查出来所有老师，然后查询每个老师的student list
- TeacherMapper.xml
    - 首先查出所有老师，虽然这里是*，实际上查到id, name, students(直接搜为null)
    - 然后通过resultMap，将id, name, 转换成Teacher类的attribute
        - 查出来的id 赋值为 Teacher的id（这步其实不用写出来）
        - 查出来的name 赋值为 Teacher的name（这步其实不用写出来）
        - 查出来的students 是一个java类的collection，需要使用collection标签
    - collection标签：用老师的id去查对应的所有学生，然后放回到Teacher 的students list中
        - collection中的column是id，代表用老师id来进行学生查询，这个id也会作为getStudentByTeacherId的input param
        - collection中的 JavaType是ArrayList（mybatis指定的对应text），因为查出来的是一个object array
        - collection中的 JofType表示这个ArrayList中的java class类型是Student
        - getStudentByTeacherId通过老师的id来查出多行学生，其实返回的是一个Student List
        - 这个Student List会存回到Teacher的students attribute中
    - 遗留问题：为什么这里需要用ArrayList，方法2不用？

    ```xml
    <!--method 2-->
    <select id="getTeacherAlt" resultMap="TeacherStudentAlt">
        select * from mybatis.teacher where id = ${tid};
    </select>

    <resultMap id="TeacherStudentAlt" type="Teacher">
    <!--        <result property="id" column="id"></result>-->
        <collection column="id" property="students" javaType="ArrayList" ofType="Student" select="getStudentByTeacherId"/>
    </resultMap>

    <select id="getStudentByTeacherId" resultType="Student">
        select * from mybatis.student where tid = #{tid};
    </select>
    ```

### 一对多 `方法2`

- 方法2：类似链表查询，根据学生tid和老师id的关系连表查询
- TeacherMapper.xml
    - 直接连表查出来 sid, sname, tname, tid
    - 然后通过resultMap分别存到Teacher attribute中
        - 查出来的sid 赋值为 Teacher 的id
        - 查出来的sname 赋值为 Teacher 的name
        - 查出来的sid, sname要赋值给Teacher的students list中的每个student 的attribute，这里涉及到一对多关系，所以使用collection处理
    - collection
        - property 标签表示最后要赋值给students 属性
        - ofType代表这里每个item的类型是Student
        - 然后对应将sid, sname, tid赋值到这个Student中（这步Student 和 StudentList在mybatis中没有文字区分，这里其实是a list of Student）

    ```xml
    <!--method 1-->
    <select id="getTeacher" resultMap="TeacherStudent">
        select s.id sid, s.name sname, t.name tname, t.id tid
        from mybatis.student s, mybatis.teacher t
        where s.tid = t.id
    </select>

    <resultMap id="TeacherStudent" type="Teacher">
        <result property="id" column="id"></result>
        <result property="name" column="name"></result>
        <!--List<Student>在这里用ofType-->
        <collection property="students" ofType="Student">
            <result property="id" column="sid"/>
            <result property="name" column="sname"/>
            <result property="tid" column="tid"/>
        </collection>
    </resultMap>
    ```

### 总结

- 多对一 association
- 一对多 collection
- javaType
    - 指定关联的java class类型
- ofType
    - 指定映射到list/collection中的pojo类型，泛型中的约束类型
    - 如List<Student> 中的Student

### 注意

- 保证SQL可读性，尽量通俗易懂
- 一对多和多对一注意field和attribute问题
- 如果不好排查，使用日志，建议Log4j
- 面试题
    - 索引优化，索引
    - MySQL引擎，InnoDB底层原理
- [ ]  [P1919、复杂查询环境搭建18:49](https://www.bilibili.com/video/BV1NE411Q7Nx?p=19)
- [ ]  [P2020、多对一的处理24:06](https://www.bilibili.com/video/BV1NE411Q7Nx?p=20)
- [ ]  [P2121、一对多的处理30:37](https://www.bilibili.com/video/BV1NE411Q7Nx?p=21)

## 动态SQL

### 动态SQL

- 动态SQL概念：根据不同条件生成不同的SQL query
- MyBatis上对动态SQL的描述

    The Dynamic SQL elements should be familiar to anyone who has used JSTL or any similar XML-based text processors. MyBatis employs powerful OGNL based expressions to eliminate most of the other elements:

    - if
    - choose (when, otherwise)
    - trim (where, set)
    - foreach

### 环境搭建

1. SQL create table

    ```sql
    CREATE TABLE `blog`(
    `id` VARCHAR(50) NOT NULL COMMENT "博客id",
    `title` VARCHAR(100) NOT NULL COMMENT "博客标题",
    `author` VARCHAR(30) NOT NULL COMMENT "博客作者",
    `create_time` DATETIME NOT NULL COMMENT "创建时间",
    `views` INT(30) NOT NULL COMMENT "浏览量"
    )ENGINE=INNODB DEFAULT CHARSET=utf8;
    ```

2. 构建一个基础工程　maven
3. 导包
4. 编写config文件
5. 编写pojo实体类

    ```java
    @Data
    public class Blog {
        private String id;
        private String title;
        private String author;
        private Date createTime; // Different from col name in DB
        private int views;
    }
    ```

6. 编写实体类对应的Mapper interface & Mapper.xml

    ```java
    public interface BlogMapper {
        //insert data point
        int addBook(Blog blog);
    }
    ```

    ```xml
    <?xml version="1.0" encoding="UTF8" ?>
    <!DOCTYPE mapper
            PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

    <mapper namespace="com.kaitan.dao.BlogMapper">
        <insert id="addBook" parameterType="blog">
            insert into mybatis.blog (id,title,author,create_time, views)
            values (#{id},#{title},#{author},#{createTime},#{views});
        </insert>
    </mapper>
    ```

7. test

    ```java
    public class MyTest {
        @Test
        public void addInitBlog(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

            Blog blog = new Blog();
            blog.setId(IDutils.getId());
            blog.setTitle("Mybatis so easy");
            blog.setAuthor("Kaitan");
            blog.setCreateTime(new Date());
            blog.setViews(99999);
            mapper.addBook(blog);

            blog.setId(IDutils.getId());
            blog.setTitle("java so easy");
            mapper.addBook(blog);

            blog.setId(IDutils.getId());
            blog.setTitle("sql so easy");
            mapper.addBook(blog);

            sqlSession.close();
        }
    }
    ```

### Tips

- 在config中，设置数据库中的`＿`会自动对应java中的驼峰命名

    ```xml
    <settings>
    <!--        标准logging实现-->
        <setting name="logImpl" value="STDOUT_LOGGING"/>
        <setting name="mapUnderscoreToCamelCase" value="true"></setting>
    </settings>
    ```

- If there is gray remaindar for potential issues, you can suppress it

    ```java
    @SuppressWarnings("all")
    public class IDutils {
    }
    ```

- Use randomly generated UUID for id (instead of 0, 1, 2, 3, ...
    - very very very unlikely to be duplicated. All generated one should be unique

    ```java
    public class IDutils {    
    		public static String getId(){
            return UUID.randomUUID().toString().replaceAll(".","");
        }

        @Test
        public void test(){
            System.out.println(IDutils.getId());
            System.out.println(IDutils.getId());
            System.out.println(IDutils.getId());
        }
    }
    ```

### Dynamic SQL - IF

- interface BlogMapper

    ```java
    public interface BlogMapper {
        //insert data point
        int addBook(Blog blog);

        // search blog
        List<Blog> queryBlogIF(Map map);
    }
    ```

- BlogMapper.xml
    - use `if` tag, if true, the inner part will be part of SQL

    ```xml
    <select id="queryBlogIF" parameterType="map" resultType="blog">
        select * from mybatis.blog where 1=1
        <if test="title !=null">
            and title = #{title}
        </if>
        <if test="author !=null">
            and title = #{author}
        </if>
    </select>
    ```

- Test

    ```java
    @Test
    public void queryBlogIF(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();

        map.put("title","java so easy");

        List<Blog> blogs = mapper.queryBlogIF(map);

        for (Blog blog : blogs) {
            System.out.println(blog);
        }
    }
    ```

### where tag

- Issues with where

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2067.png)

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2068.png)

- solution

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2069.png)

- example
    - prio

        ```xml
        <select id="queryBlogIF" parameterType="map" resultType="blog">
            select * from mybatis.blog where 1=1
            <if test="title !=null">
                and title = #{title}
            </if>
            <if test="author !=null">
                and title = #{author}
            </if>
        </select>
        ```

    - after

        ```xml
        <select id="queryBlogIF" parameterType="map" resultType="blog">
            select * from mybatis.blog
            <where>
                <if test="title !=null">
                    title = #{title}
                </if>
                <if test="author !=null">
                    and title = #{author}
                </if>
            </where>
        </select>
        ```

### choose (where, otherwise)

- choose: go with the first met condition
    - if con 1 and con 3 are met, only go with con 1 since con 1 is before con 3
    - where: case when
    - otherwise: otherwise if no `case when` has been met
- interface

    ```java
    public interface BlogMapper {
        // query - use choose
        List<Blog> queryBlogChoose(Map map);
    }
    ```

- mapper xml

    ```xml
    <select id="queryBlogChoose" parameterType="map" resultType="blog">
        select * from mybatis.blog
        <where>
            <choose>
                <when test="title != null">
                    title=#{title}
                </when>
                <when test="author != null">
                    and author = #{author}
                </when>
                <otherwise>
                    and views = #{views}
                </otherwise>
            </choose>
        </where>
    </select>
    ```

- Test

    ```java
    @Test
    public void queryBlogChoose(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();

        map.put("title","java so easy");
        map.put("views","99999");

        List<Blog> blogs = mapper.queryBlogChoose(map);

        for (Blog blog : blogs) {
            System.out.println(blog);
        }
    }
    ```

### set

- set

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2070.png)

- interface

    ```java
    public interface BlogMapper {
        // update blog
        int updateBlog(Map map);
    }
    ```

- mapper xml

    ```xml
    <update id="updateBlog" parameterType="map">
        update mybatis.blog
        <set>
            <if test="title !=null">
                title = #{title},
            </if>
            <if test="author !=null">
                author = #{author}
            </if>
        </set>
        where id = #{id}
    </update>
    ```

- Test

    ```java
    @Test
    public void updateBlog(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();

    //        map.put("title","JAVA so easy");
    //        map.put("views","100000");
        map.put("author","kk");
        map.put("id","bc778785d99e4360b3f68951b77772ae");

        mapper.updateBlog(map);
    }
    ```

### trim

- trim： customized auto-sql-grammer-ajustment

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2071.png)

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2072.png)

- 例子

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2073.png)

### sql标签

- 用处：将经常复用的sql片段单独存到sql标签中，需要使用的时候，使用include标签用isql片段的d导入即可
- 注意：
    - 最好基于单表定义SQL片段
    - sql片段中，尽量不要包含where，因为where是跟着上面的select语句走的
- 原本

    ```xml
    <update id="updateBlog" parameterType="map">
        update mybatis.blog
        <set>
            <if test="title !=null">
                title = #{title},
            </if>
            <if test="author !=null">
                author = #{author}
            </if>
        </set>
        where id = #{id}
    </update>
    ```

- 使用sql

    ```xml
    <sql id="if-title-author">
        <if test="title !=null">
            title = #{title},
        </if>
        <if test="author !=null">
            author = #{author}
        </if>
    </sql>

    <update id="updateBlog" parameterType="map">
        update mybatis.blog
        <set>
            <include refid="if-title-author"></include>
        </set>
        where id = #{id}
    </update>
    ```

### foreach

- 定义

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2074.png)

- interface

    ```java
    public interface BlogMapper {
        //query 1,2,3 blog
        List<Blog> queryBlogForeach(Map map);
    }
    ```

- mapper xml

    ```xml
    <!--select * from mybatis.blog where 1=1 and (id=1 or id=2 or id=3)-->
    <select id="queryBlogForeach" parameterType="map" resultType="blog">
        select * from mybatis.blog
        <where>
            <foreach collection="ids" item="id" open="and (" close=")" separator="or">
                id = #{id}
            </foreach>

        </where>
    </select>
    ```

- Test

    ```java
    @Test
    public void queryBlogForeach(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        HashMap map = new HashMap();

        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);

        map.put("ids",ids);

        List<Blog> blogs = mapper.queryBlogForeach(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
    }
    ```

### 总结

- 所谓的动态SQL，本质还是SQL，只是我们可以在SQL层面，去执行逻辑代码（比如if, case when），并且保证动态执行时，SQL基础语法不会出错
- Dynamic SQL就是在拼接SQL语句，只要保证SQL正确行，按照SQL格式，去拼接就好了
- 需要先在MySQL中测试确定SQL的正确行！再修改为Dynamic SQL

- [ ]  [P2222、动态SQL环境搭建19:21](https://www.bilibili.com/video/BV1NE411Q7Nx?p=22)
- [ ]  [P2323、动态SQL之IF语句09:43](https://www.bilibili.com/video/BV1NE411Q7Nx?p=23)
- [ ]  [P2424、动态SQL常用标签30:53](https://www.bilibili.com/video/BV1NE411Q7Nx?p=24)
- [ ]  [P2525、动态SQL之Foreach30:58](https://www.bilibili.com/video/BV1NE411Q7Nx?p=25)

## 缓存

### 什么是缓存

- 缓存
    - 查询需要连接DB，很耗费资源
    - 将某次从DB查询到的结果，暂存在一个直接可以取到的地方，即内存中
    - 第二次查询相同数据的时候，直接从缓存获取，就快了，不用走数据库了
- 什么是缓存cache
    - 存在内存中的临时数据
- 为什么使用缓存
    - 从缓存中查询，提高查询效率，提高系统效率
    - 解决高并发系统的性能问题
- 什么样的数据能使用缓存？
    - 经常查询，而且不经常改变的数据

### 架构理解

- 用户连接服务器，服务器连接DB
- 随着用户增加，服务器可能不够用了，需要多台服务器访问DB，此时可能出现独写问题
- 读写问题：多台服务器处理同一个DB的时候，出现并发问题，有延迟问题出现（查得慢）
- 早年解决办法 memecached：读写分离，增加一台memecached服务器专门负责read，DB只负责写，减轻压力。服务器从memecached读取，写在DB上。

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2075.png)

- 随着用户增加，需要多个服务器，多个DB
    - DB也要水平复制（ACID原则），需要保证每一个DB都相同
    - 数据库DB之间的一致性如何保证？用主从复制~
    - 读写分离，主从复制：哨兵概念，一有更新，所有DB立刻同步

### MyBatis缓存

- MyBatis包含一个强大的查询缓存特性，可以方便指定和配置缓存
- MBatis有两级缓存
    - 一级缓存：默认开启，SqlSession级别，也称本地缓存，在getSqlSession和sqlSession.close()之间有效，关了就没了
    - 二级缓存：需要手动开启、配置，属于namespace级别（mapper级别）
- 为了提高扩展性，MyBatis定义了缓存接口Cache，可以通过impelment Cache接口定义二级缓存
    - 进入Cache源码

        ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2076.png)

    - 实现方式（缓存策略）

        ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2077.png)

### 缓存策略

- 当内存存满的时候，需要清除替换内容
- 清除策略：默认是LRU

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2078.png)

### 一级缓存-测试

1. 打开日志（配置日志）
    - mybatis-config.xml

        ```xml
        <settings>
        <!--        标准logging实现-->
            <setting name="logImpl" value="STDOUT_LOGGING"/>
            <setting name="mapUnderscoreToCamelCase" value="true"></setting>
        </settings>
        ```

2. 配置环境
    - User Mapper

        ```java
        public interface UserMapper {
            User queryUsersById(@Param("id") int id);
        }
        ```

    - User Mapper xml

        ```xml
        <mapper namespace="com.kaitan.dao.UserMapper">
            <select id="queryUsersById" resultType="user">
                select * from user where id = #{id}
            </select>
        </mapper>
        ```

3. 测试在一个Session中两次查询同一个SQL

    ```java
    public class MyTest {
        @Test
        public void test(){
            SqlSession sqlSession = MybatisUtils.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            User user1 = mapper.queryUsersById(1);
            System.out.println(user1);
            User user2 = mapper.queryUsersById(1);
            System.out.println(user2);
            System.out.println(user1==user2);
            sqlSession.close();

        }
    }
    ```

4. 查看日志输出
    - 连接数据库，到最后关闭session，只进行了一次数据库查询
    - 在第二次查询的时候，直接给出结果，没有再次从DB查询

    ```prolog
    Opening JDBC Connection
    Tue Aug 17 19:16:45 EDT 2021 WARN: Establishing SSL connection without server's identity verification is not recommended. According to MySQL 5.5.45+, 5.6.26+ and 5.7.6+ requirements SSL connection must be established by default if explicit option isn't set. For compliance with existing applications not using SSL the verifyServerCertificate property is set to 'false'. You need either to explicitly disable SSL by setting useSSL=false, or set useSSL=true and provide truststore for server certificate verification.
    Created connection 1287934450.
    ==>  Preparing: select * from user where id = ?
    ==> Parameters: 1(Integer)
    <==    Columns: id, name, pwd
    <==        Row: 1, kaitan, 123456
    <==      Total: 1
    User(id=1, name=kaitan, pwd=123456)
    User(id=1, name=kaitan, pwd=123456)
    true
    Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@4cc451f2]
    Returned connection 1287934450 to pool.
    ```

### 缓存执行

1. 所有select语句的结果会被缓存
2. insert, update, delete会刷新缓存
    - 增删改DB之后，可能会改变原有数据，所以原有储存的select缓存会失效
    - 比如之前select的是user 1，修改了user 2之后，user 1 的缓存也会消失，需要重新查询
3. 缓存会使用LRU （least recently used）清除策略
4. 缓存不定时进行刷新
5. 缓存失效
    1. select 的是不同的内容
    2. 增删改操作，会使原本的缓存失效
    3. 查询不同的Mapper.xml
    4. 手动清除缓存

        ```java
        public class MyTest {
            @Test
            public void test(){
                SqlSession sqlSession = MybatisUtils.getSqlSession();
                UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                User user1 = mapper.queryUsersById(1);
                System.out.println(user1);

                sqlSession.clearCache();

                User user2 = mapper.queryUsersById(1);
                System.out.println(user2);
                System.out.println(user1==user2);
                sqlSession.close();

            }
        }
        ```

### 一级缓存-小结

- 一级缓存默认开启，也无法关闭，但可以手动清除
- 只在一次SQL Session中有效，也就是拿到connection到关闭connection之间有效
- 只有在User多次查询相同内容的时候有效
- 可以把ta理解为一个map，存key-value，取的时候直接取
    - 如下，debug模式中可见，cache信息储存在mapper 的methodCache中，就是使用map方式，key是查询id

        ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2079.png)

### 二级缓存

- 二级缓存也叫全局缓存，基于namespace，在一个mapper中全部生效
- 一级缓存作用域太低了，所以诞生了二级缓存
- 工作机制
    - 一个session查询某数据，这个数据会被放在当前session的一级cache中
    - 如果当前session关闭了，一级cache就没有了；如果想在session结束后依旧保有cache，需要将data保存在二级cache中
    - 新的session可以从二级cache中读取之前旧session保存的东西
    - 不同的mapper查询的data放在自己对应的cache中
- 标签
    - 若想启用global二级缓存，只需要在Mapper xml中添加一个`cache`标签
    - 可以在这个`cache`标签下config有关cache的设定

        ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2080.png)

### 二级缓存-使用步骤

1. 虽然默认是true，一般会在mybatis-config.xml中显式配置cacheEnabled，以增强可读性

    ```xml
    <settings>
        <setting name="cacheEnabled" value="true"/>
    </settings>
    ```

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2081.png)

2. 在mapper.xml中添加`cache`标签
    - cache标签放到mapper标签中
    - 如果某个select语句不想启用cache，可以通过`useCache="false"` 设置

        ```xml
        <mapper namespace="com.kaitan.dao.UserMapper">

            <!--在当前mapper中使用二级缓存-->
            <cache/>

            <select id="queryUsersById" resultType="user" useCache="false">
                select * from user where id = #{id}
            </select>
        </mapper>
        ```

    - 也可以自定义cache config

        ```xml
        <mapper namespace="com.kaitan.dao.UserMapper">

        <!--在当前mapper中使用二级缓存-->
        <cache eviction="FIFO"
                   flushInterval="60000"
                   size="512"
                   readOnly="true"/>

            <select id="queryUsersById" resultType="user" useCache="false">
                select * from user where id = #{id}
            </select>
        </mapper>
        ```

3. 测试
    - 测试1：
        - 在mybatis-config不配置`<cache/>` , 分别开两个session执行相同select语句，看session2是否会走session1的cache

            ```java
            @Test
            public void testCache1(){
                SqlSession sqlSession1 = MybatisUtils.getSqlSession();
                SqlSession sqlSession2 = MybatisUtils.getSqlSession();
                UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
                UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
                User user1 = mapper1.queryUsersById(1);
                System.out.println(user1);

                System.out.println("-------");
                User user2 = mapper2.queryUsersById(1);
                System.out.println(user2);

                sqlSession1.close();
                sqlSession2.close();

            }
            ```

        - 没用cache，因为没有开二级cache（全局cache）

            ```prolog
            Opening JDBC Connection
            Tue Aug 17 22:10:55 EDT 2021 WARN: Establishing SSL connection without serve...
            Created connection 1287934450.
            ==>  Preparing: select * from user where id = ?
            ==> Parameters: 1(Integer)
            <==    Columns: id, name, pwd
            <==        Row: 1, kaitan, 123456
            <==      Total: 1
            User(id=1, name=kaitan, pwd=123456)
            ==>  Preparing: select * from user where id = ?
            ==> Parameters: 1(Integer)
            <==    Columns: id, name, pwd
            <==        Row: 1, kaitan, 123456
            <==      Total: 1
            User(id=1, name=kaitan, pwd=123456)
            false
            Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@4cc451f2]
            Returned connection 1287934450 to pool.
            ```

        - 即使配置了 `<cache/>` ，第二次查询仍然走不了cache，因为第一个session尚未关闭
    - 测试2
        - 在mybatis-config不配置`<cache/>` , 分别开两个session执行相同select语句，等session1close之后，再开session2，看session2是否会走session1的cache

            ```java
            @Test
            public void testCache2(){
                SqlSession sqlSession1 = MybatisUtils.getSqlSession();
                UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
                User user1 = mapper1.queryUsersById(1);
                System.out.println(user1);
                sqlSession1.close();

                System.out.println("-------");

                SqlSession sqlSession2 = MybatisUtils.getSqlSession();
                UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
                User user2 = mapper2.queryUsersById(1);
                System.out.println(user2);
                sqlSession2.close();

                System.out.println(user1==user2);
            }
            ```

        - session没有使用cache，因为没有打开二级cache

            ```prolog
            Created connection 692331943.
            ==>  Preparing: select * from user where id = ?
            ==> Parameters: 1(Integer)
            <==    Columns: id, name, pwd
            <==        Row: 1, kaitan, 123456
            <==      Total: 1
            User(id=1, name=kaitan, pwd=123456)
            Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@294425a7]
            Returned connection 692331943 to pool.
            -------
            Opening JDBC Connection
            Checked out connection 692331943 from pool.
            ==>  Preparing: select * from user where id = ?
            ==> Parameters: 1(Integer)
            <==    Columns: id, name, pwd
            <==        Row: 1, kaitan, 123456
            <==      Total: 1
            User(id=1, name=kaitan, pwd=123456)
            Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@294425a7]
            Returned connection 692331943 to pool.
            false
            ```

    - 测试3
        - 在mybatis-config配置`<cache/>` , 分别开两个session执行相同select语句，等session1close之后，再开session2，看session2是否会走session1的cache

            ```java
            @Test
            public void testCache2(){
                SqlSession sqlSession1 = MybatisUtils.getSqlSession();
                UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);
                User user1 = mapper1.queryUsersById(1);
                System.out.println(user1);
                sqlSession1.close();

                System.out.println("-------");

                SqlSession sqlSession2 = MybatisUtils.getSqlSession();
                UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
                User user2 = mapper2.queryUsersById(1);
                System.out.println(user2);
                sqlSession2.close();

                System.out.println(user1==user2);
            }
            ```

        - session使用了cache，因为打开二级cache，第一个session结束的时候，存入了二级cache，然后新的session2开的时候，可以从二级cache读取信息

            ```prolog
            Created connection 232307208.
            ==>  Preparing: select * from user where id = ?
            ==> Parameters: 1(Integer)
            <==    Columns: id, name, pwd
            <==        Row: 1, kaitan, 123456
            <==      Total: 1
            User(id=1, name=kaitan, pwd=123456)
            Closing JDBC Connection [com.mysql.jdbc.JDBC4Connection@dd8ba08]
            Returned connection 232307208 to pool.
            -------
            Cache Hit Ratio [com.kaitan.dao.UserMapper]: 0.5
            User(id=1, name=kaitan, pwd=123456)
            true
            ```

- 可能的问题
    - 报错

        ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2082.png)

    - 解决方案：将实体类序列化

        ```java
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public class User implements Serializable {
            private int id;
            private String name;
            private String pwd;
        }
        ```

### 二级cache小结

- 只要开启了二级cache，在同一个mapper下就有效
- 所有的data都会先放在一级cache中
- 只有当session提交，或者关闭的时候，才会转存到二级cache中！

### MyBatis缓存原理

- 流程图

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2083.png)

- 解读
    - 最上面是User，其实先在二级cache看有没有data，有的话直接取用，没有则查看一级cache，都没有则重新访问DB访问data
    - 会有多个SqlSession来访问DB
    - 每个SqlSession连接DB查询data之后，会把查询结果放到自己的一级cache中
    - 当某session关闭的时候，其一级cache的内容会被提交保存到二级cache

### useCache & flushCache

- select有useCache标签，表示在执行sql语句时，是否要启用cache功能
- update, delete不涉及到cache，所以没有useCache标签，相应的，他们有flushCache标签，控制ta们的执行是否会刷新之前的cache

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2084.png)

### 自定义缓存 `EhCache`

缓存一般会用Redis，这里重点掌握自定义Cache的概念

- EhCache是一个纯Java进程内cache架构，具有快速，精干的特点，时Hibernate中默认的CacheProvider
- Hibernate可以自动运行SQL，但基本已经淘汰了
- EhCache是一种广泛使用的开源Java分布式cache，主要面向通用cache
- 不过现在Redis出来了，一般使用Redis

### `EhCache`使用

- 导包

    ```xml
    <dependency>
        <groupId>org.mybatis.caches</groupId>
        <artifactId>mybatis-ehcache</artifactId>
        <version>1.1.0</version>
    </dependency>
    ```

- 在mybatis-config.xml的cache标签中指定EhCache

    ```xml
    <cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
    ```

- 添加配置文件`src/main/resources/ehcache.xml`
    - diskStore：缓存路径，ehcache分为memory和disk两级，此属性定义disk的cache位置
    - user.home：用户main/home directory
    - user.dir：用户当前working directory
    - java.io.tmpdir：默认临时文件路径

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
             updateCheck="false">
        <!--
           diskStore：为缓存路径，ehcache分为内存和磁盘两级，此属性定义磁盘的缓存位置。参数解释如下：
           user.home – 用户主目录
           user.dir  – 用户当前工作目录
           java.io.tmpdir – 默认临时文件路径
         -->
        <diskStore path="./tmpdir/Tmp_EhCache"/>
        
        <defaultCache
                eternal="false"
                maxElementsInMemory="10000"
                overflowToDisk="false"
                diskPersistent="false"
                timeToIdleSeconds="1800"
                timeToLiveSeconds="259200"
                memoryStoreEvictionPolicy="LRU"/>
     
        <cache
                name="cloud_user"
                eternal="false"
                maxElementsInMemory="5000"
                overflowToDisk="false"
                diskPersistent="false"
                timeToIdleSeconds="1800"
                timeToLiveSeconds="1800"
                memoryStoreEvictionPolicy="LRU"/>
        <!--
           defaultCache：默认缓存策略，当ehcache找不到定义的缓存时，则使用这个缓存策略。只能定义一个。
         -->
        <!--
          name:缓存名称。
          maxElementsInMemory:缓存最大数目
          maxElementsOnDisk：硬盘最大缓存个数。
          eternal:对象是否永久有效，一但设置了，timeout将不起作用。
          overflowToDisk:是否保存到磁盘，当系统当机时
          timeToIdleSeconds:设置对象在失效前的允许闲置时间（单位：秒）。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。
          timeToLiveSeconds:设置对象在失效前允许存活时间（单位：秒）。最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。
          diskPersistent：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false.
          diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。
          diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。
          memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
          clearOnFlush：内存数量最大时是否清除。
          memoryStoreEvictionPolicy:可选策略有：LRU（最近最少使用，默认策略）、FIFO（先进先出）、LFU（最少访问次数）。
          FIFO，first in first out，这个是大家最熟的，先进先出。
          LFU， Less Frequently Used，就是上面例子中使用的策略，直白一点就是讲一直以来最少被使用的。如上面所讲，缓存的元素有一个hit属性，hit值最小的将会被清出缓存。
          LRU，Least Recently Used，最近最少使用的，缓存的元素有一个时间戳，当缓存容量满了，而又需要腾出地方来缓存新的元素的时候，那么现有缓存元素中时间戳离当前时间最远的元素将被清出缓存。
       -->

    </ehcache>
    ```

    - defaultCache: 默认缓存策略，dangehcache找不到定义的缓存时，使用这个策略
    - name:缓存名称。
    - maxElementsInMemory：缓存最大个数。
    - eternal:对象是否永久有效，一但设置了，timeout将不起作用。
    - timeToIdleSeconds：设置对象在失效前的允许闲置时间（单位：秒）。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。
    - timeToLiveSeconds：设置对象在失效前允许存活时间（单位：秒）。最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。
    - overflowToDisk：当内存中对象数量达到maxElementsInMemory时，Ehcache将会对象写到磁盘中。
    - diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。
    - maxElementsOnDisk：硬盘最大缓存个数。
    - diskPersistent：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false.
    - diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。
    - memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
    - clearOnFlush：内存数量最大时是否清除。
    - memoryStoreEvictionPolicy: LRU, FIFO, LFU
- 自定义使用缓存接口
    - 实现Cache接口，然后重写方法

    ```java
    package com.kaitan.utils;
    import org.apache.ibatis.cache.Cache;

    public class MyCache implements Cache {
        @Override
        ...
    }
    ```

### 引用缓存

- 概念

    ![Untitled](%E3%80%90%E7%8B%82%E3%80%91MyBatis%2035654ec344a64c00a6816da6be99f80f/Untitled%2085.png)

### 练习题

- 根据接口，完成mybatis-11的mapper.xml部分