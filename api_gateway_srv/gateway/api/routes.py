from flask import jsonify, request, abort
from flask_jwt_extended import jwt_required, create_access_token, set_access_cookies, unset_jwt_cookies, current_user

from . import bp, services

@bp.route("/user/login", methods=['POST'])
def login_user():
    username = request.json['username']
    password = request.json['password']

    # Login user 
    response = services.login_user(username, password)
    if not response:
        abort(401)

    # Create token (with username) & set cookie 
    access_token = create_access_token(identity=username)
    set_access_cookies(response, access_token)

    return jsonify( {} )

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

    # Get reminders from user
    result = services.get_reminders(current_user)

    return jsonify( {"reminders":result} )

@bp.route("/reminder", methods=['POST'])
@jwt_required()
def post_reminders():
    content = request.json['content']

    reminder_id = services.post_reminder(current_user, content)
    if not reminder_id:
        abort(400)

    return jsonify( {'id': reminder_id} )

@bp.route("/reminder/<reminder_id>", methods=['PUT'])
@jwt_required()
def edit_reminders(reminder_id):
    content = request.json['content']

    reminder_id = services.put_reminder(current_user, content)
    if not reminder_id:
        abort(400)

    return jsonify( {'id': reminder_id} )

@bp.route("/reminder/<reminder_id>", methods=['DELETE'])
@jwt_required()
def delete_reminders(reminder_id):

    result = services.delete_reminder(current_user, reminder_id)
    if not result:
        abort(404)

    return jsonify( {} )
