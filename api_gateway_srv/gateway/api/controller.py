from .microservices_api import APIERROR, user_service, reminder_service

def login_user(username, password):
    user_srv = user_service()

    # Get user
    try:
        user_srv.get_user(username)
    except APIERROR as ex:
        if ex.error_code == 404:
            # If doesn't exist => register user
            user_srv.add_user(username, password)
        else:
            raise
    else:
        user_srv.verify_user(username, password)

    return

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

    # POST reminders in user
    user_srv.post_reminder(username, id)

    return id

def put_reminder(username, id, content):
    user_srv = user_service()
    reminder_srv = reminder_service()

    # Verify reminder is in user list
    reminders_list = user_srv.get_reminders(username)
    if not (id in reminders_list):
        raise APIERROR(404)

    # PUT reminders
    reminder_srv.put_reminder(id, content)

    return

def delete_reminder(username, reminder_id):
    user_srv = user_service()
    reminder_srv = reminder_service()

    # DELETE reminder from user (if reminder doesn't belongs to user request will be rejected)
    user_srv.delete_reminder(username, reminder_id)

    # DELETE reminder (on error ignore orphan reminders, reference has been removed from user)
    reminder_srv.delete_reminder(reminder_id)

    return
