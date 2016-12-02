package it.sudoku;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 28/11/2016.
 */
public class ParallelSolver {
    private Board board;

    public static ForkJoinPool pool = ForkJoinPool.commonPool();

    public ParallelSolver(String percorso){
        Path path = Paths.get(percorso);
        board = new Board(path);
    }

    public boolean start() {
        ForkTask task = new ForkTask(board, 0);
        return pool.invoke(task);
/**
        for (ForkJoinTask<Boolean> t : task.getTaskList()) {
            System.out.println("prima del join");
            t.join();
            System.out.println("dopo del join");
            task.removeTask(t);
        }
        return task.isSolvable();*/
    }

}
