package com.sudoku.controller;
import com.sudoku.exception.InvalidCharacterException;
import com.sudoku.exception.SudokuFileNotFoundException;
import com.sudoku.model.Sudoku;
import com.sudoku.service.SudokuSolver;
import lombok.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.*;

import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class SudokuController implements CommandLineRunner {

    private final SudokuSolver sudokuSolver;

    @Override
    public void run(String... args) throws Exception {
        String[] puzzles = {"puzzle1.txt", "puzzle2.txt", "puzzle3.txt", "puzzle4.txt"};

        IntStream.range(0, puzzles.length).forEach(i -> {
            try {
                System.out.println("Puzzle " + (i + 1));
                Sudoku sudoku = sudokuSolver.loadPuzzle(puzzles[i]);
                sudoku.printGridsBoard();

                if (sudokuSolver.solveBoard(sudoku)) {
                    System.out.println("Puzzle " + (i + 1) + " Solved");
                    sudoku.printGridsBoard();
                    sudokuSolver.saveSolution(sudoku);
                } else {
                    System.out.println("Could not solve puzzle " + (i + 1));
                }
            } catch (SudokuFileNotFoundException | InvalidCharacterException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error processing puzzle: " + e.getMessage());
            }
        });
    }
}