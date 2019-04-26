package com.xiangyi.dsaa;

/**
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
