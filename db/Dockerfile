# MySQL 8.0 for Lab 3
FROM mysql:8.0

# Set working directory
WORKDIR /tmp

# Copy employee database files
COPY test_db/*.sql /tmp/
COPY test_db/*.dump /tmp/

# Initialize database using the MySQL client
COPY init-employees.sh /docker-entrypoint-initdb.d/

# Lab-only password
ENV MYSQL_ROOT_PASSWORD=example