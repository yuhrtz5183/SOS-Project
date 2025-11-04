# SOS Game Project

## Project Overview

This is a Java-based implementation of the SOS game, where players can configure the game to their liking! Current features include:

- Choose board size (3x3 to 9x9)
- Select game mode:
  - Simple Game: Player wins immediately after forming "SOS"
  - General Game: Player scores points for each SOS formed, with the player having the most points winning
- Start a new game at any time
- Make moves by selecting a cell and placing S or O
- Alternates turns between Player Blue and Player Red
- Player configuration (Human or Computer)

## How to Run

### Local Setup
1. Clone the repository into your IDE of choice (making sure it can run Java projects)
3. Ensure JUnit 4 is properly configured in the project folder
4. Navigate to the `src/sprint2/` folder

### Running the Program
1. To launch the game, run the file `SOSGUI.java`
2. To run JUnit tests, two files can be run
   - `TestBoard.java`
   - `TestGameMode.java`

## Sprint Development

### Sprint 0 - Setup Requirements
- GUI design with Java Swing
- JUnit testing framework
- Basic test cases (SquareTest, CountATest, GUITestingTest)

### Sprint 1 - User Story Planning
- User stories and acceptance criteria development
- Requirements analysis

### Sprint 2 - Initial Implementation 
- Interactive GUI
- Complete game board logic
- Complete S/O placement for Human Player
- Automated testing (TestBoard, TestGameMode)

## Notes
Further features will be added in the continuing sprints such as:
- "SOS" logic
- Win condition for **Simple** and **General** gamemode
- Computer player integration
- Better UI updates
- Proper error handling

