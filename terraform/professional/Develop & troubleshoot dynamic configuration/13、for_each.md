``` terraform
variable "user_names" {
  type    = set(string)
  default = ["1", "2", "3", "2"]
}

resource "aws_iam_user" "this" {
  for_each = var.user_names
  name     = each.value
}
```

```
provider "aws" {
  region = "ap-south-1"
}


variable "my-map" {
    default = {
        key = "ami-02d26659fd82cf299"
        key1 = "ami-06fa3f12191aa3337"
    }
}

resource "aws_instance" "web" {
  for_each      = var.my-map
  ami           = each.value
  instance_type = "t2.micro"

  tags = {
    Name = each.key
  }
}ee
```