package org.jiezhou.core.support.struct.lru.Impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.houbb.heaven.util.lang.ObjectUtil;
import org.jiezhou.api.ICacheEntry;
import org.jiezhou.core.exception.CacheRuntimeException;
import org.jiezhou.core.model.CacheEntry;
import org.jiezhou.core.model.CircleListNode;
import org.jiezhou.core.support.struct.lru.ILruMap;

import java.util.HashMap;
import java.util.Map;


/**
 * 基于循环列表实现
 * @param <K>
 * @param <V>
 */
public class LruMapCircleList<K,V> implements ILruMap<K,V> {

    private static final Log LOG = LogFactory.get();

    /**
     * 头节点
     */
    private CircleListNode<K,V> head;

    /**
     * 映射 map
     * @return
     */
    private Map<K,CircleListNode<K,V>> indexMap;

    public LruMapCircleList() {
        this.head = new CircleListNode<>(null);
        this.head.next(head);
        this.head.pre(head);

        indexMap = new HashMap<>();
    }

    /**
     * 删除最老元素
     * @return
     */
    @Override
    public ICacheEntry<K, V> removeEldest() {
        if(isEmpty()){
            LOG.error("LruMapCircleList is empty");
            throw new CacheRuntimeException("不可以删除头节点");
        }

        CircleListNode<K,V> node = this.head;
        while (node.next() != this.head){
            node = node.next();

            if(!node.accessFlag()){

                K key = node.key();
                this.removeKey(key);
                return CacheEntry.of(key,node.value());
            } else {
                node.accessFlag(false);
            }
        }

        //如果循环一遍都没找到，直接删除第一个元素
        CircleListNode<K, V> next = this.head.next();
        return CacheEntry.of(next.key(),next.value());

    }

    /**
     * 放入元素
     * 类似于FIFO ,直接放在队列最后
     * @param key
     */
    @Override
    public void updateKey(K key) {
        CircleListNode<K, V> node = indexMap.get(key);
        // 存在

        if(ObjectUtil.isNotNull(node)){
            node.accessFlag(true);
            LOG.debug("节点已存在，设置访问标志为true,key : {}",key);
        }else {
            node = new CircleListNode<>(key);

            CircleListNode<K, V> tail = head.pre();
            tail.next(node);
            node.pre(tail);
            node.next(head);
            head.pre(node);

            // 放入indexMap ,便于快速定位
            indexMap.put(key,node);
            LOG.debug("节点不存在，新建节点并放入循环列表,key : {}",key);
        }
    }

    /**
     * 删除元素
     * @param key
     */
    @Override
    public void removeKey(K key) {
        CircleListNode<K, V> node = indexMap.get(key);
        if(ObjectUtil.isNull(node)){
            LOG.warn("LruMapCircleList not contains key : {}",key);
            return;
        }

        CircleListNode<K, V> pre = node.pre();
        CircleListNode<K, V> next = node.next();

        pre.next(next);
        next.pre(pre);
        indexMap.remove(key);

        LOG.debug("删除节点,key : {}",key);
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
