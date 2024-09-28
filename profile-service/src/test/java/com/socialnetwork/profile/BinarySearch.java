package com.socialnetwork.profile;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class BinarySearch {
    @Test
    void testCase1() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        var value = 5;
        var foundValue = binarySearch(numbers, value);
    }

    private Integer binarySearch(List<Integer> numbers, Integer value) {
        int left = 0;
        int right = numbers.size() - 1;
        int mid;

        while (left <= right) {
            mid = (left + right) / 2;
            var midValue = numbers.get(mid);

            if (value > midValue)
                left = mid + 1;
            else if (value < midValue)
                right = mid - 1;
            else
                return midValue;
        }

        return null;
    }
}
