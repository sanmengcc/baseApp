package com.base.util;

import java.util.Random;

/**
 * 随机工具类
 */
public class RandomUtils {

    private static final int DEFAULT_LENGTH = 6;

    /**
     * 随机数生成
     * @param length
     * @return
     */
    public static String generate(int length) {
        length = length <= 0 ? 6 : length;
        String no = "";
        int[] defaultNums = new int[10];

        for(int i = 0; i < defaultNums.length; defaultNums[i] = i++) {
        }

        Random random = new Random();
        int[] nums = new int[length];
        int canBeUsed = 10;

        int i;
        for(i = 0; i < nums.length; ++i) {
            int index = random.nextInt(canBeUsed);
            nums[i] = defaultNums[index];
            swap(index, canBeUsed - 1, defaultNums);
            --canBeUsed;
        }

        if (nums.length > 0) {
            for(i = 0; i < nums.length; ++i) {
                no = no + nums[i];
            }
        }

        return no;
    }

    private static void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
