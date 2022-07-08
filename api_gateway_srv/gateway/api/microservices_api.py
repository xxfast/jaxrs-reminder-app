from flask import current_app
from requests import Session
from urllib.parse import urljoin

class APIERROR(Exception):
    def __init__(self, error_code):
        self.error_code = error_code
        super().__init__("API ERROR - " + str(error_code))

class service(Session):
    host = None

    def __init__(self, host):
        super().__init__()
        self.host = host

    def request(self, method, resource, **kwargs):
        url = urljoin(self.host, resource)
        return super().request(method, url, **kwargs)

class reminder_service(service):
    def __init__(self):
        super().__init__(current_app.config['REMINDER_SERVICE_HOST'])

    def get_reminder(self, id):
        response = self.request( 'GET', '/' + id )
        if response.status_code != 200:
            raise APIERROR(response.status_code)
        
        return response.json()['content']

    def post_reminder(self, content):
        data = { 'content':content }
        response = self.request( 'POST', '/', json=data )
        if response.status_code != 200:
            raise APIERROR(response.status_code)

        return response.json()['id']

    def put_reminder(self, id, content):
        data = { 'content':content }
        response = self.request( 'PUT', '/' + id, json=data )
        if response.status_code != 200:
            raise APIERROR(response.status_code)

        return response.json()['id']

    def delete_reminder(self, id):
        response = self.request( 'DELETE', '/' + id )
        if response.status_code != 200:
            raise APIERROR(response.status_code)

        return

class user_service(service):
    def __init__(self):
        super().__init__(current_app.config['USER_SERVICE_HOST'])
    
    def get_user(self, username):
        response = self.request( 'GET', '/' + username )
        if response.status_code != 200:
            raise APIERROR(response.status_code)
        
        return

    def add_user(self, username, password):
        data = { 'username':username, 'password':password }
        response = self.request( 'POST', '/', json=data )
        if response.status_code != 200:
            raise APIERROR(response.status_code)
        
        return

    def verify_user(self, username, password):
        data = { 'password':password }
        response = self.request( 'POST', '/' + username + '/login', json=data )
        if response.status_code != 200:
            raise APIERROR(response.status_code)
        
        return

    def get_reminders(self, username):
        response = self.request( 'GET', '/' + username + '/reminder' )
        if response.status_code != 200:
            raise APIERROR(response.status_code)
        
        return response.json()['reminders']

    def post_reminder(self, username, id):
        data = { 'reminder_id':id }
        response = self.request( 'POST', '/' + username + '/reminder', json=data )
        if response.status_code != 200:
            raise APIERROR(response.status_code)

        return response.json()['reminders']

    def delete_reminder(self, username, id):
        response = self.request( 'DELETE', '/' + username + '/reminder/' + id )
        if response.status_code != 200:
            raise APIERROR(response.status_code)

        return response.json()['reminders']
