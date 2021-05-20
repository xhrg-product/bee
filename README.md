### bee-gateway

基于netty的网关

### 开发手册
1. 利用下面这句话docker启动mysql，用户密码是 root/123456
2. docker run --name mysql5.7 -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7
3. 根据bee.sql文件初始化名称为bee的数据库
4. 启动网关