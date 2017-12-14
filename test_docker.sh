#!/bin/bash

docker run -d -p 8080:8080 gliacloud/stanfordnlpserver  # run NLP server in background
python -c "from zhconvert import ZHConvert; print ZHConvert().tw_postag(u'Beautiful soup')"
# 正確輸出: [(u'Beautiful', u'JJ'), (u'soup', u'NN')]
