package com.xiangyi.dsaa;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * @author zengchao
 * @date 2019-03-20
 */
public class RBTree {
    private Node root;

    private class Node{
        private Node p;
        private Node l;
        private Node r;
        private int v;
        private boolean red=true;

        Node(Node p, int v){
            this.p=p;
            this.v=v;
        }

        private void rc(Node c,Node p,Node g){
            if(g!=null){
                if(g.l==p){
                    g.l=c;
                }else{
                    g.r=c;
                }
            }else{
                c.red=false;
                root=c;
            }
        }
        private void xz(boolean toLeft){
            if(p!=null){
                Node c=this;
                Node p=c.p;
                Node g=p.p;
                p.p=c;
                c.p=g;
                if(toLeft){
                    //向下搜索的时候可能存在此情况
                    p.r=c.l;
                    if(p.r!=null){
                        p.r.p=p;
                    }
                    c.l=p;
                }else{
                    p.l=c.r;
                    if(p.l!=null){
                        p.l.p=p;
                    }
                    c.r=p;
                }
                rc(c,p,g);
            }
        }
        /*
        右旋
        1.改变父节点颜色
        2.改变节点颜色
        3.父节点变成右子节点，节点变成父节点
         */
        void toRight(){
            xz(false);

        }
        /*
        左旋
        1.改变父节点颜色
        2.改变节点颜色
        3.父节点变成左子节点，节点变成父节点
         */
        void toLeft(){
            xz(true);
        }
    }

    private class Printer{
        /**
         * 获取树的最大可能高度
         * @return
         */
        private int getHeight(){
            int i=0;
            Node c=root;
            while (c!=null){
                i++;
                c=c.l;
            }
            return i+1;
        }

        /**
         * 获取树的宽度
         * @return
         */
        private int getWidth(){
            return 1<<getHeight();
        }

