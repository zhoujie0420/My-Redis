package org.jiezhou.core.support.load;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.sun.xml.internal.ws.util.StringUtils;
import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheLoad;
import org.jiezhou.core.model.PersistRdbEntry;
import sun.reflect.misc.FieldUtil;

import java.util.List;

/**
 * 加载策略 -- RDB
 */
public class CacheLoadRdb<K,V> implements ICacheLoad<K,V> {
    private final static Log log = Log.get();

    /**
     * 文件路径
     */
    private final String dbPath;

    public CacheLoadRdb(String dbPath) {
        this.dbPath = dbPath;
    }


    @Override
    public void load(ICache<K, V> cache) {
        List<String> lines = FileUtil.readLines(dbPath, "utf-8");
        log.info("加载文件:{}", dbPath);
        if (CollectionUtil.isEmpty(lines)) {
            log.info("文件内容为空");
            return;
        }

        for (String line : lines) {
            if(StringUtil.isEmpty(line)){
                continue;
            }

            PersistRdbEntry<K,V> bean = JSONUtil.toBean(line, PersistRdbEntry.class);

            K key = bean.getKey();
            V value = bean.getValue();
            Long expire = bean.getExpire();

            cache.put(key,value);

            if(ObjectUtil.isNotNull(expire)){
                cache.expire().expire(key,expire);
            }
        }
    }
}
