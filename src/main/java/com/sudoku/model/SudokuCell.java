package com.sudoku.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SudokuCell {
    private int value;
    private boolean ifPutANumber;

    public boolean isEmpty() {
        return value == 0;
    }
}