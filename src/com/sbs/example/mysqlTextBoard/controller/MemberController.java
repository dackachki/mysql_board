package com.sbs.example.mysqlTextBoard.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.service.MemberService;

public class MemberController extends Controller {
	private static MemberService memberService;
	Scanner sc = new Scanner(System.in);

	public MemberController() {

		memberService = new MemberService();

	}

	public void doCommand(String cmd) {
		if (cmd.equals("member join")) {
			memberJoin();
		} else if (cmd.equals("member login")) {
			memberLogin();
		} else if (cmd.equals("member list")) {
			showMemberList();
		} else if (cmd.equals("member logout")) {
			memberLogout();
		} else if (cmd.equals("member whoami")) {
			memberWhoAmI();
		}

	}

	private void memberWhoAmI() {
		if (Container.session.loginedId == 0) {
			System.out.println("로그인 중인 회원이 없습니다.");
			return;
		}
		Member member = new Member();
		member = getMemberByLoginedId();

		System.out.println("== 현재 로그인 중인 회원 정보 ==");
		System.out.printf("회원 이름 : %s\n", member.memberName);
		System.out.printf("회원 이름 : %s\n", member.memberId);

	}

	private void memberLogout() {
		System.out.println("== 로그아웃 되었습니다. ==");
		Container.session.loginedId = 0;

	}

	private void memberLogin() {
		if (Container.session.loginedId != 0) {
			System.out.println("로그인 중인 아이디가 있습니다.");
			return;
		}
		Member loginTryMember = null;
		System.out.println("== 회원 로그인 ==");

		boolean idPassOk = false;

		int tryC = 0;
		int maxC = 3;
		while (true) {

			System.out.printf("아이디 입력 : ");
			String memberId = sc.nextLine().trim();
			if (tryC >= maxC) {
				System.out.println("확인 후 다시 시도하세요.");

				return;
			}

			for (Member member : getMembers()) {
				if (member.memberId.equals(memberId)) {
					idPassOk = true;
					loginTryMember = member;
				}
			}
			if (memberId.length() == 0) {
				System.out.println("아이디를 입력하세요.");
				tryC++;

			} else if (idPassOk == false) {
				System.out.println("존재하지 않는 아이디입니다.");
				tryC++;

			}

			if (idPassOk) {
				break;

			}

		}
		int passwdTryC = 0;
		int passwdMaxC = 3;
		while (true) {
			if (passwdTryC >= passwdMaxC) {
				System.out.println("비밀번호 확인 후 다시 시도하세요.");
				return;
			}

			System.out.printf("비밀번호 입력 : ");
			String passwd = sc.nextLine().trim();
			if (passwd.length() == 0) {
				System.out.println("비밀번호를 입력하세요");
				passwdTryC++;
			} else if (loginTryMember.memberPw.equals(passwd) == false) {
				System.out.println("비밀번호가 일치하지 않습니다.");
				passwdTryC++;

			}

			else if (loginTryMember.memberPw.equals(passwd)) {
				System.out.printf("%s 님 환영합니다.\n", loginTryMember.memberName);
				Container.session.loginedId = loginTryMember.memberIndex;

				break;
			}
		}
	}

	public void memberJoin() {
		System.out.println("== 회원 가입 ==");
		String name = "";
		String memberId = "";
		String memberPw = "";
		int joinTryC = 0;
		int joinMaxC = 3;
		while (true) {
			boolean joinNameOk = false;
			if (joinTryC >= joinMaxC) {
				System.out.println("잠시 후 다시 시도하세요.");
				return;
			}
			System.out.printf("이름 입력 : ");
			name = sc.nextLine().trim();
			if (name.length() == 0) {
				System.out.println("이름을 입력하세요.");
				joinTryC++;
				continue;
			}
			if (joinNameOk) {
				break;
			}
			break;
		}
		joinTryC = 0;
		joinMaxC = 3;
		while (true) {
			boolean joinIdOk = false;
			if (joinTryC >= joinMaxC) {
				System.out.println("잠시 후 다시 시도하세요.");
				return;
			}
			System.out.printf("아이디 입력 : ");
			memberId = sc.nextLine().trim();
			if (memberId.length() == 0) {
				System.out.println("아이디을 입력하세요.");
				joinTryC++;
				continue;

			}
			for (Member member : getMembers()) {
				if (member.memberId.equals(memberId)) {
					System.out.println("이미 존재하는 아이디 입니다.");
					joinTryC++;

				}
				continue;
			}
			
			if (joinIdOk) {
				break;
			}
			break;
		}
		joinTryC = 0;
		joinMaxC = 3;
		while (true) {
			boolean joinPwOk = false;
			if (joinTryC >= joinMaxC) {
				System.out.println("잠시 후 다시 시도하세요.");
				return;
			}
			System.out.printf("비밀번호 입력 : ");
			memberPw = sc.nextLine().trim();
			if (memberPw.length() == 0) {
				System.out.println("비밀번호을 입력하세요.");
				joinTryC++;
				continue;
			}
			if (joinPwOk) {
				break;
			}
			break;
		}
		memberService.memberJoin(name, memberId, memberPw);

	}

	public void showMemberList() {
		System.out.println("== 게시물 리스트 ==");

		List<Member> members = memberService.getMembers();

		System.out.println("번호 / 이름 / 아이디");

		for (Member member : members) {
			System.out.printf("%d / %s / %s\n", member.memberIndex, member.memberName, member.memberId);
		}

	}

	private List<Member> getMembers() {
		return memberService.getMembers();
	}

	public Member getMemberByLoginedId() {
		return memberService.getMemberByLoginedId();
	}
}