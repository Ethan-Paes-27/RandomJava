package FactorProductOf2Primes;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.text.DecimalFormat;

public class ShorAlgorithm {
    private BigInteger n;
    private int a;
    private BigInteger firstNum;
    private BigInteger secondNum;
    private int r;

    public String factorProductOfPrimes(BigInteger n) {
        long startTime = System.nanoTime();

        System.out.println("Calculating...");
        this.n = n;

        // if it is divisible by 2, return 2 and n / 2
        if (n.mod(BigInteger.TWO) == (BigInteger.ZERO)) {
            firstNum = BigInteger.TWO;
            secondNum = n.divide(BigInteger.TWO);
            
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            double durationInSeconds = duration / 1000000000.0;

            return formatter(durationInSeconds);
        }

        pickA();

        // if (n.gcd(BigInteger.valueOf(a)) != BigInteger.ONE) {
        //     firstNum = n.gcd(BigInteger.valueOf(a));
        //     secondNum = n.divide(firstNum);

        //     long endTime = System.nanoTime();
        //     long duration = endTime - startTime;
        //     double durationInSeconds = duration / 1000000000.0;

        //     return formatter(durationInSeconds);
        // }
        
        findR();

        getR();

        findNums();

        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        double durationInSeconds = duration / 1000000000.0;

        return formatter(durationInSeconds);
    }

    private String formatter(double durationInSeconds) {
        DecimalFormat df = new DecimalFormat("0.000000");
        return "The factors of " + n + " are " + firstNum.min(secondNum) + " and " + firstNum.max(secondNum) + ", which took " + df.format(durationInSeconds) + " seconds to calculate.";
    }

    private void pickA() {
        if (n.intValue() < 100) {
            a = (int)(Math.random() * n.intValue()) + 1;
        }
        else a = (int)(Math.random() * 100) + 1;
    }

    private void findR() {
        if (getX() == BigInteger.ONE) {
            if (r % 2 == 1) {
                pickA();
                r = 1;
                findR();
            }
            else return;
        }

        r++;
        findR();
    }

    private boolean validateR() {
        BigInteger x = getX();

        return x != BigInteger.ONE && x != n.subtract(BigInteger.ONE);
    }

    private BigInteger getX() {
        return BigInteger.valueOf((long)Math.pow(a, r)).mod(n);
    }

    private void getR() {
        while (!validateR()) {
            pickA();
            findR();
        }
    }

    private void findNums() {
        BigInteger x = getX();
        firstNum = n.gcd(x.subtract(BigInteger.ONE));
        secondNum = n.gcd(x.add(BigInteger.ONE));

        if (firstNum == BigInteger.ONE || firstNum == n || secondNum == BigInteger.ONE || secondNum == n) {
            getR();
            findNums();
        }
    }

    public static void main(String[] args) {
        ShorAlgorithm s = new ShorAlgorithm();

        System.out.println(s.factorProductOfPrimes(BigInteger.valueOf(77)));
    }
}
