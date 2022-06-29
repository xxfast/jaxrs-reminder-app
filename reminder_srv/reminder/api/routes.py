import logging
from flask import jsonify, request, abort

from . import bp, model

@bp.route("/", methods=['POST'])
def post_item():
    req_data = request.json

    content = req_data.get('content', None)
    if not content:
        abort(400)

    id = model.save_reminder(content)

    return jsonify( { 'id': id } )

@bp.route("/<id>", methods=['GET'])
def get_item(id):
    reminder = model.read_reminder(id)

    # verify wrong id
    if not reminder:
        abort(404)

    return jsonify( { 'content': reminder.content } )

@bp.route("/<id>", methods=['PUT'])
def put_item(id):
    req_data = request.json

    content = req_data.get('content', None)
    if not content:
        abort(400)

    if not model.edit_reminder(id, content):
        abort(404)

    return jsonify( {} )

@bp.route("/<id>", methods=['DELETE'])
def delete_item(id):
    if not model.delete_reminder(id):
        abort(404)

    return jsonify( {} )
