import logging
from flask import current_app, jsonify, request, abort

from . import bp, model

@bp.route("/", methods=['POST'])
def post_item():
    current_app.logger.info('Request.json - ' + str(request.json))
    req_data = request.json

    content = req_data.get('content', None)
    if not content:
        current_app.logger.info('Reminder content empty - ' + str(id))
        abort(400)

    id = model.save_reminder(content)

    current_app.logger.info('New reminder, id - ' + str(id))
    return jsonify( { 'id': id } )

@bp.route("/<id>", methods=['GET'])
def get_item(id):
    reminder = model.read_reminder(id)

    # verify wrong id
    if not reminder:
        current_app.logger.info('Reminder Id not found - ' + str(id))
        abort(404)

    return jsonify( { 'content': reminder.content } )

@bp.route("/<id>", methods=['PUT'])
def put_item(id):
    current_app.logger.info('Request.json - ' + str(request.json))

    req_data = request.json

    content = req_data.get('content', None)
    if not content:
        current_app.logger.info('Reminder content empty - ' + str(id))
        abort(400)

    if not model.edit_reminder(id, content):
        current_app.logger.info('Reminder Id not found - ' + str(id))
        abort(404)

    current_app.logger.info('Reminder changed, id - ' + str(id))
    return jsonify( { 'id': id } )

@bp.route("/<id>", methods=['DELETE'])
def delete_item(id):
    if not model.delete_reminder(id):
        current_app.logger.info('Reminder Id not found - ' + str(id))
        abort(404)

    current_app.logger.info('Reminder deleted, id - ' + str(id))
    return jsonify( {} )
