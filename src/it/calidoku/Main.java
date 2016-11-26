package it.calidoku;

import java.util.Scanner;

/** Classe che gestisce il menu di avvio iniziale.
 *  Per far partire l'esecuzione sequenziale digitare il comando "sequential" seguito dal path del file in questione.. Esempio -> sequential C:\homework.txt
 *  Per far partire l'esecuzione parallela digitare il comando "parallel" seguito dal path del file in questione.. Esempio -> parallel C:\homework.txt       TODO: Non ancora implementato
 */
public class Main {

    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        String command = s.nextLine();                                      //leggo il comando in input
        String[] parameters = command.split( " ");                   //splitto il comando cosi da avere da una parte la parola chaive (sequential) e dall'altra parte il path
        if (parameters[0].equals("sequential")) {                          //se il primo parametro è "sequential" allora eseguo l'algoritmo sequenziale
            SequentialSolver solver = new SequentialSolver(parameters[1]);//esegue il tutto
            System.out.println(solver.start());
            solver.printBoard().getBoard();
        }
    }


}
