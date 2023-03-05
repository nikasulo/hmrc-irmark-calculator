# Building the project

Building the project comprises of four steps

### Step 1

Compile the Java code into Java class files by running the following in the root of the project directory

`javac -cp '.:lib/jce-jdk13-114.jar:lib/xmlsec-1.4.1.jar:lib/xalan_enhanced.jar:lib/commons-logging-1.1.1.jar' *.java`

### Step 2

Create the build/ directory

`mkdir -p build/uk/gov/hmrc/mark`

### Step 3

Move the class files into the build directory
`mv *.class build/uk/gov/hmrc/mark`

### Step 4

Bundle the files into a .jar file
`jar cfv markcalc.jar build/uk/gov/hmrc/mark`

### Step 5

Move the new .jar file into the lib/ directory
`mv markcalc.jar lib/`

### Step 6

Delete the build directory
`rm -rf build/`

#### Note: All commands must be run in the root of the project

After the new jar file is created, you may move the lib/ directory into the destination project

## Dependencies

You will need JAVA 1.4 or later installed.
