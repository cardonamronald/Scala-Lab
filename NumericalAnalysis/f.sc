def f(x: Double): Double  = math.cos(x)
def fprima(x: Double): Double = 3*math.pow(x, 2) - 2*x + (2*math.sin(x-1)*math.cos(x-1)) - 1
fprima(1)
fprima(-1.5)
fprima(-0.5)
f(-1.5)
f(0.5)
