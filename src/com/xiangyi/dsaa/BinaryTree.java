package com.xiangyi.dsaa;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zengchao
 * @date 2019-03-12
 */
public class BinaryTree{

    private Node root;

    private static class Node{
        private Node l;
        private Node r;
        private int v;

        public Node(int v){
            this.v=v;
        }
    }

    public void insert(int v){
        if(root==null){
            root=new Node(v);
            return;
        }
        Node c=root;
        while (true){
            Node p=c;
            c=v<p.v?p.l:p.r;
            if(c==null){
                if(v<p.v){
                    p.l=new Node(v);
                }else {
                    p.r=new Node(v);
                }
                break;
            }
        }
    }

    public List<Node> find(int v){
        if(root==null){
            return null;
        }
        List<Node> list=new ArrayList<>();
        Node c=root;
        while (true){
            if(c.v==v){
                list.add(c);
            }
            c=v<c.v?c.l:c.r;
            if(c==null){
                break;
            }
        }
        return list;
    }
    public List<Node> delete(int v){
        List<Node> list=new ArrayList<>();
        Node n;
        while ((n=deleteSingle(v))!=null){
            list.add(n);
        }
        return list;
    }
    private Node deleteSingle(int v){
        if(root==null){
            return null;
        }
        Node c=root,p=null;
        while (true){
            if(c.v==v){
                if(c.l==null || c.r==null){
                    if(p==null){
                        root=c.l==null?c.r:c.l;
                    }else {
                        if(c.v<p.v){
                            p.l=c.l==null?c.r:c.l;
                        }else{
                            p.r=c.l==null?c.r:c.l;
                        }
                    }
                }else{
                    Node cc=c.r,cp=null;
                    while (true){
                        if(cp.l==null){
                            break;
                        }
                        cp=cc;
                        cc=cc.l;
                    }
                    if(p!=null){
                        if(c.v<p.v){
                            p.l=cc;
                        }else{
                            p.r=cc;
                        }
                    }
                    if(cp!=null){
                        cp.l=null;
                    }
                    cc.l=c.l;
                    cc.r=c.r;
                }
                break;
            }
            p=c;
            c=v<c.v?c.l:c.r;
            if(c==null){
                break;
            }
        }
        return c;
    }

    public List<Node> getAll(){
        List<Node> list=new ArrayList<>();
        if(root!=null){
            loop(root,list);
        }
        return list;
    }

    private void loop(Node n,List list){
        list.add(n);
        if(n.l!=null){
            loop(n.l,list);
        }

        if(n.r!=null){
            loop(n.r,list);
        }
    }

    public void deep(){
        Stack<Node> stack=new Stack<>();
        stack.push(root);
        while (!stack.empty()){
            Node c=stack.pop();
            System.out.println(c.v);
            if(c.r!=null){
                stack.push(c.r);
            }
            if(c.l!=null){
                stack.push(c.l);
            }
        }
    }

    public void broad(){
        Queue<Node> queue=new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node c=queue.poll();
            System.out.print(c.v+",");
            if(c.l!=null){
                queue.offer(c.l);
            }
            if(c.r!=null){
                queue.offer(c.r);
            }
        }
    }

    public static void main(String[] args) {
        BinaryTree tree=new BinaryTree();
        Random r=new Random();
        for(int i=0;i<31;i++){
            tree.insert(r.nextInt(100*10));
        }
//        tree.insert(50);
//        tree.insert(40);
//        tree.insert(60);
//        tree.insert(35);
//        tree.insert(45);
//        tree.insert(55);
//        tree.insert(65);
//        tree.insert(70);
//        tree.getAll().stream().collect(Collectors.toList());
//        Integer[] array=tree.getAll().stream().map(i->i.v).collect(Collectors.toList()).toArray(new Integer[0]);
//        print(array);
//        validate(array);
//        int v=r.nextInt(1000*10);
//        while (tree.find(v).size()==0){
//            v=r.nextInt(1000*10);
//        }
//        System.out.println("find "+v);
//        System.out.println(tree.find(v).size()==tree.delete(v).size());
        Integer[] array=tree.getAll().stream().map(i->i.v).collect(Collectors.toList()).toArray(new Integer[0]);
        print(array);
//        validate(array);
//        System.out.println(notExist(array,v));
//        tree.deep();
        tree.broad();
//        int[] x=array;
    }

    private static void print(Integer[] array){
        for (int i : array) {
            System.out.print(i + "\t");
        }
        System.out.println();
    }
    private static void validate(Integer[] array){
        boolean flag=true;
        for(int i=1;i<array.length;i++){
            if(array[i-1]>array[i]){
                flag=false;
            }
        }
        if(flag){
            System.out.println("排序成功");
        }else {
            System.err.println("排序失败");
        }
    }

    private static boolean notExist(Integer[] array,int v){
        for(int i=0;i<array.length;i++){
            if(array[i]==v){
                return false;
            }
        }
        return true;
    }

}
