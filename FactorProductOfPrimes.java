import java.util.ArrayList;
import java.awt.Point;

public class FactorProductOfPrimes { //https://www.youtube.com/watch?v=-UrdExQW0cs

    private final ArrayList<Integer> USED_INTEGERS = new ArrayList<Integer>(); //Contains all used numbers

    /**
     * 
     * @param n, which is the product of two different prime numbers
     * @return Point, which is the two factors of n in the order smaller number, bigger number
     */
    public Point factorProductOfPrimes(int n) {
        // if it is divisible by 2, return 2 and n / 2
        if (n % 2 == 0) {
            return new Point(2, n / 2);
        }
        
        USED_INTEGERS.add(1);

        int gPowRPlus1 = findGPowRPlus1(n);
        int firstNum = getFirstNum(gPowRPlus1, n);
        
        //if firstNum is one, keeps finding a new one
        while (firstNum == 1) {
            gPowRPlus1 = findGPowRPlus1(n);
            firstNum = getFirstNum(gPowRPlus1, n);
        }

        int secondNum = n / firstNum;

        return (new Point (Math.min(firstNum, secondNum), Math.max(firstNum, secondNum)));
    }

    /**
     * 
     * @param n, which is the product of two different prime numbers
     * @return GPowRPlus1, which shares a common multiple with n
     */
    public int findGPowRPlus1(int n) {
        int g = findCoprime(n);
        int r = findR(n, g);

        boolean foundR = !(r % 2 == 1);

        while (!foundR) { //Checks to make sure that r is valid
            g = findCoprime(n);
            r = findR(n, g);

            foundR = !(r % 2 == 1);
        }

        int gPowRPlus1 = (int)Math.pow(g, r/2) + 1;

        if (!isGPowRPlus1Okay(gPowRPlus1, n)) { //Checks to make sure that GPowRPlus1 is okay, otherwise uses recursion
            return findGPowRPlus1(n);
        }
        else return gPowRPlus1;
    }

    /**
     * 
     * @param gPowRPlus1, gPowRPlus1 from above
     * @param n, which is the product of two different prime numbers
     * @return a boolean, which tells if the remainder after modding gPowRPlus1 by n is NOT 0 (false = 0)
     */
    public boolean isGPowRPlus1Okay(int gPowRPlus1, int n) {
        return gPowRPlus1 % n != 0;
    }

    /**
     * 
     * @param n, which is the product of two different prime numbers
     * @return g, a coprime of n (has no common factors with n)
     */
    public int findCoprime(int n) {
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
     * @param n, which is the product of two different prime numbers
     * @return e, a random even number that is less than n
     */
    public int randomEvenNumber(int n) {
        int e = 1;
        while (e % 2 == 1 || e >= n) {
            e = (int)(Math.random() * n) + 1;
        }

        return e;
    }

    /**
     * 
     * @param n, which is the product of two different prime numbers
     * @param g, which is a coprime of n
     * @return r, which is some power where g^r modded by n is 1
     */
    public int findR(int n, int g) {
        boolean foundR = false;
        int r = 1;
        while (!foundR) {
            int remainder = (int)(Math.pow(g, r)) % n;
            if (remainder == 1) {
                foundR = true;
            }
            else r++;
        }

        return r;
    }

    /**
     * 
     * @param gPowRPlus1, which is g^(r/2) + 1 (found above)
     * @param n, which is the product of two different prime numbers
     * @return theFirstNum, which uses Euclid's Algorithm to get a common factor of gPowRPlus1 and n (is technically the remainder or bottom)
     */
    public int getFirstNum(int gPowRPlus1, int n) {
        boolean foundR = false;

        int top = Math.max(gPowRPlus1, n);
        int bottom = Math.min(gPowRPlus1, n);
        int remainder = top % bottom;

        if (remainder == 0) {
            return bottom;
        }

        while (!foundR) {
            int tempRem = remainder;
            if (bottom % remainder != 0) {
                remainder = bottom % remainder;
                bottom = tempRem;
            } else foundR = true;
        }

        return remainder;
    }

    public static void main(String[] args) {
        FactorProductOfPrimes f = new FactorProductOfPrimes();
        System.out.println(f.factorProductOfPrimes(77));
    }
}
