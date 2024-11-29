# Conway's Game of Life 🚀  

A multithreaded implementation of **Conway's Game of Life** using **Java** and **JavaFX** for visualization. The game simulates cellular automata, where cells live, die, or multiply based on simple rules, creating fascinating patterns over time.  

---

## Features 🧩  
- **Multithreaded Execution:** Each update cycle is efficiently calculated using Java threads to improve performance.  
- **Pixel-Based Visualization:** A smooth, real-time grid-based display powered by JavaFX.  
- **Customizable Grid:** Adjust the grid size, initial states, and simulation speed.  
- **Dynamic Simulation:** The grid updates every second to show the evolution of the game.  

---

## Rules of Conway's Game of Life 🌱  
The game follows these simple rules:  
1. **Underpopulation:** Any live cell with fewer than 2 live neighbors dies.  
2. **Overpopulation:** Any live cell with more than 3 live neighbors dies.  
3. **Reproduction:** Any dead cell with exactly 3 live neighbors becomes alive.  
4. **Survival:** Any live cell with 2 or 3 live neighbors survives to the next generation.  

---

## Prerequisites ⚙️  
Ensure you have the following installed:  
- **Java JDK 11+**  
- **JavaFX SDK 11+**  

---

## Installation & Running the Project 🛠️  

1. **Clone the Repository**  
   ```bash  
   git clone https://github.com/yassirH9/Conway-Game-WithU.git  
   cd Conway-Game-WithU
2. **Add JavaFX Libraries**  
   - Download the [JavaFX SDK](https://openjfx.io/) and set up the `PATH_TO_FX` environment variable.  
   - To set the `PATH_TO_FX`, follow these steps:  
     - On **Windows**:  
       1. Download and extract the JavaFX SDK to a directory of your choice.  
       2. Open System Properties > Environment Variables.  
       3. Add a new variable called `PATH_TO_FX` and set its value to the `lib` folder of your JavaFX SDK directory (e.g., `C:\path\to\javafx-sdk\lib`).  
     - On **Mac/Linux**:  
       1. Download and extract the JavaFX SDK to a directory of your choice.  
       2. Open your terminal and add the following line to your shell configuration file (`~/.bashrc`, `~/.zshrc`, etc.):  
          ```bash  
          export PATH_TO_FX=/path/to/javafx-sdk/lib  
          ```  
       3. Reload your shell configuration:  
          ```bash  
          source ~/.bashrc  
          ```  

3. **Compile and Run**  
   - Use the following commands to compile and run the project:  
     ```bash  
     javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml -d out src/*.java  
     java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml -cp out Main  
     ```
## How to Play 🎮
- Launch the application.
- A grid will appear representing the initial state of the simulation.
- Watch as the simulation evolves based on Conway's rules.
- Customize parameters in code to change the simulation results.

## Screenshots 🌌
![video](src/main/resources/assets/conway_game_example.gif)

---

## Contributing 🤝  

Contributions are welcome!  

If you would like to improve this project, follow these steps:  

1. **Fork the repository**  
   - Click on the "Fork" button at the top right of this repository's GitHub page to create a copy in your GitHub account.  

2. **Clone your fork**  
   - Clone the forked repository to your local machine:  
     ```bash  
     git clone https://github.com/your-username/Conway-Game-WithU.git  
     cd Conway-Game-WithU  
     ```  

3. **Create a new branch**  
   - Use a meaningful name for your branch:  
     ```bash  
     git checkout -b feature-name  
     ```  

4. **Make your changes**  
   - Implement the feature, bug fix, or improvement. Ensure your code follows best practices and is properly documented.  

5. **Commit your changes**  
   - Write a clear and descriptive commit message:  
     ```bash  
     git commit -m "Add feature-name"  
     ```  

6. **Push your branch**  
   - Push the changes to your forked repository:  
     ```bash  
     git push origin feature-name  
     ```  

7. **Open a Pull Request (PR)**  
   - Navigate to the original repository and click on "Pull Requests."  
   - Click "New Pull Request" and provide a detailed description of your changes.  

Once your PR is reviewed and approved, it will be merged into the main repository.  

---

## Acknowledgments 🌟  

- Inspired by John Conway's work on cellular automata.  
- Built with passion and curiosity by [Yassir H9](https://github.com/yassirH9).  

---

Enjoy exploring Conway's Game of Life! ✨  
