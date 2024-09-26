package com.socialnetwork.identity.util;

public class RedisKeyManager {
    private static final String DISCOUNT = "DISCOUNT:%s";

    public static String getDiscountKey(String discountId) {
        return String.format(DISCOUNT, discountId);
    }
}
