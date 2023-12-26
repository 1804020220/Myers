package com.example.myers.diff.main;

import com.example.myers.diff.MyersDiff;
import com.example.myers.diff.PathNode;

import java.util.Arrays;
import java.util.List;

/**
 * @author gk
 * @Description:
 * @date 2023/12/25
 */
public class Main {

    public static void main(String[] args) {
        String oldText = "A\nB\nC\nA\nB\nB\nA";
        String newText = "C\nB\nA\nB\nA\nC";
        List<String> oldList = Arrays.asList(oldText.split("\\n"));
        List<String> newList = Arrays.asList(newText.split("\\n"));
        MyersDiff<String> myersDiff = new MyersDiff<>();
        try {
            PathNode pathNode = myersDiff.buildPath(oldList, newList);
            System.out.println(pathNode);
            myersDiff.buildDiff(pathNode, oldList, newList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
