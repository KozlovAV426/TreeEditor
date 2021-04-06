package commands

import State
import commands.description.Description
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.nio.file.Path
import java.nio.file.Paths

@Description(name = "savetreejson", param = "<path to file>", description = "save tree in file using json format")
class SaveTreeJson(override var state: State) : Command {
    override fun execute(args: List<String>) {
        if (state.isLoadBlocked() || state.isSaveBlocked()) {
            println("Can't save tree, because tree is using in other operation")
            return
        }

        try {
            var path: Path = Paths.get(args[0])
            if (!path.isAbsolute) {
                val dir = Paths.get(System.getProperty("user.dir"))
                path = dir.resolve(path)
            }

            val job = GlobalScope.launch(Dispatchers.IO) {
                println("saving tree in file...")
                path.toFile().writeText(Json.encodeToString(state.tree.root))
                println("tree was saved in file")
            }
            state.lastSaveJob = job
        } catch (e: Exception) {
            println("Error")
        }
    }
}