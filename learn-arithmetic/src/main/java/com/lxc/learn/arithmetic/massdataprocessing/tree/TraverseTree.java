package com.lxc.learn.arithmetic.massdataprocessing.tree;

import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Auther: lixianchun
 * @Date: 2020/4/11 22:10
 * @Description:
 */
@Slf4j
public class TraverseTree {

    /**
     * 三种遍历次序：
     *
     * 前序遍历
     * 若二叉树为空，则返回；否则，先遍历根结点，然后遍历左子树，再遍历右子树。
     * 中序遍历
     * 如果二叉树为空，则返回；否则，从根结点开始，先遍历左子树，然后是根结点，左后是右子树。
     * 后续遍历
     * 如果二叉树为空，则返回；否则，先遍历左子树，然后遍历右子树,最后根结点。
     * 层序遍历
     * 如果二叉树为空，则返回；否则，从树的第一层，根结点开始访问，从上而下逐层遍历，在同一层中，按从左到右的顺序逐个结点访问。

     */

    /**
     * 前序遍历
     */
    public static void before(Tree tree){
        if (tree != null){
            System.out.print(tree.getData()+" ");
            before(tree.getLeft());
            before(tree.getRight());
        }
        System.out.println();
    }

    public static Tree beforeConstruction(Tree parent,int flag){
        Tree o = null;
        Object input = null;
        try {
            input = CommonQueue.QUEUE.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!"#".equals(input)) {
            //System.out.print(input + "\t");
            Tree tree = new Tree(input);
            if (flag == 0) {
                o = tree;
            } else if (flag == 1) {
                parent.setLeft(tree);
            } else if (flag == 2){
                parent.setRight(tree);
            }
            beforeConstruction(tree,1);
            beforeConstruction(tree,2);
        }
        return o;
    }




    /**
     * 中序遍历
     */
    public static void middle(Tree tree){
        if (tree != null){
            middle(tree.getLeft());
            System.out.print(tree.getData()+" ");
            middle(tree.getRight());
        }
        System.out.println();
    }

    /**
     * 后序遍历
     */
    public static void after(Tree tree){
        if (tree != null){
            after(tree.getLeft());
            after(tree.getRight());
            System.out.print(tree.getData()+" ");
        }
        System.out.println();
    }



    public static void visit(Object c, int level)
    {
        System.out.printf("%s 位于第 %d 层 \n", c, level);
    }

    /**
     *
     * @param T
     * @param level
     */
    public static void PreOrderTraverse(Tree T, int level)
    {
        if (T != null)                       //遍历终止条件 NULL
        {
            visit(T.getData(), level);   //递归：根 - 左 - 右
            PreOrderTraverse(T.getLeft(), level + 1);
            PreOrderTraverse(T.getRight(), level + 1);
        }
    }



    public static void levelOrder(Tree root) {
        LinkedBlockingQueue<Tree> queue = new LinkedBlockingQueue();
        try {
            queue.put(root);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!queue.isEmpty())
        {
            Tree front = null;
            try {
                front = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (front.getLeft() != null){
                queue.add(front.getLeft());
            }
            if (front.getRight() != null){
                queue.add(front.getRight());
            }
            System.out.print(front.getData() + "\t");
        }
    }


    /**
     * 层序遍历
     */
    public static void level(Tree tree){
        while (tree != null){
            log.info("{}",tree.getData());
            log.info("{}",getData(tree.getLeft()));
            log.info("{}",getData(tree.getLeft()));
            before(tree.getLeft());
            before(tree.getRight());

        }
    }

    public static Object getData(Tree tree){
        if (tree != null){
            return tree.getData();
        }else {
            return null;
        }
    }

}
