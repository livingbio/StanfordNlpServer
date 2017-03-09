#!/bin/sh

wget http://nlp.stanford.edu/software/stanford-segmenter-2015-12-09.zip
unzip http://nlp.stanford.edu/software/stanford-segmenter-2015-12-09.zip
mv stanford-segmenter-2015-12-09 segmenter

wget http://nlp.stanford.edu/software/stanford-postagger-full-2015-12-09.zip
unzip stanford-postagger-full-2015-12-09.zip
mv stanford-postagger-full-2015-12-09 postagger

wget http://nlp.stanford.edu/software/stanford-ner-2015-12-09.zip
unzip stanford-ner-2015-12-09.zip
mv stanford-ner-2015-12-09 ner

wget http://nlp.stanford.edu/software/stanford-parser-full-2015-12-09.zip
unzip stanford-parser-full-2015-12-09.zip
mv stanford-parser-full-2015-12-09/ parser



# parser
wget http://nlp.stanford.edu/software/stanford-chinese-corenlp-2016-01-19-models.jar
wget http://nlp.stanford.edu/software/stanford-english-corenlp-2016-01-10-models.jar
wget http://nlp.stanford.edu/software/stanford-french-corenlp-2016-01-14-models.jar
wget http://nlp.stanford.edu/software/stanford-german-2016-01-19-models.jar
wget http://nlp.stanford.edu/software/stanford-spanish-corenlp-2015-10-14-models.jar

# ner
wget http://nlp.stanford.edu/software/stanford-german-2015-10-14-models.jar
wget http://nlp.stanford.edu/software/stanford-spanish-corenlp-2015-10-14-models.jar
wget http://nlp.stanford.edu/software/stanford-chinese-corenlp-2015-12-08-models.jar
mv *.jar lib

rm *.zip

sh make.sh
