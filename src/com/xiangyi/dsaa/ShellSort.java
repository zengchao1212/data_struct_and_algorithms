package com.xiangyi.dsaa;

/**
 * O(N*(logN)^2)
 * @author zengchao
 * @date 2019-03-12
 */
public class ShellSort implements Sort{
    public static void main(String[] args) {
        Sort sort=new ShellSort();
        int[] array=sort.genArray(16);
        sort.print(array);
        sort.sort(array);
        sort.print(array);
        sort.validate(array);
    }

    @Override
    public void sort(int[] array) {
        int h=1;

        while (3*h+1<array.length){
            h=3*h+1;
        }
        while (h>0){
            for(int i=0;i<h;i++){
                for(int m=h+i;m<array.length;m+=h){
                    int t=array[m];
                    int j=m;
                    while (j>=h&&t<array[j-h]){
                        array[j]=array[j-h];
                        j-=h;
                    }
                    array[j]=t;

                }
            }
            h=(h-1)/3;
        }
    }
}
