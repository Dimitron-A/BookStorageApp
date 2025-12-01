#!/bin/bash
echo "Building Book Management System JAR..."

# Create bin directory if it doesn't exist
mkdir -p bin

# Compile all Java files
javac -d bin *.java

# Create manifest file
echo "Main-Class: BookManagementGUI" > manifest.txt

# Create JAR file
jar cfm BookManagementSystem.jar manifest.txt -C bin .

# Clean up
rm manifest.txt

echo ""
echo "Build complete! BookManagementSystem.jar created."
echo "Double-click the JAR file to run the application."
