package com.sbs.example.mysqlTextBoard.dto;

import java.util.Map;
import java.util.*;

public class Recommand {
	public int articleNumber;
	int[] memberList; 
	public Recommand(Map<String, Object> recommandMap) {
		this.articleNumber = (int) recommandMap.get("게시물 번호");
		this.memberList = (array) recommandMap.get("추천회원");
	}
	
}
