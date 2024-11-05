import javax.swing.*;
import java.io.*;



public class GridIO {
    public static void saveGrid(JTextField[][] grid) {
        try (PrintWriter writer = new PrintWriter(new File("grid.grid"))) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
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
    
    public static void loadGrid(JTextField[][] grid) {
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
}
