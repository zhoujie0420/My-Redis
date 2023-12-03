package org.jiezhou.core.support.evict;


import org.jiezhou.api.ICacheEntry;
import org.jiezhou.api.ICacheEvict;
import org.jiezhou.api.ICacheEvictContext;

/**
 * * 丢弃策略-抽象实现类
 */

public abstract class AbstractCacheEvict<K,V> implements ICacheEvict<K,V> {

    @Override
    public ICacheEntry<K,V> evict(ICacheEvictContext<K, V> context) {
        //3. 返回结果
        return doEvict(context);
    }

    /**
     * 执行移除
     * @param context 上下文
     * @return 结果
     */
    protected abstract ICacheEntry<K,V> doEvict(ICacheEvictContext<K, V> context);

    @Override
    public void updateKey(K key) {

    }

    @Override
    public void removeKey(K key) {

    }
}
