package com.socialnetwork.identity.util;

public class RedisKeyManager {
    private static final String DISCOUNT = "discount";

    public static String getDiscountKey(String discountId) {
        return DISCOUNT + ":" + discountId;
    }
}
