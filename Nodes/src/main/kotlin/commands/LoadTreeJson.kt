package commands

import State
import commands.description.Description
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.nio.file.Path
import java.nio.file.Paths

@Description(name = "loadtreejson", param = "<path to file>", description = "load tree from human-readable representation")
class LoadTreeJson(override var state: State) : Command {
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
                    state.tree.root = Json.decodeFromString(path.toFile().readText())
                    state.nodeInteractive = state.tree.root
                    state.tree.updateCurrentId()
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