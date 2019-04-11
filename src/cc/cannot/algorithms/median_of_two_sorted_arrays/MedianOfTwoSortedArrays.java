package cc.cannot.algorithms.median_of_two_sorted_arrays;


public class MedianOfTwoSortedArrays {
    public static double sol_v1(int[] nums1, int[] nums2) {
        int num1Len = (nums1 == null ? 0 : nums1.length);
        int num2Len = (nums2 == null ? 0 : nums2.length);
        if (num1Len == 0 || (num2Len > 0 && nums1[0] > nums2[0])) {
            int[] t = nums1;
            nums1 = nums2;
            nums2 = t;
            num1Len = (nums1 == null ? 0 : nums1.length);
            num2Len = (nums2 == null ? 0 : nums2.length);
        }
        int n1cur = 0;
        int n2cur = 0;
        int cnt = 0;
        int totalLength = num1Len + num2Len;
        int pre = Integer.MIN_VALUE;
        int cur = Integer.MIN_VALUE;
        int next = Integer.MAX_VALUE;
        for (; n1cur < num1Len; n1cur++) {
            cur = nums1[n1cur];
            cnt++;
            if (n1cur < num1Len - 1) {
                next = nums1[n1cur + 1];
            } else if (n1cur == num1Len - 1) {
                next = Integer.MAX_VALUE;
            }
            if (cnt - 1 >= totalLength / 2) {
                if (totalLength % 2 == 0) {
                    return (cur + pre) / 2.0;
                } else {
                    return cur;
                }
            }
            for (; n2cur < num2Len; n2cur++) {
                int num2 = nums2[n2cur];
                if (num2 >= cur && num2 <= next) {
                    cnt++;
                    pre = cur;
                    cur = num2;
                    if (cnt - 1 >= totalLength / 2) {
                        if (totalLength % 2 == 0) {
                            return (cur + pre) / 2.0;
                        } else {
                            return cur;
                        }
                    }
                } else {
                    break;
                }
            }

            pre = cur;
        }
        return 0.0;
    }

    public static void main(String[] args) {
//        int[] nums1 = new int[]{1, 3};
//        int[] nums2 = new int[]{2};
//        double test1 = sol_v1(nums1, nums2);
//        System.out.println(test1);
//
//        double test2 = sol_v1(new int[]{1, 2}, new int[]{3, 4});
//        System.out.println(test2);
//        System.out.println(sol_v1(new int[]{}, new int[]{3, 4}));
//        System.out.println(sol_v1(new int[]{2}, new int[]{}));
        System.out.println(sol_v1(new int[]{3}, new int[]{-2, -1}));
    }
}
