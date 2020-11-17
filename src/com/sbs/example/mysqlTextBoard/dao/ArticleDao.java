package com.sbs.example.mysqlTextBoard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Reply;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class ArticleDao {
		int articleSize = 0;
	

	public int getArticleSize() {
		return articleSize;
	}

	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> articleListMap = MysqlUtil
				.selectRows(new SecSql().append("SELECT * FROM article order by id desc"));

		for (Map<String, Object> articleMap : articleListMap) {
			Article article = new Article(articleMap);
			articles.add(article);

		}
		articleSize = articles.size();
		return articles;
	}

	public Article getDetailById(int inputid) {

		Map<String, Object> articleMap = MysqlUtil
				.selectRow(new SecSql().append("SELECT * FROM article where id = ? ; ", inputid));
		if (articleMap.isEmpty()) {

			return null;
		}

		return new Article(articleMap);

	}

	public static void articleModified(int inputid, String title, String body) {

		MysqlUtil.update(new SecSql().append("update article set title = ?,body = ?,updateDate = now() where id = ?",
				title, body, inputid));

		System.out.printf("%d번 게시물이 수정되었습니다.\n", inputid);
	}

	public void deleteArticleById(int inputid) {
		MysqlUtil.update(new SecSql().append("delete from article where id = ?", inputid));

		System.out.printf("%d번 게시물이 삭제되었습니다.\n", inputid);

	}

	public void doWrite(int memberId, int boardId, String title, String body) {

		MysqlUtil.update(new SecSql().append(
				"insert into article set title = ?,body = ?,updateDate = now(),memberId =? ,boardId = ?;", title, body,
				memberId, boardId));
		int lastArticleId = MysqlUtil.selectRowIntValue(new SecSql().append("SELECT COUNT(*) FROM article;"));

		System.out.printf("%d번 게시물이 생성되었습니다.\n", lastArticleId);

	}

	public void doWriteReply(int inputid, String replyBody) {

		SecSql sql = new SecSql();
		int writeMemberId = Container.session.loginedId;
		sql.append("insert into replies set bodyR = ?,writeMemberId = ?,articleNumber = ?;", replyBody, writeMemberId,
				inputid);
		System.out.printf("%d번 게시물에 댓글이 생성되었습니다.\n", inputid);

	}

	public List<Reply> getArticleReplyById(int inputid) {
		List<Reply> repliesById = new ArrayList<>();
		List<Map<String, Object>> replyListMap = MysqlUtil
				.selectRows(new SecSql().append("SELECT * FROM replies where articleNumber = ?", inputid));

		for (Map<String, Object> replyMap : replyListMap) {
			Reply reply = new Reply(replyMap);
				repliesById.add(reply);

			}
		
		return repliesById;	
			
		}
		
	}


