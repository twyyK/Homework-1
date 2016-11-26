package it.calidoku;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Board {
    public static final int SIZE = 9;
    private Integer[][] board = new Integer[SIZE][SIZE];


    public Board(Path path){
        File inputFile = new File(path.toString());
        try  {
            Scanner s = new Scanner(inputFile);
            for(int row = 0; row < SIZE; row++){
                String line = s.nextLine();
                for (int column = 0; column < SIZE; column++){
                    board[row][column] = readValue(line.charAt(column));
                }
                row++;
            }
        } catch (FileNotFoundException e) {System.out.println("File non trovato");}
    }

    private Integer readValue(char c) {
        return c != '.' ? Integer.valueOf(c + "") : null;
    }

    public List<Integer> getOptionsForCell(int row, int column) {
        List<Integer> options = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        removeOptionsInRow(options, row);
        removeOptionsInColumn(options, column);
        removeOptionsInBox(options, row, column);
        return options;
    }

    private void removeOptionsInBox(List<Integer> options, int row, int column) {
        int boxRow = row - row%3, boxColumn = column - column%3;
        for (int rowOffset=0; rowOffset<3; rowOffset++) {
            for (int columnOffset=0; columnOffset<3; columnOffset++) {
                options.remove(board[boxRow+rowOffset][boxColumn+columnOffset]);
            }
        }
    }

    private void removeOptionsInColumn(List<Integer> options, int column) {
        for (int row=0; row<SIZE; row++) options.remove(board[row][column]);
    }


    private void removeOptionsInRow(List<Integer> options, int row) {
        for (int column=0; column<SIZE; column++) options.remove(board[row][column]);
    }

    public void setCellValue(int row, int column, int value) {
        board[row][column] = value;
    }

    public void clearCell(int row, int column) {
        board[row][column] = null;
    }


    public boolean isFilled(int row, int column) {
        return board[row][column] != null;
    }

    public void getBoard(){
        for (int row = 0; row  < SIZE; row++) {
            for (int column = 0; column < SIZE; column++)
                System.out.print(board[row][column]);
            System.out.println();
        }

    }
}
