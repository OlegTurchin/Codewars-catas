object Snail {
  
  def snail(xs: List[List[Int]]): List[Int] = {
    def getColumn(n: Int, a: List[List[Int]]): List[Int] = a.map {_(n - 1)}

    def extractCenter(xs: List[List[Int]], b: List[Int]): List[List[Int]] = 
      xs.map(_.drop(1)).map(_.dropRight(1)).drop(1).dropRight(1)

    @scala.annotation.tailrec
    def chain(xs: List[List[Int]], temp: List[Int] = Nil): List[Int] = {
      if (xs.isEmpty) Nil
      else if (xs.length == 1) temp ++ xs.head
      else if (xs.length == 2) temp ++ xs.head ++ xs.tail.head.reverse
      else {
        val acc = temp ++ xs.head.dropRight(1) ++ getColumn(xs.length, xs).dropRight(1) ++ xs.last.reverse.dropRight(1) ++ getColumn(1, xs).reverse.dropRight(1)
        chain(extractCenter(xs, acc), acc)
      }
    }
    chain(xs)
  }
}