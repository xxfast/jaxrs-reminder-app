from flask import Flask
from flask_sqlalchemy import SQLAlchemy
import logging

from user.config import Config

db = SQLAlchemy()

def create_app(config_class=Config):
    app = Flask(__name__)
    app.config.from_object(config_class)

    gunicorn_logger = logging.getLogger('gunicorn.error')
    app.logger.handlers = gunicorn_logger.handlers
    app.logger.setLevel(gunicorn_logger.level)

    db.init_app(app)

    from user.api import bp as api_bp
    app.register_blueprint(api_bp)

    @app.before_first_request
    def create_tables():
        db.create_all()

    return app
