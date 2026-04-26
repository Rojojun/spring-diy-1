package com.diy.framework.enums;

public enum HttpMethod {
    GET,
    POST,
    PUT,
    DELETE
    ;

    public static HttpMethod equals(String compare) {
        return valueOf(compare);
//        return this.toString().equals(compare) ? this : null;
    }
}
