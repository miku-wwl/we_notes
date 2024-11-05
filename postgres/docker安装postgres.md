docker pull postgres

docker run --name postgres -e POSTGRES_PASSWORD=nixx -p 5432:5432 -d postgres

镜像导出导入
docker save busybox > busybox.tar
docker load < busybox.tar
