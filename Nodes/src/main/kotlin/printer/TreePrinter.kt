package printer

import tree.Node
import tree.Tree
import java.lang.StringBuilder


class TreePrinter(var tree: Tree) {

    fun show() {
        tree.root?.let { root ->
            val builder = StringBuilder()
            print(root, builder, "", "")
            println(builder.toString())}
    }

    private fun print(node: Node, builder: StringBuilder, prefix: String, childrenPrefix: String) {
        builder.append(prefix)
        builder.append("(${node.id}) ${node.value}")
        builder.append("\n")

        for (i in 0 until node.nodes.size) {
            if (i != node.nodes.size - 1) {
                print(node.nodes[i], builder, "$childrenPrefix├── ", "$childrenPrefix│   ")
            } else {
                print(node.nodes[i], builder, "$childrenPrefix└── ", "$childrenPrefix    ")
            }
        }
    }

    fun showInteractive(node: Node?) {
        node?.let {
            node -> val builder = StringBuilder()
            builder.append("(${node.id}) ${node.value}")
            builder.append("\n")
            for (i in 0 until node.nodes.size) {
                if (i != node.nodes.size - 1) {
                    builder.append("├── ")
                    builder.append("(${node.nodes[i].id}) ${node.nodes[i].value}")
                    builder.append("\n")
                } else {
                    builder.append("└── ")
                    builder.append("(${node.nodes[i].id}) ${node.nodes[i].value}")
                    builder.append("\n")
                }
            }
            println(builder.toString())
        }
    }

}