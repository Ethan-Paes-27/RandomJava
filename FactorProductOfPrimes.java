import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.SpringLayout;

import java.awt.Point;

public class FactorProductOfPrimes { //https://www.youtube.com/watch?v=-UrdExQW0cs

    private static final ArrayList<Integer> USED_INTEGERS = new ArrayList<Integer>();

    public static Point factorProductOfPrimes(int n) {
        if (n % 2 == 0) {
            return new Point(2, n / 2);
        }

        USED_INTEGERS.add(1);
        
        int gPowRPlus1 = findGPowRPlus1(n);

        int firstNum = getFirstNum(gPowRPlus1, n);

        if (firstNum == 1) {
            
        }
        int secondNum = n / firstNum;

        return (new Point (firstNum, secondNum));
    }

    private static int findGPowRPlus1(int n) {
        int g = findCoprime(n);
        int r = findR(n, g);
   
        boolean foundR = !(r % 2 == 1);

        while (!foundR) {
            g = findCoprime(n);
            r = findR(n, g);

            foundR = !(r % 2 == 1);
        }

        int gPowRPlus1 = (int)Math.pow(g, r/2) + 1;

        if (!isGPowRPlus1Okay(gPowRPlus1, n)) {
            return findGPowRPlus1(n);
        }
        else return gPowRPlus1;
    }

    private static boolean isGPowRPlus1Okay(int gPowRPlus1, int n) {
        if (gPowRPlus1 % n == 0) {
            return false;
        }
        else return true;
    }

    private static int findCoprime(int n) {
        int g = 0;
        boolean foundG = false;

        while (!foundG) {
            g = randomEvenNumber(n);

            if (USED_INTEGERS.contains(g)) {
                foundG = false;
            }
            else {
                foundG = true;
                USED_INTEGERS.add(g);
            }
        }
        return g;
    }
    private static int randomEvenNumber(int n) {
        int r = 1;
        while (r % 2 == 1 || r >= n) {
            r = (int)(Math.random() * n) + 1;
        }

        return r;
    }

    private static int findR(int n, int g) {
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

    private static int getFirstNum(int gPowRPlus1, int n) {
        boolean foundR = false;

        int top = Math.max(gPowRPlus1, n);
        int bottom = getSmaller(gPowRPlus1, n);
        int remainder = top % bottom;

        if (remainder == 0) {
            return bottom;
        }

        while (!foundR) {
            int tempRem = remainder;
            if (bottom % remainder != 0) {
                remainder = bottom % remainder;
                bottom = tempRem;
            }
            else foundR = true;
        }

        return remainder;
    }
    private static int getSmaller(int a, int b) {
        if (a != b) {
            int multiplied = a * b;
            return multiplied / Math.max(a, b);
        }
        else return a;
    }

    
    public static void main(String[] args) {
        System.out.println(factorProductOfPrimes(77));
//         int n = 77;
//         int g = findCoprime(n);
//         System.out.println(g);

//         int r = findR(n, g);

//         while(r == -1) {
//             System.out.println("Retrying with a new g...");
//             g = findCoprime(n); // Restart with a new random g
//             r = findR(n, g);
//         }
        
//         boolean  foundR = !(r % 2 == 1);

//         while (!foundR) {
//             g = findCoprime(n);
//             System.out.println(g);
//             r = findR(n, g);
// System.out.println(r);
//             foundR = !(r % 2 == 1);
//             System.out.println();
//         }


//         int gPowRPlus1 = (int) (Math.pow(g, r / 2)) + 1;
//         System.out.println(gPowRPlus1);

//         int firstNum = getFirstNum(gPowRPlus1, n);
//         System.out.println(firstNum);
//         int secondNum = n / firstNum;
//         System.out.println(secondNum);
    }
}