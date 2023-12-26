package com.example.myers.diff;

/**
 * @author gk
 * @Description:
 * @date 2023/12/25
 */
public final class DiffNode extends PathNode {
    public DiffNode(int i, int j, PathNode prev) {
        super(i, j, prev);
    }

    @Override
    public Boolean isSnake() {
        return false;
    }
}
