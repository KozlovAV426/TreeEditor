package commands

import State
import commands.description.Description

@Description(name = "sm", param = "<>", description = "switches mode (interactive, default)")
class SwitchMode(override var state: State) : Command {
    override fun execute(args: List<String>) {
        try {
            state.isInInteractiveMode = !state.isInInteractiveMode
            if (state.isInInteractiveMode) {
                println("Interactive mode")
            } else println("Default mode")
        } catch (e: Exception) {
            println("Error")
        }
    }
}