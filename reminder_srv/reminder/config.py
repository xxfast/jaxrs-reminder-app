import os

class Config(object):
    MONGODB_DB = os.environ.get('MONGODB_DB')
    MONGODB_HOST = os.environ.get('MONGODB_HOST')
    MONGODB_PORT = int(os.environ.get('MONGODB_PORT','27017'))
    MONGODB_USERNAME = os.environ.get('MONGODB_USERNAME')
    MONGODB_PASSWORD = os.environ.get('MONGODB_PASSWORD')

class TestConfig(object):
    MONGODB_DB = 'pytest'
    MONGODB_HOST = 'mongomock://localhost'
    MONGODB_PORT = 27017
    MONGODB_USERNAME = 'root'
    MONGODB_PASSWORD = 'example'
