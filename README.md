# TreeEditor
 <img src="https://user-images.githubusercontent.com/44925915/113745304-f8d4d980-970d-11eb-98e9-d8b3190669af.png">

TreeEditor is a console applictation which makes your work with trees easier.  In spite of its simplicity, it provides you good functionality for operating with trees.  Here is a list of what it can do:
* Edit trees (e.g. add nodes, remove nodes, rehang subtrees)
* Display trees
* Save trees in file and load them from file

## Usage

### Two ways to work with tree

TreeEditor provides two modes of operation. The first is the default mode, the second is interactive. These methods are quite similar but differ in the way they work with tree.

#### Default mode

This mode allows you to work with whole tree, you can edit it using node indices, `print` displays whole tree.
![default](https://user-images.githubusercontent.com/44925915/113751881-0f326380-9715-11eb-8bfe-4626bebb9b4b.gif)

#### Interactive mode
In this mode you can walk through you tree, `print` displays only current node and its children, all transformations are applied to the current node.  
![interactive](https://user-images.githubusercontent.com/44925915/113752419-9f70a880-9715-11eb-8e3a-23788b3f1f1c.gif)

### Saving and loading trees

#### Serialization and deserialization

TreeEditor can save trees in file and load them, using standart java object serialization, but unfortunately such a file is not human-readable, so  TreeEditor provides you opportunity to save and load trees, using `json` format. For example such tree 
```
(0) root
├── (1) child1
│   └── (3) child3
└── (2) child2
```
will correspond to
```
{
  "value": "root",
  "nodes": [
    {
      "value": "child1",
      "nodes": [
        {
          "value": "child3",
          "nodes": [],
          "id": 3
        }
      ],
      "id": 1
    },
    {
      "value": "child2",
      "nodes": [],
      "id": 2
    }
  ],
  "id": 0
}
```
#### You are not blocked while saving or loading tree

Since saving and loading the tree takes place in the background, you can continue to work with the tree (for example, you can walk around the tree while it is being saved). Of course, in the case of working with a disk, this is not so important, but if, for example, work over a network, then it may be useful.

## Extension points
You can add your own commands to the app, to do this you need to implement `Command` interface and add yor class to map of commands. Pay attention that you may add `Description` annotation to your command, that provides info for `help` command.

```
@Description(name = "myGreatCommand", param = "<>", description = "does smth awesome")
class MyCommand(override var state: State) : Command {
    override fun execute(args: List<String>) {
        }
    }

```
