package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Reply {
	public int id;
	public String bodyR;
	public int writeMemberId;
	public int articleNumber;
	public String regDate;
	public String updateDate;
	
	
	public Reply(Map<String, Object> replyMap) {
		this.id = (int)replyMap.get("id");
		this.bodyR= (String)replyMap.get("bodyR");
		this.articleNumber = (int)replyMap.get("articleNumber");
		this.writeMemberId =  (int)replyMap.get("writeMemberId");
		this.regDate = (String)replyMap.get("regDate");
		this.updateDate = (String)replyMap.get("updateDate");
	}
	
}
