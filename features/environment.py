from behave import *
import requests
import subprocess
import time

def before_all(context):
    print('Starting server')
    process = subprocess.Popen(['java', '-jar', 'cicd-series-assembly.jar'])
    time.sleep(2)
    print('Saving process to context')
    context.proc = process

def after_all(context):
    print('Terminating server')
    context.proc.terminate()
    print('Server terminated')

def before_scenario(context, scenario):
    url = 'http://localhost:8080/clear'
    response = requests.delete(url)
    assert response.status_code == 204