        private void writeValue(Graphics2D graphics,int x,int y,Node c){
            graphics.fillOval(x, y, 50, 50);
            graphics.setColor(Color.WHITE);
            FontMetrics metrics = graphics.getFontMetrics(graphics.getFont());
            int widthOffset = 25 - metrics.stringWidth(String.valueOf(c.v)) / 2;
            int heightOffset = 25 + metrics.getHeight() / 2;
            graphics.drawString("" + c.v, x + widthOffset, y + heightOffset);
        }
        public void toImage(String path) throws IOException {

            int width=getWidth()*50;
            int height=getHeight()*200;
            BufferedImage img=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics=img.createGraphics();

            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, img.getWidth(), img.getHeight());
            graphics.setFont(new Font(null,Font.BOLD,16));
            Queue<Node> queue=new LinkedList<>();
            Map<Node,Integer> wm=new HashMap<>();
            Map<Node,Integer> hm=new HashMap<>();
            queue.offer(root);
            wm.put(root,width/2);
            hm.put(root,100);
            for(Node c:broad()){
                if(c.red){
                    graphics.setColor(Color.RED);
                }else{
                    graphics.setColor(Color.BLACK);
                }
                if(c.p==null){
                    writeValue(graphics,wm.get(c),hm.get(c),c);
                }else {
                    int px = wm.get(c.p);
                    int py = hm.get(c.p);
                    int cx;
                    int cy = py + 200;
                    if (c.p.p == null) {
                        if (c.v < c.p.v) {
                            cx = px / 2;
                        } else {
                            cx = px + px / 2;
                        }
                    } else {
                        int ppx = wm.get(c.p.p);
                        if (c.v < c.p.v) {
                            cx = px - Math.abs((ppx - px)) / 2;
                        } else {
                            cx = px + Math.abs((ppx - px)) / 2;
                        }
                    }

                    wm.put(c, cx);
                    hm.put(c, cy);
                    writeValue(graphics,wm.get(c),hm.get(c),c);
                    graphics.setColor(Color.BLUE);
                    graphics.drawLine(px + 25, py + 50, cx + 25, cy);
                }
            }
            File file=new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            String ext=path.substring(path.lastIndexOf('.')+1);
            ImageIO.write(img,ext,file);
        }
    }

    //当前节点和父节点都是红色的情况需要旋转
    private void xz(Node c,Node p,Node g){
        //外侧子节点
        if((p.v<g.v && c.v<p.v)||(p.v>=g.v && c.v>=p.v)){
            g.red=!g.red;
            p.red=false;
            //左外侧
            if(p.v<g.v && c.v<p.v){
                p.toRight();
            }else{
                p.toLeft();
            }
        }else{
            g.red=!g.red;
            c.red=false;
            if(p.v<g.v && c.v>=p.v){
                c.toLeft();
                c.toRight();
            }else{
                c.toRight();
                c.toLeft();
            }
        }
    }
    public void insert(int v){
        if(root==null){
            root=new Node(null,v);
            root.red=false;
            return;
        }
        Node c=root;
        while (true){
            Node p=c.p,g=null;
            if(p!=null){
                g=p.p;
            }
            //黑色节点有两个红色子节点，节点变成红色(根节点除外)，子节点变成黑色
            if(c.l!=null && c.r!=null && c.l.red && c.r.red){
                if(c!=root){
                    c.red=true;
                }
                c.l.red=false;
                c.r.red=false;
                //父节点也为红色，则需要旋转
                if(p!=null&&p.red){
                    xz(c,p,g);
                }
            }
            //将当前节点设为子节点
            g=p;
            p=c;
            c=v<c.v?c.l:c.r;
            if(c==null){
                c=new Node(p,v);
                if(v<p.v){
                    p.l=c;
                }else {
                    p.r=c;
                }
                //父节点也为红色，则需要旋转
                if(p.red){
                    xz(c,p,g);
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
                //没有子节点，直接删除
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

    /**
     * 递归实现深度优先遍历
     * @return
     */
    public List<Node> getAll(){
        List<Node> list=new ArrayList<>();
        if(root!=null){
            loop(root,list);
        }
        return list;
    }

    private void loop(Node n, List list){
        if(n!=null){
            list.add(n);
            loop(n.l,list);
            loop(n.r,list);
        }
    }

    /**
     * 广度优先遍历
     * @return
     */
    public List<Node> broad(){
        Queue<Node> queue=new LinkedList<>();
        List<Node> nodes=new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node c=queue.poll();
            nodes.add(c);
            if(c.l!=null){
                queue.offer(c.l);
            }
            if(c.r!=null){
                queue.offer(c.r);
            }
        }
        return nodes;
    }

    /**
     * 深度度优先遍历
     * @return
     */
    public List<Node> deep(){
        Stack<Node> stack=new Stack<>();
        List<Node> nodes=new LinkedList<>();
        stack.push(root);
        while (!stack.isEmpty()){
            Node c=stack.pop();
            nodes.add(c);

            if(c.r!=null){
                stack.push(c.r);
            }
            if(c.l!=null){
                stack.push(c.l);
            }
        }
        return nodes;
    }

    public void print(String path) throws IOException {
        new Printer().toImage(path);
    }

    public static void main(String[] args) throws IOException {
        RBTree tree=new RBTree();
        int nodeCount=100;
        int[] array=new int[nodeCount];
        Random random=new Random();
        System.out.print("array =[");
        for(int i=0;i<nodeCount;i++){
            array[i]=random.nextInt(nodeCount*10);
            tree.insert(array[i]);
            System.out.print(array[i]+",");
        }
        System.out.print("]");
        System.out.println();
        tree.print("/Users/zengchao/rbtree.png");

        int searchValue=array[random.nextInt(nodeCount)];
        System.out.println("search value "+searchValue);
        int findSize=tree.find(searchValue).size();
        int reallySize=0;
        for(int value:array){
            if(value==searchValue){
                reallySize++;
            }
        }
        assert findSize==reallySize;
        System.out.println("search value is "+searchValue+",and finds "+findSize);

//        tree.delete(searchValue);
//        tree.print("/Users/zengchao/rbtree2.png");
//        assert tree.find(searchValue).size()==0;
        for(Node node:tree.getAll()){
            System.out.print(node.v+",");
        }
        System.out.println();
        for(Node node:tree.deep()){
            System.out.print(node.v+",");
        }
        System.out.println();
//        tree.broad();
//        tree.print();
    }
}
