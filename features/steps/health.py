from behave import *
import requests

@given('we have a service running')
def step_impl(context):
    pass

@when('we query /health')
def step_impl(context):
    url = 'http://localhost:8080/health'
    response = requests.get(url)
    context.response = response

@then('the response should be 200')
def step_impl(context):
    assert context.response.ok is True