package FactorProductOf2Primes;

import java.text.DecimalFormat;

public class PollardRhoAlgorithm {

    private long c;
    private long n;
    private long x;
    private long y;


    public String factor(long n) {

        long startTime = System.nanoTime();

        System.out.println("Calculating...");
        this.n = n;
        c = 1;
        x = 2;
        y = 3;

        long d = findD();
        
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        double durationInSeconds = duration / 1000000000.0;

        DecimalFormat df = new DecimalFormat("0.000000"); //divide by a billion to convert to seconds

        return "The factors of " + n + " are " + Math.min(d, n / d) + " and " + Math.max(d, n / d) + ", which took " + df.format(durationInSeconds) + " seconds to calculate.";
    }

    private long function (long z) {
        return z * z + c;
    }

    private long findD() {
        long d = gcd(Math.abs(x - y), n);

        while (d == 1) {
            x = function(x) % n;
            y = function(function(y)) % n;

            d = gcd(Math.abs(x - y), n);

            if (d == n) {
                incrementC();
            }
        }

        return d;
    }

    private long gcd(long a, long b) {
        while (b != 0) {
        long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private void incrementC() {
        c++;
    }

    public static void main(String[] args) {
        PollardRhoAlgorithm p = new PollardRhoAlgorithm();
        long n = 213438286801L;
        System.out.println(p.factor(n));
    }
}
