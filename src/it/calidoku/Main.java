package it.calidoku;

import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        String command = s.nextLine();
        String[] parameters = command.split( " ");
        if (parameters[0].equals("sequential")) {
            SequentialSolver solver = new SequentialSolver(parameters[1]);
            System.out.println(solver.start());
            solver.printBoard().getBoard();
        }
    }


}
