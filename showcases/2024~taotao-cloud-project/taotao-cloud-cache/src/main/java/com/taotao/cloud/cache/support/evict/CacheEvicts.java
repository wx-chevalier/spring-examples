package com.taotao.cloud.cache.support.evict;

import com.taotao.cloud.cache.api.ICacheEvict;

/**
 * 丢弃策略
 *
 * @author shuigedeng
 * @since 2024.06
 */
public final class CacheEvicts {

    private CacheEvicts(){}

    /**
     * 无策略
     *
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 2024.06
     */
    public static <K, V> ICacheEvict<K, V> none() {
        return new CacheEvictNone<>();
    }

    /**
     * 先进先出
     *
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 2024.06
     */
    public static <K, V> ICacheEvict<K, V> fifo() {
        return new CacheEvictFifo<>();
    }

    /**
     * LRU 驱除策略
     *
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 2024.06
     */
    public static <K, V> ICacheEvict<K, V> lru() {
        return new CacheEvictLru<>();
    }

    /**
     * LRU 驱除策略
     *
     * 基于双向链表 + map 实现
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 2024.06
     */
    public static <K, V> ICacheEvict<K, V> lruDoubleListMap() {
        return new CacheEvictLruDoubleListMap<>();
    }


    /**
     * LRU 驱除策略
     *
     * 基于LinkedHashMap
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 2024.06
     */
    public static <K, V> ICacheEvict<K, V> lruLinkedHashMap() {
        return new CacheEvictLruLinkedHashMap<>();
    }

    /**
     * LRU 驱除策略
     *
     * 基于 2Q 实现
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 2024.06
     */
    public static <K, V> ICacheEvict<K, V> lru2Q() {
        return new CacheEvictLru2Q<>();
    }

    /**
     * LRU 驱除策略
     *
     * 基于 LRU-2 实现
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 2024.06
     */
    public static <K, V> ICacheEvict<K, V> lru2() {
        return new CacheEvictLru2<>();
    }

    /**
     * LFU 驱除策略
     *
     * 基于 LFU 实现
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 2024.06
     */
    public static <K, V> ICacheEvict<K, V> lfu() {
        return new CacheEvictLfu<>();
    }

    /**
     * 时钟算法
     * @param <K> key
     * @param <V> value
     * @return 结果
     * @since 2024.06
     */
    public static <K, V> ICacheEvict<K, V> clock() {
        return new CacheEvictClock<>();
    }

}
