#!/bin/bash
set -e

cd /tmp
mysql -uroot -p"$MYSQL_ROOT_PASSWORD" < employees.sql