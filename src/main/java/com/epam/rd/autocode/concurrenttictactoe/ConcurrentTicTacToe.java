package com.epam.rd.autocode.concurrenttictactoe;


public class ConcurrentTicTacToe implements TicTacToe {
    private final char[][] board = new char[3][3];
    private volatile char lastMark = ' ';

    public ConcurrentTicTacToe() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    @Override
    public synchronized void setMark(int x, int y, char mark) {
        if (board[x][y] != ' ') {
            throw new IllegalArgumentException("Cell is already occupied");
        }
        board[x][y] = mark;
        lastMark = mark;
    }

    @Override
    public synchronized  char[][] table() {
        char[][] copy = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                copy[i][j] = board[i][j];
            }
        }
        return copy;
    }

    @Override
    public  char lastMark() {
        return lastMark;
    }


    public static TicTacToe buildGame() {
        return new ConcurrentTicTacToe();
    }
}
