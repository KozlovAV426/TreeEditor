package commands

import State
import commands.description.Description

@Description(name = "exit", param = "<>", description = "stops program")
class Exit(override var state: State) : Command {
    override fun execute(args: List<String>) {
        if (state.isLoadBlocked() || state.isSaveBlocked()) {
            println("Can't exit, because tree is using in other operation")
            return
        }
        state.isRunning = false
    }
}