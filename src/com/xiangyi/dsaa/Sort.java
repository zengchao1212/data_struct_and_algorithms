package com.xiangyi.dsaa;

import java.util.Random;

/**
 * @author zengchao
 * @date 2019-03-12
 */
public interface Sort {
    default int[] genArray(int length){
        int[] array=new int[length];
        Random r=new Random();
        for(int i=0;i<length;i++){
            array[i]=r.nextInt(length*10);
        }
        return array;
    }
    default void print(int[] array){
        for (int i : array) {
            System.out.print(i + "\t");
        }
        System.out.println();
    }
    default void validate(int[] array){
        boolean flag=true;
        for(int i=1;i<array.length;i++){
            if(array[i-1]>array[i]){
                flag=false;
            }
        }
        if(flag){
            System.out.println("排序成功");
        }else {
            System.err.println("排序失败");
        }
    }
    void sort(int[] array);
}
