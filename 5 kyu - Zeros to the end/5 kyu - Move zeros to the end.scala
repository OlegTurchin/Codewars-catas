object Kata {
	def moveZeroes(list: List[Int]): List[Int] = list.filter(x => x != 0) ++ list.filter(x => x == 0)
}