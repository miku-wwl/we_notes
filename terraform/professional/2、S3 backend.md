``` terraform
terraform {
  backend "s3" {
    bucket = "demo-user-sample-bucket"
    region = "us-west-1"
  }
}

variable "file_name" {
  default = "hello.txt"
}

resource "local_file" "foo" {
  content  = "foo!"
  filename = var.file_name
}
```


