package com.example.demo.controller;

import com.example.demo.model.GameState;
import com.example.demo.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public GameState get() {
        return gameService.get();
    }

    @PostMapping("/reset")
    public GameState reset() {
        return gameService.reset();
    }

    @PostMapping("/moves")
    public GameState move(@RequestBody MoveRequest request) {
        return gameService.move(request.column());
    }

    public record MoveRequest(Integer column) {
        public MoveRequest {
            if (column == null) {
                throw new IllegalArgumentException("column is outside the board");
            }
        }
    }
}
