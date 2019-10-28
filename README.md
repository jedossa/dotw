## Dotty Workshop

Let’s play and get familiar with Scala 3 features

### gitpod

To start the repository in [Gitpod](https://www.gitpod.io/) just use the below link:

https://gitpod.io/#https://github.com/jedossa/dotw

### sbt

> First, make sure `code`, the binary for Visual Studio Code, is on your `$PATH`,
  this is the case if you can start the IDE by running `code` in a terminal. This
  is the default on all systems except Mac where you'll need to follow these
  instructions: https://code.visualstudio.com/docs/setup/mac#_command-line

From repository path just run:

```shell
sbt launchIDE
```

### just trying dotty

https://scastie.scala-lang.org/?target=dotty

### More information

- [dotty getting started](http://dotty.epfl.ch/docs/usage/getting-started.html)
- [dotty example project](https://github.com/lampepfl/dotty-example-project)
- [porting an existing project](https://github.com/lampepfl/dotty-example-project#getting-your-project-to-compile-with-dotty)

#### It is recommended to set `edtitor.autoSeave = on` in Preferences (`Ctrl+,`)

### TODO
- Add more tests and examples.
- Address comments' questions and micro-challenges
- Translate common patterns and good practices, e.g. https://github.com/debasishg/frdomain
- Add a `Shapeless 3` example
- Fix typos and improve comments explanations
- Add links to other Dotty example projects
- Try upgrading dotty version for Cats PoC: (spotted-leopards)[https://github.com/typelevel/spotted-leopards], (dotty-experiments)[https://github.com/travisbrown/dotty-experiments]
- Explore [`macros`](https://dotty.epfl.ch/docs/reference/metaprogramming/macros.html)
- etc. (add yours)

#### PRs are :green_heart: