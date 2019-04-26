package com.xiangyi.dsaa;

/**
 * @author zengchao
 * @date 2019-03-12
 */
public class OddEvenSingleThreadSort implements Sort{
    public static void main(String[] args) {
        Sort sort=new OddEvenSingleThreadSort();
        int[] array=sort.genArray(1000);
        sort.print(array);
        sort.sort(array);
        sort.print(array);
        sort.validate(array);
    }

    @Override
    public void sort(int[] array) {
        while (true){
            for(int i=0;i<array.length-1;i+=2){
                if(array[i]>array[i+1]){
                    int t=array[i];
                    array[i]=array[i+1];
                    array[i+1]=t;
                }
            }
            boolean sorted=true;
            for(int i=1;i<array.length-1;i+=2){
                if(array[i]>array[i+1]){
                    int t=array[i];
                    array[i]=array[i+1];
                    array[i+1]=t;
                    sorted=false;
                }
            }
            if(sorted){
                break;
            }
        }
    }
}
