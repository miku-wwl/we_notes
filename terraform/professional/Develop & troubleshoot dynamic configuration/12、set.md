``` terraform
variable "my-set" {
  type    = set(string)
  default = ["alice", "bob", "john", "alice"]
}

output "myset" {
  value = var.my-set
}
```