package functions

class TailRecursion {
}

tailrec fun factorialV2(n: Int, curr: Int = 1) : Int {
    return if (n <= 1) {
        curr
    } else {
        factorialV2(n - 1, n * curr)
    }
}