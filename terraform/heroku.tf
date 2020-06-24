# Example copied from - https://www.terraform.io/docs/github-actions/setup-terraform.html

terraform {
 backend "remote" {
   organization = "cicd-series"

   workspaces {
     name = "heroku-prod"
   }
 }
}

resource "null_resource" "terraform-github-actions" {
 triggers = {
   value = "This resource was created using GitHub Actions!"
 }
}

//resource "heroku_app" "guestbook" {
//  name   = "guestbook"
//  region = "us"
//
//  config_vars = {
//    FOOBAR = "baz"
//  }
//
//  buildpacks = [
//    "heroku/go"
//  ]
//}