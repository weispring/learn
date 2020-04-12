package com.lxc.learn.arithmetic.massdataprocessing.tree;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: lixianchun
 * @Date: 2020/4/11 21:41
 * @Description:
 */
@Slf4j
public class StructureTree {


    /**
     * 树的构造、遍历、以及哈夫曼树和哈夫曼编码
     *
     * @param args
     */

    public static void main(String[] args) {
        Object[] levelArray = new Object[]{"A",
                "B","C",
                "D","E","F","G",
                "H","","","","I","","","J",
                "","K"};


        Tree root = TraverseTree.beforeConstruction(null,0);
        System.out.print("前序");
        TraverseTree.before(root);
        System.out.print("中序");
        TraverseTree.middle(root);
        System.out.print("后序");
        TraverseTree.after(root);
        System.out.print("层序");
        TraverseTree.levelOrder(root);
    }

    /**
     * 通过层序排列的数组构造 tree
     * @param array
     * @param root
     * @return
     */
    public static Tree structure(Object[] array,Tree root){
        if (array.length == 1){
            root.setData(array[0]);
            return root;
        }else if (array.length > 1){
            Tree parent = root;
            int count = (array.length - 1) / 2;
            int leave = (array.length - 1) % 2;
            for (int i=1;i<count;i+=2){
                Tree left = new Tree(array[i]);
                parent.setLeft(left);

                Tree right = new Tree(array[i+1]);
                parent.setRight(right);

                parent = left;
            }


        }
        log.info("前序遍历");
        TraverseTree.before(root);
        log.info("中序遍历");
        TraverseTree.middle(root);
        log.info("后序遍历");
        TraverseTree.after(root);
        return root;
    }


}
