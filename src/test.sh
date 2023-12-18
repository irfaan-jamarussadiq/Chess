#!/bin/bash

for var in "$@"
do
	javac -d target -cp target:lib/junit-platform-console-standalone-1.6.0.jar "tests/$var.java"
	java -jar lib/junit-platform-console-standalone-1.6.0.jar --class-path target --select-class "tests.$var" 
done
