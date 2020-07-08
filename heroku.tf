# Example copied from - https://www.terraform.io/docs/github-actions/setup-terraform.html

terraform {
 backend "remote" {
   organization = "<change to your Terraform Cloud org>"

   workspaces {
     name = "<change to your Terraform Cloud workspace>"
   }
 }
}

provider "heroku" {
  version = "~> 2.0"
}

variable "build_url" {
  type = string
}

resource "heroku_app" "guestbook_app" {
  name   = "<change to your Herkou app name>"
  region = "us"
}

# Build code & release to the app
resource "heroku_build" "guestbook_build" {
  app = heroku_app.guestbook_app.name
  buildpacks = ["https://github.com/heroku/heroku-buildpack-scala"]

  source = {
    url = var.build_url
  }
}

# Launch the app's web process by scaling-up
resource "heroku_formation" "guestbook_formation" {
  depends_on = [heroku_build.guestbook_build]

  app        = heroku_app.guestbook_app.name
  type       = "web"
  quantity   = 1
  size       = "free"
}

output "guestbook_url" {
  value = "https://${heroku_app.guestbook_app.name}.herokuapp.com"
}
