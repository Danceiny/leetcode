package cc.cannot.algorithms.add_tow_numbers;

/**
 * ==> https://leetcode.com/problems/add-two-numbers/
 * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
 * <p>
 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
 * <p>
 * Example:
 * <p>
 * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
 * Output: 7 -> 0 -> 8
 * Explanation: 342 + 465 = 807.
 */
public class AddTwoNumbers {
    /**
     * Definition for singly-linked list.
     * public class ListNode {
     * int val;
     * ListNode next;
     * ListNode(int x) { val = x; }
     * }
     */
    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.val);
            ListNode listNode = this.next;
            while (listNode != null) {
                sb.append(",").append(listNode.val);
                listNode = listNode.next;
            }
            return sb.toString();
        }
    }

    /**
     * 下面的解法是顺序的，即 (2->4->3表示243，而原题是反过来的，所以还是要仔细审题啊
     */
    public static int carry_natural_order(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return 0;
        } else if (l1 == null) {
            return l2.val >= 10 ? 1 : 0;
        } else if (l2 == null) {
            return l1.val >= 10 ? 1 : 0;
        }
        int v = l1.val + l2.val + carry_natural_order(l1.next, l2.next);
        return v >= 10 ? 1 : 0;
    }

    public static ListNode sol_natural_order(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode result_ptr = result;
        while (l1 != null && l2 != null) {
            int v = l1.val + l2.val + carry_natural_order(l1.next, l2.next);

            result_ptr.next = new ListNode(v >= 10 ? v - 10 : v);
            result_ptr = result_ptr.next;
            l1 = l1.next;
            l2 = l2.next;
        }
        return result.next;
    }


    public static ListNode sol_reverse_order(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode result_ptr = result;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int v = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + carry;
            if (v >= 10) {
                carry = 1;
                v = v - 10;
            } else {
                carry = 0;
            }
            result_ptr.next = new ListNode(v);
            result_ptr = result_ptr.next;
            l1 = l1 == null ? null : l1.next;
            l2 = l2 == null ? null : l2.next;
        }
        if (carry == 1) {
            result_ptr.next = new ListNode(1);
        }
        return result.next;
    }

    public static void main(String[] args) {
        int[] l1Vals = new int[]{2, 4, 3};
        int[] l2Vals = new int[]{5, 6, 4};
        ListNode l1 = new ListNode(l1Vals[0]);
        ListNode l1_ptr = l1;
        for (int i = 1, l = l1Vals.length; i < l; i++) {
            l1_ptr.next = new ListNode(l1Vals[i]);
            l1_ptr = l1_ptr.next;
        }
        ListNode l2 = new ListNode(l2Vals[0]);
        ListNode l2_ptr = l2;
        for (int i = 1, l = l2Vals.length; i < l; i++) {
            l2_ptr.next = new ListNode(l2Vals[i]);
            l2_ptr = l2_ptr.next;
        }
        System.out.println(String.format("l1: %s, l2: %s, result: %s", l1, l2, sol_reverse_order(l1, l2).toString()));
    }
}
