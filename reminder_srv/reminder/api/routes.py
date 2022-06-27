from . import bp

@bp.route("/")
def hello_world():
    return "<p>Hello, World!</p>"