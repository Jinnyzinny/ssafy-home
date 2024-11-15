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
    private static final int DEFAULT_PGNO = 1;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    // Get list of articles
    @GetMapping
    public ResponseEntity<Map<String, Object>> listArticle(@RequestParam Map<String, String> paramMap) throws Exception {
        int pgno = parsePageNumber(paramMap);
        paramMap.put("pgno", String.valueOf(pgno));

        List<BoardDto> articles = boardService.listArticle(paramMap);
        PageNavigation navigation = boardService.makePageNavigation(paramMap);

        Map<String, Object> response = new HashMap<>();
        response.put("articles", articles);
        response.put("navigation", navigation);

        return ResponseEntity.ok(response);
    }

    // Get single article details
    @GetMapping("/{articleNo}")
    public ResponseEntity<BoardDto> getArticle(@PathVariable int articleNo) throws Exception {
        BoardDto article = boardService.getArticle(articleNo);
        return ResponseEntity.ok(article);
    }

    // Create new article
    @PostMapping
    public ResponseEntity<Void> writeArticle(@RequestBody BoardDto boardDto, HttpSession session) throws Exception {
        MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
        if (memberDto != null) {
            boardDto.setUserId(memberDto.getUserId());
            boardService.writeArticle(boardDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Update existing article
    @PutMapping("/{articleNo}")
    public ResponseEntity<Void> modifyArticle(@PathVariable int articleNo, @RequestBody BoardDto boardDto) throws Exception {
        boardDto.setArticleNo(articleNo);
        boardService.modifyArticle(boardDto);
        return ResponseEntity.noContent().build();
    }

    // Delete an article
    @DeleteMapping("/{articleNo}")
    public ResponseEntity<Void> deleteArticle(@PathVariable int articleNo) throws Exception {
        boardService.deleteArticle(articleNo);
        return ResponseEntity.noContent().build();
    }

    // Helper method to parse page number
    private int parsePageNumber(Map<String, String> paramMap) {
        int pgno;
        try {
            pgno = Integer.parseInt(paramMap.getOrDefault("pgno", "1"));
            if (pgno < 1) {
                pgno = DEFAULT_PGNO;
            }
        } catch (NumberFormatException e) {
            pgno = DEFAULT_PGNO;
        }
        return pgno;
    }
}
