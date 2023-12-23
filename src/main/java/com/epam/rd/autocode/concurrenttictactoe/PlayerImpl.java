package com.epam.rd.autocode.concurrenttictactoe;


import java.util.Map;

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
        while (true) {
        synchronized (ticTacToe) {
            if (!checkIfGotChance(ticTacToe.table())){
                break;
            }
                if (ticTacToe.lastMark() == mark) {
                    // wait for other player to make a move
//
                }
                else   {
                    // other player has already made a move, so exit
                    Move move = strategy.computeMove(mark, ticTacToe);
                    ticTacToe.setMark(move.row, move.column, mark);

//                        ticTacToe.notifyAll();
                }
            }
        }
        Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
        for (Thread thread : threads.keySet()) {
            if (thread.getName().contains("Thread")) {
                thread.interrupt();
            }
        }
    }

    private synchronized boolean checkIfGotChance(char[][] table) {
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


