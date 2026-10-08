package com.example.demo.service;

import com.example.demo.model.Game;
import com.example.demo.model.GameState;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private Game game = new Game();

    public synchronized GameState get() {
        return state();
    }

    public synchronized GameState reset() {
        game = new Game();
        return state();
    }

    public synchronized GameState move(int column) {
        game.play(column);
        return state();
    }

    private GameState state() {
        return new GameState(
                game.getBoard(),
                game.getCurrentPlayer(),
                game.getStatus(),
                game.getWinner(),
                game.getMoveCount());
    }
}
