``` terraform
data "http" "my_ip" {
  url = "http://ip-api.com/json/?lang=zh-CN"
}

locals {
  ip_info = jsondecode(data.http.my_ip.response_body)
}

output "ip_info" {
  value = {
    ip          = local.ip_info.query
    country     = local.ip_info.country
    region      = local.ip_info.regionName
    city        = local.ip_info.city
    isp         = local.ip_info.isp
    latitude    = local.ip_info.lat
    longitude   = local.ip_info.lon
  }
}
```

``` terraform
data "local_file" "foo" {
  filename = "${path.module}/demo.txt"
}

output "data" {
    value = data.local_file.foo.content
}
```

``` terraform
provider "aws" {
    region = "ap-south-1"
}

data "aws_instances" "example" {}

output "aws_instances_ids" {
    value = data.aws_instances.example.ids
}
```