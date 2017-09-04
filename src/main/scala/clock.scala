package topologies
/**  counters are logical clocks for causal ordering of events */
import scodec._, codecs.{utf8, int64L}


sealed trait Counter {
  def incr: Counter
  def version: Long 
}


object Counter {
  def compare(l: Counter, r: Counter) = l.version - r.version
}


/**  Vector Clocks, are labeled monotonic counters */
case class VectorClock(key: String, c: Long)  extends Counter {
  def incr: VectorClock = copy( c = (c + 1L )  )
  def version: Long = c
}


/**  Has two monotonic counters, one for adds, the other for deletes**/

case class PNCounter(key: String, add: Long, delete: Long ) extends Counter {
  def incr: PNCounter = copy( add = (add + 1L ) )
  def decr: PNCounter = copy( delete = ( delete + 1L ) )
  def version: Long = add + delete
}


object PNCounter {
  def zero: PNCounter = PNCounter("", 0L, 0L )
  def codec = (utf8 :: int64L :: int64L).as[PNCounter]
}


object VectorClock {
  def codec = (utf8 :: int64L).as[VectorClock]
  def zero: VectorClock = VectorClock("", 0L)
}
