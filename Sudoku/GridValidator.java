import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GridValidator {

    public static boolean validateGrid(JTextField[][] grid) {
        final int GRID_SIZE = 9;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                String text = grid[i][j].getText();
                if (!text.isEmpty()) {
                    try {
                        int value = Integer.parseInt(text);
                        if (value < 1 || value > 9) {
                            JOptionPane.showMessageDialog(null, "La grille contient des valeurs invalides !");
                            return false;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "La grille contient des valeurs non numériques !");
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean verifyDuplicates(JTextField[][] grid) {
        for (int i = 0; i < 9; i++) {
            if (!verifyRow(i, grid) || !verifyColumn(i, grid)) {
                return false;
            }
        }
        return verifyRegions(grid);
    }

    private static boolean verifyRow(int row, JTextField[][] grid) {
        boolean[] present = new boolean[10];
        for (int j = 0; j < 9; j++) {
            String text = grid[row][j].getText();
            if (!text.isEmpty()) {
                int number = Integer.parseInt(text);
                if (number < 1 || number > 9) {
                    JOptionPane.showMessageDialog(null, "La grille contient des valeurs invalides !");
                    return false;
                }
                if (present[number]) {
                    JOptionPane.showMessageDialog(null, "Doublon trouvé dans la ligne " + (row + 1) + " !");
                    return false;
                }
                present[number] = true;
            }
        }
        return true;
    }

    private static boolean verifyColumn(int column, JTextField[][] grid) {
        boolean[] present = new boolean[10];
        for (int i = 0; i < 9; i++) {
            String text = grid[i][column].getText();
            if (!text.isEmpty()) {
                int number = Integer.parseInt(text);
                if (number < 1 || number > 9) {
                    JOptionPane.showMessageDialog(null, "La grille contient des valeurs invalides !");
                    return false;
                }
                if (present[number]) {
                    JOptionPane.showMessageDialog(null, "Doublon trouvé dans la colonne " + (column + 1) + " !");
                    return false;
                }
                present[number] = true;
            }
        }
        return true;
    }

    public static boolean verifyRegions(JTextField[][] grid) {
        // Vérification des doublons dans les régions
        for (int regionRow = 0; regionRow < 3; regionRow++) {
            for (int regionCol = 0; regionCol < 3; regionCol++) {
                if (!verifySubGrid(regionRow * 3, regionCol * 3, grid)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean verifySubGrid(int startRow, int startCol, JTextField[][] grid) {
        boolean[] present = new boolean[10];
        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                String text = grid[row][col].getText();
                if (!text.isEmpty()) {
                    int number = Integer.parseInt(text);
                    if (number < 1 || number > 9) {
                        JOptionPane.showMessageDialog(null, "La grille contient des valeurs invalides !");
                        return false;
                    }
                    if (present[number]) {
                        JOptionPane.showMessageDialog(null, "Doublon trouvé dans une région de 3x3 !");
                        return false;
                    }
                    present[number] = true;
                }
            }
        }
        return true;
    }
}
