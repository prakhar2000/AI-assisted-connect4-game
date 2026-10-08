package com.example.demo.model;

public class GameState {
    private final Player[][] board;
    private final Player currentPlayer;
    private final GameStatus status;
    private final Player winner;
    private final int moveCount;

    public GameState(Player[][] board, Player currentPlayer, GameStatus status, Player winner, int moveCount) {
        this.board = new Player[board.length][];
        for (int row = 0; row < board.length; row++) {
            this.board[row] = board[row].clone();
        }
        this.currentPlayer = currentPlayer;
        this.status = status;
        this.winner = winner;
        this.moveCount = moveCount;
    }

    public Player[][] getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Player getWinner() {
        return winner;
    }

    public int getMoveCount() {
        return moveCount;
    }
}
