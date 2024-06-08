# Sudoku-Game-2.0

This is the first program that i've created. It's a Sudoku Game. So, this game basically for the final project of my college subject "Algorithm and Programming 2". This game created using Java Language. Here are the preview of the Game
# Main Menu Preview
![image](https://github.com/isanovsky/Sudoku-Game-2.0/assets/69060626/31dd2afe-5364-4fd9-b837-bc50e7d49d9d)
# Gameplay Preview
![image](https://github.com/isanovsky/Sudoku-Game-2.0/assets/69060626/231e9f64-f9d4-46f9-8052-4ad8d50178f6)


There are 8 classes in src package, there are :
- SudokuFrame Class
- SudokuPanel Class
- SudokuPuzzle Class
- SudokuPuzzleType Class
- SudokuGenerator Class
- SudokuLauncher Class
- SudokuMain Menu Class
- BackgroundPanel Class

The explanation of each classes are below :
# SudokuFrame Class
  - Purpose: The main window of the Sudoku game application.
  - Responsibilities:
  Initializes and displays the main game interface.
  Contains the JMenuBar with menus for game options (new game, exit), difficulty levels, and hints.
  Manages interactions between the user and the game, such as starting a new game, changing difficulty, and providing hints.
  - Connections:
  Instantiates and uses SudokuPanel to display the Sudoku board and handle game logic.
  Uses SudokuGenerator to generate new Sudoku puzzles with the selected difficulty.
# SudokuPanel Class
  - Purpose: The main display and interaction area for the Sudoku board.
  - Responsibilities:
  Renders the Sudoku grid and handles user input (mouse clicks, keyboard entries).
  Manages game logic, including starting a new game, providing hints, and checking for completion.
  Highlights cells based on user interactions and duplicate entries.
  - Connections:
  Uses SudokuPuzzle to represent the current state of the Sudoku board.
  Interacts with SudokuGenerator to generate new puzzles based on the selected difficulty.
  Communicates with SudokuFrame to update the game state and UI.
# SudokuPuzzle Class
  - Purpose: Represents the state of a Sudoku puzzle.
  Responsibilities:
  - Stores the current numbers on the Sudoku board.
  Provides methods to manipulate and validate the board state, such as making moves and checking for valid entries.
  - Connections:
  Used by SudokuPanel to manage and update the Sudoku board based on user actions.
  Initialized and populated by SudokuGenerator when a new game starts.
# SudokuPuzzleType Class
  - Purpose: Defines the structure and rules for different types of Sudoku puzzles.
  - Responsibilities:
  Stores configuration details such as the number of rows, columns, box width, box height, and valid values for a Sudoku puzzle.
  - Connections:
  Used by SudokuGenerator to create puzzles with specific configurations.
  Referenced by SudokuPuzzle to ensure the board adheres to the defined structure and rules.
# SudokuGenerator Class
  - Purpose: Generates Sudoku puzzles of varying difficulty.
  - Responsibilities:
  Provides methods to generate complete and valid Sudoku puzzles.
  Removes a specified number of digits to create puzzles of different difficulties (easy, medium, hard).
  - Connections:
  Generates SudokuPuzzle instances with the specified difficulty for use by SudokuPanel.
  Utilizes SudokuPuzzleType to ensure the generated puzzles adhere to the correct format and rules.
# SudokuMainMenu Class
  - Purpose: Created Main Menu of the Sudoku Game
  - Responsibilities:
  Provides a "New Game" and "Exit" Button and also the combo box for selecting difficulties
  - Connections:
  Linked up with SudokuFrame to execute the main Application.
# SudokuLauncher Class
  - Purpose: The main class of Sudoku Application.
  - Responsibilities:
  Initializes and displays the main game interface.
  Execute the program to the SudokuMainMenu Class.
  - Connections:
  Uses SudokuMainMenu Class to display the UI of the Application
# BackgroundPanel Class
  - Purpose: initiate the background for the Main Menu
  - Responsibilities
    This class is responsible for import and place the image background for the SudokuMainMenu Class
  - Connections
    BackgroundPanel has a connection for the SudokuMainMenu which is generate the background image

# Relationships and Workflow
1. SudokuFrame initializes the game interface, including the menu bar and game panel.
2. When the user starts a new game or changes difficulty, SudokuFrame instructs SudokuPanel to start a new game with the selected difficulty.
3. SudokuPanel uses SudokuGenerator to generate a new puzzle. SudokuGenerator creates and configures a SudokuPuzzle using the settings from SudokuPuzzleType.
4. SudokuPanel displays the generated puzzle, handles user input, and updates the board state.
5. If the user requests a hint, SudokuPanel provides one using its internal game logic.
6. SudokuPanel checks for puzzle completion and interacts with SudokuFrame to display appropriate messages (e.g., congratulations on completion).

Overall it's a basic and beginner game. So, if there's some bugs, i hope you could send me a me suggestion. 
Anyway Thank you and Enjoy the Game.
