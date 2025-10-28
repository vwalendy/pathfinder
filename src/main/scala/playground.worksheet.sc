import pathfinder.*
import grid.*
import BloxorzLevels.*
import BloxorzMove.*

val solver = new Solver(Level1)
solver.shortestPaths(BloxorzState(Pos(1,1),Pos(1,1)))
