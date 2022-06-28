def get_item(client, id):
    return client.get('/' + id)

def post_item(client, data):
    response = client.post('/', json={"data":data})

    return response

def put_item(client, id, data):
    response = client.put('/' + id, json={"data":data})

    return response

def delete_item(client, id):
    return client.delete('/' + id)

def test_wrong_item(client):
    # GET wrong item
    response = get_item(client, '1')
    assert response.status_code == 404

    # PUT wrong item
    response = put_item(client, '1', '')
    assert response.status_code == 404

    # DELETE wrong item
    response = delete_item(client, '1')
    assert response.status_code == 404

def test_post_new_item(client):
    item_data = "New item - test_post_new_item"

    # GET item num 1
    response = get_item(client, '1')
    assert response.status_code == 404

    # POST a new item
    response = post_item(client, item_data)
    assert response.status_code == 200

    # It must be item num 1
    assert response.json["id"] == '1'

    # GET item num 1
    response = get_item(client, '1')
    assert response.status_code == 200

    # It must respond same item info 
    assert response.json["data"] == item_data

def test_edit_item(client):
    item_data = "New item - test_edit_item"

    # Ensure it has an item
    response = post_item(client, item_data)
    assert response.status_code == 200
    item_id = response.json["id"]

    # EDIT data
    edit_data = "Edited item - test_edit_item"
    response = put_item(client, item_id, item_data)
    assert response.status_code == 200

    # It must respond same item new info 
    response = get_item(client, '1')
    assert response.status_code == 200
    assert response.json["data"] == edit_data

def test_delete_item(client):
    item_data = "New item - test_delete_item"

    # Ensure it has an item
    response = post_item(client, item_data)
    assert response.status_code == 200
    item_id = response.json["id"]

    # Confirm by getting it
    response = get_item(client, item_id)
    assert response.status_code == 200

    # DELETE item
    response = delete_item(client, item_id)
    assert response.status_code == 200

    # It shouldn't found item
    response = get_item(client, item_id)
    assert response.status_code == 404
