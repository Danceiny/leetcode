package cc.cannot.algorithms.longest_substring_without_repeating_characters;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Danceiny@GitHub.com on 2017/7/17.
 * ==> https://leetcode.com/problems/longest-substring-without-repeating-characters/
 * ############     Problem Description       #########
 * Given a string, find the length of the longest substring without repeating characters.
 * <p>
 * Examples:
 * <p>
 * Given "abcabcbb", the answer is "abc", which the length is 3.
 * <p>
 * Given "bbbbb", the answer is "b", with the length of 1.
 * <p>
 * Given "pwwkew", the answer is "wke", with the length of 3. Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
 */
public class longest_substring_without_repeating_characters {
    /* Approach #1 Brute Force [Time Limit Exceeded] */
    public static boolean allUnique(String substring, int start, int end) {
        Set<Character> set = new HashSet<>();
        for (int i = start; i < end; i++) {
            Character ch = substring.charAt(i);
            if (set.contains(ch)) {
                return false;
            }
            set.add(ch);
        }
        return true;
    }

    public static int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= n; j++) {
                if (allUnique(s, i, j)) {
                    ans = Math.max(ans, j - i);
                }
            }
        }
        return ans;

    }

    /*  Approach #2 Sliding Window [Accepted]   */
    public static int lengthOfLongestSubstring_slidingwindow(String s) {
        int n = s.length();
        Set<Character> set = new HashSet<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            //try to extend the range [i,j]
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                ans = Math.max(ans, j - 1);
            } else {
                set.remove(s.charAt(i++));
            }
        }
        return ans;
    }

    // The reason is that if s[j]s[j] have a duplicate in the range [i, j) with index j'  , we don't need to increase ii little by little. We can skip all the elements in the range [i, j'] and let i to be j' + 1 directly.
    public static int lengthOfLongestSubstring_slidingwindow_optimized(String s) {
        int n = s.length();
        int ans = 0;
        Map<Character, Integer> map = new HashMap<>();   //current index of character
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            ans = Math.max(ans, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return ans;
    }

    // 考虑到字符char的编码，在假定使用英文字母或者ASCII或者扩展的 ASCII的情况下，分别可用int[26],int[128],int[256]来替换上一解法的hashmap数据结构。
    public static int lengthOfLongestSubstring_encode(String s) {
        int n = s.length();
        int ans = 0;
        int[] index = new int[128];
        for (int j = 0, i = 0; j < n; j++) {
            char c = s.charAt(j);
            //这里不用像上面一样判断containsKey是因为这里的key是int，而且被初始化为0
            i = Math.max(index[c], i);
            ans = Math.max(ans, j - i + 1);
            index[c] = j + 1;
        }
        return ans;
    }

    public static void main(String[] args) {

    }
}
