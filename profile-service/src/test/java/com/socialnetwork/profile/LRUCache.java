package com.socialnetwork.profile;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.*;

@Slf4j
public class LRUCache {
    @Test
    void testCase1() {
        List<String> requests = Arrays.asList("item3", "item2", "item1", "item2", "item3");

        int k = 1;
        var result = getMinimumSize(requests, k);
        log.info("Testcase 1: {}", result);
    }

    @Test
    void testCase2() {
        List<String> requests = Arrays.asList("item1");

        int k = 1;
        var result = getMinimumSize(requests, k);
        log.info("Testcase 2: {}", result);
    }

    private int getMinimumSize(List<String> requests, int k) {
        int left = 1;
        int mid;
        int right = requests.size();
        int result = -1;
        while (left <= right) {
            mid = (left + right) / 2;

            var hits = countCacheHits(requests, mid);
            if (hits >= k) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }

    private int countCacheHits(List<String> requests, int cacheSize) {
        Set<String> cache = new LinkedHashSet<>();
        int hits = 0;
        for (var request : requests) {
            if (cache.contains(request)) {
                hits++;
                cache.remove(request);
                cache.add(request);
            } else {
                if (cacheSize == cache.size()) {
                    var it = cache.iterator();
                    it.next();
                    it.remove();
                }
                cache.add(request);
            }
        }
        return hits;
    }
}
