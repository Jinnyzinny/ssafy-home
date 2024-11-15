
show databases;

use ssafyhome;

show tables;

select * from members;

INSERT INTO members (user_id, user_name, user_password, email_id, email_domain)
VALUES ('test', 'test', '$2a$10$E6rIzb.95738eZ9tKJuNY.jjx/1RKNIVdn.q6.xh273iZLD.vfhYi', 'example', 'domain.com');
