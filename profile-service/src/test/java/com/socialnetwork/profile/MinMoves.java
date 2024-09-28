package com.socialnetwork.profile;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class MinMoves {
    @Test
    void testCase1() {
        List<Integer> arr = Arrays.asList(1, 0, 1, 1, 0, 0, 0);
        var result = minMoves(arr);
    }

    private int minMoves(List<Integer> arr) {
        int zeroCount = 0, oneCount = 0;
        int zerosBeforeOnes = 0, onesBeforeZeros = 0;

        for (var value : arr) {
            if (value == 1) {
                oneCount++;
                onesBeforeZeros += zeroCount;
            } else {
                zeroCount++;
                zerosBeforeOnes += oneCount;
            }
        }

        return Math.min(zerosBeforeOnes, onesBeforeZeros);
    }
}
