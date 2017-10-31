package streams

import common._

/**
  * This component implements the solver for the Bloxorz game
  */
trait Solver extends GameDef {

  /**
    * Returns `true` if the block `b` is at the final position
    */
  def done(b: Block): Boolean =
    if (b.isStanding && b.b1.row == goal.row && b.b1.col == goal.col)


  def neighborsWithHistory(b: Block, history: List[Move]): Stream[(Block, List[Move])] = {
    b.legalNeighbors.map {
      case (block, move) => (block, move :: history)
    }.toStream
  }

  /**
    * This function returns the list of neighbors without the block
    * positions that have already been explored. We will use it to
    * make sure that we don't explore circular paths.
    */
  def newNeighborsOnly(neighbors: Stream[(Block, List[Move])], explored: Set[Block]): Stream[(Block, List[Move])] =
    neighbors.filter(x => explored contains(x) == false)

  /**
    * The function `from` returns the stream of all possible paths
    * that can be followed, starting at the `head` of the `initial`
    * stream.
    *
    * The blocks in the stream `initial` are sorted by ascending path
    * length: the block positions with the shortest paths (length of
    * move list) are at the head of the stream.
    *
    * The parameter `explored` is a set of block positions that have
    * been visited before, on the path to any of the blocks in the
    * stream `initial`. When search reaches a block that has already
    * been explored before, that position should not be included a
    * second time to avoid cycles.
    *
    * The resulting stream should be sorted by ascending path length,
    * i.e. the block positions that can be reached with the fewest
    * amount of moves should appear first in the stream.
    *
    * Note: the solution should not look at or compare the lengths
    * of different paths - the implementation should naturally
    * construct the correctly sorted stream.
    */
  def from(initial: Stream[(Block, List[Move])],
           explored: Set[Block]): Stream[(Block, List[Move])] = initial match {
    // El pattern match habla por si solo
    case Stream.empty => Stream.empty
    case head #:: tail =>
      //Aqui tomamos el bloque inicial
      val (block, moves) = head
      // Se usan las funciones que programamos antes para obtener los vecinos
      val candidates = neighborsWithHistory(block, moves)
      // Se filtran por los bloques no visitados
      val availableNeighbors = newNeighborsOnly(candidates, explored)
      // Se agregan los bloques no explorados a la lista de explorados
      val exploredAumented = explored ++ (availableNeighbors.map(_._1))
      //**Se añade el inicial al resultado y se hace el llamado recursivo
      // con tail ++ los vecinos disponibles para ser visitados y el Set
      // de explored con sus nuevos integrantes*/
      head #:: from(tail ++ availableNeighbors, exploredAumented)
  }

  /**
    * The stream of all paths that begin at the starting block.
    */
  lazy val pathsFromStart: Stream[(Block, List[Move])] =
    from(Stream(startBlock, Nil), Set(startBlock))

  /**
    * Returns a stream of all possible pairs of the goal block along
    * with the history how it was reached.
    */
  lazy val pathsToGoal: Stream[(Block, List[Move])] =
    pathsFromStart.filter(block => done(block))

  /**
    * The (or one of the) shortest sequence(s) of moves to reach the
    * goal. If the goal cannot be reached, the empty list is returned.
    *
    * Note: the `head` element of the returned list should represent
    * the first move that the player should perform from the starting
    * position.
    */
  lazy val solution: List[Move] = {
    val solutions = pathsToGoal.map {
      case (_, moves) => moves.reverse
    }

    solutions match {
      case Stream.Empty => Nil
      case x #:: xs     => x
    }
  }
}