<div align="center">
   <h1>
      Jredirect
   </h1>
   <p align="center">
      A command-line tool that allows you to trace URL redirections written in Java.
      <br/>
      So you will never get rickrolled again.
   </p>
   <img src="./assets/Jredirect-preview.gif">
</div>

## Installation

Download the `.jar` file is provided inside the `bin` directory.

## Usage

```bash
$ java -jar Jredirect.jar <url>
```

## Building a `.jar` file from source

Create the `.class` file:

```bash
$ javac -d . src/Jredirect.java
```

Build the `.jar` file:

```bash
$ jar cvmf MANIFEST.MF Jredirect.jar Jredirect.class
```

In case you are on a **UNIX** system you can do the following to create a sort of binary executable.

```bash
$ echo '#!/usr/bin/java -jar' > Jredirect
$ cat Jredirect.jar > Jredirect
$ chmod +x Jredirect
```


## License

Distributed under the MIT License. See [`LICENSE.md`](./LICENSE.md) for more information.

