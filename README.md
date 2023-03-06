# Building the project

Building the project comprises of four steps

### Step 1

Compile the Java code into Java class files by running the following in the root of the project directory

`javac -cp 'lib/dependencies/*' java-src/*.java`

### Step 2

Create the build/ directory

`mkdir -p build/uk/gov/hmrc/mark`

### Step 3

Move the class files into the build directory
`mv java-src/*.class build/uk/gov/hmrc/mark`

### Step 4

Bundle the files into a .jar file
`cd build/ && jar cfv markcalc.jar uk/gov/hmrc/mark && cd -`

### Step 5

Move the new .jar file into the lib/ directory
`mv build/markcalc.jar lib/dependencies/`

### Step 6

Delete the build directory
`rm -rf build/`

### Step 7

Update and add new tests to cover changes

### Step 8

Run specs

### Step 8

Bump the version of the gem in the `.gemspec` file

#### Note: All commands must be run in the root of the project

## Dependencies

You will need Java 17.0.1 or later installed.
