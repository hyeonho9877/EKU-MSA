insert into free_board(id, created_at, modified_at, building, comments, content, writer, dept, disabled)
values(10001,LOCALTIMESTAMP, LOCALTIMESTAMP, '00010', 1, 'Test Article 1', '201713883', '컴퓨터공학부', FALSE);

insert into free_board(id, created_at, modified_at, building, comments, content, writer, dept, disabled)
values(10002,LOCALTIMESTAMP, LOCALTIMESTAMP, '00011', 0, 'Test Article 2', '201515923', '융합보안학과', FALSE);

insert into free_board(id, created_at, modified_at, building, comments, content, writer, dept, disabled)
values(10003,LOCALTIMESTAMP, LOCALTIMESTAMP, '00010', 0, 'Test Article 3', '201713883', '컴퓨터공학부', FALSE);

insert into free_board(id, created_at, modified_at, building, comments, content, writer, dept, disabled)
values(10004,LOCALTIMESTAMP, LOCALTIMESTAMP, '00010', 3, 'Test Article 4', '202219103', '국어국문학과', FALSE);

insert into free_board_comment(id, created_at, modified_at, comment, writer, article_id, dept, disabled)
values(20001,LOCALTIMESTAMP, LOCALTIMESTAMP, 'Test Comment 1', '201813952', 10001, '화학공학과', FALSE);

insert into free_board_comment(id, created_at, modified_at, comment, writer, article_id, dept, disabled)
values(20002,LOCALTIMESTAMP, LOCALTIMESTAMP, 'Test Comment 2', '201713883', 10004, '컴퓨터공학부', FALSE);

insert into free_board_comment(id, created_at, modified_at, comment, writer, article_id, dept, disabled)
values(20003,LOCALTIMESTAMP, LOCALTIMESTAMP, 'Test Comment 3', '202013950', 10004, '실용음악학과', FALSE);

insert into free_board_comment(id, created_at, modified_at, comment, writer, article_id, dept, disabled)
values(20004,LOCALTIMESTAMP, LOCALTIMESTAMP, 'Test Comment 4', '202116210', 10004, '수학과', FALSE);
