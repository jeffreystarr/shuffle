package com.ztoztechnologies.shuffle;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigInteger;

/**
 * Tests for {@link com.ztoztechnologies.shuffle.Shuffle}
 */
@RunWith(JUnit4.class)
public class ShuffleTest {

    @Test
    public void outOfBounds() {
        try {
            Shuffle.shuffle(0, BigInteger.ZERO);
            fail("n > 0 (tried 0)");
        } catch(IllegalArgumentException e) { /* pass */ }

        try {
            Shuffle.shuffle(-1, BigInteger.ZERO);
            fail("n > 0 (tried -1)");
        } catch(IllegalArgumentException e) { /* pass */ }

        try {
            Shuffle.shuffle(1, new BigInteger("-1"));
            fail("index >= 0 (tried -1)");
        } catch(IllegalArgumentException e) { /* pass */ }

        try {
            Shuffle.shuffle(3, new BigInteger("6"));
            fail("index <= n!-1 (tried n!)" );
        } catch(IllegalArgumentException e) { /* pass */ }

        try {
            Shuffle.shuffle(3, new BigInteger("7"));
            fail("index <= n!-1 (tried n!)" );
        } catch(IllegalArgumentException e) { /* pass */ }
    }

    @Test
    public void shuffleSingle() {
        assertArrayEquals(new byte[] { 0 }, Shuffle.shuffle(1, new BigInteger("0")));
        assertArrayEquals(new Character[] { 'q' }, Shuffle.shuffle(new Character[] { 'q' }, new BigInteger("0")));
    }

    /**
     * Verify correctness of a shuffle of 3 items
     * @see {http://en.wikipedia.org/wiki/Factoradic#Permutations}
     *
     * Decimal  | Factoradic    | Permutation
     * 0        | 000           | 0 1 2 (identity)
     * 1        | 010           | 0 2 1
     * 2        | 100           | 1 0 2
     * 3        | 110           | 1 2 0
     * 4        | 200           | 2 0 1
     * 5        | 210           | 2 1 0
     *
     */
    @Test
    public void shuffle3() {
        final int n = 3;

        assertArrayEquals(new byte[] { 0, 1, 2 }, Shuffle.shuffle(n, new BigInteger("0")));
        assertArrayEquals(new byte[] { 0, 2, 1 }, Shuffle.shuffle(n, new BigInteger("1")));
        assertArrayEquals(new byte[] { 1, 0, 2 }, Shuffle.shuffle(n, new BigInteger("2")));
        assertArrayEquals(new byte[] { 1, 2, 0 }, Shuffle.shuffle(n, new BigInteger("3")));
        assertArrayEquals(new byte[] { 2, 0, 1 }, Shuffle.shuffle(n, new BigInteger("4")));
        assertArrayEquals(new byte[] { 2, 1, 0 }, Shuffle.shuffle(n, new BigInteger("5")));
    }

    @Test
    public void shuffle3Objects() {
        String[] cards = new String[] {"4H", "8C", "QD"};

        assertArrayEquals(new String[] {"4H", "8C", "QD"}, Shuffle.shuffle(cards, new BigInteger("0")));
        assertArrayEquals(new String[] {"4H", "QD", "8C"}, Shuffle.shuffle(cards, new BigInteger("1")));
        assertArrayEquals(new String[] {"8C", "4H", "QD"}, Shuffle.shuffle(cards, new BigInteger("2")));
        assertArrayEquals(new String[] {"8C", "QD", "4H"}, Shuffle.shuffle(cards, new BigInteger("3")));
        assertArrayEquals(new String[] {"QD", "4H", "8C"}, Shuffle.shuffle(cards, new BigInteger("4")));
        assertArrayEquals(new String[] {"QD", "8C", "4H"}, Shuffle.shuffle(cards, new BigInteger("5")));
    }
}
