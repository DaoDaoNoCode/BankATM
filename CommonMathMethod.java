import java.math.BigDecimal;

public class CommonMathMethod {
    /**
     * @param decimal the input decimal
     * @return decimal after round half up
     */
    static public double twoDecimal(double decimal) {
        BigDecimal newDecimal = BigDecimal.valueOf(decimal);
        return newDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    static public double fourDecimal(double decimal) {
        BigDecimal newDecimal = BigDecimal.valueOf(decimal);
        return newDecimal.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    static public double bigDecimalMultiply(double d1, double d2) {
        BigDecimal dBig1 = BigDecimal.valueOf(d1);
        BigDecimal dBig2 = BigDecimal.valueOf(d2);
        return dBig1.multiply(dBig2).doubleValue();
    }
}
