package cc.cannot;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Danceiny on 2017/6/25.
 * Given nums = [2, 7, 11, 15], target = 9,

 Because nums[0] + nums[1] = 2 + 7 = 9,
 return [0, 1].

 */
public class TwoSum {
    public static int[] twoSum(int[] numbers, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < numbers.length; i++) {
            if (map.containsKey(target - numbers[i])) {
                result[1] = i;
                result[0] = map.get(target - numbers[i]);
                return result;
            }
            map.put(numbers[i], i);
        }
        return result;

    }
    public static void sol(){
        int[] nums = {4,5,6,1};
        int target = 10;
        int target2 = 2;
        int [] result = twoSum(nums,target);
        System.out.println(String.format("%d,%d",result[0],result[1]));

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