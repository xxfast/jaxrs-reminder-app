from mongoengine import Document, StringField
from mongoengine.errors import ValidationError, DoesNotExist

class Reminder(Document):
    content = StringField(required=True)

def save_reminder(content):
    reminder = Reminder(content=content)
    reminder.save()

    return str(reminder.id)

def read_reminder(id):
    try:
        reminder = Reminder.objects.get(id=id)
    except (ValidationError, DoesNotExist):
        return None

    return reminder

def edit_reminder(id, content):
    try:
        reminder = Reminder.objects.get(id=id)
    except (ValidationError, DoesNotExist):
        return False

    reminder.content = content
    reminder.save()

    return True

def delete_reminder(id):
    try:
        reminder = Reminder.objects.get(id=id)
    except (ValidationError, DoesNotExist):
        return False

    reminder.delete()

    return True

