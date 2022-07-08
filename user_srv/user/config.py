import os

class Config(object):
    SQLALCHEMY_DATABASE_URI = os.environ.get('DATABASE_URL')

class TestConfig(object):
    SQLALCHEMY_DATABASE_URI = 'sqlite:///:memory:'