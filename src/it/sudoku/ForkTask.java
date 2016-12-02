package it.sudoku;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * Created by User on 28/11/2016.
 */
public class ForkTask extends RecursiveTask<Boolean> {
    private Board board;
    private int index;
    private int SIZE = Board.SIZE;

    private int row;
    private int column;
    private int value = 0;

    //public static int count = 0;
    private static boolean solvable = false;

    public ForkTask(Board board, int index){
        this.board = board;
        this.index = index;
        row  = index / SIZE;
        column = index % SIZE;
    }

    /**
     * The main computation performed by this task.
     *
     * @return the result of the computation
     */
    static int count = 0;
    @Override
    protected Boolean compute() {
        return solve(board, index);
    }


    private boolean solve(Board board, int index) {
        int row = index / SIZE, column = index % SIZE;                             //row = trova la riga della cella corrente, column = trova la colonna della cella corrente
        List<ForkTask> taskList = new ArrayList<>();
        if (index == SIZE * SIZE) {                     //se index = 81 vuol dire che sono arrivato alla fine del sudoku e quindi l'ho risolto
            count++;                                //incremento il contatore delle soluzioni perchè quando si entra dentro questo IF vuol dire che è stato risolto un sudoku
            //board.getPrintedBoard();
            return true;
        }
        if (board.isFilled(row, column))
            return solve(board, index + 1);  //se la cella corrente è piena passo a quella successiva ricorsivamente


        for (Integer value : board.getOptionsForCell(row, column)) {           //se arriviamo qui vuol dire che la cella è vuota e itero su ogni valore che posso mettere dentro alla cella corrente
            Board b = new Board(copyBoard(board.getBoard()));
            b.setCellValue(row, column, value);                            //setto il valore nella cella

            taskList.add(new ForkTask(b, index+1));
            //taskList.add(task);
            //task.getBoard().getPrintedBoard();
        }

        invokeAll(taskList);
        board.clearCell(row, column);

        //se arrivo qui vuol dire che il sudoku non è risolvibile quindi ritorno false


        return solvable;
    }

    //public List<ForkTask> getTaskList() {
      //  return taskList;
    //}

    private Integer[][] copyBoard(Integer[][] griglia){
        Integer[][] newBoard = new Integer[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++)
                newBoard[i][j] = griglia[i][j];
        return newBoard;
    }

    //public void removeTask(ForkJoinTask t){
    //    taskList.remove(t);
    //}

    public static boolean isSolvable() {
        return solvable;
    }

    public Board getBoard() {
        return board;
    }
}
