package com.xiangyi.dsaa;

/**
 * @author zengchao
 * @date 2019-03-12
 */
public class BinaryTreeSort implements Sort{
    private int i=0;
    private static class Node{
        private Node l;
        private Node r;
        private int v;

        public Node(int v){
            this.v=v;
        }
        public Node addLeft(int v){
            Node node=new Node(v);
            this.l=node;
            return node;
        }
        public Node addRight(int v){
            Node node=new Node(v);
            this.r=node;
            return node;
        }
    }
    public static void main(String[] args) {
        Sort sort=new BinaryTreeSort();
        int[] array=sort.genArray(1000);
        sort.print(array);
        sort.sort(array);
        sort.print(array);
        sort.validate(array);
    }

    @Override
    public void sort(int[] array) {
        Node r=new Node(array[0]);
        for(int i=1;i<array.length;i++){
            Node p=r;
            while (true){
                Node n=array[i]<p.v?p.l:p.r;
                if(n!=null){
                    p=n;
                }else if(array[i]< p.v){
                    p.addLeft(array[i]);
                    break;
                }else {
                    p.addRight(array[i]);
                    break;
                }
            }
        }
        x(r,array);
    }

    private void x(Node p,int[] array ){
        if(p.l!=null){
            x(p.l,array);
        }
        System.out.print(p.v+"\t");
        array[i++]=p.v;
        if(p.r!=null){
            x(p.r,array);
        }
    }
}
