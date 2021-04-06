package tree

import java.io.Serializable
import java.lang.Integer.max
import kotlin.collections.ArrayDeque

class Tree(var root: Node?): Iterable<Node>, Serializable {
    companion object {
        val DEFAULT_BRANCH_SIZE = 5
    }

    private var currentId = 0

    constructor(): this(null)


    fun removeAt(index: Int): Boolean {
        val parent = getParent(index)
        parent?.let { parent ->
            for (child in parent.nodes) {
                if (child.id == index) {
                    if (child.nodes.isEmpty()) {
                        parent.nodes.remove(child)
                        return true
                    }
                }
            }
        }
        return false
    }

    fun setValue(index: Int, value: String) {
        val it = iterator()
        while (it.hasNext()) {
            val node = it.next()
            if (node.id == index) {
                node.value = value
                return
            }
        }
    }

    fun rehangTree(fromIndex: Int, toIndex: Int) {
        var to: Node? = null
        var from: Node? = null
        var fromArray = mutableListOf<Node>()

        val it = iterator()
        while (it.hasNext()) {
            val node = it.next()
            if (node.id == toIndex) {
                to = node
            }
            else if (node.id == fromIndex) {
                from = node
            }
            for (child in node.nodes) {
                if (child.id == fromIndex) {
                    fromArray = node.nodes
                    break
                }
            }

            if (to != null && from != null) break
        }

        from?.let { f ->
            to?.nodes?.add(f)
            fromArray.remove(f)
        }

    }

    fun addNode(index: Int, value: String) {
        val it = iterator()
        while (it.hasNext()) {
            val node = it.next()
            if (node.id == index) {
                node.nodes.add(Node(value, currentId++))
                break
            }
        }
    }

    fun addNode(value: String) {
        if (root == null) {
            root = Node(value, currentId++)
            return
        }

        val it = iterator()
        while (it.hasNext()) {
            val node = it.next()
            if (node.nodes.size < DEFAULT_BRANCH_SIZE) {
                node.nodes.add(Node(value, currentId++))
                break
            }
        }
    }

    fun getParent(index: Int): Node? {
        val it = iterator()
        while (it.hasNext()) {
            val current = it.next()
            for (child in current.nodes) {
                if (child.id == index) {
                    return current
                }
            }
        }
        return null
    }

    fun updateCurrentId() {
        val it = iterator()
        while (it.hasNext()) {
            val current = it.next()
            currentId = max(current.id, currentId)
        }
        currentId++
    }

    override fun iterator(): Iterator<Node> {
        return TreeIterator()
    }


    inner class TreeIterator(): Iterator<Node> {
        private val queue = ArrayDeque<Node>()
        private var currentChildren = mutableListOf<Node>()

        init {
            root?.let { root -> queue.addLast(root) }
        }

        override fun hasNext(): Boolean {
            return !queue.isEmpty()
        }

        override fun next(): Node {
            val node = queue.removeFirst()
            for (child in node.nodes) {
                queue.addLast(child)
            }
            currentChildren = node.nodes
            return node
        }


    }
}