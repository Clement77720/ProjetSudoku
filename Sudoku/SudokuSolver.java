import java.util.Arrays;

public class SudokuSolver {
    private int[][] grid;

    public SudokuSolver(int[][] grid) {
        this.grid = grid;
    }

    public boolean solve() {
        int[] emptyCell = findEmptyCell();
        int row = emptyCell[0];
        int col = emptyCell[1];
        
        if (row == -1 && col == -1) {
            return true; // Aucune case vide trouvée, la grille est résolue
        }

        for (int num = 1; num <= 9; num++) {
            if (isValidMove(row, col, num)) {
                grid[row][col] = num;
                
                if (solve()) {
                    return true;
                }
                
                grid[row][col] = 0; // Annuler la modification si la solution n'est pas valide
            }
        }

        return false; // Aucune solution trouvée pour cette configuration
    }

    private int[] findEmptyCell() {
        int[] cell = new int[] {-1, -1};

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    cell[0] = row;
                    cell[1] = col;
                    return cell;
                }
            }
        }

        return cell; // Aucune case vide trouvée
    }

    private boolean isValidMove(int row, int col, int num) {
        // Vérifier la validité du numéro dans la ligne
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num) {
                return false;
            }
        }

        // Vérifier la validité du numéro dans la colonne
        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == num) {
                return false;
            }
        }

        // Vérifier la validité du numéro dans la région 3x3
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true; // Numéro valide dans cette configuration
    }

    public void printGrid() {
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void main(String[] args) {
        int[][] sudokuGrid = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        SudokuSolver solver = new SudokuSolver(sudokuGrid);
        if (solver.solve()) {
            System.out.println("Grille résolue : ");
            solver.printGrid();
        } else {
            System.out.println("Impossible de résoudre la grille.");
        }
    }

    public int[][] getGrid() {
    return grid;
}
}
