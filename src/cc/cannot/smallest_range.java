package cc.cannot;

import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created by Danceiny@GitHub.com on 2017/7/17.
 *
 * Description:
 * You have k lists of sorted integers in ascending order. Find the smallest range that includes at least one number from each of the k lists.

 We define the range [a,b] is smaller than range [c,d] if b-a < d-c or a < c if b-a == d-c.

 Example 1:
 Input:[[4,10,15,24,26], [0,9,12,20], [5,18,22,30]]
 Output: [20,24]
 Explanation:
 List 1: [4, 10, 15, 24,26], 24 is in range [20,24].
 List 2: [0, 9, 12, 20], 20 is in range [20,24].
 List 3: [5, 18, 22, 30], 22 is in range [20,24].

 Note:
 The given list may contain duplicates, so ascending order means >= here.
 1. 1 <= k <= 3500
 2. -105 <= value of elements <= 105.
 3. For Java users, please note that the input type has been changed to List<List<Integer>>. And after you reset the code template, you'll see this point.

 */
public class smallest_range {
    public int[] smallest_range_bruteforce(int[][] nums){
        int minx = 0, miny = Integer.MAX_VALUE;
        for(int i=0; i<nums.length; i++){
            for(int j=0; j<nums[i].length; j++){
                for(int k=i; k<nums.length; k++){
                    for(int l=(k==i?j:0); l<nums[k].length; l++){
                        int min = Math.min(nums[i][j],nums[k][l]);
                        int max = Math.max(nums[i][j],nums[k][l]);
                        int n,m;
                        for(m=0; m<nums.length; m++){
                            //for循环搜索，也可以用:
                            // n = Arrays.binarySearch(nums[m],min);
                            // if(n<0)n=-1-n;
                            // if(n==nums[m].length||nums[m][n]<min||nums[m][n]>max)break;
                            for(n=0; n<nums[m].length; n++){
                                if(nums[m][n] >= min && nums[m][n] <= max){
                                    break;
                                }
                            }
                            if(n == nums[m].length){
                                break;
                            }//
                        }
                        if(m == nums.length){
                            if(miny - minx > max - min || (miny - minx == max - min && minx > min)){
                                miny = max;
                                minx = min;
                            }
                        }
                    }
                }
            }
        }
        return new int[] {minx,miny};
    }

    /** Alforithm Using pointers
     * 1. init pointers array next[], next[i] refers to the element which needs to be considered next in the (i-1)th list.（考虑最小值所在的那一列）
     * 2. consider the first(minimum) element among all the lists.
     * @param nums
     * @return
     */
    public int[] smallest_range_pointers(int[][] nums){
        int minx = 0, miny = Integer.MAX_VALUE;
        int[] next = new int[nums.length];
        boolean flag = true;
        for(int i=0; i<nums.length && flag; i++){
            for(int j=0; j<nums[i].length && flag; j++){
                int min_i = 0, max_i = 0;
                for(int k=0; k<nums.length; k++){
                    if (nums[min_i][next[min_i]] > nums[k][next[k]]){
                        min_i = k;
                    }
                    if(nums[max_i][next[max_i]] < nums[k][next[k]]){
                        max_i = k;
                    }
                }
                if(miny - minx > nums[max_i][next[max_i]] - nums[min_i][next[min_i]]){
                    miny = nums[max_i][next[max_i]];
                    minx = nums[min_i][next[min_i]];
                }
                next[min_i]++;
                if(next[min_i] == nums[min_i].length){
                    flag = false;
                }
            }
        }
        return new int[] {minx,miny};
    }

    public int[] smallest_range_priority_queue(int[][] nums){
        int minx = 0, miny = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int[] next = new int[nums.length];
        boolean flag = true;
        PriorityQueue<Integer> min_queue = new PriorityQueue<>((i,j) -> nums[i][next[i]] - nums[j][next[j]]);//最小堆
        for(int i=0; i<nums.length; i++){
            for(int j=0; j<nums[i].length && flag; j++){
                int min_i = min_queue.poll();   //poll和remove都是获取并删除队首元素
                if(miny - minx > max - nums[min_i][next[min_i]]){
                    minx = nums[min_i][next[min_i]];
                    miny = max;
                }
                next[min_i]++;
                if (next[min_i] == nums[min_i].length){
                    flag = false;
                    break;
                }
                min_queue.offer(min_i); //add 和offer语义相同，只是插入失败时的处理不同
                max = Math.max(max,nums[min_i][next[min_i]]);
            }
        }
        return new int[]{minx,miny};
    }
}
