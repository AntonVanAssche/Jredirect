# Build instructions for creating a **LINUX** executable

Create the `.class` file:

```bash
$ javac -d . src/Jredirect.java
```

Build the `.jar` file:

```bash
$ jar cvmf MANIFEST.MF Jredirect.jar Jredirect.class
```

Convert the `.jar` file to a **LINUX** executable:

```bash
$ cat build/convert-to-executable.sh Jredirect.jar > Jredirect && chmod +x Jredirect
```

