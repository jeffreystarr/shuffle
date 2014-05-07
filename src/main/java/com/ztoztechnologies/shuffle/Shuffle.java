package com.ztoztechnologies.shuffle;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates a specific permutation (shuffle) of a set of n items.
 */
public class Shuffle {
    /**
     * Return a specific permutation of n items for a given index. The index
     * refers to the lexicographic sorting of the permutations and can range
     * from 0 (the initial or identity permutation) to n!-1.
     *
     * For a deck of 52 cards, there are 80,658,175,170,943,878,571,660,636,856,403,766,975,289,505,440,883,277,824,000,000,000,000
     * permutations or less than 2**226.
     *
     * Algorithm:
     *
     * shuffle(identity permutation, factoradic):
     *    identity permutation[factoradic.first] :: shuffle(identity - identity[factoradic.first], factoradic.rest)
     *
     * @param n Size of set (e.g. 52 for a standard deck of cards). Must be positive.
     * @param index Index of permutation to return (0 to n!-1 inclusive)
     * @return Array with n items, each value unique and between 0 and n-1 inclusive
     */
    static public byte[] shuffle(int n, BigInteger index) {
        if(n < 1) {
            throw new IllegalArgumentException("n must be positive");
        }
        if(index.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("0 <= index < n!");
        }
        BigInteger nFact = Factoradic.factorial(new BigInteger(String.valueOf(n)));
        if(index.compareTo(nFact) >= 0) {
            throw new IllegalArgumentException("0 <= index < n!");
        }

        // Will include all leading and trailing terms (including 0!) (e.g. 010 for decimal 1), initially zero
        byte[] factoradic = new byte[n];
        // Not padded with zeros
        byte[] factoradicShort = new Factoradic(index).representation();
        // Insert actual factoradic value (overwrite zeros)
        System.arraycopy(factoradicShort, 0, factoradic, factoradic.length - factoradicShort.length, factoradicShort.length);

        List<Byte> identity = new ArrayList<Byte>();
        for(byte i = 0; i < n; i++) {
            identity.add(i);
        }

        byte[] permutation = new byte[identity.size()];

        for(int i = 0; i < factoradic.length; i++) {
            permutation[i] = identity.remove(factoradic[i]);
        }

        return permutation;
    }

    /**
     * Return a new array that is the index permutation of set
     *
     * @param list array of items to permute
     * @param index specific permutation to return (0 <= index < list.length!)
     * @param <T> array type
     * @return T permutation of list
     */
    static public <T> T[] shuffle(T[] list, BigInteger index) {
        byte[] permutation = shuffle(list.length, index);
        T[] permuted = list.clone();

        for(int i = 0; i < permuted.length; i++) {
            permuted[i] = list[permutation[i]];
        }

        return permuted;
    }
}
