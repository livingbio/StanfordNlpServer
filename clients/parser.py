# -*- coding: utf-8 -*-
from suds.client import Client
import re
from zhconvert import ZHConvert

parClient = Client('http://localhost:9997/parser?wsdl')
zh = ZHConvert()

sent1 = u'希拉蕊關注的是美國人的薪資。'
sent2 = u'她想要把最低時薪從7.25美元上調至至少12美元，但這只會影響到一小部分工人。'
sent3 = u'經濟學家指出，為了能真正讓工資上漲，美國經濟的增長幅度要比現任總統奧巴馬執政期間增長的更大。'

words = []
tags = []
for sent in [sent1, sent2, sent3]:
    tagtext = zh.tw_postag(sent)
    words.append(' '.join([w for w, _ in tagtext]))
    tags.append(' '.join([t for _, t in tagtext]))

word_tag = '$$$'.join(words) + '###' + '$$$'.join(tags)
result = parClient.service.getParserResult(word_tag)

parsed_sents = []
for sent in result.split('$$$'):
    tmp = []
    for r in sent.split():
        rel = r.split(',')
        tmp.append((rel[0], int(rel[1]), int(rel[2])))
    parsed_sents.append(tmp)
