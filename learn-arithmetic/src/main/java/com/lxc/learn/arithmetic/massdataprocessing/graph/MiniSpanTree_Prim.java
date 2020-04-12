package com.lxc.learn.arithmetic.massdataprocessing.graph;

/**
 * @Auther: lixianchun
 * @Date: 2020/4/12 18:05
 * @Description:
 */

public class MiniSpanTree_Prim {

    /**
     * 思想：先生一颗最小生成树，第一个节点和其他节点相连
     * 然后找出和第一个节点距离最小的点，找到那个点后，再以此类推

     本方法输出 节点小标：连接的节点下标

     */
    public static void main(String[] args) {
        prim(Graph.graph);
    }

    public static void prim(Graph g){
        int min,i,j,k;
        Integer[] adjvetx = new Integer[g.getVertex().length];
        Integer[] lowcost = new Integer[g.getVertex().length];
        lowcost[0] = 0;
        adjvetx[0] = 0;
        for (i=1;i<g.getVertex().length;i++){
            lowcost[i] = g.getArc()[0][i];
            adjvetx[i] = 0;
        }
        int sum = 0;
        for (i=1;i<g.getVertex().length;i++){
            min = Graph.N;
            j=1;k=0;
            while (j<g.getVertex().length){
                if (lowcost[j] != 0 && lowcost[j] < min){
                    min = lowcost[j];
                    k = j;
                }
                j++;
            }
            //System.out.printf("%s,%d",adjvetx[k],k);
            System.out.println(adjvetx[k] + " : " + k);
            sum = sum + g.getArc()[k][adjvetx[k]];

            lowcost[k] = 0;
            for (j=1;j<g.getVertex().length;j++){
                if (lowcost[j] != 0 && g.getArc()[k][j] < lowcost[j]) {
                    lowcost[j] = g.getArc()[k][j];
                    adjvetx[j] = k;
                }
            }
        }
        System.out.println("sum : " + sum);
    }
}




