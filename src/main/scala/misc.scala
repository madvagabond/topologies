
sealed trait Counter {
  def incr: Counter
  def version: Long 
}

object Counter {
  def compare(l: Counter, r: Counter) = l.version - r.version
}

case class VectorClock(key: String, c: Long)  extends Counter {
  def incr: VectorClock = copy( c = (c + 1L )  )
  def version: Long = c
}

case class PNCounter(key: String, add: Long, delete: Long ) extends Counter {
  def incr: PNCounter = copy( add = (add + 1L ) )
  def decr: PNCounter = copy( delete = ( delete + 1L ) )
  def version: Long = add + delete
}


object PNCounter {
  def zero: PNCounter = PNCounter("", 0, 0 )  
}


object VectorClock {
  def zero: VectorClock = VectorClock("", 0)
}

