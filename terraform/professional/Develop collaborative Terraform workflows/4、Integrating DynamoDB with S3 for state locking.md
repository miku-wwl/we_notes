``` terraform
terraform {
  backend "s3" {
    bucket = "kplabs-terraform-backend-142"
    key    = "network/demo.tfstate"
    region = "us-west-1"
    dynamodb_table = "terraform-state-locking-142"
  }
}


provider "aws" {
  region = "us-west-1"
}

resource "time_sleep" "wait_150_seconds" {

  create_duration = "5s"
}
```