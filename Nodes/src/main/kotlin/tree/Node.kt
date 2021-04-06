package tree

import java.io.Serializable

@kotlinx.serialization.Serializable
class Node(var value: String, var nodes: MutableList<Node>, val id: Int): Serializable {
    constructor(value: String, id: Int): this(value, ArrayList<Node>(), id)
}