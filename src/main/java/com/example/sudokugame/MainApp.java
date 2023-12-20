package com.example.sudokugame;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuGameFacade gameFacade = new SudokuGameFacadeImpl();
            gameFacade.startNewGame(SudokuPuzzleType.NINEBYNINE, 26);
        });
    }
}

