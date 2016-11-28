package it.sudoku;

import java.nio.file.Path;
import java.nio.file.Paths;

/** Classe che si occupa del vero e proprio algoritmo sequenziale*/
public class SequentialSolver {
    private Board board;
    public static int count = 0;
    private static boolean solvable = false; //variabile che tiene conto se un sudoku ha almeno una soluzione

    /** Costruttore che da in pasto il percorso alla classe che gestisce la board
     * @param percorso il percorso
     */
    public SequentialSolver(String percorso){
        Path path = Paths.get(percorso);
        board = new Board(path);
    }

    public boolean start() {
        solvable = false;
        count = 0;
        return solve(board, 0);
    }

    /** Metodo che risolve l'intero sudoku utilizzando l'algoritmo sequenziale
     * @param board la board del sudoku
     * @param index la posizione della cella corrente (va da 0 a 81 perchè le celle sono numerati a partite da in alto a sinistra fino a inbasso a destra e sono 81 in tutto)
     * @return true se il sudoku è stato risolto, false altrimenti
     */
    private boolean solve(Board board, int index) {
        int SIZE = Board.SIZE;
        int row = index/SIZE, column = index%SIZE;                             //row = trova la riga della cella corrente, column = trova la colonna della cella corrente


        if (index == SIZE*SIZE){                     //se index = 81 vuol dire che sono arrivato alla fine del sudoku e quindi l'ho risolto
            count++;                                //incremento il contatore delle soluzioni perchè quando si entra dentro questo IF vuol dire che è stato risolto un sudoku
            //printBoard().getBoard();
            return true;
        }
        if (board.isFilled(row, column))  return solve(board, index+1);  //se la cella corrente è piena passo a quella successiva ricorsivamente

        for (Integer value : board.getOptionsForCell(row, column)) {           //se arriviamo qui vuol dire che la cella è vuota e itero su ogni valore che posso mettere dentro alla cella corrente
            board.setCellValue(row, column, value);                            //setto il valore nella cella
            if (solve(board, index +1)) solvable = true;                //settando solvable = true in questo punto indico che c'è almeno una soluzione
        }

        board.clearCell(row, column);                                         //se arrivo qui vuol dire che il sudoku non è risolvibile quindi ritorno false
        return solvable == false ? false : true;                              //se solvable è true allora ritorno true altrimenti false perchè non è risolvibile
    }

    /*** ritorna la board del sudoku corrente
     * @return la board del sudoku corrente
     */
    public Board printBoard(){
        return board;
    }
}
