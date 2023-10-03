CREATE USER doctest_user WITH ENCRYPTED PASSWORD 'doctest_password';
CREATE DATABASE doctest OWNER doctest_user;
GRANT ALL PRIVILEGES ON DATABASE doctest to doctest_user;