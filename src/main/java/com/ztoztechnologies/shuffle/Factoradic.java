package com.ztoztechnologies.shuffle;

import java.math.BigInteger;
import java.util.*;

/**
 * Factoradic is a number expressed in a factorial number system.
 *
 * The least significant digit is in base 0!, the second in 1!, the third in 2!,
 * and so on. For example, decimal 16 in Factoradic is 2200:
 *
 * 16 = 2 * 3! + 2 * 2! + 0 * 1! + 0 * 0!
 *
 *
 * Factoradics can store large numbers very efficiently. Internally, this implementation
 * stores the multiplying terms as bytes, limiting the number of terms to 127 or a maximal
 * value of 128! - 1. However, this value is greater than 2**716 or 10**215 and is vastly
 * greater than the number of atoms in the universe, so it should not be a meaningful
 * limitation.
 */
public class Factoradic {

    private byte[] repr; /* factors multiplied by radix factorials in BigEndian format to arrive at value */

    /**
     * Construct a Factoradic from a BigInteger
     *
     * @param n BigInteger >= 0
     */
    public Factoradic(BigInteger n) {
        n = Objects.requireNonNull(n);
        if(n.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }
        if(n.compareTo(BigInteger.ZERO) == 0) {
            repr = new byte[] { 0 };
        } else {
            int radix = nLessThan(n); // radix of 1, 2, 3, 4, ...
            BigInteger radixFactorial = factorial(new BigInteger(Integer.toString(radix)));
            repr = new byte[radix + 1];

            for(int i = radix; i > 0; i--) {
                BigInteger[] divAndRem = n.divideAndRemainder(radixFactorial);
                repr[radix - i] = divAndRem[0].byteValue();
                n = divAndRem[1];
                radixFactorial = radixFactorial.divide(new BigInteger(Integer.toString(i)));
            }
        }
    }

    /**
     * Return n! (e.g. n * n-1 * n-2 * .. * 2 * 1)
     *
     * 0! == 1
     *
     * @param n non-negative value
     * @return n!
     */
    public static BigInteger factorial(BigInteger n) {
        n = Objects.requireNonNull(n);
        if(n.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("n must be non-negative");
        }
        if(n.compareTo(BigInteger.ZERO) == 0) {
            return BigInteger.ONE;
        }

        BigInteger index = BigInteger.ONE;
        BigInteger fact = BigInteger.ONE;

        while(index.compareTo(n) <= 0) {
            fact = fact.multiply(index);
            index = index.add(BigInteger.ONE);
        }

        return fact;
    }

    /**
     * Return the factors that represent this value.
     *
     * The byte array is a copy of the internal representation; changes to the
     * returned array will not affect the object instance.
     *
     * @return Factoradic representation of value as a Big-endian set of bytes
     */
    public byte[] representation() {
        return Arrays.copyOf(this.repr, this.repr.length);
    }

    /**
     * Find the largest factorial that is less than or equal to parameter r
     *
     * @param r value
     * @return n such that n! <= r
     */
    private int nLessThan(BigInteger r) {
        if(r.compareTo(BigInteger.ZERO) <= 0) {
            throw new IllegalArgumentException("r >= 1");
        }

        BigInteger n = BigInteger.ONE;
        BigInteger nFact = BigInteger.ONE;

        // while (n+1)! <= r
        while(nFact.multiply(n.add(BigInteger.ONE)).compareTo(r) <= 0) {
            n = n.add(BigInteger.ONE);
            nFact = nFact.multiply(n);
        }

        return n.intValue();
    }
}
