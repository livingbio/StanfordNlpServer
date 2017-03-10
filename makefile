#
# makefile
# lizongzhe, 2017-03-10 17:18
#

libpath=WEB-INF/lib
classes=WEB-INF/classes


init: WEB-INF/classes/service
	@echo "makefile needs your attention"

segmenter:
	wget http://nlp.stanford.edu/software/stanford-segmenter-2015-12-09.zip
	unzip stanford-segmenter-2015-12-09.zip && \
	rm -rf segmenter && \
	mv stanford-segmenter-2015-12-09 segmenter && \
	rm -rf stanford-segmenter-2015-12-09.zip && \
	mv segmenter/*.jar ${libpath}

postagger:
	wget http://nlp.stanford.edu/software/stanford-postagger-full-2015-12-09.zip && \
    unzip stanford-postagger-full-2015-12-09.zip && \ 
	rm -rf postagger && \
	mv stanford-postagger-full-2015-12-09 postagger && \
	rm -rf stanford-postagger-full-2015-12-09.zip && \
	mv postagger/*.jar ${libpath}

parser:
	wget http://nlp.stanford.edu/software/stanford-parser-full-2015-12-09.zip && \
	unzip stanford-parser-full-2015-12-09.zip && \
	rm -rf parser && \
	mv stanford-parser-full-2015-12-09 parser && \
	rm -rf stanford-parser-full-2015-12-09.zip && \
	mv parser/*.jar ${libpath}

ner:
	wget http://nlp.stanford.edu/software/stanford-ner-2015-12-09.zip && \
	unzip stanford-ner-2015-12-09.zip && \
	rm -rf ner && \
	mv stanford-ner-2015-12-09 ner && \
	rm -rf stanford-ner-2015-12-09.zip && \
	mv ner/*.jar ${libpath}


*.jar:
	mkdir -p WEB-INF/lib && \
	wget http://nlp.stanford.edu/software/`basename $0` -O WEB-INF/lib

WEB-INF/classes/service: segmenter postagger parser ner src/*.java \
	WEB-INF/lib/stanford-chinese-corenlp-2016-01-19-models.jar \
	WEB-INF/lib/stanford-english-corenlp-2016-01-10-models.jar \
	WEB-INF/lib/stanford-french-corenlp-2016-01-14-models.jar \
	WEB-INF/lib/stanford-german-2016-01-19-models.jar \
	WEB-INF/lib/stanford-spanish-corenlp-2015-10-14-models.jar \
	WEB-INF/lib/stanford-german-2015-10-14-models.jar \
	WEB-INF/lib/stanford-spanish-corenlp-2015-10-14-models.jar \
	WEB-INF/lib/stanford-chinese-corenlp-2015-12-08-models.jar 
	javac -cp ${libpath}:. ${classes}

build: init

