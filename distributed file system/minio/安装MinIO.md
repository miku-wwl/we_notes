[MinIO | Code and downloads to create high performance object storage](https://min.io/download?license=agpl&platform=linux)

wget https://dl.min.io/server/minio/release/linux-amd64/minio

chmod +x minio

MINIO_ROOT_USER=admin MINIO_ROOT_PASSWORD=password ./minio server /mnt/data --console-address ":9001"

使用 nohup 后台运行 MinIO
nohup ./minio server /mnt/data --console-address ":9001" &
