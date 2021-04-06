package commands

import State
import commands.description.Description

@Description(name = "setvalue", param = "<index?, value>", description = "interactive mode - sets value to current node\n" +
        "  default mode - sets value to specified node")
class SetValue(override var state: State) : Command {
    override fun execute(args: List<String>) {
        if (state.isLoadBlocked() || state.isSaveBlocked()) {
            println("Can't change tree, because tree is using in other operation")
            return
        }
        try {
            if (state.isInInteractiveMode) {
                state.refreshNodeInteractive()
                state.tree.setValue(state.nodeInteractive?.id!!, args[0])
            } else {
                state.tree.setValue(args[0].toInt(), args[1])
            }
        } catch (e: Exception) {
            println("Error")
        }
    }
}