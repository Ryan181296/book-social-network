package com.socialnetwork.profile;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class CountPair {
    @Test
    void testCase1() {
        List<Integer> taskCosts = Arrays.asList(1, 2, 3, 4, 6, 3, 5);
        int target = 2;

        var result = countPair(taskCosts, target);
        log.info("Testcase 1: {}", result);
    }

    private int countPair(List<Integer> taskCosts, int target) {
        Set<Integer> seenCosts = new HashSet<>();
        int count = 0;

        for (var cost : taskCosts) {
            if (seenCosts.contains(cost - target) || seenCosts.contains(cost + target)) {
                count++;
            }
            seenCosts.add(cost);
        }
        return count;
    }
}
