from flask import Flask
import logging
from mongoengine import connect

from reminder.config import Config

def create_app(config_class=Config):
    app = Flask(__name__)
    app.config.from_object(config_class)

    gunicorn_logger = logging.getLogger('gunicorn.error')
    app.logger.handlers = gunicorn_logger.handlers
    app.logger.setLevel(gunicorn_logger.level)

    connect(
            db=app.config['MONGODB_DB'],
            host=app.config['MONGODB_HOST'],
            port=app.config['MONGODB_PORT'],
            username=app.config['MONGODB_USERNAME'],
            password=app.config['MONGODB_PASSWORD']
        )

    from reminder.api import bp as api_bp
    app.register_blueprint(api_bp)

    return app
