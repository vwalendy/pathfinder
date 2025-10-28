package pathfinder.grid

enum TestMove:
  case Left, Right, Up, Down
import TestMove.*

case class TestState(p: Pos):
  def left = TestState(p.deltaCol(-1))
  def right = TestState(p.deltaCol(1))
  def up = TestState(p.deltaRow(-1))
  def down = TestState(p.deltaRow(1))

trait TestDef extends TerrainGameDef[TestState, TestMove]:
  def startPos: Pos
  def goal: Pos

  override def startState: TestState = TestState(startPos)

  override def physicalNeighbors(state: TestState): Map[TestMove, TestState] =
    return Map(
      TestMove.Left -> state.left,
      TestMove.Right -> state.right,
      TestMove.Up -> state.up,
      TestMove.Down -> state.down
    )

  override def neighbors(state: TestState): Map[TestMove, TestState] =
    physicalNeighbors(state).filter((_, s) => isLegal(s))

  override def isDone(state: TestState): Boolean = state.p == goal

  def isLegal(state: TestState): Boolean = terrain(state.p)

class SolutionChecker(gameDef: TestDef):
  /** This method applies a list of moves `ls` to the block at position
    * `startPos`. This can be used to verify if a certain list of moves is a
    * valid solution, i.e. leads to the goal.
    */
  def applyMoves(moves: Seq[TestMove]): TestState =
    moves.foldLeft(gameDef.startState) { case (state, move) =>
      require(gameDef.isLegal(state)) // The solution must always lead to legal blocks
      move match
        case Left  => state.left
        case Right => state.right
        case Up    => state.up
        case Down  => state.down
    }

  def checkSolution(solution: Seq[TestMove]) = applyMoves(solution).p == gameDef.goal
