package commands

import State
import commands.description.Description
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.FileOutputStream
import java.io.ObjectOutputStream
import java.nio.file.Path
import java.nio.file.Paths

@Description(name = "savetree", param = "<path to file>", description = "save tree in file")
class SaveTree(override var state: State) : Command {
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
                val fileOutputStream = FileOutputStream(path.toString())
                val objectOutputStream = ObjectOutputStream(fileOutputStream)

                objectOutputStream.writeObject(state.tree)
                objectOutputStream.flush()
                objectOutputStream.close()
                println("tree was saved in file")
            }
            state.lastSaveJob = job
        } catch (e: Exception) {
            println("Error")
        }
    }
}