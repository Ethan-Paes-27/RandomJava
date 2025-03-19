import java.util.ArrayList;
import java.math.BigInteger;

public class FactorProductOfPrimes { //https://www.youtube.com/watch?v=-UrdExQW0cs

    private final ArrayList<Integer> USED_INTEGERS = new ArrayList<Integer>(); //Contains all used numbers
    int countR;

    /**
     *
     * @param n, which is the product of two prime numbers
     * @return Point, which is the two factors of n in the order smaller number, bigger number
     */
    public void factorProductOfPrimes(long n) {
        System.out.println("Calculating...");

        // if it is divisible by 2, return 2 and n / 2
        if (n % 2 == 0) {
            System.out.println(factorPrinter(n, BigInteger.TWO, BigInteger.valueOf(n / 2)));
        }

        // if it has a perfect square root, return its square roots
        double d1 = Math.sqrt(n);
        if (d1 == Math.floor(d1) && d1 == Math.ceil(d1)) {
            System.out.println(factorPrinter(n, BigInteger.valueOf((long) Math.sqrt(n)), BigInteger.valueOf((long) Math.sqrt(n))));
        }

        USED_INTEGERS.add(1);

        BigInteger gPowRPlus1 = findGPowRPlus1(n);
        BigInteger firstNum = getFirstNum(gPowRPlus1, n);

        //if firstNum is one, keeps finding a new one
        while (firstNum.abs().equals(BigInteger.ONE) || firstNum.abs().equals(BigInteger.valueOf(n))) {
            gPowRPlus1 = findGPowRPlus1(n);
            firstNum = getFirstNum(gPowRPlus1, n);
        }

        BigInteger secondNum = BigInteger.valueOf(n).divide(firstNum);

        System.out.println(factorPrinter(n, firstNum, secondNum));
    }

    /**
     *
     * @param n, the product of two prime numbers
     * @param firstNum, calculated using findFirstNum
     * @param secondNum, calculated using n / firstNum
     * @return A string converter to print the factors of n
     */
    private String factorPrinter(long n, BigInteger firstNum, BigInteger secondNum) {
        return "The factors of " + n + " are " + firstNum.min(secondNum) + " and " + firstNum.max(secondNum) + ".";
    }

    /**
     *
     * @param n, which is the product of two prime numbers
     * @return GPowRPlus1, which shares a common multiple with n
     */
    private BigInteger findGPowRPlus1(long n) {
        int g = findCoprime(n);
        int r = findR(n, g);

        boolean foundR = r % 2 == 0 && r != -1;

        while (!foundR) { //Checks to make sure that r is valid
            g = findCoprime(n);
            r = findR(n, g);

            foundR = r % 2 == 0 || r == -1;
        }

        BigInteger gPowRPlus1 = BigInteger.valueOf((long)Math.pow(g, (double) r /2) + 1);

        if (!isGPowRPlus1Okay(gPowRPlus1, n)) { //Checks to make sure that GPowRPlus1 is okay, otherwise uses recursion
            return findGPowRPlus1(n);
        }
        else return gPowRPlus1;
    }

    /**
     *
     * @param n, which is the product of two prime numbers
     * @return g, a coprime of n (has no common factors with n)
     */
    private int findCoprime(long n) {
        int g = 0;
        boolean foundG = false;

        while (!foundG) {
            g = randomEvenNumber(n);

            if (!USED_INTEGERS.contains(g)) {
                foundG = true;
                USED_INTEGERS.add(g);
            }
        }

        return g;
    }

    /**
     *
     * @param n, which is the product of two prime numbers
     * @return e, a random even number that is less than n
     */
    private int randomEvenNumber(long n) {
        int e = 1;
        while (e % 2 == 1 || e >= n) {
            e = (int)(Math.random() * n) + 1;
        }

        return e;
    }

    /**
     *
     * @param n, which is the product of two prime numbers
     * @param g, which is a coprime of n
     * @return r, which is some power where g^r modded by n is 1
     */
    private int findR(long n, int g) {
        boolean foundR = false;
        int r = 1;
        int countR = 0;

        while (!foundR) {
            if (countR == 100) {
                return r = -1;
            }

            int remainder = (int) (Math.pow(g, r) % n);

            if (remainder == 1) {
                foundR = true;
            }
            else r++;
            countR++;
        }

        return r;
    }

    /**
     *
     * @param gPowRPlus1, gPowRPlus1 from above
     * @param n, which is the product of two prime numbers
     * @return a boolean, which tells if the remainder after modding gPowRPlus1 by n is NOT 0 (false = 0)
     */
    private boolean isGPowRPlus1Okay(BigInteger gPowRPlus1, long n) {
        BigInteger nBig = BigInteger.valueOf(n);
        return gPowRPlus1.mod(nBig).intValue() != 0;
    }

    /**
     *
     * @param gPowRPlus1, which is g^(r/2) + 1 (found above)
     * @param n, which is the product of two prime numbers
     * @return theFirstNum, which uses Euclid's Algorithm to get a common factor of gPowRPlus1 and n (is technically the remainder or bottom)
     */
    private BigInteger getFirstNum(BigInteger gPowRPlus1, long n) {
        boolean foundR = false;

        BigInteger top = gPowRPlus1.max(BigInteger.valueOf(n));
        BigInteger bottom = gPowRPlus1.min(BigInteger.valueOf(n));
        BigInteger remainder = top.abs().mod(bottom.abs());

        if (remainder.equals(BigInteger.ZERO)) {
            return bottom;
        }

        while (!foundR) {
            BigInteger tempRem = remainder;
            if (!bottom.mod(remainder).equals(BigInteger.ZERO)) {
                remainder = bottom.mod(remainder);
                bottom = tempRem;
            } else foundR = true;
        }

        return remainder;
    }

    public static void main(String[] args) {
        FactorProductOfPrimes f = new FactorProductOfPrimes();
        f.factorProductOfPrimes(3917 * 2851);
    }
}
