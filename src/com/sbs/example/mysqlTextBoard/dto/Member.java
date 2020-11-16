package com.sbs.example.mysqlTextBoard.dto;

public class Member {
	public Member() {

	}

	public Member(int id, String memberName, String memberId, String memberPw) {
		this.memberIndex = id;
		this.memberName = memberName;
		this.memberId = memberId;
		this.memberPw = memberPw;

	}

	public int memberIndex;
	public String memberName;
	public String memberId;
	public String memberPw;
}
