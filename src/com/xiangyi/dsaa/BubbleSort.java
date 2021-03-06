package com.xiangyi.dsaa;

/**
 * 比较与交换均为O(N^2)
 * @author zengchao
 * @date 2019-03-12
 */
public class BubbleSort implements Sort{
    public static void main(String[] args) {
        Sort sort=new BubbleSort();
        int[] array=sort.genArray(1000);
        sort.print(array);
        sort.sort(array);
        sort.print(array);
        sort.validate(array);
    }

    @Override
    public void sort(int[] array) {
        for(int i=0;i<array.length-2;i++){
            for(int j=0;j<array.length-i;j++){
                if(array[j]> array[j+1]){
                    int t=array[j];
                    array[j]=array[j+1];
                    array[j+1]=t;
                }
            }
        }
    }
}
