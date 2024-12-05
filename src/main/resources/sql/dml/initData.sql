-- member
insert into member(id, username, email, password, init_date, nickname) values (1, 'test', 'test@naver.com', 'test', now(), 'test');

-- title
insert into title(id, member_id, title, content) values (1, 1, '테스트1', '테스트1 내용');
insert into title(id, member_id, title, content) values (2, 1, '테스트2', '테스트2 내용');