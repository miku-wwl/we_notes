``` terraform
variable "password" {
  default = "supersecretpassw0rd"
}
resource "local_file" "foo" {
  content  = var.password
  filename = "password.txt"
}
```

使用-var 传值
terraform apply -var password=666