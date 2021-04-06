
import kotlinx.coroutines.Job
import printer.TreePrinter
import tree.Node
import tree.Tree

class State(var tree: Tree, var treePrinter: TreePrinter) {
    var isInInteractiveMode = false
    var isRunning = true
    var nodeInteractive: Node? = null
    var app: App? = null

    var lastLoadJob: Job? = null
    var lastSaveJob: Job? = null


    fun isLoadBlocked(): Boolean {
        var isSafe = false
        lastLoadJob?.let { load ->
            if (!load.isCompleted) isSafe = true
        }
        return isSafe
    }

    fun isSaveBlocked(): Boolean {
        var isSafe = false
        lastSaveJob?.let { save ->
            if (!save.isCompleted) isSafe = true
        }
        return isSafe
    }

    fun refreshNodeInteractive() {
        if (nodeInteractive == null) {
            nodeInteractive = tree.root
        }
    }
}