package com.example.myers.diff

import java.util.Collections

/**
 * @author gk
 * @Description:
 * @date 2023/12/25
 */
class MyersDiff<T> {
    /**
     * 默认相等规则
     */
    private val DEFAULT_EQUALIZER =
        Comparator { original: T, revised: T -> if (original == revised == true) 0 else 1 }
    private val equalizer: Comparator<T>

    constructor() {
        equalizer = DEFAULT_EQUALIZER
    }

    constructor(equalizer: Comparator<T>) {
        this.equalizer = equalizer
    }

    /**
     * 寻找最优路径
     */
    @Throws(Exception::class)
    fun buildPath(orig: List<T>?, rev: List<T>?): PathNode? {
        requireNotNull(orig) { "original sequence is null" }
        requireNotNull(rev) { "revised sequence is null" }
        val N = orig.size
        val M = rev.size
        //最大步数（先全减后全加）
        val MAX = N + M + 1
        val size = 1 + 2 * MAX
        val middle = size / 2
        //构建纵坐标数组（用于存储每一步的最优路径位置）
        val diagonal = arrayOfNulls<PathNode>(size)
        //用于获取初试位置的辅助节点
        diagonal[middle + 1] = Snake(0, -1, null)
        //外层循环步数
        for (d in 0 until MAX) {
            //内层循环所处偏移量，以2为步长，因为从所在位置走一步，偏移量只会相差2
            var k = -d
            while (k <= d) {

                //找出对应偏移量所在的位置，以及它上一步的位置（高位与低位）
                val kmiddle = middle + k
                val kplus = kmiddle + 1
                val kminus = kmiddle - 1
                //若k为-d，则一定是从上往下走，即i相同
                //若diagonal[kminus].i < diagonal[kplus].i，则最优路径一定是从上往下走，即i相同
                var i: Int
                var prev: PathNode?
                if (k == -d || k != d && diagonal[kminus]!!.i < diagonal[kplus]!!.i) {
                    i = diagonal[kplus]!!.i
                    prev = diagonal[kplus]
                } else {
                    //若k为d，则一定是从左往右走，即i+1
                    //若diagonal[kminus].i = diagonal[kplus].i，则最优路径一定是从左往右走，即i+1
                    i = diagonal[kminus]!!.i + 1
                    prev = diagonal[kminus]
                }
                //根据i与k，计算出j
                var j = i - k
                //上一步的低位数据不再存储在数组中（每个k只清空低位即可全部清空）
                diagonal[kminus] = null
                //当前是diff节点
                var node: PathNode = DiffNode(i, j, prev)
                //判断被比较的两个数组中，当前位置的数据是否相同，相同，则去到对角线位置
                while (i < N && j < M && equals(orig[i], rev[j])) {
                    i++
                    j++
                }
                //判断是否去到对角线位置，若是，则生成snack节点，前节点为diff节点
                if (i > node.i) {
                    node = Snake(i, j, node)
                }
                //设置当前位置到数组中
                diagonal[kmiddle] = node
                //达到目标位置，返回当前node
                if (i >= N && j >= M) {
                    return diagonal[kmiddle]
                }
                k += 2
            }
        }
        throw Exception("could not find a diff path")
    }

    private fun equals(orig: T, rev: T): Boolean {
        return equalizer.compare(orig, rev) == 0
    }

    /**
     * 打印diff
     */
    fun buildDiff(path: PathNode?, orig: List<T>?, rev: List<T>?) {
        var path = path
        val result: MutableList<String?> = ArrayList()
        requireNotNull(path) { "path is null" }
        requireNotNull(orig) { "original sequence is null" }
        requireNotNull(rev) { "revised sequence is null" }
        while (path!!.prev != null && path.prev!!.j >= 0) {
            if (java.lang.Boolean.TRUE == path.isSnake) {
                val endi = path.i
                val begini = path.prev!!.i
                for (i in endi - 1 downTo begini) {
                    result.add("  " + orig[i])
                }
            } else {
                val i = path.i
                val j = path.j
                val prei = path.prev!!.i
                if (prei < i) {
                    result.add("- " + orig[i - 1])
                } else {
                    result.add("+ " + rev[j - 1])
                }
            }
            path = path.prev
        }
        Collections.reverse(result)
        for (line in result) {
            println(line)
        }
    }
}