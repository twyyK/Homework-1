package it.calidoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** Classe che gestisce la creazione della board e dei parametri di ogni cella */
public class Board {
    public static final int SIZE = 9;
    private Integer[][] board = new Integer[SIZE][SIZE];
    private static int contChar = 0;  //contatore


    /** Costruttore che prende in input il file del sudoku ed inizializza la board
     * @param path percorso dove trovare il file del sudoku
     */
    public Board(Path path){
        File inputFile = new File(path.toString());
        try  {
            Scanner s = new Scanner(inputFile);
            int row = 0;
            while(s.hasNextLine()){
                String line = s.nextLine();
                for (int column = 0; column < SIZE; column++)
                    board[row][column] = readValue(line.charAt(column)); //questa riga setta realmente la cella della board
                row++;
            }
            System.out.println("Empty cells: " + (81 - contChar));
            double percFill = 100 * contChar / 81;
            System.out.println("Fill factor: " + percFill + "%");
        } catch (FileNotFoundException e) {System.out.println("File non trovato");}
    }

    /** Metodo che prende in input un carattere e se è un numero lo trasforma in Integer, altrimenti ritorna null
     * @param c un carattere letto dal file in input del costruttore
     * @return un intero rappresentante il carattere in input o null
     */
    private Integer readValue(char c) {
        if (c != '.'){
            contChar++;
            return Integer.valueOf(c + "");
        } else
            return null;
    }

    /** Metodo che prende in input una cella (row, column) e ritorna la lista dei numeri che possono essere inseriti in quella cella in base alle regole del sudoku
     * @param row la riga
     * @param column la colonna
     * @return la lista delle opzioni per quella cella
     */
    public List<Integer> getOptionsForCell(int row, int column) {
        List<Integer> options = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        removeOptionsInRow(options, row);
        removeOptionsInColumn(options, column);
        removeOptionsInBox(options, row, column);
        return options;
    }

    /** Metodo che rimuove dalla lista dei numeri che possono essere inseriti in quella cella, i numeri che sicuramente non possono essere inseriti IN BASE AL BOX DEL SUDOKU(3X3)
     * @param options la lista delle opzioni della cella
     * @param row la riga
     * @param column la colonna
     */
    private void removeOptionsInBox(List<Integer> options, int row, int column) {
        int boxRow = row - row%3, boxColumn = column - column%3;        //boxRow si posiziona all'inizio della riga del box della cella in input, boxColumn si posiziona all'inizio della colonna del box della cella in input
        for (int rowOffset=0; rowOffset<3; rowOffset++) {               //i due for annidati eliminano dalla lista delle opzioni i numeri all'interno del BOX
            for (int columnOffset=0; columnOffset<3; columnOffset++) {
                options.remove(board[boxRow+rowOffset][boxColumn+columnOffset]);
            }
        }
    }

    /** Elimina dalla lista i numeri che non possono essere messi nella cella controllando la colonna della cella in input
     * @param options la lista delle opzioni della cella
     * @param column la colonna
     */
    private void removeOptionsInColumn(List<Integer> options, int column) {
        for (int row=0; row<SIZE; row++) options.remove(board[row][column]);
    }


    /** Elimina dalla lista i numeri che non possono essere messi nella cella controllando la riga della cella in input
     * @param options
     * @param row la riga
     */
    private void removeOptionsInRow(List<Integer> options, int row) {
        for (int column=0; column<SIZE; column++) options.remove(board[row][column]);
    }

    /** Setta la cella ad un valore dato in input
     * @param row la riga
     * @param column la colonna
     * @param value il valore da settare nella cella
     */
    public void setCellValue(int row, int column, int value) {
        board[row][column] = value;
    }

    /** Cancella il valore all'interno della cella in input
     * @param row la riga
     * @param column la colonna
     */
    public void clearCell(int row, int column) {
        board[row][column] = null;
    }

    /** Dice se la cella in input è vuota o no
     * @param row la riga
     * @param column la colonna
     * @return true se la cella è piena, altrimenti false
     */
    public boolean isFilled(int row, int column) {
        return board[row][column] != null;
    }

    /** Metodo che stampa la board del sudoku TODO: da modificare per farlo bene */
    public void getBoard(){
        for (int row = 0; row  < SIZE; row++) {
            if (row%3 == 0) {
                System.out.print("|");
                System.out.println("-----------|");
            }

            for (int column = 0; column < SIZE; column++) {
                if (column%3 == 0) System.out.print("|");
                System.out.print(board[row][column]);
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("|-----------|");
        System.out.println();
        System.out.println();
    }
}
