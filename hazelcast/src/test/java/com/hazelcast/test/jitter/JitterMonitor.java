package com.hazelcast.test.jitter;


import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;

public class JitterMonitor {
    private static AtomicBoolean started = new AtomicBoolean();

    private static JitterThread jitterThread;
    private static JitterRecorder jitterRecorder;

    public static void ensureRunning() {
        if (started.compareAndSet(false, true)) {
            startMonitoringThread();
        }
    }

    public static Iterable<Slot> getSlotsBetween(long startTime, long stopTime) {
        if (jitterRecorder == null) {
            return Collections.EMPTY_LIST;
        }
        return jitterRecorder.getSlotsBetween(startTime, stopTime);
    }

    private static void startMonitoringThread() {
        jitterRecorder = new JitterRecorder();
        jitterThread = new JitterThread(jitterRecorder);
        jitterThread.setDaemon(true);
        jitterThread.start();
    }
}
