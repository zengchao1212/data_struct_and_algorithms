package com.xiangyi.dsaa;

/**
 * 比较与交换均为O(N*logN)
 * @author zengchao
 * @date 2019-03-12
 */
public class RecursionMergeSort implements Sort{
    public static void main(String[] args) {
        Sort sort=new RecursionMergeSort();
        int[] array=sort.genArray(16);
        sort.print(array);
        sort.sort(array);
        sort.print(array);
        sort.validate(array);
    }

    @Override
    public void sort(int[] array) {
        int[] na=new int[array.length];
        rsort(na,array,0,array.length-1);
    }
    private void rsort(int[] na,int[] a,int s,int e){
        if(s==e){
            return;
        }
        int m=(s+e)/2;
        rsort(na,a,s,m);
        rsort(na,a,m+1,e);
        merge(na,a,s,m+1,e);
    }
    private void merge(int[] na,int[] a,int l,int m,int h){
        int i=0,ls=l,le=m-1,hs=m,he=h;
        while (ls<=le && hs<=he){
            if(a[ls]<a[hs]){
                na[i++]=a[ls++];
            }else{
                na[i++]=a[hs++];
            }
        }
        while (ls<=le){
            na[i++]=a[ls++];
        }
        while (hs<=he){
            na[i++]=a[hs++];
        }
        for(int j=0;j<h-l+1;j++){
            a[l+j]=na[j];
        }
    }
}
