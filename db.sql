# DB 생성
DROP DATABASE IF EXISTS textBoard;
CREATE DATABASE textBoard;
USE textBoard;

# 게시물 테이블 생성
CREATE TABLE article (
    id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
    regDate DATETIME NOT NULL,
    updateDate DATETIME NOT NULL,
    title CHAR(200) NOT NULL,
    `body` TEXT NOT NULL,
    memberId INT(10) UNSIGNED NOT NULL,
    boardId INT(10) UNSIGNED NOT NULL
);

# 게시물 데이터 3개 생성
INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목1',
`body` = '내용1',
memberId = 1,
boardId = 1;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목2',
`body` = '내용2',
memberId = 1,
boardId = 1;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = '제목3',
`body` = '내용3',
memberId = 1,
boardId = 1;
// -------------------------------------------------------------------------------
create table recommand (
     articleNum int(10) not null,
     memberId int(10) not null
);
insert into recommand set articleNum = 1,memberId = 4;
INSERT INTO recommand SET articleNum = 2,memberId = 2;
INSERT INTO recommand SET articleNum = 3,memberId = 3;
INSERT INTO recommand SET articleNum = 1,memberId = 1;

select a.id as '게시물번호',
group_concat(r.memberId) as '추천인',
count(r.memberId) as '추천수'
from recommand as r
join article as a
on a.id = r.articleNum
group by a.id;