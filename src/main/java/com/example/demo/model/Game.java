package com.example.demo.model;

public class Game {
    public static final int ROWS = 6;
    public static final int COLS = 7;

    private final Player[][] board = new Player[ROWS][COLS];
    private Player currentPlayer = Player.RED;
    private GameStatus status = GameStatus.IN_PROGRESS;
    private int moveCount = 0;
    private Player winner = null;

    public Player[][] getBoard() {
        return board;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public GameStatus getStatus() {
        return status;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public Player getWinner() {
        return winner;
    }

    public void play(int column) {
        if (status != GameStatus.IN_PROGRESS) {
            throw new IllegalStateException("game is over");
        }
        if (column < 0 || column >= COLS) {
            throw new IllegalArgumentException("column is outside the board");
        }
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][column] == null) {
                board[row][column] = currentPlayer;
                moveCount++;
                if (win(row, column)) {
                    winner = currentPlayer;
                    status = GameStatus.WIN;
                } else if (moveCount == ROWS * COLS) {
                    status = GameStatus.DRAW;
                } else {
                    currentPlayer = currentPlayer.next();
                }
                return;
            }
        }
        throw new IllegalArgumentException("column is full");
    }

    private boolean win(int row, int column) {
        Player player = board[row][column];
        return line(row, column, player, 1, 0)
                || line(row, column, player, 0, 1)
                || line(row, column, player, 1, 1)
                || line(row, column, player, 1, -1);
    }

    private boolean line(int row, int column, Player player, int rowStep, int colStep) {
        int count = 1;
        count += steps(row, column, player, rowStep, colStep);
        count += steps(row, column, player, -rowStep, -colStep);
        return count >= 4;
    }

    private int steps(int row, int column, Player player, int rowStep, int colStep) {
        int count = 0;
        int r = row + rowStep;
        int c = column + colStep;
        while (r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == player) {
            count++;
            r += rowStep;
            c += colStep;
        }
        return count;
    }
}
