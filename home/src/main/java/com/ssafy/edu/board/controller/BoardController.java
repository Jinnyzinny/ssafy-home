package com.ssafy.edu.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.edu.board.BoardDto;
import com.ssafy.edu.board.model.service.BoardService;
import com.ssafy.edu.member.MemberDto;
import com.ssafy.edu.util.PageNavigation;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // 글 목록 조회
    @GetMapping
    public ResponseEntity<Map<String, Object>> listArticle(@RequestParam Map<String, String> paramMap) {
        try {
            List<BoardDto> articles = boardService.listArticle(paramMap);
            PageNavigation navigation = boardService.makePageNavigation(paramMap);

            Map<String, Object> response = new HashMap<>();
            response.put("articles", articles);
            response.put("navigation", navigation);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 특정 글 조회
    @GetMapping("/{articleNo}")
    public ResponseEntity<BoardDto> getArticle(@PathVariable int articleNo) {
        try {
            BoardDto article = boardService.getArticle(articleNo);
            if (article != null) {
                return ResponseEntity.ok(article);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 글 작성
    @PostMapping
    public ResponseEntity<Void> writeArticle(@RequestBody BoardDto boardDto, HttpSession session) {
        try {
            MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
            if (memberDto != null) {
                boardDto.setUserId(memberDto.getUserId());
                boardService.writeArticle(boardDto);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 글 수정
    @PutMapping("/{articleNo}")
    public ResponseEntity<Void> modifyArticle(@PathVariable int articleNo, @RequestBody BoardDto boardDto) {
        try {
            boardDto.setArticleNo(articleNo);
            boardService.modifyArticle(boardDto);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 글 삭제
    @DeleteMapping("/{articleNo}")
    public ResponseEntity<Void> deleteArticle(@PathVariable int articleNo) {
        try {
            boardService.deleteArticle(articleNo);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
