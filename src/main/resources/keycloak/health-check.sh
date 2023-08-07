#!/bin/bash

# Open connection & associate it with file descriptor 3
exec 3<>/dev/tcp/localhost/8080

# Send GET request through file descriptor 3
echo -e "GET /health HTTP/1.1\nhost: localhost:8080\n" >&3

# Read response from file descriptor 3
# Find strings "status" & "UP"
timeout --preserve-status 1 cat <&3 | grep -m 1 status | grep -m 1 UP

# Preserve status code of the previous command in the ERROR var
ERROR=$?

# Closing network connection associated with file descriptor 3
exec 3<&-
exec 3>&-

# Exit with captured status code
exit $ERROR