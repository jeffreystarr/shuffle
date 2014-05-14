package com.ztoztechnologies.shuffle;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.GenerateMicroBenchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Benchmark tests for comparing Shuffle and Collections.shuffle
 *
 * Comparing the performance of Collections.shuffle and Shuffle.shuffle is not straight-forward
 * because the two algorithms
 */
@State(Scope.Thread)
public class ShufflePerformanceTest {

    /*
     * The actual contents of n24 .. listN96 are irrelevant, but they should be non-null
     */
    private static Byte[] n24 = new Byte[24];
    private static Byte[] n48 = new Byte[48];
    private static Byte[] n72 = new Byte[72];
    private static Byte[] n96 = new Byte[96];
    private static Byte[] n240 = new Byte[240];

    private static List<Byte> listN24 = new ArrayList<Byte>();
    private static List<Byte> listN48 = new ArrayList<Byte>();
    private static List<Byte> listN72 = new ArrayList<Byte>();
    private static List<Byte> listN96 = new ArrayList<Byte>();
    private static List<Byte> listN240 = new ArrayList<Byte>();

    private static BigInteger half24factorial = new BigInteger("310224200866619719680000");

    public ShufflePerformanceTest() {
        for(int i = 0; i < 24; i++) { n24[i] = new Byte((byte) 24); }
        for(int i = 0; i < 24; i++) { listN24.add((byte) 24); }
        for(int i = 0; i < 48; i++) { n48[i] = new Byte((byte) 48); }
        for(int i = 0; i < 48; i++) { listN48.add((byte) 48); }
        for(int i = 0; i < 72; i++) { n72[i] = new Byte((byte) 72); }
        for(int i = 0; i < 72; i++) { listN72.add((byte) 72); }
        for(int i = 0; i < 96; i++) { n96[i] = new Byte((byte) 96); }
        for(int i = 0; i < 96; i++) { listN96.add((byte) 96); }
        for(int i = 0; i < 240; i++) { n240[i] = new Byte((byte) 240); }
        for(int i = 0; i < 240; i++) { listN240.add((byte) 240); }
    }

    @GenerateMicroBenchmark
    public void benchCollectionsShuffle24() {
        Collections.shuffle(listN24);
    }

    @GenerateMicroBenchmark
    public void benchCollectionsShuffle48() {
        Collections.shuffle(listN48);
    }

    @GenerateMicroBenchmark
    public void benchCollectionsShuffle72() {
        Collections.shuffle(listN72);
    }

    @GenerateMicroBenchmark
    public void benchCollectionsShuffle96() {
        Collections.shuffle(listN96);
    }

    @GenerateMicroBenchmark
    public void benchCollectionsShuffle240() {
        Collections.shuffle(listN240);
    }

    @GenerateMicroBenchmark
    public Byte[] benchShuffle24() {
        return Shuffle.shuffle(n24, half24factorial);
    }

    @GenerateMicroBenchmark
    public Byte[] benchShuffle48() {
        return Shuffle.shuffle(n48, half24factorial);
    }

    @GenerateMicroBenchmark
    public Byte[] benchShuffle72() {
        return Shuffle.shuffle(n72, half24factorial);
    }

    @GenerateMicroBenchmark
    public Byte[] benchShuffle96() {
        return Shuffle.shuffle(n96, half24factorial);
    }

    @GenerateMicroBenchmark
    public Byte[] benchShuffle240() {
        return Shuffle.shuffle(n240, half24factorial);
    }

    public static void main(String[] args) throws IOException, RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + ShufflePerformanceTest.class.getSimpleName() + ".*")
                .warmupIterations(5)
                .measurementIterations(10)
                .forks(1)
                .build();

        new Runner(opt).run();
    }
}
