package com.xiangyi.dsaa;

/**
 * 比较为O(N^2)，交换为O(N)
 * @author zengchao
 * @date 2019-03-12
 */
public class SelectSort implements Sort{
    public static void main(String[] args) {
        Sort sort=new SelectSort();
        int[] array=sort.genArray(1000);
        sort.print(array);
        sort.sort(array);
        sort.print(array);
        sort.validate(array);
    }

    @Override
    public void sort(int[] array) {
        for(int i=0;i<array.length-1;i++){
            int min=Integer.MAX_VALUE;
            int index=-1;
            for(int j=i+1;j<array.length;j++){
                if(array[j]<min){
                    min=array[j];
                    index=j;
                }
            }
            if(array[index]<array[i]){
                int t=array[i];
                array[i]=min;
                array[index]=t;
            }
        }
    }
}
