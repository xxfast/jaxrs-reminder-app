# jaxrs-reminder-app
_Fork by Sajid_

__Goal :__
   Transform a monolithic application into a horizontally scalable microservice-oriented application.



__Current Stage :__


![alt text](https://docs.google.com/drawings/d/e/2PACX-1vTHsD495ma1D0OgdzN7dZ_J5ugf804SF7MTyzPe5772rwvmjRva_A-3HFqiKf29NzFtwMbiek8JxKJL/pub?w=1440&h=1080 "ReminderApp Arch")

*** **_user_** class was added in order to illustrate in the best possible way how this became another microservice as the **_reminder_** class 

seems this app will better fit in a NoSQL databases so I decided  to experiment a bit with OGM / JPA abstraction levels giving us the freedom to switch backends without having to rewrite all of our data layers and in case the app become complex at some point possible a multi-model approach might be helpful and could be worth  having a common way to work with them .
