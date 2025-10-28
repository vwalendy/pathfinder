package pathfinder

/** Solves i.e., finds the shortest path from the start to the end of a game */
class Solver[State, Move](gameDef: GameDef[State, Move]):

  type Path = (State, List[Move])

  /** The function `shortestPaths` lazily returns all shortest paths to any
    * other state, starting at the `from` state.
    */
  def shortestPaths(from: State): LazyList[Path] =
    ???

  /** Returns the lazy list of all shortest paths to any other state, beginning
    * at the starting state.
    */
  lazy val pathsFromStart: LazyList[Path] =
    ???

  /** Returns the lazy list of all shortest paths leading to the goal state,
    * beginning at the starting state.
    */
  lazy val pathsToGoal: LazyList[Path] =
    ???

  /** The (or one of the) shortest sequence(s) of moves to reach the goal. If
    * the goal cannot be reached, the empty list is returned.
    *
    * Note: the `head` element of the returned list should represent the first
    * move that the player should perform from the starting position.
    */
  lazy val solution: List[Move] =
    ???
