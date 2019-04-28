package com.xiangyi.dsaa;

/**
 * O(N*logN)
 * @author zengchao
 * @date 2019-03-12
 */
public class QuickSort implements Sort{
    public static void main(String[] args) {
        Sort sort=new QuickSort();
        int[] array=sort.genArray(10);
        sort.print(array);
        sort.sort(array);
        sort.print(array);
        sort.validate(array);
    }

    @Override
    public void sort(int[] array) {
        rsort(array,0,array.length-1);
    }

    private void rsort(int[] a,int s,int e){
        if(e-s<=0){
            return;
        }
        int x=a[e];
        int lp=p(a,s,e,x);
        rsort(a,s,lp-1);
        rsort(a,lp+1,e);
    }
    private int p(int[] a,int s,int e,int x){
        //本例以最右边元素作为对比位，所以必须先从左边开始搜索
        int lp=s-1,rp=e;
        while(true){
            //从左边开始搜索小于对比位的第一个值
            while (a[++lp]<x){}
            //从右边开始搜索大于对比位的第一个值
            while (rp>0 && a[--rp]>x){}
            if(lp>=rp){
                break;
            }
            x(a,lp,rp);
        }
        //最后交换对比为与左搜索的值
        x(a,lp,e);
        //返回分割位置
        return lp;
    }
    private void x(int[] a ,int i,int j){
        int t=a[i];
        a[i]=a[j];
        a[j]=t;
    }
}
