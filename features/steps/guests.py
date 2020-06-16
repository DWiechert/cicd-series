from behave import *
import requests

@given('an empty guestbook')
def step_impl(context):
  pass

@given('a guestbook with one guest')
def step_impl(context):
  url = 'http://localhost:8080/guests'
  guest = {'name': 'Dan', 'age': 31}
  post_response = requests.post(url, json=guest)
  assert post_response.status_code == 201
  list_response = requests.get(url)
  assert list_response.status_code == 200
  assert 'guests' in list_response.json()
  guests = list_response.json()['guests']
  assert len(guests) == 1
  assert guests[0] == guest

@when('we query /guests')
def step_impl(context):
  url = 'http://localhost:8080/guests'
  response = requests.get(url)
  context.response = response

@when('we add a guest')
def step_impl(context):
  url = 'http://localhost:8080/guests'
  json = {'name': 'Dan', 'age': 31}
  post_response = requests.post(url, json=json)
  context.response = post_response

@when('we delete an unknown guest')
def step_impl(context):
  url = 'http://localhost:8080/guests/unknown'
  response = requests.delete(url)
  context.response = response

@when('we delete a guest')
def step_impl(context):
  url = 'http://localhost:8080/guests/Dan'
  response = requests.delete(url)
  context.response = response

@then('the response should be empty')
def step_impl(context):
  assert context.response.status_code == 200
  assert 'guests' in context.response.json()
  guests = context.response.json()['guests']
  assert len(guests) == 0

@then('the response should be 201')
def step_impl(context):
  assert context.response.status_code == 201

@then('the response should be 409')
def step_impl(context):
  assert context.response.status_code == 409

@then('a single guest should be found with /guests')
def step_impl(context):
  url = 'http://localhost:8080/guests'
  guest = {'name': 'Dan', 'age': 31}
  list_response = requests.get(url)
  assert list_response.status_code == 200
  assert 'guests' in list_response.json()
  guests = list_response.json()['guests']
  assert len(guests) == 1
  assert guests[0] == guest

@then('the response should be 404')
def step_impl(context):
  assert context.response.status_code == 404

@then('the response should be 204')
def step_impl(context):
  assert context.response.status_code == 204

@then('no guests should be found with /guests')
def step_impl(context):
  url = 'http://localhost:8080/guests'
  list_response = requests.get(url)
  assert list_response.status_code == 200
  assert 'guests' in list_response.json()
  guests = list_response.json()['guests']
  assert len(guests) == 0