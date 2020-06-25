# Example copied from - https://www.terraform.io/docs/github-actions/setup-terraform.html

//terraform {
// backend "remote" {
//   organization = "cicd-series"
//
//   workspaces {
//     name = "heroku-prod"
//   }
// }
//}

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
  buildpacks = ["https://github.com/heroku/heroku-buildpack-scala"]

  source = {
    url = "https://github.com/DWiechert/cicd-series/archive/0.0.1.tar.gz"
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