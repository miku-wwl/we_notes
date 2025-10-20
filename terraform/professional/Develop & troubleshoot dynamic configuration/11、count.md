``` terraform
provider "aws" {
  region     = "ap-south-1"
}

variable "iam_names" {
  type = list
  default = ["user-01","user-02","user-03"]
}

resource "local_file" "iam_user" {
  count = length(var.iam_names)
  content = "IAM User: ${var.iam_names[count.index]}"
  filename = "${var.iam_names[count.index]}.txt"
}
```