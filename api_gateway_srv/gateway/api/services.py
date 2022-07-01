import requests

def login_user(username, password):
    # Get user 
    response = services.get_user(username)
    if not response:
        # If doesn't exist => register user
        if not services.add_user(username, password):
            abort(401)
    else:
        # If exist => verificar credenciales
        if not services.verify_user(username, password):
            abort(401)
    pass

def get_reminder(username):
    reminders_list = services.get_reminders(current_user)

    # serialize all reminders
    result = []
    for reminder_id in reminder_list:
        reminder_data = services.get_reminder(current_user, reminder_id)
        result.append(
            {
                'id':reminder_id,
                'content':reminder_data
            }
        )

    pass

def post_reminder(username, content):
    # POST reminders
    # POST reminders en user
    pass

def put_reminder(username, content):
    # PUT reminders
    pass

def delete_reminder(current_user, reminder_id):
    # DELETE reminder de user
    # DELETE reminder
    pass
