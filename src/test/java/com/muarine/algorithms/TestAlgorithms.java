/*
 *
 *  *
 *  *  * RT MAP, Home of Professional MAP
 *  *  * Copyright 2016 Bit Main Inc. and/or its affiliates and other contributors
 *  *  * as indicated by the @author tags. All rights reserved.
 *  *  * See the copyright.txt in the distribution for a
 *  *  * full listing of individual contributors.
 *  *
 *
 */

package com.muarine.algorithms;

import java.util.*;

/**
 * com.muarine.algorithms.TestAlgorithms
 *
 * @author Muarine<maoyun0903@163.com>
 * @date 16/8/2
 * @since 1.0
 */
public class TestAlgorithms {


    public static void main(String[] args) {

//        String url = "http://www.baidu.com?key=asdasdadsdas&method=post&operator=add";
//
//        int r1 = HashAlgorithms.additiveHash(url , 13);
//        System.out.println(r1);
//
//        int r2 = HashAlgorithms.java(url);
//        System.out.println();

        int[] numbers = new int[4];
        numbers[0] = 3;
        numbers[1] = 5;
        numbers[2] = 2;
        numbers[3] = 10;

        int target = 15;

        int[] re = twoSum(numbers, target);
        for (int i : re){
            System.out.println(i);
        }

        List<String> list = new ArrayList<>(6);
        list.add("1");
        list.add("2");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");

        List<String> combine = combine(list);

        System.out.println(combine.size());
        System.out.println("list:");
        int cols2 = 20;
        for (String s : combine){
            System.out.print(s + " ");
            if (cols2-- == 1) {
                System.out.println();
                cols2 = 20;
            }
        }
        System.out.println();
        System.out.println(set.size());
        int cols = 10;
        for (String s : set) {
            System.out.print(s + " ");
            if (cols-- == 1) {
                System.out.println();
                cols = 10;
            }
        }

    }

    public static int[] twoSum(int[] numbers, int target){

        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<>();

        for (int i=0; i<numbers.length; i++){

            if(map.containsKey(target - numbers[i])){
                result[1] = i;
                result[0] = map.get(target-numbers[i]);
                return result;
            }
            map.put(numbers[i], i);
        }
        return result;

    }

    /**
     * 输出1,2,2,3,4,5的所有排列组合,4不能在第三位,3和5不能相邻
     *
     * @param list
     * @return
     */
    public static List<String> combine(List<String> list){
        List<String> rtn = new ArrayList<>();
        String str;
        for (int i=0;i<list.size();i++){
            str = list.get(i);
            list.remove(i);
            if (list.size() == 0){
                rtn.add(str);
            } else {
                List<String> sList = combine(list);
                for (String s : sList){
                    rtn.add(str + s);
                    if (s.length() == 5){
                        addNumber(str + s);
                    }
                }
            }
            list.add(i, str);
        }
        return rtn;
    }

    public static Set<String> set = new TreeSet<>();

    public static void addNumber(String str){

        if (str.charAt(2) == '4' || str.contains("35") || str.contains("53")) {
            return;
        }
        set.add(str);
    }

}
