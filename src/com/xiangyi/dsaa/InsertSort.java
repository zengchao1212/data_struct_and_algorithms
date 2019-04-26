package com.xiangyi.dsaa;

/**
 * 比较为O(N^2)，交换为O(N)，在高度有序下，每一轮只需一次比较(若果为有序数组，则总共只需N次比较，复杂度为O(N))，而选择排序仍需N次比较，
 * 故一般比选择排序更高效
 * @author zengchao
 * @date 2019-03-12
 */
public class InsertSort implements Sort{
    public static void main(String[] args) {
        Sort sort=new InsertSort();
        int[] array=sort.genArray(1000);
        sort.print(array);
        sort.sort(array);
        sort.print(array);
        sort.validate(array);
    }

    @Override
    public void sort(int[] array) {
        int mark=-1;
        for(int i=1;i<array.length;i++){
            if(array[i-1]>array[i]){
                mark=i;
                break;
            }
        }
        for(;mark<array.length;mark++){
            int t=array[mark];
            int j;
            for(j=mark;j>0;j--){
                if(array[j-1]>t){
                    array[j]=array[j-1];
                }else{
                    break;
                }
            }
            array[j]=t;
        }
    }
}
