variable "my-map" {
  type = map(any)
  default = {
    Name = "Alice"
    Team = "Payments"
  }
}

variable "my-object" {
  type = object({
    id      = string
    enabled = bool
  })
  default = {
    id      = "obj-123"
    enabled = true
  }
}

output "variable_value" {
  value = var.my-map
}

output "object_value" {
  value = var.my-object
}