package FactorProductOf2Primes;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.DecimalFormat;

public class PollardRhoAlgorithm {

    private BigInteger c;
    private BigInteger n;
    private BigInteger x;
    private BigInteger y;

    public String factor(BigInteger n) {

        long startTime = System.nanoTime();

        System.out.println("Calculating...");
        this.n = n;
        c = BigInteger.ONE;
        x = BigInteger.TWO;
        y = BigInteger.valueOf(3);

        BigInteger d = findD();

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        double durationInSeconds = duration / 1000000000.0;

        DecimalFormat df = new DecimalFormat("0.000000"); // divide by a billion to convert to seconds

        return "The factors of " + n + " are " + d.min(n.divide(d)) + " and " + d.max(n.divide(d)) + ", which took "
                + df.format(durationInSeconds) + " seconds to calculate.";
    }

    private BigInteger function(BigInteger z) {
        return z.multiply(z).add(c);
    }

    private BigInteger findD() {

        BigInteger d = gcd((x.subtract(y).abs()), n);

        while (d.equals(BigInteger.ONE)) {
            x = function(x).mod(n);
            y = function(function(y)).mod(n);

            d = gcd((x.subtract(y).abs()), n);

            if (d == n) {
                incrementC();
            }
        }

        return d;
    }

    private BigInteger gcd(BigInteger a, BigInteger b) {
        while (!b.equals(BigInteger.ZERO)) {
            BigInteger temp = b;
            b = a.mod(b);
            a = temp;
        }
        return a;
    }

    private void incrementC() {
        c.add(BigInteger.ONE);
    }

    public static void main(String[] args) {
        PollardRhoAlgorithm p = new PollardRhoAlgorithm();
        BigInteger n = productOfTwoBigPrimes();
        System.out.println(n);
        System.out.println(p.factor(n));
    }

    public static BigInteger productOfTwoBigPrimes() {
        // Upper bound is sqrt(10 ^ 27)
        BigInteger upperLimit = new BigInteger("1000000000000000000000000000");
        upperLimit.sqrt();
        int maxBitLength = 45;
        int minBitLength = 30;

        int bitLength = (int)(Math.random() * (maxBitLength - minBitLength)) + minBitLength;

        // SecureRandom for random prime generation
        SecureRandom random = new SecureRandom();

        // Initialize primes and their product
        BigInteger prime1, prime2, product;

        // Keep trying until the product is less than upperLimit

        // Generate two random prime numbers
        prime1 = BigInteger.probablePrime(bitLength, random); // 80 bits of precision
        prime2 = BigInteger.probablePrime(bitLength, random);

        while (prime1.compareTo(upperLimit) == 1) {
            prime1 = BigInteger.probablePrime(bitLength, random);
        }
        while (prime2.compareTo(upperLimit) == 1) {
            prime1 = BigInteger.probablePrime(bitLength, random);
        }

        // Calculate the product of the two primes
        product = prime1.multiply(prime2);

        return product;
    }
}
