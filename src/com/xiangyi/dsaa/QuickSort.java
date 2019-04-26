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
        int lp=s-1,rp=e;
        while(true){
            while (a[++lp]<x){}
            while (rp>0 && a[--rp]>x){}
            if(lp>=rp){
                break;
            }
            x(a,lp,rp);
        }
        x(a,lp,e);
        return lp;
    }
    private void x(int[] a ,int i,int j){
        int t=a[i];
        a[i]=a[j];
        a[j]=t;
    }
}
