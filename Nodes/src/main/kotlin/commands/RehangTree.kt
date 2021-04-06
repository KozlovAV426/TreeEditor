package commands

import State
import commands.description.Description


@Description(name = "rehangtree", param = "<index from, index to>", description = "interactive mode - not allowed\n" +
        "  default mode - rehang tree from index to index")
class RehangTree(override var state: State) : Command {
    override fun execute(args: List<String>) {
        if (state.isLoadBlocked() || state.isSaveBlocked()) {
            println("Can't change tree, because tree is using in other operation")
            return
        }
        try {
            if (state.isInInteractiveMode) {
                println("not allowed in interactive mode, use default mode")
                return
            }
            if (args[0].toInt() == state.tree.root?.id) {
                println("not allowed to rehang root")
                return
            }
            state.tree.rehangTree(args[0].toInt(), args[1].toInt())
        } catch (e: Exception) {
            println("Error")
        }
    }
}