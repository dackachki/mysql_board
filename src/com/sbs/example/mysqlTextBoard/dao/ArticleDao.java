package com.sbs.example.mysqlTextBoard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class ArticleDao {
	List<Article> articles = new ArrayList<>();
	
	public int getArticleSize() {
		return articles.size();
	}


	public List<Article> getArticles() {
		

		List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(new SecSql().append("SELECT * FROM article"));

		for (Map<String, Object> articleMap : articleListMap) {
			Article article = new Article();
			article.id = (int) articleMap.get("id");
			article.regDate = (String) articleMap.get("regDate");
			article.updateDate = (String) articleMap.get("updateDate");
			article.title = (String) articleMap.get("title");
			article.body = (String) articleMap.get("body");
			article.memberId = (int) articleMap.get("memberId");
			article.boardId = (int) articleMap.get("boardId");
			articles.add(article);

		}

		return articles;
	}

	public  Article getDetailById(int inputid) {

		Map<String, Object> articleMap = MysqlUtil
				.selectRow(new SecSql().append("SELECT * FROM article where id = ? ; ", inputid));

		Article article = new Article();
		article.id = (int) articleMap.get("id");
		article.regDate = (String) articleMap.get("regDate");
		article.updateDate = (String) articleMap.get("updateDate");
		article.title = (String) articleMap.get("title");
		article.body = (String) articleMap.get("body");
		article.memberId = (int) articleMap.get("memberId");
		article.boardId = (int) articleMap.get("boardId");

		return article;

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
		

		MysqlUtil.update(new SecSql().append("insert into article set title = ?,body = ?,updateDate = now(),memberId =? ,boardId = ?;",title,body,memberId,boardId));
		int lastArticleId = MysqlUtil.selectRowIntValue(new SecSql().append("SELECT COUNT(*) FROM article;"));
			
			
				

				

		System.out.printf("%d번 게시물이 생성되었습니다.\n", lastArticleId);

	
	}

}
