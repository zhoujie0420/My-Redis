package org.jiezhou.core.support.persist;


import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import org.jiezhou.api.ICache;
import org.jiezhou.core.model.PersistRdbEntry;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 缓存持久化-RDB
 * @param <k>
 * @param <V>
 */
public class CachePersistRDB <K,V> extends CachePersistAdaptor<K,V>{
    /**
     * 数据库路径
     */
    private final String dbPath;

    public CachePersistRDB(String dbPath) {
        this.dbPath = dbPath;
    }

    /**
     * 持久化
     */
    @Override
    public void persist(ICache<K, V> cache) {

        Set<Map.Entry<K, V>> entries = cache.entrySet();

        // 1.创建文件
        FileUtil.touch(dbPath);
        //清空文件
        FileUtil.writeUtf8String("",dbPath);

        entries.forEach(entry->{
            K key = entry.getKey();
            Long expireTime = cache.expire().expireTime(key);
            PersistRdbEntry<K, V> persistRdbEntry = new PersistRdbEntry<K, V>();
            persistRdbEntry.setKey(key);
            persistRdbEntry.setValue(entry.getValue());
            persistRdbEntry.setExpire(expireTime);


            String jsonStr = JSONUtil.toJsonStr(persistRdbEntry);
            FileUtil.appendUtf8String(jsonStr+"\n",dbPath);

        });
    }

    @Override
    public long delay() {
        return 5;
    }

    @Override
    public long period() {
        return 5;
    }

    @Override
    public TimeUnit timeUnit() {
        return TimeUnit.MINUTES;
    }
}
