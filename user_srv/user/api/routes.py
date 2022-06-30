from flask import jsonify, request, abort

from user import db
from . import bp, model

@bp.route("/<username>", methods=['GET'])
def get_user(username):
    user = model.User.query.filter_by(username=username).first()
    if not user:
        abort(404)
    
    return jsonify( {'username':user.username} )

@bp.route("/", methods=['POST'])
def post_user():
    username = request.json['username']

    user = model.User.query.filter_by(username=username).first()
    if user:
        abort(400)

    new_user = model.User(username=username)
    new_user.set_password(request.json['password'])
    db.session.add(new_user)
    db.session.commit()

    return jsonify( {} )

@bp.route("/<username>", methods=['PUT'])
def edit_user(username):
    user = model.User.query.filter_by(username=username).first()
    if not user:
        abort(404)

    user.set_password(request.json['password'])
    db.session.commit()
    
    return jsonify( {} )

@bp.route("/<username>/login", methods=['POST'])
def login_user(username):
    user = model.User.query.filter_by(username=username).first()
    if not user:
        abort(404)

    if not user.check_password(request.json['password']):
        abort(400)

    return jsonify( {} )

@bp.route("/<username>/reminders", methods=['POST'])
def add_reminder(username):
    reminder_id = request.json['reminder']

    user = model.User.query.filter_by(username=username).first()
    if not user:
        abort(404)

    if model.Reminder.query.filter_by(id=reminder_id).first():
        abort(400)

    new_reminders = model.Reminder(id=reminder_id, user_id=user.id)
    db.session.add(new_reminders)
    db.session.commit()

    return jsonify( {'reminders':user.get_reminders_id_list()} )

@bp.route("/<username>/reminders", methods=['GET'])
def get_reminders(username):
    user = model.User.query.filter_by(username=username).first()
    if not user:
        abort(404)

    return jsonify( {'reminders':user.get_reminders_id_list()} )

@bp.route("/<username>/reminders/<reminder_id>", methods=['DELETE'])
def delete_reminder(username, reminder_id):
    user = model.User.query.filter_by(username=username).first()
    if not user:
        abort(404)

    reminder = model.Reminder.query.filter_by(id=reminder_id).first()
    if not reminder:
        abort(400)

    db.session.delete(reminder)
    db.session.commit()

    return jsonify( {'reminders':user.get_reminders_id_list()} )
