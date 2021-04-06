package commands

import State
import tree.Tree
import commands.description.Description
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.io.ObjectInputStream
import java.nio.file.Path
import java.nio.file.Paths


@Description(name = "loadtree", param = "<path to file>", description = "load tree from file")
class LoadTree(override var state: State) : Command {
    override fun execute(args: List<String>) {
        if (state.isLoadBlocked() || state.isSaveBlocked()) {
            println("Can't load tree, because tree is using in other operation")
            return
        }

        try {
            var path: Path = Paths.get(args[0])
            if (!path.isAbsolute) {
                val dir = Paths.get(System.getProperty("user.dir"))
                path = dir.resolve(path)
            }

            val job = GlobalScope.launch(Dispatchers.IO) {
                println("loading tree from file...")
                try {
                    val fileInputStream = FileInputStream(path.toString())
                    val objectInputStream = ObjectInputStream(fileInputStream)
                    state.tree = objectInputStream.readObject() as Tree
                    state.treePrinter.tree = state.tree
                    state.nodeInteractive = state.tree.root
                    objectInputStream.close()
                } catch (e: Exception) {
                    println("Error occurred while loading tree")
                }
                println("tree was loaded from file")
            }
            state.lastLoadJob = job
        } catch (e: Exception) {
            println("Error")
        }
    }
}