package com.xiangyi.dsaa;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zengchao
 * @date 2019-04-02
 */
public class Permutation {
    private static class Node{
        private String value;
        private boolean visited;
        private List<Node> links;

        public Node(String value){
            this.value=value;
            this.links=new ArrayList<>();
        }

        public void addLink(Node link){
            links.add(link);
        }
    }

    private static List<Node> init(String s){
        List<Node> all=new ArrayList<>(s.length());
        for(int i=0;i<s.length();i++){
            all.add(new Node(String.valueOf(s.charAt(i))));
        }
        for(Node node:all){
            all.stream().filter(item->!item.equals(node)).forEach(item->{
                node.addLink(item);
            });
        }
        return all;
    }
    private static void deepFirst(Node node,LinkedList<Node> linkedList,int size,LinkedList<String> container){
        linkedList.add(node);
        node.visited=true;
        for(Node link:node.links){
            if(!link.visited){
                deepFirst(link,linkedList,size,container);
            }
        }
        StringBuilder builder=new StringBuilder();
        linkedList.forEach(i->builder.append(i.value));
        if(linkedList.size()==size){
            container.add(builder.toString());
        }
        linkedList.removeLast().visited=false;

    }
    public static String[] gen(String s,int size){
        if(size>s.length()){
            size=s.length();
        }
        List<Node> all=init(s);
        LinkedList<String> container=new LinkedList<>();
        for(Node node:all){
            LinkedList<Node> linkedList=new LinkedList<>();
            all.forEach(ni-> ni.links.forEach(li->li.visited=false));
            deepFirst(node,linkedList,size,container);
        }
        return container.toArray(new String[0]);
    }

    public static void main(String[] args) {

        String[] pp=gen("ABCDE",4);
        for(int i=1;i<=pp.length;i++){
            System.out.println(String.format("%03d.%s",i,pp[i-1]));
        }

    }
}
