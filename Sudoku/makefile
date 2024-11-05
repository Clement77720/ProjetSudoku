# Variables
JAVAC=javac
JAVA=java
SRC_DIR=.
BIN_DIR=.
MAIN_CLASS=SudokuGridCreator
SOLVER_CLASS=SudokuSolver
SOLVER_UI_CLASS=SudokuSolverGUI
GRID_VALIDATOR_CLASS=GridValidator
GRID_IO_CLASS=GridIO

# Définition des fichiers sources et des fichiers objets
MAIN_SRC=$(SRC_DIR)/$(MAIN_CLASS).java
SOLVER_SRC=$(SRC_DIR)/$(SOLVER_CLASS).java
SOLVER_UI_SRC=$(SRC_DIR)/$(SOLVER_UI_CLASS).java
GRID_VALIDATOR_SRC=$(SRC_DIR)/$(GRID_VALIDATOR_CLASS).java
GRID_IO_SRC=$(SRC_DIR)/$(GRID_IO_CLASS).java

MAIN_CLASS_FILE=$(BIN_DIR)/$(MAIN_CLASS).class
SOLVER_CLASS_FILE=$(BIN_DIR)/$(SOLVER_CLASS).class
SOLVER_UI_CLASS_FILE=$(BIN_DIR)/$(SOLVER_UI_CLASS).class
GRID_VALIDATOR_CLASS_FILE=$(BIN_DIR)/$(GRID_VALIDATOR_CLASS).class
GRID_IO_CLASS_FILE=$(BIN_DIR)/$(GRID_IO_CLASS).class

# Cible par défaut pour construire le programme
all: $(MAIN_CLASS_FILE) $(SOLVER_CLASS_FILE) $(SOLVER_UI_CLASS_FILE) $(GRID_VALIDATOR_CLASS_FILE) $(GRID_IO_CLASS_FILE)

# Règle de construction des fichiers .class pour le fichier SudokuGridCreator.java
$(MAIN_CLASS_FILE): $(MAIN_SRC)
	$(JAVAC) $< -d $(BIN_DIR)

# Règle de construction des fichiers .class pour le fichier SudokuSolver.java
$(SOLVER_CLASS_FILE): $(SOLVER_SRC)
	$(JAVAC) $< -d $(BIN_DIR)

# Règle de construction des fichiers .class pour le fichier SudokuSolverGUI.java
$(SOLVER_UI_CLASS_FILE): $(SOLVER_UI_SRC)
	$(JAVAC) $< -d $(BIN_DIR)

# Règle de construction des fichiers .class pour le fichier GridValidator.java
$(GRID_VALIDATOR_CLASS_FILE): $(GRID_VALIDATOR_SRC)
	$(JAVAC) $< -d $(BIN_DIR)

# Règle de construction des fichiers .class pour le fichier GridIO.java
$(GRID_IO_CLASS_FILE): $(GRID_IO_SRC)
	$(JAVAC) $< -d $(BIN_DIR)

# Cible pour exécuter SudokuGridCreator
run_creator: $(MAIN_CLASS_FILE)
	$(JAVA) -cp $(BIN_DIR) $(MAIN_CLASS)

# Cible pour exécuter le solveur Sudoku
run_solver: $(SOLVER_UI_CLASS_FILE)
	$(JAVA) -cp $(BIN_DIR) $(SOLVER_UI_CLASS)

# Cible pour nettoyer les fichiers compilés
clean:
	rm -f $(BIN_DIR)/*.class
