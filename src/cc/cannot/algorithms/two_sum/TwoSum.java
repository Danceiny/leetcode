package cc.cannot.algorithms.two_sum;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Danceiny on 2017/6/25.
 * ==> https://leetcode.com/problems/two-sum
 * Given nums = [2, 7, 11, 15], target = 9,
 * <p>
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
public class TwoSum {
    public static int[] sol_v1(int[] nums, int target) {
        if (nums == null) {
            return null;
        }
        int l = nums.length;
        if (l < 2) {
            return null;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < l; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < l; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                int index = map.get(complement);
                if (index != i) {
                    return new int[]{index, i};
                }
            }
        }
        return null;
    }

    public static int[] sol_v2(int[] nums, int target) {
        if (nums == null) {
            return null;
        }
        int l = nums.length;
        if (l < 2) {
            return null;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < l; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                int index = map.get(complement);
                if (index != i) {
                    return new int[]{index, i};
                }
            }
            map.put(nums[i], i);
        }
        return null;
    }

    public static void main(String[] args) {
        int[][] allnums = new int[][]{{4, 5, 6, 1}, {1, 1, 28, 8, 2, 123}};
        int[] targets = new int[]{10, 2};
        for (int[] nums : allnums) {
            System.out.println(String.format("nums: %s", StringUtils.join(nums, ',')));
            for (int target : targets) {
                int[] result = sol_v1(nums, target);
                if (result == null) {
                    System.out.println(String.format("target: %d not work", target));
                } else {
                    System.out.println(String.format("target: %d = nums[%d]+nums[%d]", target, result[0], result[1]));
                }
            }
            System.out.println('\n');
        }

    }
}


//python O(n)
//补数思想,python字典的灵活性(如同Map<Integer,Integer>)
//class Solution(object):
//    def twoSum(self, nums, target):
//        if len(nums) <= 1:
//            return False
//            buff_dict = {}
//        for i in range(len(nums)):
//            if nums[i] in buff_dict:
//                return [buff_dict[nums[i]], i]
//            else:
//                buff_dict[target - nums[i]] = i