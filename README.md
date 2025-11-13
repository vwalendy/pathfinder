# Pathfinder – CS-214 Labs (EPFL)

This repository contains my implementation of the **Pathfinder** lab for the EPFL course **CS-214 – Software Construction**.

The project consists of implementing a **general pathfinding solver** using:
- Lazy evaluation  
- State transition graphs  
- Breadth-first search (BFS)  
- Scala 3  
- LazyList-based exploration  

This solver is then applied to a simplified version of the classic puzzle game **Bloxorz**.

---

## 🚀 Project Overview

The goal is to:
1. Implement a generic solver capable of finding **shortest paths** in a game defined by states and moves.
2. Implement the simplified **Bloxorz** game:
   - parsing terrains  
   - modeling block positions  
   - generating neighbors  
   - validating moves  
3. Use the solver to automatically compute the **shortest sequence of moves** needed to reach the goal.
4. Visualize and play Bloxorz directly in the browser using Scala.js.

---


