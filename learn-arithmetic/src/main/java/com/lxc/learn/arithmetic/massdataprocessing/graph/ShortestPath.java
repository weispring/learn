package com.lxc.learn.arithmetic.massdataprocessing.graph;

/**
 * @Auther: lixianchun
 * @Date: 2020/4/13 21:05
 * @Description:
 */
public class ShortestPath {


    /**
     * 最短路径
     * 先求其他节点的路径，然后再找此最短路径的下一个节点的最短路径
     * @param args
     */
    public static void main(String[] args) {
        dijkstra(Graph.path,0);
    }

    public static void dijkstra(Graph g, int vo){

        int length = g.getVertex().length;
        //前驱顶点下标
        int[] p = new int[length];
        //路径长度
        int[] d =  new int[length];
        //路径节点
        String[] path = new String[length];

        int v,w,k=0,min;
        //当前下标对应节点是否已处理
        int[] finish = new int[length];
        for (v=0;v<length;v++){
            finish[v] = 0;
            d[v] = g.getArc()[vo][v];
            p[v] = 0;

            path[v] = vo + " "  +  v +" ";
        }

        d[vo] = 0;
        finish[vo] = 1;

        String t = "";

        for (v=1;v<length;v++){
            min = g.N;
            for (w=0;w<length;w++){
                if (finish[w] == 0
                        &&( d[w] < min && d[w] > 0)){
                    k = w;
                    min = d[w];
                    t = path[w];
                }
            }
            finish[k] = 1;
            for (w=0;w<length;w++){
                boolean f = (min + g.getArc()[k][w]) < d[w];
                System.out.println(f);
                if (finish[w] == 0
                        && ((min + g.getArc()[k][w]) < d[w] && (min + g.getArc()[k][w]) > 0  )){
                    d[w] = min + g.getArc()[k][w];
                    p[w] = k;
                    path[w] = t + " " +  w;
                }
            }
        }

        for (int j=0;j<length;j++){
            System.out.printf("到点 %d 的路径为： %s",j,path[j]);
            System.out.println();
            System.out.printf("到点 %d 长度： %d",j,d[j]);
            System.out.println();
        }

        for (int j=0;j<length;j++){
            System.out.printf("%d 前驱节点： %d ",j,p[j]);
            System.out.println();
        }

    }

}
