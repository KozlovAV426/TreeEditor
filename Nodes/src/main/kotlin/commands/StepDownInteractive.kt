package commands

import State
import commands.description.Description

@Description(name = "down", param = "<index>", description = "works only in interactive mode, moves into specified node")
class StepDownInteractive(override var state: State) : Command {
    override fun execute(args: List<String>) {
        try {
            if (state.isInInteractiveMode)
                state.nodeInteractive?.let { node ->
                    for (child in node.nodes) {
                        if (child.id == args[0].toInt()) {
                            state.nodeInteractive = child
                        }
                    }
                }
            state.treePrinter.showInteractive(state.nodeInteractive)
        } catch (e: Exception) {
            println("Error")
        }
    }
}