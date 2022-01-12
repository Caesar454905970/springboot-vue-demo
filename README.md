```

vo:参数对象（接收到前端的参数；后端返回给前端的参数）
entity: model定义数据库的字段
mapper:定义操作数据库语句的函数方法
serve:里面写逻辑（提供给controller调用）
controller:调用mapper里面函数方法和接受前端传递的参数
domain:公共的方法common
```

github项目：
type="jdk" jdkName="corretto-1.8" 

运行

```
挂载到后台进行启动:
nohup java -jar springboot-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod &

查看启动参数
tailf nohup.out
```

