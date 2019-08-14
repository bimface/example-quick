# 功能介绍

基于BIMFACE的快速入门，可以上传源文件，发起文件转换，发起模型集成，生成离线包，查看文件列表和转换列表的功能

# 运行必须的组建

- JDK 1.8
- Maven
- MySql 5.5+

# 如何在本地运行？
## 使用git下载源代码

```
git clone https://github.com/bimface/bimface-java-sample.git
```

## 新建数据库表
在数据库里面新建三张表，建表语句参考quick.sql

## 修改application-test.properties

```
app.key={appKey}
app.secret={appSecret}
spring.datasource.url=jdbc:mysql://{数据库IP}:3306/{数据库名}?useUnicode=true&characterEncoding=UTF-8
spring.datasource.username={数据库用户名}
spring.datasource.password={数据库连接密码}
```



## 如果需要显示默认的文件、转换和集成列表，可以按如下格式修改application-test.properties（若不需要，可以忽略此步）
```
init.files=fileId,[fileId]...
init.integrates=integrateId,[integrateId]...
init.integrate.files=integrateId:fileId,[fileId];[integrateId:fileId,[fileId]]...
```



## 使用Maven编译JAVA程序

进入根目录
```
mvn clean install -DskipTests
```

## 运行打包后的Jar程序

进入打包的target目录，使用命令行运行
```
java -jar example-quick.jar
```

## 在网页中访问

```
http://localhost:9004/eg-quick
```
