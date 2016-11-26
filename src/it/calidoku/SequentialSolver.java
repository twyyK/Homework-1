package it.calidoku;

import java.nio.file.Path;
import java.nio.file.Paths;


public class SequentialSolver {
    private Board board;

    public SequentialSolver(String percorso){
        Path path = Paths.get(percorso);
        board = new Board(path);

    }

    public boolean start() {
        return solve(board, 0);
    }

    private boolean solve(Board board, int index) {
        int SIZE = Board.SIZE;
        int row = index/SIZE, column = index%SIZE;

        if (index == SIZE*SIZE) return true;
        if (board.isFilled(row, column))  return solve(board, index+1);

        for (Integer value : board.getOptionsForCell(row, column)) {
            board.setCellValue(row, column, value);
            if (solve(board, index+1)) return true;
        }
        board.clearCell(row, column);
        return false;
    }

    public Board printBoard(){
        return board;
    }
}
