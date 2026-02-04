package com.sudoku.model;

import lombok.Data;

@Data
public class Sudoku {
    private SudokuCell[][] grids;
    private String puzzleName;

    public Sudoku() {
        this.grids = new SudokuCell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                grids[i][j] = new SudokuCell(0, false);
            }
        }
    }

    public void printGridsBoard() {
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("_________________________________________");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    System.out.print(" || ");
                }
                int value = grids[i][j].getValue();
                System.out.print(" " + value + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
    }
}