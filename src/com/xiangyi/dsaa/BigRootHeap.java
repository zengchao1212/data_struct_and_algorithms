package com.xiangyi.dsaa;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author zengchao
 * @date 2019-03-28
 */
public class BigRootHeap implements Sort{
    private int[] array=null;
    private int position=0;

    public void add(int x){
        if(array==null){
            array=new int[10];
        }
        if(position>array.length-1){
            array=Arrays.copyOf(array,array.length*2);
        }
        int i=position;
        while (i>0){
            int p=(i-1)>>1;
            if(array[p]<x){
                array[i]=array[p];
                i=p;
            }else {
                break;
            }
        }
        array[i]=x;
        position++;
    }

    public int size(){
        return position;
    }

    public int get(){
        if(position==0){
            throw new RuntimeException("empty");
        }
        int root=array[0];
        int last=array[position-1];
        int i=0;
        while (i<position){
            int rp=i*2+2;
            if(rp<position){
                int next=array[rp-1]<array[rp]?rp:rp-1;
                int t=array[next];
                if(last>t){
                    break;
                }
                array[i]=t;
                i=next;
            }else if(rp-1<position){
                int next=rp-1;
                int t=array[next];
                if(last>t){
                    break;
                }
                array[i]=t;
                i=next;
            }else {
                break;
            }
        }
        array[i]=last;
        position--;
        return root;
    }

    public static void main(String[] args) {
        BigRootHeap sort=new BigRootHeap();
        int[] array=sort.genArray(1000);
//        int[] array={97	,33	,70	,};
        sort.print(array);
        for(int i:array){
            sort.add(i);
        }
        sort.sort(array);
        sort.print(array);
        sort.validate(array);

    }

    @Override
    public void sort(int[] array) {
        int i=array.length-1;
        int[] narray=new int[array.length];
        while (true){
            try {
                narray[i--]=get();
            }catch (Exception e){
                break;
            }
        }
        System.arraycopy(narray,0,array,0,array.length);
    }
}
