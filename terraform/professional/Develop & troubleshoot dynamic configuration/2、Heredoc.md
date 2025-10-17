``` terraform
resource "local_file" "this" {
    filename = "heredoc.txt"
    content = <<-ABC
    This is Line 1
    Now it becomes a multi-line document
    This is getting little confusing to read
    ABC
}
```