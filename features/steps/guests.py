from behave import *
import requests

@given('an empty guestbook')
def step_impl(context):
  pass

@when('we query /guests')
def step_impl(context):
  url = 'http://localhost:8080/guests'
  response = requests.get(url)
  context.response = response

@then('the response should be empty')
def step_impl(context):
  assert context.response.status_code == 200
  assert 'guests' in context.response.json()
  guests = context.response.json()['guests']
  assert len(guests) == 0