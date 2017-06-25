package cc.cannot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Danceiny on 2017/6/25.
 */
public class Substring_with_Concatenation_of_All_Words {
    // travel all the words combinations to maintain a window
    // there are wl(word len) times travel
    // each time, n/wl words, mostly 2 times travel for each word
    // one left side of the window, the other right side of the window
    // so, time complexity O(wl * 2 * N/wl) = O(2N)
    public static List<Integer> findSubstring(String S, List<String> L) {
        List<Integer> ans = new ArrayList<>();
        int n = S.length(), cnt = L.size();
        if (n <= 0 || cnt <= 0) return ans;

        // init word occurence
        Map<String, Integer> dict = new HashMap<>();
        for (int i = 0; i < cnt; ++i) dict.put(L.get(i),i);

        // travel all sub string combinations
        int wl = L.get(0).length();
        for (int i = 0; i < wl; ++i) {
            int left = i, count = 0;
            Map<String, Integer> tdict = new HashMap<>();
            for (int j = i; j <= n - wl; j += wl) {
                String str = S.substring(j, j+wl);
                // a valid word, accumulate results
                if (dict.containsKey(str)) {
                    if(tdict.containsKey(str))
                        tdict.put(str,tdict.get(str)+1);
                    else
                        tdict.put(str,dict.get(str));

                    if (tdict.get(str) <= dict.get(str))
                        count++;
                    else {
                        // a more word, advance the window left side possiablly
                        while (tdict.get(str) > dict.get(str)) {
                            String str1 = S.substring(left, left+wl);
                            tdict.put(str1,tdict.get(str1)-1);

                            if (tdict.get(str1) < dict.get(str1)) count--;
                            left += wl;
                        }
                    }
                    // come to a result
                    if (count == cnt) {
//                        ans.push_back(left);
                        ans.add(left);
                        // advance one word
//                        tdict[S.substr(left, wl)]--;
                        String key = S.substring(left,left+wl);
                        tdict.put(key,tdict.get(key) - 1);
                        count--;
                        left += wl;
                    }
                }
                // not a valid word, reset all vars
                else {
                    tdict.clear();
                    count = 0;
                    left = j + wl;
                }
            }
        }

        return ans;
    }
    public static void sol(){
        String s =  "barfoothefoobarman";
        List<String> words = new ArrayList<String>(){{add("foo");add("bar");}};

//        You should return the indices: [0,9].
        List<Integer> result = (List<Integer>)findSubstring(s,words);
        System.out.println(result);
//        for(int i = 0;i < result.size(); i++){
//            System.out.println(result.get(i));
//        }
    }
}
