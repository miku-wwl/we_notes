使用docker stats 发现postgres cpu占用异常

![alt text](d9a0497bc1750fe65af9103efdcff52.png)


修改方法
进入容器：docker exec -it ba5 bash
使用postgres登录：su postgres
连接数据库：psql -U postgres
修改postgres用户密码：Alter user postgres with password ‘123456’;
退出数据库连接：\q
