# 1.项目简介
##看板系统
看板管理，Kanban方法最初起源于丰田的JIT（Just In Time），是丰田生产模式中的重要概念，指为了达到准时生产方式（JIT）控制现场生产流程的工具。准时生产方式中的拉式（Pull）生产系统可以使信息的流程缩短，并配合定量、固定装货容器等方式，而使生产过程中的物料流动顺畅, 之后作为一种高效管理软件开发流程的技术和思想应用于互联网行业。
Kanban方法以价值流动为核心，不断发现团队中的瓶颈工序进行改进，使价值流动更加顺畅和快速。简单的说，就是保证软件的持续集成并且不让开发团队超负荷
##使用看板的好处

1）可视化工作流程。所有的工作进度会全部显示在Kanban上，每一个人都可以一目了然了解自己的工作进度以及项目进度、流程以及瓶颈，能够增加团队之间的协作，使他们能够集中精力促进流程。

2）限制在制工作，可以避免任务切换导致的问题，并减少不断重新确定项目优先次序的需要。WIP 限制释放了看板的全部潜力，使团队能够在更健康、更可持续的环境中以比以往更快的速度提供高质量的工作。

3）管理并优化流程。Kanban能够动态地展示团队工作流程的瓶颈。一旦项目管理者发现某个环节影响到团队进度，t就可以及时调配相应的资源改进这个环节，使流程得到优化。

4）缩短开发周期。可以更好的发现问题，解决问题，从而找到更科学的方法提高开发效率

##使用人群
工作管理开发等都可以有所应用

##系统介绍
分为前后端两部分，包含从用户的开始注册登录以及使用，主要是将工作主要内容以卡片的形式更加直观的展示出来。

#2.看板系统-后端管理
##第一轮迭代计划
```
第一次会议：初步了解看板系统的大致使用方法，对工作项（卡片）的存储结构详细分解，计划2天内完成初步规划的数据表字段，同时反思这种结构是否满足看板的需要。
```
```
第二次会议：SpringBoot+mybatis框架的简单应用和Git的使用
           开始推送各自版本
```
```
第三次会议：统一规范规则
```
```
第四次会议：加入前端vue，实现异步通信，对数据表能进行增删改查
```
```
第五次会议：后端加入对控制器的测试，加入燃尽图，开始合并版本
```
```
第六次会议：多表关联查询，第2次合并后的版本加入了新的框架Swagger，辅助文档。。给前后端开发者查阅使用。
```
```
第七次会议：统一前后端传参格式，后端功能开始向用户权限操作转移，开始面向具体使用编程，前端优先从Bootstrap框架提供的样式开始
```
```
第八次会议：统一数据格式，后端控制器返回的数据格式全部改为Msg类型，其中data属性为具体的数据，result为执行的成功与否，message为执行后的消息。
```
```
第九次会议：再次明确格式的统一要求，和编码方式，修复各自的bug，统一估算点的衡量标准
```
```
第十次会议：动态显示登录状态，主要问题集中在前端和后端的通信和交互上，前端应该使用什么技术与后端通信，前端如何获得后端数据，如何将拿到的数据显示在前端页面上，又如何使用前端框架美化页面。
```
```
第十一次会议：加入sort字段，支持排序，解决前端与后端交互之间的bug
```
```
第十二次会议：简单统一前端的样式界面
``````
##3.版本更新：
###版本kanbanV14.0:
新增用户，故事，任务，工作流的管理界面，实现可登陆对其管理页面的增加删除修改查找。


##4.关于我们：
###一群来自武汉高校热爱编程码码的少年，还有一杯“老酒”的加持。
###联系我们：kan_ban@163.com。
###以上开发均为老酒新茶团队原创，一切解释权归老酒新茶团队所有。


