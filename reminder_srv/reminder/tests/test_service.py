def get_item(client, id):
    return client.get('/' + id)

def post_item(client, content):
    response = client.post('/', json={"content":content})

    return response

def put_item(client, id, content):
    response = client.put('/' + id, json={"content":content})

    return response

def delete_item(client, id):
    return client.delete('/' + id)

def test_wrong_item(client):
    # GET wrong item
    response = get_item(client, 'fakeID')
    assert response.status_code == 404

    # PUT wrong item
    response = put_item(client, 'fakeID', 'fake_data')
    assert response.status_code == 404

    # DELETE wrong item
    response = delete_item(client, 'fakeID')
    assert response.status_code == 404

def test_post_new_item(client):
    item_content = "New item - test_post_new_item"

    # POST a new item
    response = post_item(client, item_content)
    assert response.status_code == 200
    item_id = response.json["id"]

    # GET item
    response = get_item(client, item_id)
    assert response.status_code == 200

    # It must respond same item content
    assert response.json["content"] == item_content

def test_edit_item(client):
    # Ensure it has an item
    response = post_item(client, "New item - test_edit_item")
    assert response.status_code == 200
    item_id = response.json["id"]

    # EDIT content
    edit_content = "Edited item - test_edit_item"
    response = put_item(client, item_id, edit_content)
    assert response.status_code == 200

    # It must respond same item new info 
    response = get_item(client, item_id)
    assert response.status_code == 200
    assert response.json["content"] == edit_content

def test_delete_item(client):
    item_content = "New item - test_delete_item"

    # Ensure it has an item
    response = post_item(client, item_content)
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

def test_empty_item(client):
    # It shouldn't accept empty content
    response = post_item(client, '')
    assert response.status_code == 400

    # Ensure it has an item
    response = post_item(client, "Test item - test_empty_item")
    assert response.status_code == 200
    item_id = response.json["id"]

    response = put_item(client, item_id, '')
    assert response.status_code == 400
