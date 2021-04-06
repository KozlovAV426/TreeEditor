package commands

import State

interface Command {
    var state: State

    fun execute(args: List<String>)
}