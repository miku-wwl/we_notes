docker pull postgres

docker run --name root -e POSTGRES_PASSWORD=123123 -d postgres

镜像导出导入
docker save busybox > busybox.tar
docker load < busybox.tar
