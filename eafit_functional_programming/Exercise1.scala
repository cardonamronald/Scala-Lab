package co.edu.eafit.dis.progfun.scala.intro

object Exercise1 extends App {
    def and(x: Boolean, y: Boolean): Boolean = if(x) y else false
    
    def or (x: Boolean, y: Boolean): Boolean = if(x) true else y

    // Note, Note that && and || do not always need their 
    // right operand to be evaluated.

    def shortCircuitAnd(x: Boolean, y: => Boolean): Boolean = 
        if(x) y else false

    def shortCircuitOr(x: Boolean, y: => Boolean): Boolean = 
        if(x) true else y



    def abs(x: Int): Int =
        if (x >= 0) {
            x
        } else {
            -x
        }
}