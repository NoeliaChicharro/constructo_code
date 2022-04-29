#!/bin/bash

echo "$0: Enable SQL Server Agent"
/opt/mssql/bin/mssql-conf set sqlagent.enabled true

echo "$0: Starting SQL Server"
docker-entrypoint-initdb.sh & /opt/mssql/bin/sqlservr