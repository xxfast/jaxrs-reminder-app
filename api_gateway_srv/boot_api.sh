#!/bin/sh
exec gunicorn --timeout 1200 --workers=4 --worker-class gthread --threads 2 -b 0.0.0.0:80 app:app
