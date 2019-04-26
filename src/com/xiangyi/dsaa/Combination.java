package com.xiangyi.dsaa;

public class Combination {
    public static void main(String[] args){
        String[][] xx={{"A1","A2","A3","A4"},{"B1","B2","B3"},{"C1","C2","C3"}};
        int total=1;
        //所有组合的计数
        for(String[] x:xx){
            total*=x.length;
        }
        String[][] xx2=new String[total][xx.length];
        /*
         A1B1C1
         A2B1C1
         A3B1C1
         A4B1C1
         A1B2C1
         A2B2C1
         A3B2C1
         A4B2C1
         A1B3C1
         A2B3C1
         A3B3C1
         A4B3C1
         A1B1C2
         A2B1C2
         A3B1C2
         A4B1C2
         A1B2C2
         A2B2C2
         A3B2C2
         A4B2C2
         A1B3C2
         A2B3C2
         A3B3C2
         A4B3C2
         A1B1C3
         A2B1C3
         A3B1C3
         A4B1C3
         A1B2C3
         A2B2C3
         A3B2C3
         A4B2C3
         A1B3C3
         A2B3C3
         A3B3C3
         A4B3C3
         */
        int m=1;
        for(int j=0;j<xx.length;j++){
            for(int i=0;i<total;i++){
                int x=(int)Math.floor(i/m)%xx[j].length;
                /*
                每个组合i的第j个元素是xx[j]数组的中的一个值，
                关键是确定是xx[j]中的第几个元素，即x,仔细分析上面的组合，可以得出如上的计算公式
                 */
                xx2[i][j]=xx[j][x];
            }
            m*=xx[j].length;
        }
        for(String[] x:xx2){
            StringBuilder sb=new StringBuilder();
            for(String s:x){
                sb.append(s);
            }
            System.out.println(sb.toString());
        }
    }
}