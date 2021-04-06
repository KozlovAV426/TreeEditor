import commands.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

import printer.TreePrinter
import tree.Node
import tree.Tree
import java.io.*
import java.lang.Exception
import java.nio.file.Path
import java.nio.file.Paths


class App {
    private var state: State
    private val dispatcher: Map<String, Command>

    init {
        val tree = Tree()
        val treePrinter = TreePrinter(tree)

        state = State(tree, treePrinter)
        state.nodeInteractive = tree.root
        state.app = this
        dispatcher = mapOf(
            "add" to AddNode(state),
            "addto" to AddNodeTo(state),
            "exit" to Exit(state),
            "help" to Help(state),
            "loadtree" to LoadTree(state),
            "loadtreejson" to LoadTreeJson(state),
            "print" to Print(state),
            "rehangtree" to RehangTree(state),
            "remove" to Remove(state),
            "savetree" to SaveTree(state),
            "savetreejson" to SaveTreeJson(state),
            "setvalue" to SetValue(state),
            "down" to StepDownInteractive(state),
            "up" to StepUpInteractive(state),
            "sm" to SwitchMode(state)
        )
        println(intro)
    }


    fun run() {
        while (state.isRunning) {
            val line = readLine()
            val words = line?.split(" ")
            try {
                dispatcher[words!![0]]!!.execute(words.slice(1 until words.size))
            } catch (e: Exception) {
                println("Error")
            }
        }
    }

    companion object {
        val intro = "       __ _.--..--._ _\n" +
                "        .-' _/   _/\\_   \\_'-.\n" +
                "       |__ /   _/\\__/\\_   \\__|\n" +
                "          |___/\\_\\__/  \\___|\n" +
                "                 \\__/\n" +
                "                 \\__/\n" +
                "                  \\__/\n" +
                "                   \\__/\n" +
                "                ____\\__/___\n" +
                "          . - '             ' -.\n" +
                "         /                      \\\n" +
                "   ~~~~~~~  ~~~~~ ~~~~~  ~~~ ~~~  ~~~~~" +
                "\n" +
                "  _____                _____    _ _ _             \n" +
                " |_   _| __ ___  ___  | ____|__| (_) |_ ___  _ __ \n" +
                "   | || '__/ _ \\/ _ \\ |  _| / _` | | __/ _ \\| '__|\n" +
                "   | || | |  __/  __/ | |__| (_| | | || (_) | |   \n" +
                "   |_||_|  \\___|\\___| |_____\\__,_|_|\\__\\___/|_|   \n" +
                "                                                  \n" +
                "-".repeat(56) + "\n" +
                "| Hello, it is a TreeEditor, it can operate with trees |\n" +
                "| for example it can edit them, save them to file and  |\n" +
                "| load them from file. To start working you need to    |\n" +
                "| write commands, which you can know typed `help`      |\n" +
                "-".repeat(56)
    }
}