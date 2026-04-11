# Xtext Tools

Xtext Tools provides Eclipse views for inspecting the internal structures behind Xtext-based editors. It is intended for language engineers who need to understand how a document is represented as a semantic model and as a node model during parsing.

Issues and improvement ideas are welcome.

## Features

### Node Model Outline

The Node Model Outline provides a view for inspecting the Xtext node model of the currently opened file.

It is useful when you need to:

- inspect the parse tree produced by Xtext
- understand token boundaries and hidden nodes
- correlate parsed text with the underlying grammar structure
- link the outline selection with the editor bidirectionally

### Semantic Model Outline

The Semantic Model Outline provides a view for inspecting the semantic model (AST / EMF model) of the currently opened file.

It is useful when you need to:

- inspect the semantic structure created by parsing
- understand containments, attributes, and references
- navigate cross-references to their targets
- link the outline selection with the editor bidirectionally

## Installation

Install Xtext Tools into Eclipse from the update site:

1. In Eclipse, choose **Help -> Install New Software...**
2. Click **Add...**
3. Enter this update site URL: `https://xtexttools.libutzki.de/`
4. Select the category **Xtext Tools**
5. Complete the wizard and restart Eclipse when prompted

The update site contains both features:

- `Xtext Node Model Outline`
- `Xtext Semantic Model Outline`

## Using the Views

### Open the Node Model Outline

1. Choose **Window -> Show View -> Other...**
2. Select **Xtext Tools -> Node Model Outline**
3. Open an Xtext-based file in an editor
4. The view displays the underlying node model for that file

### Open the Semantic Model Outline

1. Choose **Window -> Show View -> Other...**
2. Select **Xtext Tools -> Semantic Model Outline**
3. Open an Xtext-based file in an editor
4. The view displays the underlying semantic model (AST / EMF model) for that file

## Motivation

When designing Xtext languages, a recurring question is: how does the underlying model actually look?

Files parsed by Xtext are represented as object graphs in memory. These object graphs are referred to as the abstract syntax tree (AST), the semantic model, or simply the model. Xtext models are implemented using the Eclipse Modeling Framework (EMF).

Because the semantic model is an EMF model, it can already be inspected with generic EMF tools. That is often enough, especially when validating how nested expressions or other grammar constructs are mapped into Ecore objects.

If you want to understand why that works, the Xtext documentation on EMF integration is a good starting point:

- https://www.eclipse.org/Xtext/documentation.html#emf_integration

In some cases, however, the semantic model is not enough. Xtext also builds a parse tree, usually called the node model, which retains the textual structure of the input including token-level information.

Investigating the parse tree is usually less convenient than inspecting the semantic model. In practice, many language engineers end up stepping through the debugger to understand how the parser has built the node model and how individual nodes relate back to grammar elements.

Xtext Tools is meant to reduce that debugging overhead by providing dedicated Eclipse views for the different underlying models.

Compared to generic EMF model viewers, the Semantic Model Outline is tailored to Xtext workflows and supports bidirectional linking between editor selection and model elements.

These views target language designers and are usually not useful for end users of a language.

## Compatibility

The current build is based on:

- Tycho `5.0.2`
- Eclipse release repository `2018-12`
- Xtext update site `2.8.4`

This means two different compatibility aspects matter:

- Build runtime: building this repository requires `JDK 21` because Tycho 5 runs on Java 21.
- Produced artifacts: the project is currently compiled with Java 8 source/target settings.

If you plan to use the plugins with newer Eclipse or Xtext versions, treat that as unverified unless you have tested it explicitly.

## Build From Source

Prerequisites:

- JDK `21`
- Maven `3.9.9` or newer

Build the complete reactor from the repository root:

```bash
mvn verify
```

The repository uses Tycho and contains these main modules:

- `de.libutzki.xtext.target`: target platform definition
- `de.libutzki.xtext.repo`: p2 update site/category definition
- `nodemodeloutline`: Node Model Outline feature and plugin
- `semanticmodeloutline`: Semantic Model Outline feature and plugin

After a successful build, the generated p2 repository is produced by the `de.libutzki.xtext.repo` module.

## CI and Deployment

GitHub Actions is configured for both validation builds and deployment.

- Pushes and pull requests trigger a build workflow that runs `mvn verify`.
- Pushes to the `main` branch trigger the deployment workflow.
- That deployment publishes the generated p2 repository from `de.libutzki.xtext.repo/target/repository` to the `gh-pages` branch.

As a result, each successful build on `main` updates the published p2 update site.

## License

Xtext Tools is published under the [Eclipse Public License (EPL) v2.0](https://www.eclipse.org/legal/epl-v20.html).

The software includes items that have been sourced from third parties as set out below:

- [Eclipse MoDisco](https://www.eclipse.org/MoDisco/)

## Notes

The feature metadata in this repository still contains older EPL v1.0 references. The README reflects the intended project license, but the feature manifests should be aligned as a follow-up to avoid ambiguity.
