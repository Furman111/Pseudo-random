fun main(args: Array<String>) {

    with(Generator(
            k = 6,
            p = 2,
            a = arrayOf(0, 0, 1, 0, 0, 1),
            x0 = arrayOf(1, 1, 1, 0, 1, 0)
    )) {
        for (i in 1..30) {
            print("${this.next()}, ")
        }
    }

}