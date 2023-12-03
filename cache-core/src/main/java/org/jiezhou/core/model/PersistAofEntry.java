package org.jiezhou.core.model;


import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * AOF持久化明细
 */
public class PersistAofEntry {

    /**
     * 参数名称
     */
    private Object[] params;

    /**
     * 方法名称
     */
    @Getter
    private String methodName;

    /**
     * 新建对象实例
     */
    public static PersistAofEntry newInstance() {
        return new PersistAofEntry();
    }

    public Object[] getParams() {
        return params;
    }


    public void setParams(Object[] params) {
        this.params = params;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "PersistAofEntry{" +
                "params=" + Arrays.toString(params) +
                ", methodName='" + methodName + '\'' +
                '}';
    }
}
