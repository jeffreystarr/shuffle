package com.ztoztechnologies.shuffle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigInteger;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Tests for {@link com.ztoztechnologies.shuffle.Factoradic}
 */
@RunWith(JUnit4.class)
public class FactoradicTest {
    @Test
    public void factorial() {
        // 0! == 1
        assertTrue(Factoradic.factorial(BigInteger.ZERO).compareTo(BigInteger.ONE) == 0);

        // 1! == 1
        assertTrue(Factoradic.factorial(BigInteger.ONE).compareTo(BigInteger.ONE) == 0);

        // 2! == 2
        assertTrue(Factoradic.factorial(new BigInteger("2")).compareTo(new BigInteger("2")) == 0);

        // 5! == 120
        assertTrue(Factoradic.factorial(new BigInteger("5")).compareTo(new BigInteger("120")) == 0);

        try {
            Factoradic.factorial(new BigInteger("-1"));
            fail("negative n is not legal");
        } catch (IllegalArgumentException e) { /* pass */ }
    }

    @Test
    public void conversion() {
        assertArrayEquals(new byte[] { 0 }, new Factoradic(BigInteger.ZERO).representation());
        assertArrayEquals(new byte[] { 1, 0 }, new Factoradic(BigInteger.ONE).representation());
        assertArrayEquals(new byte[] { 1, 0, 0 }, new Factoradic(new BigInteger("2")).representation());
        // 2 * 3! + 2 * 2! + 0 * 1! = 2 * 6 + 2 * 2 + 0 * 1 = 16
        assertArrayEquals(new byte[] { 2, 2, 0, 0 }, new Factoradic(new BigInteger("16")).representation());
        // 3 * 5! + 4 * 4! + 1 * 3! + 0 * 2! + 1 * 1! = 463
        assertArrayEquals(new byte[] { 3, 4, 1, 0, 1, 0 }, new Factoradic(new BigInteger("463")).representation());
    }
}
