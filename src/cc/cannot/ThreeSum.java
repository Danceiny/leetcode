package cc.cannot;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by haochen on 2017/6/25.
 * Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

 Note: The solution set must not contain duplicate triplets.

 For example, given array S = [-1, 0, 1, 2, -1, -4],

 A solution set is:
 [
     [-1, 0, 1],
     [-1, -1, 2]
 ]
 */
public class ThreeSum {
    public static List<List<Integer>> threeSum(int[] num){
        Arrays.sort(num);
        List<List<Integer>> result = new LinkedList<>();
        for(int i=0; i<num.length-2; i++){
            if(0==i || (i>0 && num[i] != num[i-1])){
                int low = i+1, high = num.length -1, sum = 0 - num[i];
                while(low < high){
                    if(num[low] + num[high] == sum){
                        result.add(Arrays.asList(num[i],num[low],num[high]));
                        //向中间逼近,避免重复
                        while(low < high && num[low] == num[low+1]) low++;
                        while(low < high && num[high] == num[high-1]) high--;
                        //准备搜索下一组
                        low++;
                        high--;
                    }
                    else if(num[low] + num[high] < sum){
                        low++;
                    }
                    else{
                        high--;
                    }
                }
            }
        }
        return result;
    }
    public static void sol(){

    }
}
