package org.jiezhou.core.constant.enums;

public enum CacheRemoveType {
    EXPIRE("expire","过期"),
    EVICT("evict","驱除"),

    ;

    private final  String code;
    private final String desc;


    CacheRemoveType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String code() {
        return code;
    }

    public String desc() {
        return desc;
    }

    @Override
    public String toString() {
        return "CacheRemoveType{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                "} " + super.toString();
    }

}
