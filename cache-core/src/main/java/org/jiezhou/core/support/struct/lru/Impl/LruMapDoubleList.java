package org.jiezhou.core.support.struct.lru.Impl;

import cn.hutool.log.Log;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import lombok.val;
import org.jiezhou.api.ICacheEntry;
import org.jiezhou.core.exception.CacheRuntimeException;
import org.jiezhou.core.model.CacheEntry;
import org.jiezhou.core.model.DoubleListNode;
import org.jiezhou.core.support.struct.lru.ILruMap;

import javax.print.attribute.standard.PrinterURI;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

public class LruMapDoubleList <K,V> implements ILruMap<K,V> {

    private static final Log LOG = Log.get();

    /**
     * 头节点
     */
    private DoubleListNode<K,V> head;

    /**
     * 尾节点
     */
    private DoubleListNode<K,V> tail;

    /**
     * 映射 map
     * @return
     */
    private Map<K,DoubleListNode<K,V>> indexMap;

    public LruMapDoubleList(){
        this.indexMap = new HashMap<>();
        this.head = new DoubleListNode<>();
        this.tail = new DoubleListNode<>();

        this.head.next(tail);
        this.tail.pre(head);
    }
    @Override
    public ICacheEntry<K, V> removeEldest() {
        // 获取尾巴节点的前一个元素
        DoubleListNode<K,V> tailPre = this.tail.pre();
        if(tailPre == this.head){
            LOG.error("LruMapDoubleList is empty");
            throw new CacheRuntimeException("不可以删除头节点");
        }
        K evictKey = tailPre.key();
        V evictValue = tailPre.value();

        this.removeKey(evictKey);

        return CacheEntry.of(evictKey,evictValue);
    }

    @Override
    public void updateKey(final K key) {
        // 执行删除
        this.removeKey(key);

        //新元素添加到头部
        DoubleListNode<K, V> newNode = new DoubleListNode<>();
        newNode.key(key);

        DoubleListNode<K, V> next = this.head.next();
        this.head.next(newNode);
        newNode.pre(head);
        next.pre(newNode);
        newNode.next(next);

        // 添加到映射
        indexMap.put(key,newNode);

    }

    /**
     * 移除元素
     * @param key
     */
    @Override
    public void removeKey(final K key) {
        DoubleListNode<K, V> node = indexMap.get(key);

        if(ObjectUtil.isNull(node)){
            return;
        }

        DoubleListNode<K, V> pre = node.pre();
        DoubleListNode<K, V> next = node.next();

        pre.next(next);
        next.pre(pre);

        // 移除映射
        this.indexMap.remove(key);
        LOG.debug("移除元素,key : {}",key);
    }

    @Override
    public boolean isEmpty() {
        return indexMap.isEmpty();
    }

    @Override
    public boolean contains(K key) {
        return indexMap.containsKey(key);
    }
}
