val input = "01111111111111111111".toList

var i = 0
var r = 0
for (elem <- input) {
  r = r + (elem * (2^(-i)))
  i = i + 1
}