package com.ssafy.edu.member.controller;

import com.ssafy.edu.member.MemberDto;
import com.ssafy.edu.member.model.service.MemberService;
import com.ssafy.edu.util.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtil jwtUtil;

	
	// 로그인 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 로그인 페이지
	@GetMapping("/loginPage")
	public String loginPage() {
		return "redirect:/user/login.html"; // /user/login.html로 리다이렉트
	}

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> loginData,
			HttpServletResponse response) {
		String userId = loginData.get("userId");
		String userPwd = loginData.get("userPwd");

		try {
			MemberDto member = memberService.findByUserId(userId);
			if (member == null || !passwordEncoder.matches(userPwd, member.getUserPwd())) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid credentials"));
			}

			// JWT 토큰 생성 (사용자 ID와 이름을 포함)
			String token = jwtUtil.generateToken(member.getUserId(), member.getUserName());
			response.setHeader("Authorization", "Bearer " + token);

			return ResponseEntity.ok(Map.of("token", token, "message", "Login successful"));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Map.of("message", "An error occurred: " + e.getMessage()));
		}
	}

	// 로그아웃
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		session.invalidate(); // 세션 무효화
		return ResponseEntity.ok("Logged out successfully");
	}

	// 회원가입 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 회원가입
	// 회원가입 페이지 요청에 대한 RESTful 응답 추가
	@GetMapping("/joinPage")
	public ResponseEntity<Map<String, String>> joinPage() {
	    Map<String, String> response = new HashMap<>();
	    response.put("redirectUrl", "/user/join.html");
	    return ResponseEntity.ok(response);
	}

	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody MemberDto memberDto) {
		System.out.println("회원 가입을 하려는 memberDto 객체 정보다: " + memberDto);
		try {
			memberDto.setUserPwd(passwordEncoder.encode(memberDto.getUserPwd())); // 비밀번호 암호화
			int result = memberService.joinMember(memberDto);
			return result > 0 ? ResponseEntity.ok("Registration successful")
					: ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}


	// 회원정보 수정 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 회원 정보 페이지
	@GetMapping("/infoPage")
	public ResponseEntity<Map<String, String>> info() {
		Map<String, String> response = new HashMap<>();
		response.put("page", "user/edit");
		return ResponseEntity.ok(response);
	}

	// 회원정보 수정
	@PutMapping("/edit")
	public ResponseEntity<?> edit(@RequestBody MemberDto memberDto, HttpSession session) {
		MemberDto currentMember = (MemberDto) session.getAttribute("userinfo");

		if (currentMember == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
		}

		try {
			memberDto.setUserId(currentMember.getUserId()); // 세션에 있는 사용자 ID로 설정
			int result = memberService.editMember(memberDto);

			if (result > 0) {
				session.setAttribute("userinfo", memberDto); // 세션에 최신 정보 업데이트
				return ResponseEntity.ok("Profile updated successfully");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Profile update failed");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// 회원 탈퇴
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(HttpSession session) {
		MemberDto member = (MemberDto) session.getAttribute("userinfo");

		if (member == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
		}

		try {
			int result = memberService.deleteMember(member.getUserId());
			if (result > 0) {
				session.invalidate(); // 탈퇴 후 세션 종료
				return ResponseEntity.ok("Account deleted successfully");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account deletion failed");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 회원 인증 상태 확인
	@GetMapping("/status")
	public ResponseEntity<Map<String, Object>> getStatus() {
		Map<String, Object> response = new HashMap<>();
		var auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null && auth.isAuthenticated()
				&& !(auth.getPrincipal() instanceof String && "anonymousUser".equals(auth.getPrincipal()))) {
			// userId 또는 username을 추출합니다.
			String userId = ((User) auth.getPrincipal()).getUsername();

			response.put("authenticated", true);
			response.put("userId", userId);
			response.put("userName", userId); // 필요에 따라 사용자 이름 설정
		} else {
			response.put("authenticated", false);
		}

		return ResponseEntity.ok(response);
	}

}
