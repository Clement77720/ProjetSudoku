import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;

public class SudokuSolverGUI {
    private JFrame frame;
    private JPanel panel;
    private JButton solveButton;
    private JButton loadButton;
    private JButton verifyButton;
    private JButton timerButton;
    private JButton hintButton; // Bouton pour l'indice
    private JTextField[][] grid;

    public SudokuSolverGUI() {
        frame = new JFrame("Sudoku Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridLayout(10, 10));
        grid = new JTextField[9][9];

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                JTextField textField = new JTextField(2);
                panel.add(textField);
                grid[row][col] = textField;
            }
        }

        solveButton = new JButton("Résoudre");
        solveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                solveSudoku();
            }
        });

        loadButton = new JButton("Charger");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                loadGrid();
            }
        });

        verifyButton = new JButton("Vérifier");
        verifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                verifyGrid();
            }
        });

        timerButton = new JButton("Timer");
        timerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                startTimer();
            }
        });

        hintButton = new JButton("Indice");
        hintButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                provideHint();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(verifyButton);
        buttonPanel.add(timerButton);
        buttonPanel.add(hintButton); // Ajout du bouton pour l'indice

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    private void solveSudoku() {
        int[][] sudokuGrid = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = grid[i][j].getText();
                sudokuGrid[i][j] = text.isEmpty() ? 0 : Integer.parseInt(text);
            }
        }

        SudokuSolver solver = new SudokuSolver(sudokuGrid);
        if (solver.solve()) {
            // Afficher la grille résolue dans les champs de texte
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    grid[i][j].setText(Integer.toString(sudokuGrid[i][j]));
                }
            }
            JOptionPane.showMessageDialog(null, "La grille a été résolue avec succès !");
        } else {
            JOptionPane.showMessageDialog(null, "Impossible de résoudre la grille.");
        }
    }

    private void loadGrid() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                for (int i = 0; i < 9; i++) {
                    String line = reader.readLine();
                    if (line != null) {
                        for (int j = 0; j < 9; j++) {
                            char ch = line.charAt(j);
                            if (ch != '0') {
                                grid[i][j].setText(Character.toString(ch));
                                grid[i][j].setEditable(false); // Rend le champ de texte non modifiable
                            } else {
                                grid[i][j].setText("");
                            }
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Grille chargée avec succès !");
            } catch (IOException | NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Erreur lors du chargement de la grille : " + e.getMessage());
            }
        }
    }

    private void verifyGrid() {
        int[][] sudokuGrid = new int[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String text = grid[i][j].getText();
                sudokuGrid[i][j] = text.isEmpty() ? 0 : Integer.parseInt(text);
            }
        }

        GridValidator validator = new GridValidator();
        if (validator.validateGrid(grid) && validator.verifyDuplicates(grid)) {
            JOptionPane.showMessageDialog(null, "La grille est valide et ne contient aucun doublon.");
            // Vérifier si la grille est complète
            if (isGridComplete()) {
                JOptionPane.showMessageDialog(null, "Youpi, vous avez gagné !");
            }
        } else {
            JOptionPane.showMessageDialog(null, "La grille contient des erreurs.");
        }
    }

    private boolean isGridComplete() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j].getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void startTimer() {
        JFrame timerFrame = new JFrame("Chronomètre");
        timerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));

        Timer timer = new Timer(1000, new ActionListener() {
            private long startTime = System.nanoTime(); // Temps de départ du chronomètre

            public void actionPerformed(ActionEvent e) {
                long elapsedTime = (System.nanoTime() - startTime) / 1000000000; // Temps écoulé en secondes
                long hours = elapsedTime / 3600;
                long minutes = (elapsedTime % 3600) / 60;
                long seconds = elapsedTime % 60;
                timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
                // Vérifie si la grille est complète
                if (isGridComplete()) {
                    ((Timer) e.getSource()).stop(); // Arrête le chronomètre une fois la grille complète
                    JOptionPane.showMessageDialog(null, "La grille est complète !");
                    timerFrame.dispose(); // Ferme la fenêtre du chronomètre
                }
            }
        });

        timer.start();

        timerFrame.getContentPane().add(BorderLayout.CENTER, timerLabel);
        timerFrame.setSize(200, 100);
        timerFrame.setVisible(true);
    }

    private void provideHint() {
        // Obtenir une case vide aléatoire
        Random random = new Random();
        int row = random.nextInt(9);
        int col = random.nextInt(9);

        // Récupérer un nombre valide pour cette case
        String hint = getRandomValidNumber(row, col);

        // Mettre ce nombre dans la case correspondante
        grid[row][col].setText(hint);
    }

    private String getRandomValidNumber(int row, int col) {
        // Ici, tu dois implémenter la logique pour obtenir un nombre valide pour la case donnée
        // Par exemple, tu peux obtenir un nombre valide en utilisant la méthode isValidMove de SudokuSolver
        return "5"; // Pour l'exemple, on retourne simplement "5"
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SudokuSolverGUI());
    }
}
