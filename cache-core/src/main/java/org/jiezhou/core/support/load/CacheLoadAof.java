package org.jiezhou.core.support.load;


import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import com.github.houbb.heaven.util.lang.StringUtil;
import com.github.houbb.heaven.util.lang.reflect.ReflectMethodUtil;
import com.github.houbb.heaven.util.util.CollectionUtil;
import org.jiezhou.annotation.CacheInterceptor;
import org.jiezhou.api.ICache;
import org.jiezhou.api.ICacheLoad;
import org.jiezhou.core.core.Cache;
import org.jiezhou.core.model.PersistAofEntry;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 加载策略 -- AOF
 */
public class CacheLoadAof<K, V> implements ICacheLoad<K, V> {

    private final static Log log = Log.get();

    /**
     * 方法缓存
     *
     * @param cache
     */
    private static final Map<String, Method> METHOD_MAP = new HashMap<>();


    static {
        Method[] methods = Cache.class.getMethods();
        for (Method method : methods) {
            CacheInterceptor annotation = method.getAnnotation(CacheInterceptor.class);

            if (annotation != null) {
                // 暂时
                if (annotation.aof()) {
                    String name = method.getName();
                    METHOD_MAP.put(name, method);
                }
            }
        }

    }

    /**
     * 文件路径
     *
     * @param cache
     */
    private final String dbPath;

    public CacheLoadAof(String dbPath) {
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

        //执行
        for (String line : lines) {
            if (StringUtil.isEmpty(line)) {
                continue;
            }

            PersistAofEntry bean = JSONUtil.toBean(line, PersistAofEntry.class);
            final String methodName = bean.getMethodName();
            final Object[] objects = bean.getParams();

            final Method method = METHOD_MAP.get(methodName);

            ReflectMethodUtil.invoke(cache, method, objects);

        }
    }
}
