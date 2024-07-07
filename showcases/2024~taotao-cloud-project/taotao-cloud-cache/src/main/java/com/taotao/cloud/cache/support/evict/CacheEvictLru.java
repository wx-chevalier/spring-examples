package com.taotao.cloud.cache.support.evict;

import com.taotao.cloud.cache.api.ICache;
import com.taotao.cloud.cache.api.ICacheEntry;
import com.taotao.cloud.cache.api.ICacheEvictContext;
import com.taotao.cloud.cache.model.CacheEntry;

import java.util.LinkedList;
import java.util.List;

/**
 * 丢弃策略-LRU 最近最少使用
 * @author shuigedeng
 * @since 2024.06
 */
public class CacheEvictLru<K,V> extends AbstractCacheEvict<K,V> {

    private static final Logger LOG = LoggerFactory.getLogger(CacheEvictLru.class);

    /**
     * list 信息
     * @since 2024.06
     */
    private final List<K> list = new LinkedList<>();

    @Override
    protected ICacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context) {
        ICacheEntry<K, V> result = null;
        final ICache<K,V> cache = context.cache();
        // 超过限制，移除队尾的元素
        if(cache.size() >= context.size()) {
            K evictKey = list.get(list.size()-1);
            V evictValue = cache.remove(evictKey);
            result = new CacheEntry<>(evictKey, evictValue);
        }

        return result;
    }


    /**
     * 放入元素
     * （1）删除已经存在的
     * （2）新元素放到元素头部
     *
     * @param key 元素
     * @since 2024.06
     */
    @Override
    public void updateKey(final K key) {
        this.list.remove(key);
        this.list.add(0, key);
    }

    /**
     * 移除元素
     * @param key 元素
     * @since 2024.06
     */
    @Override
    public void removeKey(final K key) {
        this.list.remove(key);
    }

}
