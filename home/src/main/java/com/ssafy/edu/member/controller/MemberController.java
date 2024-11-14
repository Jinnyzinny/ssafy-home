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

	// 로그인
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> loginData, HttpServletResponse response) {
		String userId = loginData.get("userId");
		String userPwd = loginData.get("userPwd");

		try {
			Map<String, String> loginParams = Map.of("userId", userId, "userPwd", userPwd);
			MemberDto member = memberService.loginMember(loginParams);

			if (member != null && passwordEncoder.matches(userPwd, member.getUserPwd())) {
				// 로그인 성공 시 /로 리디렉션
				response.sendRedirect("/");
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// 회원가입
	@PostMapping("/join")
	public ResponseEntity<?> join(@RequestBody MemberDto memberDto) {
		try {
			memberDto.setUserPwd(passwordEncoder.encode(memberDto.getUserPwd())); // 비밀번호 암호화
			int result = memberService.joinMember(memberDto);
			return result > 0 ? ResponseEntity.ok("Registration successful")
					: ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	// 로그아웃
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpSession session) {
		session.invalidate(); // 세션 무효화
		return ResponseEntity.ok("Logged out successfully");
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

	// 회원 인증 상태 확인
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 사용자 인증 상태 확인
	@GetMapping("/status")
	public ResponseEntity<Map<String, Object>> getStatus(HttpSession session) {
		MemberDto member = (MemberDto) session.getAttribute("userinfo");

		Map<String, Object> response = new HashMap<>();
		response.put("authenticated", member != null); // 인증 상태 (로그인 여부)
		if (member != null) {
			response.put("userId", member.getUserId()); // 로그인된 사용자 ID 반환
			response.put("userName", member.getUserName()); // 로그인된 사용자 이름 반환
		}

		return ResponseEntity.ok(response);
	}

}
