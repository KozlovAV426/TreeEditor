package commands

import State
import com.sun.tools.javac.Main
import commands.description.Description

@Description(name = "help", param = "<>", description = "prints description of commands")
class Help(override var state: State) : Command {
    companion object {
        val classNames = arrayListOf<String>(
            "AddNode", "AddNodeTo", "Exit", "Help", "LoadTree", "LoadTreeJson",
            "Print", "RehangTree", "Remove", "SaveTree", "SaveTreeJson", "SetValue",
            "StepDownInteractive", "StepUpInteractive", "SwitchMode"
        )
    }
    override fun execute(args: List<String>) {
        println("Here is a list of supported commands")
        try {
            for (commandName in classNames) {
                println("-".repeat(120))
                val cls = Class.forName("commands.$commandName")
                val descr = cls.getDeclaredAnnotation(Description::class.java) as Description
                println(descr.name + " " + descr.param + " " + descr.description)
            }
        } catch (e: ClassNotFoundException) {
            println("Error")
        }
    }
}