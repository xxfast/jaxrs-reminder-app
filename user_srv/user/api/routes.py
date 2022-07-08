from flask import jsonify, request, abort, current_app

from user import db
from . import bp, model

@bp.route("/<username>", methods=['GET'])
def get_user(username):
    user = model.User.query.filter_by(username=username).first()
    if not user:
        current_app.logger.info('User not found - ' + str(user))
        abort(404)
    
    return jsonify( {'username':user.username} )

@bp.route("/", methods=['POST'])
def post_user():
    current_app.logger.info('Request.json - ' + str(request.json))

    username = request.json['username']
    password = request.json['password']
    
    user = model.User.query.filter_by(username=username).first()
    if user:
        current_app.logger.info('USER already exist DB - ' + str(user))
        abort(400)

    new_user = model.User(username=username)
    new_user.set_password(password)
    db.session.add(new_user)
    db.session.commit()
    current_app.logger.info( 'New user - ' + str(new_user.username) )

    return jsonify( {'username':new_user.username} )

@bp.route("/<username>", methods=['PUT'])
def edit_user(username):
    current_app.logger.info('Request.json - ' + str(request.json))

    user = model.User.query.filter_by(username=username).first()
    if not user:
        current_app.logger.info('User not found - ' + str(user))
        abort(404)

    user.set_password(request.json['password'])
    db.session.commit()
    current_app.logger.info('User password changed - ' + str(user))
        
    return jsonify( {} )

@bp.route("/<username>/login", methods=['POST'])
def login_user(username):
    user = model.User.query.filter_by(username=username).first()
    if not user:
        current_app.logger.info('User not found - ' + str(user))
        abort(404)

    if not user.check_password(request.json['password']):
        current_app.logger.info('User & password not match - ' + str(user))
        abort(400)

    current_app.logger.info('User & password verified - ' + str(user))
    return jsonify( {} )

@bp.route("/<username>/reminder", methods=['POST'])
def add_reminder(username):
    current_app.logger.info('Request.json - ' + str(request.json))
    reminder_id = request.json['reminder_id']

    user = model.User.query.filter_by(username=username).first()
    if not user:
        current_app.logger.info('User not found - ' + str(user))
        abort(404)

    reminder = model.Reminder.query.filter_by(id=reminder_id).first()
    if reminder:
        current_app.logger.info('Reminder Id already in DB - ' + str(reminder))
        abort(400)

    new_reminders = model.Reminder(id=reminder_id, user_id=user.id)
    db.session.add(new_reminders)
    db.session.commit()
    current_app.logger.info('New reminder - ' + str(new_reminders))

    return jsonify( {'reminders':user.get_reminders_id_list()} )

@bp.route("/<username>/reminder", methods=['GET'])
def get_reminders(username):
    user = model.User.query.filter_by(username=username).first()
    if not user:
        current_app.logger.info('User not found - ' + str(user))
        abort(404)

    return jsonify( {'reminders':user.get_reminders_id_list()} )

@bp.route("/<username>/reminder/<reminder_id>", methods=['DELETE'])
def delete_reminder(username, reminder_id):
    user = model.User.query.filter_by(username=username).first()
    if not user:
        current_app.logger.info('User not found - ' + str(user))
        abort(404)

    reminder = model.Reminder.query.filter_by(id=reminder_id, user_id=user.id).first()
    if not reminder:
        current_app.logger.info('Reminder Id not found - ' + str(reminder_id))
        abort(400)

    db.session.delete(reminder)
    db.session.commit()

    current_app.logger.info('Reminder deleted - ' + str(reminder))

    return jsonify( {'reminders':user.get_reminders_id_list()} )
