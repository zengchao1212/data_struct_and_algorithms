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

        public Node(Node p,int v){
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
        public void toRight(){
            if(p!=null){
                Node c=this;
                Node p=c.p;
                Node g=p.p;
                p.p=c;
                c.p=g;
                p.l=c.r;
                if(p.l!=null){
                    p.l.p=p;
                }
                c.r=p;
                rc(c,p,g);
            }

        }

        public void toLeft(){
            if(p!=null){
                Node c=this;
                Node p=c.p;
                Node g=p.p;
                p.p=c;
                c.p=g;
                p.r=c.l;
                if(p.r!=null){
                    p.r.p=p;
                }
                c.l=p;
                rc(c,p,g);
            }
        }
    }

    private void xz(Node c,Node p,Node g){
        if(p.v<g.v && c.v<p.v){
            g.red=!g.red;
            p.red=false;
            p.toRight();
        }else if(p.v<g.v && c.v>=p.v){
            g.red=!g.red;
            c.red=false;
            c.toLeft();
            c.toRight();
        }else if(p.v>=g.v && c.v>=p.v){
            g.red=!g.red;
            p.red=false;
            p.toLeft();
        }else if(p.v>=g.v && c.v<p.v){
            g.red=!g.red;
            c.red=false;
            c.toRight();
            c.toLeft();
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
            if(c.l!=null && c.r!=null && c.l.red && c.r.red){
                if(p!=null){
                    c.red=true;
                }
                c.l.red=false;
                c.r.red=false;
                if(p!=null&&p.red){
                    xz(c,p,g);
                }
            }
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

    private void loop(Node n, List list){
        if(n!=null){
            list.add(n);
            loop(n.l,list);
            loop(n.r,list);
        }
    }
    private int getLevel(){
        Queue<Node> queue=new LinkedList<>();
        queue.offer(root);
        int i=0;
        while (!queue.isEmpty()){
            Node c=queue.poll();
            i++;
            if(c.l!=null){
                queue.offer(c.l);
            }
            if(c.r!=null){
                queue.offer(c.r);
            }
        }
        int c=0;
        while (i>0){
            c++;
            i=i>>1;
        }
        return c;
    }
    public void print() throws IOException {
        int level=getLevel();
        int width=((1<<(level-1)))*200;
        BufferedImage img=new BufferedImage(width+50,width+50,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics=img.createGraphics();

        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, img.getWidth(), img.getHeight());
        graphics.setFont(new Font(null,Font.BOLD,16));
//        graphics.fi
        Queue<Node> queue=new LinkedList<>();
        Map<Node,Integer> wm=new HashMap<>();
        Map<Node,Integer> hm=new HashMap<>();
        queue.offer(root);
        wm.put(root,width/2);
        hm.put(root,50);
        while (!queue.isEmpty()){
            Node c=queue.poll();
            if(c.red){
                graphics.setColor(Color.RED);
            }else{
                graphics.setColor(Color.BLACK);
            }
            if(c.p==null){
                graphics.drawString(""+c.v,wm.get(c),hm.get(c));
            }else{
                int px=wm.get(c.p);
                int py=hm.get(c.p);
                int cx;
                int cy=py+75;
                if(c.p.p==null){
                    if(c.v<c.p.v){
                        cx=px/2;
                    }else{
                        cx=px+px/2;
                    }
                }else{
                    int ppx=wm.get(c.p.p);
                    if(c.v<c.p.v){
                        cx=px-Math.abs((ppx-px))/2;
                    }else{
                        cx=px+Math.abs((ppx-px))/2;
                    }
                }

                wm.put(c,cx);
                hm.put(c,cy);
                graphics.drawString(""+c.v,cx,cy);
                graphics.setColor(Color.BLUE);
                graphics.drawLine(px,py,cx,cy);
            }
            if(c.l!=null){
                queue.offer(c.l);
            }
            if(c.r!=null){
                queue.offer(c.r);
            }
        }
        File file=new File("/Users/zengchao/rbtree.jpg");
        if(!file.exists()){
            file.createNewFile();
        }
        ImageIO.write(img,"jpg",file);
    }

    public void broad(){
        Queue<Node> queue=new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){
            Node c=queue.poll();
            System.out.print(c.v+"\t");
            if(c.l!=null){
                queue.offer(c.l);
            }
            if(c.r!=null){
                queue.offer(c.r);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        RBTree tree=new RBTree();
//      int[] array={388,306,793,16,338,761,816,170,551,862,92,286,503,565,891,69,233,488,521,664,982,208,397,550,626,673,893,580,669,906,902,};
        int[] array={388,306,793,16,338,761,816,170,551,862,92,286,503,565,891,69,233,488,521,664,982,208,397,550,626,673,893,580,669,906,902,};
        for(int i:array){
            tree.insert(i);
        }
//        tree.insert(16);
//        tree.insert(50);
//        tree.insert(25);
//        tree.insert(75);
//        tree.insert(12);
//        tree.insert(18);
//        tree.print();
//        tree.broad();
        tree.print();
    }
}
