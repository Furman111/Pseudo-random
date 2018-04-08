package test.spectral

    fun computeDft(inreal: DoubleArray, inimag: DoubleArray, outreal: DoubleArray, outimag: DoubleArray) {
        val n = inreal.size
        for (k in 0 until n) {  // For each output element
            var sumreal = 0.0
            var sumimag = 0.0
            for (t in 0 until n) {  // For each input element
                val angle = 2.0 * Math.PI * t.toDouble() * k.toDouble() / n
                sumreal += inreal[t] * Math.cos(angle) + inimag[t] * Math.sin(angle)
                sumimag += -inreal[t] * Math.sin(angle) + inimag[t] * Math.cos(angle)
            }
            outreal[k] = sumreal
            outimag[k] = sumimag
        }
    }
