package com.kh.spring.member.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.member.model.dto.MemberDTO;
import com.kh.spring.member.model.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j	// 기록을 남길때 씀
@Controller
public class MemberController {
	
	/*
	@RequestMapping("login")
	public void login(Member member) {
		// 1. 값 뽑기
		// 2. 데이터 가공
		System.out.println(member);
	}
	*/
	
	/*
	1번 방법 - 잘 사용하지 않는 방법
	@RequestMapping("login")
	public String login(HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		System.out.printf("id : %s , pw : %s", userId, userPwd);
		
		return "main";
	}
	*/
	
	/*
	2번 방법
	@RequestMapping("login")
	public String login(@RequestParam(value="userId", defaultValue="fffff") String id, @RequestParam(value="userPwd") String pwd) {
		
		System.out.printf("이렇게 하면 될까요? id : %s, pwd : %s", id, pwd);
		
		return "main"; 
	}
	*/
	
	/*
	3번 방법(@RequestParam(value="XXX") 생략 가능
	@RequestMapping("login")
	public String login(@RequestParam(value="userId") String userId, @RequestParam(value="userPwd") String userPwd) {
		
		System.out.printf("으흐흐흐 id : " + userId + ", pwd : " + userPwd);
		
		return "main";
	}
	*/
	
	/*
	 * HandlerAdapter의 판단 방법
	 * 
	 * 1. 매개변수 자리에 기본타입(int, boolean, String, Date...)이 있거나 RequestParam 애노테이션이 존재하는 경우 == RequestParam 으로 인식
	 * 
	 * 2. 매개변수 자리에 사용자 정의 클래스(MemberDTO, Board, Reply..)이 있거나 ModelAttribute 애노테이션이 존재하는 경우 == 커맨드 객체 방식으로 인식
	 *    @ModelAttribute - 생략 가능
	 *    
	 * 커맨드 객체 방식
	 * 
	 * 스프링에서 해당 객체를 기본생성자를 이용해서 생성한 후 내부적으로 setter메소드를 찾아서 요청 시 전달 값을 해당 필드에 대입해줌
	 * 
	 * 1. 매개변수 자료형에 반드시 기본생성자가 존재할 것
	 * 2. 전달되는 키 값과 객체의 필드명이 동일할 것
	 * 3. setter 메소드가 반드시 존재할 것
	 */
	
	// @Autowired == 필드 인젝션
	private MemberService memberService;	// = new MemberService();
	
	/*
	@Autowired == 세터 인젝션
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}	둘다 안씀
	*/
	
	@Autowired	// => ★ 권장 방법 ★ 
	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	/*
	@RequestMapping("login")
	public String login(@ModelAttribut MemberDTO member, HttpSession session, Model model) {
		// System.out.println("로그인 시 입력한 정보 : " + member);
		log.info("Member객체 필드값 확인 ~ {}", member);
		MemberDTO loginMember = memberService.login(member);
		
		if(loginMember != null) {
			log.info("로그인 성공");
		} else {
			log.info("실패");
		}
		
		if(loginMember != null) {	// 로그인에 성공
			// sessionScope에 로그인된 사용자의 정보를 담아줌
			session.setAttribute("loginMember",	loginMember);
			// 포워딩 방식 보다는 -> sendRedirect
			// localhost / spring
		
			return "redirect:/";
					
		} else {	// 실패했을 때
			
			// error_page -> 포워딩
			// requestScope에 msg라는 키 값으로 로그인 실패입니다~ 담아서 포워딩
			// Spring에서는 Model타입을 이용해서 RequestScope에 값을 담음
			model.addAttribute("msg", "로그인 실패 까비~");
			
			// Forwarding
			// /WEB-INF/views/
			// .jsp
			
			// /WEB-INF/views/include/error_page.jsp
			
			return "include/error_page";
		}
		// return "main";
	}
	*/
	
	// 두 번째 방법 : 반환타입 ModelAndView 타입으로 반환
	@PostMapping("/login")
	public ModelAndView login(MemberDTO member, HttpSession session, ModelAndView mv) {
		
		MemberDTO loginMember = memberService.login(member);
		
		if(loginMember != null) {
			session.setAttribute("loginMember", loginMember);
			mv.setViewName("redirect:/");
			
		} else {
			mv.addObject("msg", "로그인실패!").setViewName("include/error_page");
		}
		
		return mv;
	}
	
	
	// CRUD
	// INSERT	--> POST	--> /member
	// SELECT	--> GET
	// UPDATE
	// DELETE
	
	
	// 로그아웃
	@GetMapping("logout")
	public String logout(HttpSession session) {
		
		session.removeAttribute("loginMember");
		
		return "redirect:/";
	}
	
	
	// 회원가입
	@GetMapping("join")
	public String joinForm() {
		// 포워딩할 JSP파일의 논리적인 경로
		// /WEB-INF/views/member/signup.jsp
		return "member/signup";
	}
	
	@PostMapping("signup")
	public String signup(MemberDTO member, HttpServletRequest request) {
		// 아이디, 비밀번호, 이름, 이메일
		
		/*
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		*/
		
		log.info("{}", member);
		memberService.signUp(member);
		return "redirect:join";
	}
	
	
}
