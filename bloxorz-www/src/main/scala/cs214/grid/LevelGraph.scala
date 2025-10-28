package cs214.grid

import pathfinder.GameDef
import pathfinder.grid.StringParserTerrain

import scalajs.js
import scala.scalajs.js
import js.JSConverters.*

class LevelGraph[State, Move](val level: GameDef[State, Move] & StringParserTerrain):
  case class Graph(nodes: Set[State], transitions: List[(State, State, Move)])

  val graph = getGraph

  def getGraph: Graph =
    def explore(from: State, acc: Graph): Graph =
      level.neighbors(from).foldLeft(acc):
        case (graph, (m, s)) =>
          if graph.nodes.contains(s) then Graph(graph.nodes, (from, s, m) :: graph.transitions)
          else explore(s, Graph(graph.nodes + s, (from, s, m) :: graph.transitions))

    explore(level.startState, Graph(Set.empty, Nil))

  def getTerrain: Seq[(Int, Int)] =
    level.vector.zipWithIndex
      .flatMap((v, i) => v.zipWithIndex.collect { case (v, j) if Seq('o', 'S', 'T').contains(v) => (i, j) })
