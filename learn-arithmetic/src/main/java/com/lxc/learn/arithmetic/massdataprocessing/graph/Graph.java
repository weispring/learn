package com.lxc.learn.arithmetic.massdataprocessing.graph;

import lombok.Data;

/**
 * @Auther: lixianchun
 * @Date: 2020/4/12 17:28
 * @Description:
 */
@Data
public class Graph {

    /**
     * 1.邻接矩阵
     */
    //顶点
    private Object[] vertex;

    private Integer[][] arc;

    public static Integer N= Integer.MAX_VALUE;

    public Graph(){
    }

    public Graph(Object[] vertex, Integer[][] arc){
        this.vertex = vertex;
        this.arc = arc;
    }

    public static Graph graph = new Graph();

    static {
        Object[] vertex = new Object[]{"V0","V1","V2","V3","V4","V5","V6","V7","V8"};
        Integer[][] arc = new Integer[][]{
                new Integer[]{0,10,N,N,N,11,N,N,N},
                new Integer[]{10,0,18,N,N,N,16,N,12},
                new Integer[]{N,N,0,22,N,N,N,N,8},
                new Integer[]{N,N,22,0,20,N,N,16,21},
                new Integer[]{N,N,N,20,0,26,N,7,N},
                new Integer[]{11,N,N,N,26,0,17,N,N},
                new Integer[]{N,16,N,N,N,17,0,19,N},
                new Integer[]{N,N,N,16,7,N,19,0,N},
                new Integer[]{N,12,8,21,N,N,N,N,0},
        };
        graph.setArc(arc);
        graph.setVertex(vertex);
    }
}
