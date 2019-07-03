# spring-demo
一个可供直接使用的单机spring应用模板，可以直接应用在真实开发项目里.
## 项目包括
-  实现了基本的spring的controller、service、dao、map等各个应用层
-  实现了简易的前端的jsp页面
-  两个示例数据库及相应数据库表
-  加入了拦截器，过滤器
-  使用注解生成了一个登录校验
-  使用注解生成了可切换的多数据源
-  使用spring + jta 实现了多个数据源下的分布式事物
-  新增加了导出多个sheet、多级header的excel的功能
-  引入dubbo rpc服务,配合dubbo-demo即可直接使用
## 后续计划
-  引入redis缓存
-  会持续将项目拆分为多个节点
-  解决线程池访问httpclient部分超时的问题
-  用反射实现多个sheet、多级header的excel导出功能
-  引入点评CAT 做调用链路监控
