package com.sudoku.service;

import com.sudoku.exception.InvalidCharacterException;
import com.sudoku.exception.SudokuFileNotFoundException;
import com.sudoku.model.Sudoku;
import com.sudoku.model.SudokuCell;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class SudokuSolver {

    public Sudoku loadGrid(String filename) throws SudokuFileNotFoundException, InvalidCharacterException {
        File file = new File("puzzles/" + filename);
        if (!file.exists()) {
            throw new SudokuFileNotFoundException("File not found: " + filename);
        }
        Sudoku sudoku = new Sudoku();
        sudoku.setPuzzleName(filename.replace(".txt", ""));
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null && row < 9) {
                String[] values = line.trim().split("\\s+");
                for (int col = 0; col < 9 && col < values.length; col++) {
                    int value = Integer.parseInt(values[col]);
                    if (value < 0 || value > 9) {
                        throw new InvalidCharacterException("Invalid value: " + value);
                    }
                    boolean ifPutANumber = value != 0;
                    sudoku.getGrids()[row][col] = new SudokuCell(value, ifPutANumber);
                }
                row++;
            }
        } catch (IOException e) {
            throw new SudokuFileNotFoundException("Error reading file: " + filename);
        } catch (NumberFormatException e) {
            throw new InvalidCharacterException("Invalid character in file");
        }

        return sudoku;
    }

    public void saveSolution(Sudoku sudoku) throws IOException {
        String filename = "puzzles/" + sudoku.getPuzzleName() + "solution.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            SudokuCell[][] grids = sudoku.getGrids();
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    writer.write(grids[row][col].getValue() + " ");
                }
                writer.newLine();
            }
        }
    }

    public boolean solveBoard(Sudoku sudoku) {
        SudokuCell[][] grids = sudoku.getGrids();
        return solve(grids);
    }

    private boolean solve(SudokuCell[][] grids) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grids[row][col].isEmpty()) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(grids, row, col, num)) {
                            grids[row][col].setValue(num);
                            if (solve(grids)) {
                                return true;
                            }
                            grids[row][col].setValue(0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(SudokuCell[][] grids, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (grids[row][i].getValue() == num) {
                return false;
            }
        }

        for (int i = 0; i < 9; i++) {
            if (grids[i][col].getValue() == num) {
                return false;
            }
        }

        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grids[startRow + i][startCol + j].getValue() == num) {
                    return false;
                }
            }
        }

        return true;
    }
}