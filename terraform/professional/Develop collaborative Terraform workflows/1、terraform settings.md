``` terraform settings
terraform {
  required_version = "1.13.3"

  required_providers {
    aws = {
      version = "5.54.1"
      source = "hashicorp/aws"
    }
  }
}

provider "aws" {
  region = "us-west-1"
}

resource "aws_security_group" "sg_01" {
  name = "app_firewall"
}
```