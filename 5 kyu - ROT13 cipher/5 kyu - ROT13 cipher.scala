  val upperCase = 65 to 90
  val lowerCase = 97 to 122

  def rot13(message: String): String = {
    def conversion(char: Char): Char = {
      if (!lowerCase.contains(char) && !upperCase.contains(char)) return char
      if (lowerCase.contains(char)) {
        if (lowerCase.contains(char + 13)) (char + 13).toChar
        else (97 + (12 - (122 - char))).toChar
      } else {
        if (upperCase.contains(char + 13)) (char + 13).toChar
        else (65 + (12 - (90 - char))).toChar
      }
    }
    message.map(x => conversion(x))
  }