package com.socialnetwork.profile;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class GainMaxValue {
    @Test
    void testCase1() {
        List<Integer> security_val = Arrays.asList(3, 5, -2, -4, 9, 16);
        int k = 2;

        var result = gainMaxValue(security_val, k);
        log.info("Testcase 1: {}", result);
    }

    private int gainMaxValue(List<Integer> security_val, int k) {
        int n = security_val.size();
        int maxSecurity = Integer.MIN_VALUE;

        for (int i = 0; i < n; i++) {
            var currentMaxSecurity = 0;
            for (int j = i; j < n; j += k) {
                currentMaxSecurity += security_val.get(j);
            }

            maxSecurity = Math.max(maxSecurity, currentMaxSecurity);
        }

        return maxSecurity;
    }
}
