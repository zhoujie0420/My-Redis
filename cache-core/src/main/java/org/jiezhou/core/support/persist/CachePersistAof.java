package org.jiezhou.core.support.persist;

import cn.hutool.core.io.FileUtil;
import cn.hutool.log.Log;
import com.github.houbb.heaven.util.lang.StringUtil;
import org.jiezhou.api.ICache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CachePersistAof <K,V> extends CachePersistAdaptor<K,V> {
    private static final Log log = Log.get();

    /**
     * 缓存列表
     */
    private final List<String> bufferList = new ArrayList<>();

    /**
     * 数据持久化路径
     */
    private final String dbPath;

    public CachePersistAof(String dbPath) {
        this.dbPath = dbPath;
    }
    /**
     * 持久化
     * key长度 key+value
     */
    @Override
    public void persist(ICache<K,V> cache) {
        log.info("持久化数据到文件:{}",dbPath);
        // 1.创建文件
        if(!FileUtil.exist(dbPath)){
            FileUtil.touch(dbPath);
        }
        //2.写入文件
        FileUtil.appendUtf8Lines(bufferList,dbPath);

        //3.清空缓存
        bufferList.clear();
        log.info("持久化数据到文件完成");
    }


    @Override
    public long delay() {
        return 1;
    }

    @Override
    public long period() {
        return 1;
    }

    @Override
    public TimeUnit timeUnit() {
        return TimeUnit.SECONDS;
    }

    /**
     * 添加文件内容到 buffer 列表中
     * @param json json 信息
     */
    public void append(final String json) {
        if(StringUtil.isNotEmpty(json)) {
            bufferList.add(json);
        }
    }
}
