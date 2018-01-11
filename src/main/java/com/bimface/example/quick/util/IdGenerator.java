package com.bimface.example.quick.util;

import java.net.InetAddress;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class IdGenerator {
    private static final ReentrantLock lock = new ReentrantLock();
    private static final long twepoch = 1361753741828L;
    private static final long workerIdBits = 8L;
    private static final long maxWorkerId = 255L;
    private static final long sequenceBits = 5L;
    private static final long workerIdShift = 5L;
    private static final long timestampLeftShift = 13L;
    private static final long sequenceMask = 31L;
    private static long workerId;
    private static long lastTimestamp;
    private static long sequence = 0L;

    public IdGenerator() {
    }

    public static long nextId() {
        lock.lock();

        long var4;
        try {
            long timestamp = System.currentTimeMillis();
            if (lastTimestamp == timestamp) {
                sequence = sequence + 1L & 31L;
                if (sequence == 0L) {
                    timestamp = untilNextMillis(lastTimestamp);
                }
            } else {
                sequence = 0L;
            }

            if (timestamp < lastTimestamp) {
                try {
                    throw new Exception(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
                } catch (Exception var9) {
                    var9.printStackTrace();
                }
            }

            lastTimestamp = timestamp;
            long nextId = timestamp - 1361753741828L << 13 | workerId << 5 | sequence;
            var4 = nextId;
        } finally {
            lock.unlock();
        }

        return var4;
    }

    public static String getUniqueId() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replaceAll("-", "");
    }

    private static long untilNextMillis(long lastTimestamp) {
        long timestamp;
        for(timestamp = System.currentTimeMillis(); timestamp <= lastTimestamp; timestamp = System.currentTimeMillis()) {
            ;
        }

        return timestamp;
    }

    private static long getWorkerId() throws Exception {
        byte[] ip = InetAddress.getLocalHost().getAddress();
        long id = 255L & (long)ip[ip.length - 1];
        return id;
    }

    static {
        try {
            workerId = getWorkerId();
            if (workerId > 255L || workerId < 0L) {
                throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 255L));
            }
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }
}
