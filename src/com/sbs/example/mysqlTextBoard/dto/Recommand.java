package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;


public class Recommand {
	@Override
	public String toString() {
		return "Recommand [articleNumber=" + articleNumber + ", articleRecommanded=" + articleRecommanded
				+ ", memberRecommanded=" + memberRecommanded + "]";
	}
	public int articleNumber;
	public int articleRecommanded;
	public String memberRecommanded;
	
	public Recommand(Map<String, Object> recommandMap) {
		this.articleNumber = (int) recommandMap.get("게시물 번호");
		this.articleRecommanded =(int) recommandMap.get("추천수");
		this.memberRecommanded = (String) recommandMap.get("추천인");
	}
	
}
