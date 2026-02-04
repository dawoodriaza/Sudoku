package com.sudoku.service;

import com.sudoku.model.Sudoku;
import com.sudoku.model.SudokuCell;
import org.springframework.stereotype.Service;

@Service
public class SudokuSolver {
    public boolean solve(Sudoku sudoku) {
        SudokuCell[][] baord = sudoku.getGrids();
        return solveBoard(baord);
    }
    private boolean solveBoard(SudokuCell[][] baord) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (baord[row][column].isEmpty()) {
                    for (int guess = 1; guess <= 9; guess++) {
                        if (isValid(baord, row, column, guess)) {
                            baord[row][column].setValue(guess);
                            if (solveBoard(baord)) {
                                return true;
                            }
                            baord[row][column].setValue(0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
    private boolean isValid(SudokuCell[][] baord, int row, int column, int guess) {
        for (int i = 0; i < 9; i++) {
            if (baord[row][i].getValue() == guess) {
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (baord[i][column].getValue() == guess) {
                return false;
            }
        }
        int startRow = row - row % 3;
        int startCol = column - column % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (baord[startRow + i][startCol + j].getValue() == guess) {
                    return false;
                }
            }
        }
        return true;
    }
}