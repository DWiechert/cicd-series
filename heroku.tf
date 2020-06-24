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

resource "heroku_app" "guestbook_app" {
  name   = "cicd-series-guestbook"
  region = "us"
}

# Build code & release to the app
resource "heroku_build" "guestbook_build" {
  app = heroku_app.guestbook_app.name
  buildpacks = ["https://github.com/heroku/heroku-buildpack-jvm-common.git"]

  source = {
    path = "target/scala-2.13/cicd-series-assembly-0.1.0-SNAPSHOT.jar"
    version = "2.1.1"
  }
}

# Launch the app's web process by scaling-up
resource "heroku_formation" "example" {
  depends_on = [heroku_build.guestbook_build]

  app        = heroku_app.guestbook_app.name
  type       = "web"
  quantity   = 1
  size       = "Standard-1x"
}

output "example_app_url" {
  value = "https://${heroku_app.guestbook_app.name}.herokuapp.com"
}