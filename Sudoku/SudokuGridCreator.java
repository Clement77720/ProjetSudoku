import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class SudokuGridCreator {
    private static final int GRID_SIZE = 9;
    private JFrame frame;
    private JPanel panel;
    private JTextField[][] grid;

    public SudokuGridCreator() {
        frame = new JFrame("Sudoku Grid Creator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        grid = new JTextField[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                JTextField textField = new JTextField(2);
                panel.add(textField);
                grid[row][col] = textField;
            }
        }

        JButton verifyButton = new JButton("Vérifier");
        verifyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (GridValidator.validateGrid(grid) && GridValidator.verifyDuplicates(grid) && GridValidator.verifyRegions(grid)) {
                    JOptionPane.showMessageDialog(null, "La grille est valide !");
                } else {
                    JOptionPane.showMessageDialog(null, "La grille n'est pas valide. Veuillez la corriger.");
                }
            }
        });

        JButton saveButton = new JButton("Enregistrer");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (GridValidator.validateGrid(grid) && GridValidator.verifyDuplicates(grid) && GridValidator.verifyRegions(grid)) {
                    saveGrid();
                } else {
                    JOptionPane.showMessageDialog(null, "La grille n'est pas valide. Veuillez la corriger.");
                }
            }
        });
        
        JButton loadButton = new JButton("Charger");
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                loadGrid();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(verifyButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.getContentPane().add(BorderLayout.SOUTH, buttonPanel);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }

    private void saveGrid() {
        try (PrintWriter writer = new PrintWriter(new File("grid.grid"))) {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    String text = grid[row][col].getText().trim();
                    int num = text.isEmpty() ? 0 : Integer.parseInt(text);
                    writer.print(num);
                }
                writer.println();
            }
            JOptionPane.showMessageDialog(null, "Grille sauvegardée avec succès !");
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Erreur lors de la sauvegarde de la grille : " + e.getMessage());
        }
    }
    
    private void loadGrid() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                for (int i = 0; i < GRID_SIZE; i++) {
                    String line = reader.readLine();
                    if (line != null) {
                        for (int j = 0; j < GRID_SIZE; j++) {
                            char ch = line.charAt(j);
                            if (ch != '0') {
                                grid[i][j].setText(Character.toString(ch));
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SudokuGridCreator());
    }
}

