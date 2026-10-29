package utils;

public final class Bench {
    private Bench() {}
    @FunctionalInterface public interface Task { void run(); }

    public static long timeNanos(int warmup, int rounds, Task t) {
        for (int i = 0; i < warmup; i++) t.run();
        long best = Long.MAX_VALUE;
        for (int i = 0; i < rounds; i++) {
            long s = System.nanoTime(); t.run(); long e = System.nanoTime();
            best = Math.min(best, e - s);
        }
        return best;
    }
}
