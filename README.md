# quartz_demo
支持动态定时任务和静态定时任务的配置与应用
# 使用规则
* 引入当前demo，再需要使用定时任务的服务（模块）中；
* 创建需要的job类，定义id,name,group,cron;如果需要其他属性，可自行添加；
* 创建处理类;自定义开始任务的具体业务逻辑；可参考demo中的TestController 
