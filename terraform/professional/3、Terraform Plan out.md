### local_file.tf

```sh
resource "local_file" "foo" {
  content  = "Hello World"
  filename = "terraform.txt"
}
```

### Commands Used:
```sh
terraform plan -out=infra_plan
terraform apply infra_plan
```
```sh
terraform show infra_plan
terraform show -json infra_plan
```
```sh
terraform show -json infra_plan | jq
```