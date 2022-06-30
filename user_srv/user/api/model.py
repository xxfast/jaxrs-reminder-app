from werkzeug.security import generate_password_hash, check_password_hash

from user import db

class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(64), index=True, unique=True)
    password_hash = db.Column(db.String(128))
    reminders = db.relationship('Reminder', back_populates="user")

    def __repr__(self):
        return f'User({self.username})'

    def set_password(self, password):
        self.password_hash = generate_password_hash(password)

    def check_password(self, password):
        if not self.password_hash:
            self.set_password(self.username)
        return check_password_hash(self.password_hash, password)

    def get_reminders_id_list(self):
        result = []
        for reminder in self.reminders:
            result.append(reminder.id)

        return result

class Reminder(db.Model):
    id = db.Column(db.String, primary_key=True)
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'))
    user = db.relationship('User', back_populates="reminders", uselist=False)

    def __repr__(self):
        return f'Reminder({self.id, self.user_id})'
