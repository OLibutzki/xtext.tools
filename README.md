# Xtext Tools

As I work as an Xtext consultant there are a lot of situations I am confronted with again and again.
The first one I would like to look into is "how does the underlying model look like?"

Text files parsed by Xtext are represented as object graphs in memory. We call these object graphs Abstract Syntax Tree (AST), semantic model or simply model interchangeably. In Xtext models are implemented using the Eclipse Modeling Framework (EMF).

As it's an EMF (Ecore) model you can investigate your model just by using any Ecore editor/viewer. Especially for languages which provide nested expressions it helps you to double-check, if the semantic model is built the way you want it to be.

If you would like to get to know why this works I recommend to read and understand this [document](http://www.eclipse.org/Xtext/documentation.html#emf_integration). 

In many situations the information from the AST is sufficient, but in some situations you need additional syntactical information. In Xtext not only an AST is constructed while parsing but also a so called parse tree (also called node model), which contains all the textual information chunked in so called tokens.

Investigating the parse tree is not as easy as visualizing the semantic model. Often you are find myself debugging in order to get a clue how the parse tree of my model is built.

As the grammar is an Xtext language itself it is represented as an EMF model, too. It's possible to navigate from a node of the parse tree to the corresponding grammar element. As this is just another indirection from the parse tree you can imagine that the debugger is my friend in these cases, too.

Xtext Tools tries to stop exploring your models by using the debugger. It provides different views in order to visualize the different underlying models.

These views aim at language designer and are usually useless for the language's end users.


## Node Model Outline

### Install Xtext Tools from Update Site 
If you have an Eclipse running : 

1. Choose **Help -> Install New Software...** from the menu bar and click **Add...** 
2. Insert this url: **http://xtexttools.olibutzki.cloudbees.net/**
3. Select the *Xtext Node Model Outline* from the category *Xtext tools* and complete the wizard by clicking the **Next** button until you can click **Finish**.
4. After a quick download and a restart of Eclipse, the node model outline is ready to use.   

### Integrating the node model outline
If you have an Eclipse running : 

1. Choose **Window -> Show View -> Other...** from the menu bar
2. Select *Xtext Tools* -> *Node Model Outline*
3. The outline is added to your workbench
4. If you open an Xtext based file, the Node Model Outline displays the underlying Node Model