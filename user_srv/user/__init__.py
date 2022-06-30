from flask import Flask
from flask_sqlalchemy import SQLAlchemy

from user.config import Config

db = SQLAlchemy()

def create_app(config_class=Config):
    app = Flask(__name__)
    app.config.from_object(config_class)

    db.init_app(app)

    from user.api import bp as api_bp
    app.register_blueprint(api_bp)

    @app.before_first_request
    def create_tables():
        db.create_all()

    return app
