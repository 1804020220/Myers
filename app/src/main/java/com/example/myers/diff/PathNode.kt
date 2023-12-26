package com.example.myers.diff

/**
 * @author gk
 * @Description:
 * @date 2023/12/25
 */
abstract class PathNode(@JvmField val i: Int, @JvmField val j: Int, @JvmField val prev: PathNode?) {
    abstract val isSnake: Boolean?
    override fun toString(): String {
        val buf = StringBuffer("[")
        var node: PathNode? = this
        while (node != null) {
            buf.append("(")
            buf.append(node.i.toString())
            buf.append(",")
            buf.append(node.j.toString())
            buf.append(")")
            node = node.prev
        }
        buf.append("]")
        return buf.toString()
    }
}