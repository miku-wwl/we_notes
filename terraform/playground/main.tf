resource "local_file" "foo" {
  content  = "Hello World"
  filename = "terraform.txt"
}