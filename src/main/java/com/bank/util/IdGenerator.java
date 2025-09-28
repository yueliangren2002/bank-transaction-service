package com.bank.util;

import java.util.concurrent.atomic.LongAdder;

public class IdGenerator {
    private static LongAdder counter = new LongAdder();
    private static final int MAX_COUNTTER = 50000;

    /**
     * 单机版生成唯一ID的方法
     *
     * @return 唯一ID
     */
    public static long generateUniqueId() {
        // 获取当前时间戳
        long timestamp = System.currentTimeMillis();
        // 获取一个递增的计数器
        counter.increment();
        long uniqueId = counter.longValue() % MAX_COUNTTER;
        // 组合时间戳和递增计数器生成唯一ID
        return timestamp * MAX_COUNTTER + uniqueId;
    }
}
