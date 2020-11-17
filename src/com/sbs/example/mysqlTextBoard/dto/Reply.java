package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;

public class Reply {
	@Override
	public String toString() {
		return "Reply [id=" + id + ", bodyR=" + bodyR + ", writeMemberId=" + writeMemberId + ", articleNumber="
				+ articleNumber + "]";
	}
	public Reply(Map<String, Object> replyMap) {
		this.id = (int)replyMap.get("id");
		this.bodyR= (String)replyMap.get("bodyR");
		this.articleNumber = (int)replyMap.get("articleNumber");
		this.writeMemberId =  (int)replyMap.get("writeMemberId");
	}
	public int id;
	public String bodyR;
	public int writeMemberId;
	public int articleNumber;
}
