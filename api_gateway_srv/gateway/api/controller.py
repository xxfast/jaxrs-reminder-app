from .microservices_api import user_service, reminder_service

def login_user(username, password):
    user_srv = user_service()

    # Get user 
    response = user_srv.get_user(username)
    if not response:
        # If doesn't exist => register user
        if not user_srv.add_user(username, password):
            return False
    else:
        # If exist => verificar credenciales
        if not user_srv.verify_user(username, password):
            return False
    
    return True

def get_reminders(username):
    user_srv = user_service()
    reminder_srv = reminder_service()

    reminders_list = user_srv.get_reminders(username)

    # serialize all reminders
    result = []
    for reminder_id in reminders_list:
        reminder_data = reminder_srv.get_reminder(reminder_id)
        result.append(
            {
                'id':reminder_id,
                'content':reminder_data
            }
        )

    return result

def post_reminder(username, content):
    user_srv = user_service()
    reminder_srv = reminder_service()

    # POST reminders
    id = reminder_srv.post_reminder(content)
    if not id:
        return None

    # POST reminders in user
    if not user_srv.post_reminder(username, id):
        return None

    return id

def put_reminder(username, id, content):
    user_srv = user_service()
    reminder_srv = reminder_service()

    # Verify reminder is in user list
    reminders_list = user_srv.get_reminders(username)
    if not (id in reminders_list):
        return None

    # PUT reminders
    id = reminder_srv.put_reminder(id, content)
    if not id:
        return None

    return id        

def delete_reminder(username, reminder_id):
    user_srv = user_service()
    reminder_srv = reminder_service()

    # DELETE reminder from user (if reminder doesn't belongs to user request will be rejected)
    result = user_srv.delete_reminder(username, reminder_id)
    if not result:
        return False

    # DELETE reminder (on error ignore orphan reminders, reference has been removed from user)
    reminder_srv.delete_reminder(reminder_id)

    return True
