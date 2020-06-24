# Example copied from - https://www.terraform.io/docs/github-actions/setup-terraform.html

terraform {
 backend "remote" {
   organization = "cicd-series"

   workspaces {
     name = "heroku-prod"
   }
 }
}

provider "heroku" {
  version = "~> 2.0"
}

resource "heroku_app" "guestbook" {
  name   = "guestbook"
  region = "us"
}