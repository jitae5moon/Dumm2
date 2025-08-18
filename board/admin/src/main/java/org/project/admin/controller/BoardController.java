package org.project.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.admin.dto.BoardRequestDto;
import org.project.admin.dto.BoardResponseDto;
import org.project.admin.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/boards")
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String listBoards(Pageable pageable, Model model) {
        log.info("BoardController :: listBoards() :: pageable = {}", pageable);

        Page<BoardResponseDto> boards = boardService.getBoards(pageable);
        model.addAttribute("boards", boards);

        return "boards/list";
    }

    @GetMapping("/{id}")
    public String viewBoard(@PathVariable Long id, Model model) {
        log.info("BoardController :: viewBoard() :: id = {}", id);

        BoardResponseDto board = boardService.getBoard(id);
        model.addAttribute("board", board);

        return "boards/detail";
    }

    @GetMapping("/save")
    public String saveForm(Model model) {
        log.info("BoardController :: saveForm()");

        model.addAttribute("board", new BoardResponseDto(null, "", "", false, "", null));

        return "boards/form";
    }

    @PostMapping("/save")
    public String saveBoard(@Valid BoardRequestDto boardRequestDto, BindingResult bindingResult, Model model) {
        log.info("BoardController :: saveBoard() :: boardRequestDto = {}", boardRequestDto);

        if (bindingResult.hasErrors()) return "boards/form";

        boardService.saveBoard(boardRequestDto);

        return "redirect:/admin/boards";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        log.info("BoardController  :: updateForm() :: id = {}", id);

        BoardResponseDto board = boardService.getBoard(id);
        model.addAttribute("board", board);

        return "boards/form";
    }

    @PostMapping("/update/{id}")
    public String updateBoard(@PathVariable Long id, @Valid BoardRequestDto boardRequestDto, BindingResult bindingResult, Model model) {
        log.info("BoardController :: updateBoard() :: id = {}", id);

        boardService.updateBoard(id, boardRequestDto);

        return "redirect:/admin/boards/" + id;
    }

}
