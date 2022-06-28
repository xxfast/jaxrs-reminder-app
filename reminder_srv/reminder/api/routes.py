from flask import jsonify

from . import bp

@bp.route("/", methods=['POST'])
def post_item():
    # TODO verify wrong id
    
    return jsonify( { 'id': '1' } )

@bp.route("/<int:id>", methods=['GET'])
def get_item(id):
    # TODO verify wrong id

    data = 'TODO: Get data from DB'

    return jsonify( { 'data': data } )

@bp.route("/<int:id>", methods=['PUT'])
def put_item(id):
    # TODO verify wrong id

    return jsonify( {} )

@bp.route("/<int:id>", methods=['DELETE'])
def delete_item(id):
    # TODO verify wrong id

    return jsonify( {} )

