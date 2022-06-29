import pytest
from reminder import create_app
from reminder.config import TestConfig

@pytest.fixture()
def app():
    app = create_app(TestConfig)
    # app.config.update({
    #     "TESTING": True,
    # })

    # other setup can go here

    yield app

    # clean up / reset resources here


@pytest.fixture()
def client(app):
    return app.test_client()


@pytest.fixture()
def runner(app):
    return app.test_cli_runner()