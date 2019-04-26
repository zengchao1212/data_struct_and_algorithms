package com.xiangyi.dsaa;

import java.util.concurrent.CountDownLatch;

/**
 * @author zengchao
 * @date 2019-03-12
 */
public class OddEvenMultiThreadSort implements Sort{
    private CountDownLatch latch;
    private volatile int[] array;
    public static void main(String[] args) throws InterruptedException {
        OddEvenMultiThreadSort sort=new OddEvenMultiThreadSort();
        sort.array=sort.genArray(100);
        sort.print(sort.array);
        sort.sort(sort.array);
        sort.latch.await();
        sort.print(sort.array);
        sort.validate(sort.array);
    }

    @Override
    public void sort(int[] array) {
        int num=array.length/20;
        if(array.length%100!=0){
            num+=1;
        }
        if(num>5){
            num=5;
        }
        int se;
        if(array.length%num==0){
            se=array.length/num;
        }else {
            se=array.length/(num-1);
        }
        latch=new CountDownLatch(num);
        for(int i=0;i<num;i++){
            int start=i*se;
            Thread t=new Thread(()->{
                sort(array,start,se);
            });
            t.start();
        }
    }
    private void sort(int[] array,int start,int length) {
        while (true){
            boolean sorted=true;
            for(int i=start;i<=start+length&&i<array.length-1;i+=2){
                if(array[i]>array[i+1]){
                    int t=array[i];
                    array[i]=array[i+1];
                    array[i+1]=t;
                    sorted=false;
                }
            }

            for(int i=start+1;i<=start+length&&i<array.length-1;i+=2){
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
        latch.countDown();
    }
}
