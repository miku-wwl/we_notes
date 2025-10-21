``` terraform
terraform {
  backend "s3" {
    bucket = "kplabs-terraform-backend-989"
    key    = "network/terraform.tfstate"
    region = "us-west-1"
  }
}

provider "aws" {
  region = "us-west-1"
}

resource "aws_eip" "lb" {
  domain = "vpc"
}
```