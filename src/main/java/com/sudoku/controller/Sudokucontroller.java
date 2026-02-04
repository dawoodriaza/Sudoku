package com.sudoku.controller;

import com.sudoku.exception.InvalidCharacterException;
import com.sudoku.exception.SudokuFileNotFoundException;
import com.sudoku.model.Sudoku;
import com.sudoku.service.SudokuSolver;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sudoku")
@RequiredArgsConstructor
public class Sudokucontroller {

    private final SudokuSolver sudokuSolver;

    @GetMapping("/display/{puzzleName}")
    public String displayPuzzle(@PathVariable String puzzleName) {
        try {
            Sudoku sudoku = sudokuSolver.loadGrid(puzzleName + ".txt");
            return sudoku.getGridAsString();
        } catch (SudokuFileNotFoundException | InvalidCharacterException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/solve/{puzzleName}")
    public String solvePuzzle(@PathVariable String puzzleName) {
        try {
            Sudoku sudoku = sudokuSolver.loadGrid(puzzleName + ".txt");
            String original = "Original Puzzle:\n" + sudoku.getGridAsString() + "\n\n";

            if (sudokuSolver.solveBoard(sudoku)) {
                sudokuSolver.saveSolution(sudoku);
                return original + "Solved Puzzle:\n" + sudoku.getGridAsString();
            } else {
                return original + "Could not solve puzzle";
            }
        } catch (SudokuFileNotFoundException | InvalidCharacterException e) {
            return "Error: " + e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/solve-all")
    public String solveAllPuzzles() {
        String[] puzzles = {"puzzle1.txt", "puzzle2.txt", "puzzle3.txt", "puzzle4.txt"};
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < puzzles.length; i++) {
            try {
                Sudoku sudoku = sudokuSolver.loadGrid(puzzles[i]);
                result.append("Puzzle ").append(i + 1).append(":\n");
                result.append(sudoku.getGridAsString()).append("\n\n");

                if (sudokuSolver.solveBoard(sudoku)) {
                    sudokuSolver.saveSolution(sudoku);
                    result.append("Puzzle ").append(i + 1).append(" Solved:\n");
                    result.append(sudoku.getGridAsString()).append("\n\n");
                } else {
                    result.append("cannot not solve puzzle ").append(i + 1).append("\n\n");
                }
            } catch (Exception e) {
                result.append("Error: ").append(e.getMessage()).append("\n\n");
            }
        }

        return result.toString();
    }
}