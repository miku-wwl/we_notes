root@lavm-zs4titdxl5:/home/minio# docker image inspect postgres:latest | grep -i version
        "DockerVersion": "",
                "GOSU_VERSION=1.17",
                "PG_VERSION=17.0-1.pgdg120+1",
root@lavm-zs4titdxl5:/home/minio# docker tag postgres:latest postgres:17.0
root@lavm-zs4titdxl5:/home/minio# docker images
REPOSITORY        TAG       IMAGE ID       CREATED       SIZE
apache/rocketmq   5.3.1     e2f81b2b714b   3 weeks ago   416MB
postgres          17.0      d57ed788c154   5 weeks ago   434MB
postgres          latest    d57ed788c154   5 weeks ago   434MB
elasticsearch     7.15.2    9cd5b752c86e   3 years ago   791MB
root@lavm-zs4titdxl5:/home/minio#
