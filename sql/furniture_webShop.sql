DROP DATABASE IF EXISTS furniture_webShop;
CREATE DATABASE furniture_webShop DEFAULT CHARACTER SET utf8;

USE furniture_webShop;

GRANT ALL ON furniture_webShop.* TO 'root'@'%' IDENTIFIED BY 'root';

FLUSH PRIVILEGES;