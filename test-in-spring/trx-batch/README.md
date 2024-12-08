### Test DB setting
- create user
```mysql
CREATE USER 'test'@'%' IDENTIFIED BY '1234';
GRANT CREATE, DROP ON *.* TO 'test'@'%';
GRANT ALL PRIVILEGES ON `test_db_%`.* TO 'test'@'%';
FLUSH PRIVILEGES ;
```