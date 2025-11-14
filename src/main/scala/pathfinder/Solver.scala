package pathfinder

/** Solves i.e., finds the shortest path from the start to the end of a game */
class Solver[State, Move](gameDef: GameDef[State, Move]):

  type Path = (State, List[Move])

  /** The function `shortestPaths` lazily returns all shortest paths to any
    * other state, starting at the `from` state.
    */
  def shortestPaths(from: State): LazyList[Path] = {
  // chemin de départ : état initial, aucune action jouée
  val start: Path = (from, Nil)

  // fonction récursive BFS paresseuse
  def rec(initial: LazyList[Path], seen: Set[State]): LazyList[Path] = {
    initial match
      case LazyList() =>
        LazyList.empty
      case (state, moves) #:: rest =>
        // 1) voisins légaux depuis `state`
        val nextPaths: LazyList[Path] =
          gameDef.neighbors(state).to(LazyList).map { case (move, nextState) =>
            // on ajoute le move en tête de la liste d'historique
            (nextState, move :: moves)
          }
          // 2) on garde seulement ceux dont l'état n'a jamais été vu
          .filter { case (nextState, _) =>
            !seen(nextState)
          }

        // 3) on construit la nouvelle file et le nouvel ensemble des vus
        val newInitial = rest ++ nextPaths
        val newSeen    = seen ++ nextPaths.map(_._1)

        // on renvoie le chemin courant, puis récursivement les autres
        (state, moves) #:: rec(newInitial, newSeen)
  }

  // appel initial : on a vu seulement l'état de départ
  rec(LazyList(start), Set(from))
}



  /** Returns the lazy list of all shortest paths to any other state, beginning
    * at the starting state.
    */
  lazy val pathsFromStart: LazyList[Path] =
    shortestPaths(gameDef.startState)

  /** Returns the lazy list of all shortest paths leading to the goal state,
    * beginning at the starting state.
    */
  lazy val pathsToGoal: LazyList[Path] =
    pathsFromStart.filter { case (state, _) =>
    gameDef.isDone(state)
  }

  /** The (or one of the) shortest sequence(s) of moves to reach the goal. If
    * the goal cannot be reached, the empty list is returned.
    *
    * Note: the `head` element of the returned list should represent the first
    * move that the player should perform from the starting position.
    */
  lazy val solution: List[Move] =
     pathsToGoal.headOption match
      case Some((_, moves)) =>
       moves.reverse
      case None =>
        Nil
