def get_user(client, username):
    return client.get('/' + username)

def post_user(client, username, password):
    return client.post('/', json={"username":username, "password":password})

def put_user(client, username, password):
    return client.put('/' + username, json={"password":password})

def login_user(client, username, password):
    return client.post('/' + username + '/login', json={"password":password})

def post_reminder(client, username, reminder):
    return client.post('/' + username + '/reminder', json={"reminder":reminder})

def get_reminders(client, username):
    return client.get('/' + username + '/reminder')

def delete_reminder(client, username, reminder):
    return client.delete('/' + username + '/reminder/' + reminder)


def test_post_new_user(client):
    username = "new_user_1"

    # Verify user does not exist
    response = get_user(client, username)
    assert response.status_code == 404

    # Add new user
    response = post_user(client, username, "1234")
    assert response.status_code == 200

    # Verify user does exist
    response = get_user(client, username)
    assert response.status_code == 200

    # fail when try to register the same user
    response = post_user(client, username, "1234")
    assert response.status_code == 400

def test_login_user(client):
    # Add new user
    username = "new_user_2"
    password = "abcd"
    response = post_user(client, username, password)
    assert response.status_code == 200

    # Verify password
    response = login_user(client, username, "wrong_pwd")
    assert response.status_code == 400

    # Valid login
    response = login_user(client, username, password)
    assert response.status_code == 200

    # Ignore multiple logins (resource only validate username & password)
    response = login_user(client, username, password)
    assert response.status_code == 200

def test_edit_user_password(client):
    # Add new user
    username = "new_user_3"
    password = "abcd"
    new_password = "1234"
    response = post_user(client, username, password)
    assert response.status_code == 200

    # Valid login
    response = login_user(client, username, password)
    assert response.status_code == 200

    # Edit password
    response = put_user(client, username, new_password)
    assert response.status_code == 200

    # Reject old password
    response = login_user(client, username, password)
    assert response.status_code == 400

    # Accept new password
    response = login_user(client, username, new_password)
    assert response.status_code == 200

def test_add_reminder(client):
    # Add new user
    username = "new_user_4"
    response = post_user(client, username, "1234")
    assert response.status_code == 200

    # Get reminders
    response = get_reminders(client, username)
    assert response.status_code == 200
    assert len( response.json["reminders"]) == 0

    # Add new reminder
    reminder_id_1 = "id_1"
    response = post_reminder(client, username, reminder_id_1)
    assert response.status_code == 200
    assert len( response.json["reminders"]) == 1
    assert reminder_id_1 in response.json["reminders"]

    # Reject add same reminder
    response = post_reminder(client, username, reminder_id_1)
    assert response.status_code == 400

    # Add another reminder
    reminder_id_2 = "id_2"
    response = post_reminder(client, username, reminder_id_2)
    assert response.status_code == 200
    assert len( response.json["reminders"]) == 2

    # Verify first reminder still there
    assert reminder_id_1 in response.json["reminders"]

def test_delete_reminder(client):
    # Add new user
    username = "new_user_5"
    response = post_user(client, username, "1234")
    assert response.status_code == 200

    # Add new reminder
    reminder_id = "id_1"
    response = post_reminder(client, username, reminder_id)
    assert response.status_code == 200
    assert len( response.json["reminders"]) == 1

    # Delete reminder
    response = delete_reminder(client, username, reminder_id)
    assert response.status_code == 200
    assert len( response.json["reminders"]) == 0

    # Reject Delete twice
    response = delete_reminder(client, username, reminder_id)
    assert response.status_code == 400

def test_delete_reminder_from_other_user(client):
    # Add 2 users
    username_1 = "new_user_6"
    response = post_user(client, username_1, "1234")
    assert response.status_code == 200
    username_2 = "new_user_7"
    response = post_user(client, username_2, "1234")
    assert response.status_code == 200

    # Add new reminder to first user
    reminder_id = "id_1"
    response = post_reminder(client, username_1, reminder_id)
    assert response.status_code == 200
    assert len( response.json["reminders"]) == 1

    # Delete reminder from second user
    response = delete_reminder(client, username_2, reminder_id)
    assert response.status_code == 400

def test_wrong_user(client):
    fake_user = "fake_user"

    # Get user
    response = get_user(client, fake_user)
    assert response.status_code == 404

    # Edit user
    response = put_user(client, fake_user, "1234")
    assert response.status_code == 404

    # Check login
    response = login_user(client, fake_user, "1234")
    assert response.status_code == 404

    # Append reminder
    response = post_reminder(client, fake_user, "fake_reminder_id")
    assert response.status_code == 404

    # Get reminders
    response = get_reminders(client, fake_user)
    assert response.status_code == 404

    # Delete reminder
    response = delete_reminder(client, fake_user, "fake_reminder_id")
    assert response.status_code == 404
