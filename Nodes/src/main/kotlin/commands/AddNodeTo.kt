package commands

import State
import commands.description.Description


@Description(name = "addto", param = "<index of node>", description = "interactive mode - not allowed\n" +
        "  default mode - added node to index node")
class AddNodeTo(override var state: State) : Command {
    override fun execute(args: List<String>) {
        if (state.isLoadBlocked() || state.isSaveBlocked()) {
            println("Can't add node, because tree is using in other operation")
            return
        }

        try {
            if (state.isInInteractiveMode) {
                println("not allowed in interactive mode, use default mode")
                return
            }
            state.tree.addNode(args[0].toInt(), args[1])
        } catch (e: Exception) {
            println("Error")
        }
    }
}