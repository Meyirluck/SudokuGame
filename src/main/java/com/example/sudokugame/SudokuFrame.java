package com.example.sudokugame;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class SudokuFrame extends JFrame {

    private JPanel buttonSelectionPanel;
    private SudokuPanel sPanel;

    public SudokuFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Sudoku");
        this.setMinimumSize(new Dimension(800,600));

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("Game");
        JMenuItem file1 = new JMenuItem("Finish");
        file1.addActionListener(new FinishAction(sPanel));
        JMenu newGame = new JMenu("New Game");
        JMenuItem sixBySixGame = new JMenuItem("6 By 6 Game");
        sixBySixGame.addActionListener(new NewGameListener(SudokuPuzzleType.SIXBYSIX,30));
        JMenuItem nineByNineGame = new JMenuItem("9 By 9 Game");
        nineByNineGame.addActionListener(new NewGameListener(SudokuPuzzleType.NINEBYNINE,26));
        JMenuItem twelveByTwelveGame = new JMenuItem("12 By 12 Game");
        twelveByTwelveGame.addActionListener(new NewGameListener(SudokuPuzzleType.TWELVEBYTWELVE,20));
        JMenuItem sixteenBySixteenGame = new JMenuItem("16 By 16 Game");
        sixteenBySixteenGame.addActionListener(new NewGameListener(SudokuPuzzleType.SIXTEENBYSIXTEEN,20));

        newGame.add(sixBySixGame);
        newGame.add(nineByNineGame);
        newGame.add(twelveByTwelveGame);
        newGame.add(sixteenBySixteenGame);
        file.add(newGame);
        menuBar.add(file);
        menuBar.add(file1);
        this.setJMenuBar(menuBar);

        JPanel windowPanel = new JPanel();
        windowPanel.setLayout(new FlowLayout());
        windowPanel.setPreferredSize(new Dimension(800,600));

        buttonSelectionPanel = new JPanel();
        buttonSelectionPanel.setPreferredSize(new Dimension(90,500));

        sPanel = new SudokuPanel();

        windowPanel.add(sPanel);
        windowPanel.add(buttonSelectionPanel);
        this.add(windowPanel);

        rebuildInterface(SudokuPuzzleType.NINEBYNINE, 26);
    }

    public void rebuildInterface(SudokuPuzzleType puzzleType,int fontSize) {
        SudokuPuzzle generatedPuzzle = new SudokuGenerator().generateRandomSudoku(puzzleType);
        sPanel.newSudokuPuzzle(generatedPuzzle);
        sPanel.setFontSize(fontSize);
        buttonSelectionPanel.removeAll();
        for(String value : generatedPuzzle.getValidValues()) {
            JButton b = new JButton(value);
            b.setPreferredSize(new Dimension(40,40));
            b.addActionListener(sPanel.new NumActionListener());
            buttonSelectionPanel.add(b);
        }
        sPanel.repaint();
        buttonSelectionPanel.revalidate();
        buttonSelectionPanel.repaint();
    }

    public class FinishAction implements ActionListener {
        private SudokuPanel sudokuPanel;

        public FinishAction(SudokuPanel sudokuPanel) {
            this.sudokuPanel = sudokuPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (sPanel.getPuzzle().boardFull()) {
                checkSudokuAnswer();
            } else {
                showBoardNotFullMessage();
            }
        }


        private void showBoardNotFullMessage() {
            JOptionPane.showMessageDialog(sudokuPanel, "The Sudoku board is not full. You are not finished yet.", "Board Not Full", JOptionPane.INFORMATION_MESSAGE);
        }

        private void checkSudokuAnswer() {
            SudokuPuzzle puzzle = sPanel.getPuzzle();
            if (puzzle != null && puzzle.boardFull() && puzzle.isSolved()) {
                showCongratulationsDialog();
            } else {
                showIncorrectAnswerDialog();
            }
        }

        private void showCongratulationsDialog() {
            JOptionPane.showMessageDialog(sPanel, "Congratulations! You solved the Sudoku puzzle!", "Congratulations", JOptionPane.INFORMATION_MESSAGE);
            ActionListener new_game = new NewGameListener(SudokuPuzzleType.NINEBYNINE,26);
            ((NewGameListener) new_game).action();
        }

        private void showIncorrectAnswerDialog() {
            JOptionPane.showMessageDialog(sPanel, "Your answer is incorrect. Please try again.", "Incorrect Answer", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class NewGameListener implements ActionListener {

        private SudokuPuzzleType puzzleType;
        private int fontSize;

        public NewGameListener(SudokuPuzzleType puzzleType,int fontSize) {
            this.puzzleType = puzzleType;
            this.fontSize = fontSize;
        }

        public void action() {
            rebuildInterface(puzzleType,fontSize);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            rebuildInterface(puzzleType,fontSize);
        }
    }
}
