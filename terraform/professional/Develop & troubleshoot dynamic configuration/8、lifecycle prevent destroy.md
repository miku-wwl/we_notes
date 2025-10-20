``` terraform
provider "aws" {
  region = "ap-south-1"
}

resource "aws_instance" "myec2" {
  ami           = "ami-06fa3f12191aa3337"
  instance_type = "t2.micro"

  tags = {
    Name = "HelloEarth"
  }
  lifecycle {
    prevent_destroy = true
  }
}
```