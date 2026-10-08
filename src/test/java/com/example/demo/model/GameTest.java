package com.example.demo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class GameTest {

    // Sequence: column 0. Red lands on row 5, the bottom of that column.
    @Test
    void validMove() {
        Game game = new Game();

        game.play(0);

        assertEquals(Player.RED, game.getBoard()[5][0]);
        assertNull(game.getBoard()[4][0]);
        assertEquals(Player.BLUE, game.getCurrentPlayer());
        assertEquals(GameStatus.IN_PROGRESS, game.getStatus());
        assertEquals(1, game.getMoveCount());
        assertNull(game.getWinner());
    }

    // Sequence: 0, 6, 1, 6, 2, 6, 3. Red's fourth disc completes a horizontal line on row 5.
    @Test
    void horizontalWin() {
        Game game = new Game();

        play(game, 0, 6, 1, 6, 2, 6, 3);

        assertEquals(GameStatus.WIN, game.getStatus());
        assertEquals(Player.RED, game.getWinner());
    }

    // Sequence: 0, 1, 0, 1, 0, 1, 0. Red's fourth disc completes a vertical line in column 0.
    @Test
    void verticalWin() {
        Game game = new Game();

        play(game, 0, 1, 0, 1, 0, 1, 0);

        assertEquals(GameStatus.WIN, game.getStatus());
        assertEquals(Player.RED, game.getWinner());
    }

    // Sequence: 0, 1, 1, 2, 3, 2, 2, 3, 4, 3, 3.
    // Red completes the diagonal (5,0), (4,1), (3,2), (2,3).
    @Test
    void diagonalWin() {
        Game game = new Game();

        play(game, 0, 1, 1, 2, 3, 2, 2, 3, 4, 3, 3);

        assertEquals(GameStatus.WIN, game.getStatus());
        assertEquals(Player.RED, game.getWinner());
    }

    // Sequence: 0, 2, 4, 6, 1, 3, 5, repeated six times. The board fills with no line of four.
    @Test
    void drawWhenBoardIsFull() {
        Game game = new Game();
        int[] layer = {0, 2, 4, 6, 1, 3, 5};

        for (int i = 0; i < Game.ROWS; i++) {
            play(game, layer);
        }

        assertEquals(GameStatus.DRAW, game.getStatus());
        assertEquals(Game.ROWS * Game.COLS, game.getMoveCount());
        assertNull(game.getWinner());
    }

    // Sequence: 0, 6, 1, 6, 2, 6, 3, then column 4 after Red has already won.
    @Test
    void moveAfterWinThrows() {
        Game game = new Game();
        play(game, 0, 6, 1, 6, 2, 6, 3);

        assertThrows(IllegalStateException.class, () -> game.play(4));
    }

    private void play(Game game, int... columns) {
        for (int column : columns) {
            game.play(column);
        }
    }
}
