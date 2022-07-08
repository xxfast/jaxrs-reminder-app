from flask import jsonify, request, abort
from flask_jwt_extended import jwt_required, create_access_token, set_access_cookies, unset_jwt_cookies, current_user

from . import bp, controller
from .microservices_api import APIERROR
from gateway import jwt

# Register a callback function that takes whatever object is passed in as the
# identity when creating JWTs and converts it to a JSON serializable format.
@jwt.user_identity_loader
def user_identity_lookup(user):
    return user


# Register a callback function that loads a user from your database whenever
# a protected route is accessed. This should return any python object on a
# successful lookup, or None if the lookup failed for any reason (for example
# if the user has been deleted from the database).
@jwt.user_lookup_loader
def user_lookup_callback(_jwt_header, jwt_data):
    return jwt_data["sub"]


@bp.route("/user", methods=['GET'])
@jwt_required(optional=True)
def get_user():
    # If user microservice would have more user useful info, it should call it to add it to this response
    username = ''
    if current_user:
        username = str(current_user)

    return jsonify( {"username":username} )

@bp.route("/user/login", methods=['POST'])
def login_user():
    username = request.json['username']
    password = request.json['password']

    # Check login user 
    try:
        controller.login_user(username, password)
    except APIERROR as ex:
        # Force 401 error to show that user could not be authenticated without any details
        abort(401)

    # Create token (with username) & set cookie 
    access_token = create_access_token(identity=username)
    response = jsonify( {"msg": "login successful" })
    set_access_cookies(response, access_token)

    return response

@bp.route("/user/logout", methods=['POST'])
@jwt_required(optional=True)
def logout_user():

    # Remove cookie
    response = jsonify({"msg": "logout successful"})

    unset_jwt_cookies(response)

    return response

@bp.route("/reminder", methods=['GET'])
@jwt_required()
def get_reminders():

    try:
        # Get reminders from user
        result = controller.get_reminders(current_user)
    except APIERROR as ex:
        abort(ex.error_code)

    return jsonify( {"reminders":result} )

@bp.route("/reminder", methods=['POST'])
@jwt_required()
def post_reminders():
    content = request.json['content']

    try:
        reminder_id = controller.post_reminder(current_user, content)
    except APIERROR as ex:
        abort(ex.error_code)

    return jsonify( {'id': reminder_id} )

@bp.route("/reminder/<reminder_id>", methods=['PUT'])
@jwt_required()
def edit_reminders(reminder_id):
    content = request.json['content']

    try:
        controller.put_reminder(current_user, reminder_id, content)
    except APIERROR as ex:
        abort(ex.error_code)

    return jsonify( {'id': reminder_id} )

@bp.route("/reminder/<reminder_id>", methods=['DELETE'])
@jwt_required()
def delete_reminders(reminder_id):

    try:
        controller.delete_reminder(current_user, reminder_id)
    except APIERROR as ex:
        abort(ex.error_code)

    return jsonify( {} )
