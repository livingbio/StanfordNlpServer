#!/bin/bash
javac -cp lib/*:parser/*:postagger/*:segmenter/*:ner/*:. -d . src/*.java
