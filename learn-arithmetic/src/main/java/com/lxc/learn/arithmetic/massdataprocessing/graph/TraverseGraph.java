package com.lxc.learn.arithmetic.massdataprocessing.graph;

import lombok.Data;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Auther: lixianchun
 * @Date: 2020/4/12 17:32
 * @Description:
 */
@Data
public class TraverseGraph {

    /**
     * 图的深度优先遍历
     */
    private static boolean[] visited;

    public static void dfsTravese(Graph g){
        visited = new boolean[g.getVertex().length];
        int i = 0;
        for (i=0;i<g.getVertex().length;i++){
            //初始化，所有顶点均没有访问
            visited[i] = false;
        }

        for (i=0;i<g.getVertex().length;i++){
            if (!visited[i]){//未访问过顶点调用DFS
                dfs(g,i);
            }
        }
    }

    private static void dfs(Graph g, int i){
        int j;
        System.out.print(g.getVertex()[i]);
        visited[i] = true;
        for (j=0;i<g.getVertex().length;j++){
            if (g.getArc()[i][j] == 1 && !visited[j]){
                dfs(g,j);
            }
        }
    }


    /**
     * 广度优先算法
     * @param g
     */
    public static void bfsTravese(Graph g){
        visited = new boolean[g.getVertex().length];
        LinkedBlockingQueue queue = new LinkedBlockingQueue();
        for (int i=0;i<g.getVertex().length;i++){
            //初始化，所有顶点均没有访问
            visited[i] = false;
        }
        for (int i=0;i<g.getVertex().length;i++){
            if (!visited[i]){//未访问过
                visited[i] = true;
                System.out.print(g.getVertex()[i]);
                queue.add(g.getVertex()[i]);
                while (!queue.isEmpty()){
                    try {
                        queue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    for (int j=0;i<g.getVertex().length;j++){
                        if (g.getArc()[i][j] == 1 && !visited[j]){
                            System.out.print(g.getVertex()[j]);
                            queue.add(g.getVertex()[j]);
                        }
                    }
                }

                dfs(g,i);
            }
        }
    }
}
