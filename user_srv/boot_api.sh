#!/bin/sh
exec gunicorn --timeout 1200 --workers=1 --worker-class gthread --threads 1 -b 0.0.0.0:80 --access-logfile - --error-logfile - --log-level info app:app
