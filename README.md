<h1 align="center">基于Shiro | JWT整合WxJava实现微信小程序登录</h1>

<p align="center">
    <a href="https://juejin.cn/post/6942706988534988836">👉👉掘金原文教程👈👈</a>
</p>

## 前言

最近在做毕业设计，涉及到微信小程序的开发，要求前端小程序用户使用微信身份登录，登陆成功后，后台返回自定义登录状态token给小程序，后续小程序发送API请求都需要携带token才能访问后台数据。

本文是对接微信小程序，实现自定义登录状态的一个完整示例，实现了小程序的自定义登陆，将自定义登陆态token返回给小程序作为登陆凭证。用户的信息保存在数据库中，登陆态token缓存在redis中。涉及的技术栈：
* SpringBoot -> 后端基础环境
* Shiro -> 安全框架
* JWT -> 加密token
* MySQL -> 主库，存储业务数据
* MyBatis-Plus -> 操作数据库
* Redis -> 缓存token和其他热点数据
* Lombok -> 简化开发
* FastJson -> json消息处理
* RestTemplate -> 优雅的处理web请求

项目GitHub地址：https://github.com/gongsir0630/shiro-jwt-demo

## 特性
* 基于WxJava对接微信小程序，实现用户登录、消息处理
* 支持Shiro注解编程，保持高度的灵活性
* 使用JWT进行校验，完全实现无状态鉴权
* 使用Redis存储自定义登陆态token，支持过期时间
* 支持跨域请求

## 准备工作
**基础知识预备：**
* 具备SpringBoot基础知识并且会使用基本注解；
* 了解JWT（Json Web Token）的基本概念，并且会简单操作JWT的 [JAVA SDK](https://github.com/auth0/java-jwt)；
* 了解Shiro的基本概念：Subject、Realm、SecurityManager等（建议去官网学习一下）

**其他说明：**

本文只对shiro和jwt整合进行介绍说明，具体的微信登录实现是使用`RestTemplate`调用我自己的`wx-java-miniapp`项目，该项目基于`WxJava`实现，支持多个小程序登录、消息处理。

**本文使用以下调用处理即可：**
```java
// 1. todo: 微信登录: code + appid -> openId + session_key
// appid: 从配置文件读取
MultiValueMap<String, Object> request = new LinkedMultiValueMap<>();
// 参数封装, 微信登录需要以下参数
request.add("code", code);
// eg: http://localhost:8081/wx/user/{appid}/login
String path = url+"/user/"+appid+"/login";
// 请求
JSONObject dto = restTemplate.postForObject(path, request, JSONObject.class);
log.info("--->>>来自[{}]的返回 = [{}]",path,dto);

// 2. todo: 使用openId和session_key生成自定义登录状态 -> token
```
**项目地址：**

* [wx-java-miniapp -> 可以直接部署使用](https://github.com/gongsir0630/wx-java-miniapp)
* [WxJava -> 官方SDK](https://github.com/Wechat-Group/WxJava)

## 项目使用
0. 使用该项目之前请先部署[wx-java-miniapp](https://github.com/gongsir0630/wx-java-miniapp)


1. clone项目到本地
```shell
git clone https://github.com/gongsir0630/shiro-jwt-demo.git
```

2. 安装redis和mysql,执行`resources/sql/shiro-jwt-demo.sql`脚本,修改`application-dev.yml`配置文件
   

3. mvn编译运行
```shell
mvn clean package
```