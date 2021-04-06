package commands

import State
import commands.description.Description

@Description(name = "remove", param = "<index>", description = "removes node only if it doesn't have children\n" +
        "  interactive mode - removes current node\n" +
        "  default mode - remove node with index")
class Remove(override var state: State) : Command {
    override fun execute(args: List<String>) {
        if (state.isLoadBlocked() || state.isSaveBlocked()) {
            println("Can't change tree, because tree is using in other operation")
            return
        }
        try {
            if (state.isInInteractiveMode) {
                state.refreshNodeInteractive()
                if (state.nodeInteractive == state.tree.root) {
                    println("not allowed to remove root")
                    return
                }
                val parent = state.tree.getParent(state.nodeInteractive?.id!!)
                if (state.tree.removeAt(state.nodeInteractive?.id!!)) {
                    state.nodeInteractive = parent
                }
            } else {
                if (args[0].toInt() == state.tree.root?.id) {
                    println("not allowed to remove root")
                    return
                }
                state.tree.removeAt(args[0].toInt())
            }
        } catch (e: Exception) {
            println("Error")
        }
    }
}