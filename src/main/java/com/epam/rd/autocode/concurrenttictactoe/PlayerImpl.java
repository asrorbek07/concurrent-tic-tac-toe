package com.epam.rd.autocode.concurrenttictactoe;


public class PlayerImpl implements Player{
    private TicTacToe ticTacToe;
    char mark;
    PlayerStrategy strategy;

    public PlayerImpl(TicTacToe ticTacToe, char mark, PlayerStrategy strategy) {
        this.ticTacToe = ticTacToe;
        this.mark = mark;
        this.strategy = strategy;
    }

    @Override
    public void run() {
        try {
            while (checkIfGotChance(ticTacToe.table())) {
            synchronized (ticTacToe) {
                    if (ticTacToe.lastMark() == mark) {
                        // wait for other player to make a move
                        ticTacToe.wait();
                    }
                    else   {
                        // other player has already made a move, so exit
                        Move move = strategy.computeMove(mark, ticTacToe);
                        ticTacToe.setMark(move.row, move.column, mark);
                        ticTacToe.notifyAll();
                    }
                }
            }
            Thread.currentThread().interrupt();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkIfGotChance(char[][] table) {
        return !(table[0][2] == table[1][1] && table[1][1] == table[2][0] && table[1][1] != ' '||
                table[0][0] == table[1][1] && table[1][1] == table[2][2] && table[1][1] != ' ' ||
                table[0][0] == table[0][1] && table[0][1] == table[0][2] && table[0][1] != ' ' ||
                table[1][0] == table[1][1] && table[1][1] == table[1][2] && table[1][1] != ' ' ||
                table[2][0] == table[2][1] && table[2][1] == table[2][2] && table[2][1] != ' ' ||
                table[0][0] == table[1][0] && table[1][0] == table[2][0] && table[1][0] != ' ' ||
                table[0][1] == table[1][1] && table[1][1] == table[2][1] && table[1][1] != ' ' ||
                table[0][2] == table[1][2] && table[1][2] == table[2][2] && table[1][2] != ' ');
    }
}


