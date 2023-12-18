#!/bin/bash

className=$1
javac -d target -cp target:lib/junit-platform-console-standalone-1.6.0.jar "tests/$className.java"
java -jar lib/junit-platform-console-standalone-1.6.0.jar --class-path target --select-class "tests.$className" 
