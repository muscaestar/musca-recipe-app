-- ## Use to run mysql db docker image, optional if you're not using a local mysqldb
-- # docker run --name mysqldb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

-- # connect to mysql and run as root user
-- #Create Databases
CREATE DATABASE msc_dev;
CREATE DATABASE msc_prod;

-- #Create database service accounts
CREATE USER 'msc_dev_user'@'localhost' IDENTIFIED BY 'passwd';
CREATE USER 'msc_prod_user'@'localhost' IDENTIFIED BY 'passwd';
CREATE USER 'msc_dev_user'@'%' IDENTIFIED BY 'passwd';
CREATE USER 'msc_prod_user'@'%' IDENTIFIED BY 'passwd';

-- #Database grants
GRANT SELECT ON msc_dev.* to 'msc_dev_user'@'localhost';
GRANT INSERT ON msc_dev.* to 'msc_dev_user'@'localhost';
GRANT DELETE ON msc_dev.* to 'msc_dev_user'@'localhost';
GRANT UPDATE ON msc_dev.* to 'msc_dev_user'@'localhost';
GRANT SELECT ON msc_prod.* to 'msc_prod_user'@'localhost';
GRANT INSERT ON msc_prod.* to 'msc_prod_user'@'localhost';
GRANT DELETE ON msc_prod.* to 'msc_prod_user'@'localhost';
GRANT UPDATE ON msc_prod.* to 'msc_prod_user'@'localhost';
GRANT SELECT ON msc_dev.* to 'msc_dev_user'@'%';
GRANT INSERT ON msc_dev.* to 'msc_dev_user'@'%';
GRANT DELETE ON msc_dev.* to 'msc_dev_user'@'%';
GRANT UPDATE ON msc_dev.* to 'msc_dev_user'@'%';
GRANT SELECT ON msc_prod.* to 'msc_prod_user'@'%';
GRANT INSERT ON msc_prod.* to 'msc_prod_user'@'%';
GRANT DELETE ON msc_prod.* to 'msc_prod_user'@'%';
GRANT UPDATE ON msc_prod.* to 'msc_prod_user'@'%';