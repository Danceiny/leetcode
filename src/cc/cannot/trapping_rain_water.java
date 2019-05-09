package cc.cannot;

import java.util.Map;
import java.util.Stack;

/**
 * Created by Danceiny@GitHub.com on 2017/7/17.
 *
 *
 * Given n non-negative integers representing an elevation map（海拔地图） where the width of each bar is 1, compute how much water it is able to trap after raining.

 For example,
 Given [0,1,0,2,1,0,1,3,2,1,2,1], return 6.

 The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped. Thanks Marcos for contributing this image!
 */
public class trapping_rain_water {
    // Bruce Force
    // Algorithm
    /** 1. Initialize ans = 0;
     *  2. Iterate the array from left to right;
     *  3. Initialize max_left = 0 and max_right = 0
     *  4. Iterate from the current element to the beginning of array updating： max_left = max(max_left,height[j])
     *  5. Iterate from the current element to the end of array updating: max_right = max(max_right,height[j])
     *  6. Add min(max_left,max_left) - height[i] to ans.
     */
    public int trapping_rain_wather(int[] array){
        int ans = 0;
        int size = array.length;
        for(int i=0; i<size; i++){
            int max_right = 0, max_left = 0;
            for(int j=i; j<size; j++){
                max_right = Math.max(max_right,array[j]);
            }
            for(int j=i; j>0; j--){
                max_left = Math.max(max_left,array[j]);
            }
            ans += Math.min(max_left,max_right) - array[i];
        }
        return ans;
    }

    /** Algorithm
     * Find maximum height of bar from the left end upto an index i in the array left_max.
     * Find maximum height of bar from the right end upto an index i in the array right_max.
     * Iterate over the \text{height}height array and update ans:
     * Add min(max_left[i],max_right[i])−height[i] to ansans
     * @param array
     * @return
     */
    public int trapping_rain_water_store(int[] array){
        if(array == null)return 0;
        int ans = 0;
        int size = array.length;
        int[] left_max = new int[size];
        int[] right_max = new int[size];
        left_max[0] = right_max[0] = 0;
        for(int i=1; i<size; i++){
            left_max[i] = Math.max(array[i],left_max[i-1]);
        }
        right_max[size-1] = array[size-1];
        for(int i=size-2; i>=0; i--){
            right_max[i] = Math.max(array[i],right_max[i+1]);
        }
        for(int i=1; i<size-1; i++){
            ans += Math.min(left_max[i],right_max[i]) - array[i];
        }
        return ans;
    }


    /** Algorithm using stacks
     *
     * Use stack to store the indices of the bars.
     * Iterate the array:
     *  While stack is not empty and height[current] > height[st.top()]
     *      It means that the stack element can be poped. Pop the top element as top.
     *      Find the distance between the current element and the element at top of stack, which is to be filled. distance = current - st.top() - 1
     *      Find the bounded height bounded_height = min(height[current],height[st.top()]) - height[top]
     *      Add resulting trapped water to answer ans += distance * bounded_height
     *  Push current index to top of the stack
     *  Move current to the next position
     *
     * @param array
     * @return
     */
    public int trapping_rain_water_using_stack(int[] array) {
        int ans = 0;
        int size = array.length;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < size; i++) {
            while (!stack.isEmpty() && array[i] > array[stack.peek()]) {
                int top = stack.pop();
                if(stack.empty()){
                    break;
                }
                int distance = i - stack.peek() - 1;
                int bounded_height = Math.min(array[i],array[stack.peek()]) - array[top];
                ans += distance * bounded_height;
            }
            stack.push(i);
        }
        return ans;
    }



/** Algorithm Using 2 pointers
 *  1. Initialize left pointer to 0 and right pointer to size-1
 *  2. While left < right, do:
 *      If array[left] < array[right]
 *          if array[left] >= left_max, update left_max
 *          else add left_max - array[left] to ans
 *          add 1 to left
 *      Else
 *          if array[right] >= right_max, update right_max
 *          else add right_max - array[right] to ans
 *          subtract 1 from right
 *
 *
 */
    public int trapping_rain_water_using2pointers(int[] array){
        int ans = 0;
        int size = array.length;
        int left = 0, right = size - 1;
        int left_max = 0, right_max = 0;
        while(left < right){
            if(array[left] < array[right]){
                if(array[left] >= left_max){
                    left_max = array[left];
                } else{
                    ans += (left_max - array[left]);
                }
                ++left;
            }else{
                if(array[right] >= right_max){
                    right_max = array[right];
                }else{
                    ans += (right_max - array[right]);
                }
                --right;
            }
        }
        return ans;
    }
}
