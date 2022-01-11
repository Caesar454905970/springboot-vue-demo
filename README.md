github项目：
type="jdk" jdkName="corretto-1.8" 

运行

```
挂载到后台进行启动:
nohup java -jar springboot-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod &

查看启动参数
tailf nohup.out
```

