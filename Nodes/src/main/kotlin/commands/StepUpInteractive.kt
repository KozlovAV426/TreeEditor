package commands

import State
import commands.description.Description

@Description(name = "up", param = "<>", description = "works only in interactive mode, moves into parent node")
class StepUpInteractive(override var state: State) : Command {
    override fun execute(args: List<String>) {
        try {
            if (state.isInInteractiveMode)
                state.nodeInteractive?.let { node ->
                    state.tree.getParent(node.id)?.let { parent ->
                        state.nodeInteractive = parent
                    }
                }
            state.treePrinter.showInteractive(state.nodeInteractive)
        } catch (e: Exception) {
            println("Error")
        }
    }
}