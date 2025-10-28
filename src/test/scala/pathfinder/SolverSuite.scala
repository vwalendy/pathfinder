package pathfinder

import grid.TestLevels.*
import grid.TestMove.*
import grid.*

class SolverUnitSuite extends munit.FunSuite:

  type Path = (TestState, List[TestMove])

  /** Tests the methods returning a list of paths. The paths should be sorted by
    * ascending path length.
    */
  def testPaths(result: LazyList[Path], expected: Seq[(TestState, Set[List[TestMove]])]) =
    assertEquals(result.length, expected.length)

    var maxLength = 0
    for path <- result do
      val currentLength = path._2.length
      assert(currentLength >= maxLength, "The paths should be sorted by ascending path length.")
      maxLength = currentLength

    val resultMap = result.toMap
    val expectedMap = expected.toMap
    for (s, hist) <- resultMap do
      assert(expectedMap.contains(s), f"Missing a reachable state ${s}")
      assert(expectedMap(s).contains(hist), f"Incorrect history ${hist} to state ${s}")

  test("shortestPaths: Level0, start state (4pts)"):
    val initialState = TestState(Pos(row = 1, col = 2))

    val expected = Seq(
      initialState -> Set(Nil),
      TestState(Pos(row = 1, col = 3)) -> Set(List(Right)),
      TestState(Pos(row = 2, col = 2)) -> Set(List(Down)),
      TestState(Pos(row = 2, col = 3)) -> Set(List(Down, Right), List(Right, Down)),
      TestState(Pos(row = 3, col = 2)) -> Set(List(Down, Down)),
      TestState(Pos(row = 3, col = 3)) -> Set(List(Down, Down, Right), List(Down, Right, Down), List(Right, Down, Down))
    )

    testPaths(Solver(Level0).shortestPaths(initialState), expected)

  test("shortestPaths: Level3, start state (4pts)"):
    val initialState = TestState(Pos(row = 1, col = 2))

    val expected = Seq(
      initialState -> Set(Nil),
      TestState(Pos(row = 1, col = 1)) -> Set(List(Left)),
      TestState(Pos(row = 0, col = 1)) -> Set(List(Up, Left)),
      TestState(Pos(row = 2, col = 1)) -> Set(List(Down, Left)),
      TestState(Pos(row = 0, col = 0)) -> Set(List(Left, Up, Left)),
      TestState(Pos(row = 2, col = 0)) -> Set(List(Left, Down, Left)),
      TestState(Pos(row = 3, col = 1)) -> Set(List(Down, Down, Left)),
      TestState(Pos(row = 3, col = 2)) -> Set(List(Right, Down, Down, Left))
    )

    testPaths(Solver(Level3).shortestPaths(initialState), expected)

  test("pathsFromStart: Level0 (2pts)"):
    val expected = Seq(
      TestState(Pos(row = 1, col = 2)) -> Set(Nil),
      TestState(Pos(row = 1, col = 3)) -> Set(List(Right)),
      TestState(Pos(row = 2, col = 2)) -> Set(List(Down)),
      TestState(Pos(row = 2, col = 3)) -> Set(List(Down, Right), List(Right, Down)),
      TestState(Pos(row = 3, col = 2)) -> Set(List(Down, Down)),
      TestState(Pos(row = 3, col = 3)) -> Set(List(Down, Down, Right), List(Down, Right, Down), List(Right, Down, Down))
    )
    testPaths(Solver(Level0).pathsFromStart, expected)

  test("pathsFromStart: Level3 (2pts)"):
    val expected = Seq(
      TestState(Pos(row = 1, col = 2)) -> Set(Nil),
      TestState(Pos(row = 1, col = 1)) -> Set(List(Left)),
      TestState(Pos(row = 0, col = 1)) -> Set(List(Up, Left)),
      TestState(Pos(row = 2, col = 1)) -> Set(List(Down, Left)),
      TestState(Pos(row = 0, col = 0)) -> Set(List(Left, Up, Left)),
      TestState(Pos(row = 2, col = 0)) -> Set(List(Left, Down, Left)),
      TestState(Pos(row = 3, col = 1)) -> Set(List(Down, Down, Left)),
      TestState(Pos(row = 3, col = 2)) -> Set(List(Right, Down, Down, Left))
    )

    testPaths(Solver(Level3).pathsFromStart, expected)

  test("pathsToGoal: Level0 (2pts)"):
    val expected = Seq(
      TestState(Pos(row = 1, col = 3)) -> Set(List(Right))
    )

    testPaths(Solver(Level0).pathsToGoal, expected)

  test("pathsToGoal: Level3 (2pts)"):
    val expected = Seq(
      TestState(Pos(row = 3, col = 2)) -> Set(List(Right, Down, Down, Left))
    )

    testPaths(Solver(Level3).pathsToGoal, expected)

class SolverSystemSuite extends munit.FunSuite:
  def testSolution(solver: Solver[TestState, TestMove], checker: SolutionChecker, expected: Seq[TestMove]) =
    require(checker.checkSolution(expected))
    val result = solver.solution
    assert(result.length <= expected.length, "solution is not of optimal length")
    assert(checker.checkSolution(result), "solution does not lead to the goal")

  test("solution: Level0 (2pts)"):
    val expected = Seq(Right)
    testSolution(Solver(Level0), SolutionChecker(Level0), expected)

  test("solution: Level1 (2pts)"):
    val expected = Seq(Right, Right, Right, Right, Down, Right, Right, Down, Down)
    testSolution(Solver(Level1), SolutionChecker(Level1), expected)

  test("solution: Level2 (2pts)"):
    val expected = Seq(Up, Left, Left, Down, Left, Left, Left, Left)
    testSolution(Solver(Level2), SolutionChecker(Level2), expected)

  test("solution: Level3 (2pts)"):
    val expected = Seq(Left, Down, Down, Right)
    testSolution(Solver(Level3), SolutionChecker(Level3), expected)
