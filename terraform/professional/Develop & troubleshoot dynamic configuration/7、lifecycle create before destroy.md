``` terraform
provider "aws" {
  region = "ap-south-1"
}

resource "aws_instance" "myec2" {
  ami           = "ami-02d26659fd82cf299"
  instance_type = "t2.micro"

  tags = {
    Name = "HelloEarth"
  }

  lifecycle {
    create_before_destroy = true
  }
}
```