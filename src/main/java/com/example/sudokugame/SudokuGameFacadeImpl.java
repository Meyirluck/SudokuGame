package com.example.sudokugame;

public class SudokuGameFacadeImpl implements SudokuGameFacade {
    private SudokuFrame sudokuFrame;

    public SudokuGameFacadeImpl() {
        this.sudokuFrame = new SudokuFrame();
    }

    @Override
    public void startNewGame(SudokuPuzzleType puzzleType, int fontSize) {
        sudokuFrame.rebuildInterface(puzzleType, fontSize);
        sudokuFrame.setVisible(true);
    }

}


