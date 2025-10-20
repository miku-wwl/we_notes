``` terraform
variable "user_names" {
  type    = set(string)
  default = ["1", "2", "3", "2"]
}

resource "aws_iam_user" "this" {
  for_each = var.user_names
  name     = each.value
}
```