package commands

import State
import commands.description.Description


@Description(name = "add", param = "<>", description = "interactive mode - add node to current node\n" +
        "  default mode - added node to specified node")
class AddNode(override var state: State) : Command {
    override fun execute(args: List<String>) {
        if (state.isLoadBlocked() || state.isSaveBlocked()) {
            println("Can't add node, because tree is using in other operation")
            return
        }
        try {
            if (state.isInInteractiveMode) {
                state.refreshNodeInteractive()
                if (state.tree.root == null) {
                    state.tree.addNode(args[0])
                    state.refreshNodeInteractive()
                    return
                }
                state.tree.addNode(state.nodeInteractive?.id!!, args[0])
            } else {
                state.tree.addNode(args[0])
            }
        } catch (e: Exception) {
            println("Error")
        }
    }
}