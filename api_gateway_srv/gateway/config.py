from datetime import timedelta
import os

class Config(object):
    USER_SERVICE_HOST = os.environ.get('USER_SERVICE_HOST')
    REMINDER_SERVICE_HOST = os.environ.get('REMINDER_SERVICE_HOST')

    SECRET_KEY = os.environ.get('SECRET_KEY') or 'check_secret_key_anywhere_else'

    JWT_SECRET_KEY = os.environ.get('JWT_SECRET_KEY') or SECRET_KEY
    JWT_TOKEN_LOCATION = ["cookies"]
    JWT_COOKIE_SECURE = True
    JWT_CSRF_CHECK_FORM = True
    JWT_COOKIE_SAMESITE = 'Strict'
    JWT_ACCESS_TOKEN_EXPIRES = timedelta(days=1)
    
    JWT_COOKIE_CSRF_PROTECT = False

class TestConfig(object):
    pass
