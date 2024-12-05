-- authority
insert into authority(authority_name) values ('ROLE_ADMIN');
insert into authority(authority_name) values ('ROLE_GUEST');

-- member
insert into member(id, username, email, password, init_date, nickname) values (1, 'test', 'test@naver.com', '$2a$10$fZkjGmY1ANCi6GYcFzFfGuiFbQ1RINI.uPYSda7TLIiuRbLJoq9fC', now(), 'test');

-- member_authority
insert into member_authority(member_id, authority_name) values(1, 'ROLE_ADMIN');

-- title
insert into title(id, member_id, title, content) values (1, 1, '테스트1', '테스트1 내용');
insert into title(id, member_id, title, content) values (2, 1, '테스트2', '테스트2 내용');

--commit
commit;