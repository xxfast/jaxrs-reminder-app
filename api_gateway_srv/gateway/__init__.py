from flask import Flask
from flask_jwt_extended import JWTManager
import logging

from gateway.config import Config

jwt = JWTManager()

def create_app(config_class=Config):
    app = Flask(__name__)
    app.config.from_object(config_class)

    gunicorn_logger = logging.getLogger('gunicorn.error')
    app.logger.handlers = gunicorn_logger.handlers
    app.logger.setLevel(gunicorn_logger.level)

    jwt.init_app(app)

    from gateway.api import bp as api_bp
    app.register_blueprint(api_bp)

    return app
