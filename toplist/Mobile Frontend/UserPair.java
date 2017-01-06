package com.tl.joe.toplist;

import android.graphics.Bitmap;

import com.amazonaws.services.s3.AmazonS3;



/**
 * Created by Joe on 12/7/2016.
 */
/*
import java.util.ArrayList;
import java.util.Arrays;

public class Solutions {
    public boolean sol1(String S) {
        boolean[] hasChars = new boolean[256];
        for (int i = 0; i < S.length(); i++) {
            int v = S.charAt(i);
            if (hasChars[i] == true){
                return false;
            } else { hasChars[i] = true; }

        }
        return true;
    }

    public void sol2(char[] s) {
        int start = 0;
        int end = s.length -2; //null character
        while(!(start >= end)){
            char tmp = s[start];
            s[start] = s[end];
            s[end] = tmp;
            start++;
            end--;
        }

    }
    import java.util.Arrays;

    public boolean sol4(String s1, String s2) {
        char[] cs1 = s1.toCharArray();
        char[] cs2 = s2.toCharArray();
        Arrays.sort(cs1);
        Arrays.sort(cs2);
        if (cs1 == cs2){
            return true;
        }
        else return false;

    }
}

class Solution2 {
    public int solution(int[] A) {
        if (A.length == 0){
          return -1;
        }
        else {
            int left = 0;
            int sum = 0;
            for (int i = 0; i < A.length; i++){
                sum += A[i];
            }

            for (int i = 0; i < A.length; i++){

                sum -= A[i];
                if (sum == left){
                    return i;
                }
                left += A[i];
            }
            return -1;

        }

    }
}

class Solution {
    public int solution(String S) {
        String[] files = S.split("\n");
        //ArrayList<Integer> dirs = new ArrayList<Integer>
        //int cnt = 0;
        int ftabs;
        int dtabs;
        int longest = 0;
        String path;
        for (int i = files.length; i >= 0; i--){
            path = "";
            if(files[i].contains(".gif") || files[i].contains(".png") || files[i].contains(".jpeg")){
                ftabs = getTabs(files[i]);
                for (int j = i - 1; j >=0; i++){
                    if (!files[j].contains(".")){
                        dtabs = getTabs(files[j]);
                        if (dtabs < ftabs){
                            path += "/" + files[j];
                            ftabs--; //moved up a level
                        }
                    }
                }
                if (path.length() > longest){
                    longest = path.length();
                }


            }
        }
        return longest;
    }

    public int getTabs(String s){
        int tabs = 0;
        for (char c: s.toCharArray()) {
            if (c == '\t'){
                tabs++;
            }
        }
        return tabs;
    }

*/

