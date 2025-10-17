``` terraform
locals {
  content = file("${path.module}/sample.json")
  parsed  = jsondecode(local.content)
}

output "parsed_json" {
  value = local.parsed
}


variable "file_content" {
  default = <<-ABC
  {
  "app_name": "user-service",
  "version": "1.2.3",
  "enabled": true,
  "ports": [666, 6666],
  "database": {
    "type": "postgres",
    "max_connections": 50,
    "credentials": {
      "username": "db_user",
      "password": "secure_pass123"
    }
  },
  "allowed_ips": ["192.168.1.0/24", "10.0.0.0/8"],
  "resource_limits": {
    "cpu": "2核",
    "memory": "4GB"
  }
}
  ABC
}
resource "local_file" "name" {
  content = jsonencode(var.file_content)
  filename = "${path.module}/output.json"
}
```