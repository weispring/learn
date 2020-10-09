package com.lxc.learn.arithmetic.massdataprocessing.tree;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lixianchun
 * @description
 * @date 2020/9/30
 */
@Slf4j
public class ListConvertToTree {

    private static final String topCode = "";

    public List<LevelTree> getDictTree(String tableName){
        List<LevelTree> LevelTreeList = new ArrayList<>();
        return setTree(LevelTreeList, getTopTree(LevelTreeList));
    }

    /**
     * 获取到顶层树
     * @return
     */
    private List<LevelTree> getTopTree(List<LevelTree> LevelTrees) {
        return LevelTrees.stream()
                .filter(cd -> topCode.equals(cd.getCode()))
                .collect(Collectors.toList());
    }

    /**
     * 生成树型数据
     * @param levelTrees
     * @param top
     * @return
     */
    private List<LevelTree> setTree (List<LevelTree> levelTrees, List<LevelTree> top) {

        if (top.isEmpty()) {
            return  levelTrees;
        }
        // 循环给每个赋下级
        for (LevelTree dict : top) {
            Iterator<LevelTree> iterator = levelTrees.iterator();
            while (iterator.hasNext()){
                LevelTree LevelTree = iterator.next();
                if (dict.getCode().equals(LevelTree.getParentCode())) {
                    dict.getLevelTrees().add(LevelTree);
                }
            }
            if (dict.getLevelTrees().size() > 0) {
                // 递归
                setTree(levelTrees, dict.getLevelTrees());
            }
        }
        return top;
    }


    @Getter
    @Setter
    public static class LevelTree{

        private String code;

        private String parentCode;

        private String name;

        private List<LevelTree> levelTrees;
    }
}
