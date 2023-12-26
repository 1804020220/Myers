package com.example.myers.diff

/**
 * @author gk
 * @Description:
 * @date 2023/12/25
 */
class Snake(i: Int, j: Int, prev: PathNode?) : PathNode(i, j, prev) {
    override val isSnake: Boolean
        get() = true
}