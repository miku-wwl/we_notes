``` terraform
variable "local_sensitive_file" {
  default = "supersecretpassw0rd11111111"
}


resource "local_file" "foo" {
  content  = var.local_sensitive_file
  filename = "password.txt"
}


output "pass" {
  sensitive = true
  value = local_file.foo.content
}
```