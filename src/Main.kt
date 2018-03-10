fun main(args: Array<String>) {

    with(MajorityVoteGenerator(
            PseudoRandomGenerator(k = 6, p = 590, a = arrayOf(0, 0, 1, 0, 0, 1),
                    x0 = arrayOf(200, 300, 400, 500, 550, 590)),
            PseudoRandomGenerator(k = 6, p = 590, a = arrayOf(0, 0, 1, 0, 0, 1),
                    x0 = arrayOf(453, 423, 321, 500, 232, 123)),
            PseudoRandomGenerator(k = 6, p = 590, a = arrayOf(0, 0, 1, 0, 0, 1),
                    x0 = arrayOf(340, 253, 190, 580, 430, 100))
            )
    ) {
        for (i in 1 until 30) {
            print("${this.next()}, ")
        }
        print("${this.next()}.")
    }

}