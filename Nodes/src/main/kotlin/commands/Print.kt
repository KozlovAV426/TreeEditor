package commands

import State
import commands.description.Description

@Description(name = "print", param = "<>", description = "interactive mode - prints current node and its children\n" +
        "default mode - prints whole tree")
class Print(override var state: State) : Command {
    override fun execute(args: List<String>) {
        if (state.isLoadBlocked()) {
            println("Can't print tree, because tree is loading from file")
            return
        }
        try {
            if (state.isInInteractiveMode) {
                state.refreshNodeInteractive()
                state.treePrinter.showInteractive(state.nodeInteractive)
            } else state.treePrinter.show()
        } catch (e: Exception) {
            println("Error")
        }
    }
}