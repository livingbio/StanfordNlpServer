# -*- coding: utf-8 -*-
from suds.client import Client
import re


def to_half_word(text):
    '''Stanford POS tagger會自動將英文及數字轉換成全形字元
    ，這個函式用來將全形字元轉換成半形'''
    return ''.join([chr(ord(ch) - 0xff00 + 0x20)
                    if ord(ch) >= 0xff01 and ord(ch) <= 0xff5e else ch
                    for ch in text])

segClient = Client('http://localhost:8888/seg?wsdl')
posClient = Client('http://localhost:8888/pos?wsdl')
parClient = Client('http://localhost:8888/parser?wsdl')

text = u'''希拉里关注的是美国人的薪资。
她想要把最低时薪从7.25美元上调至至少12美元，但这只会影响到一小部分工人。
经济学家指出，为了能真正让工资上涨，
美国经济的增长幅度要比现任总统奥巴马执政期间增长的更大。'''

seg_text = segClient.service.getSegmentResult(text)
seg_text = re.sub('(。|？|；|！|\!|\?|;)', '\\1$$$', seg_text)
pos_text = posClient.service.getPostagResult(seg_text)
pos_text = [to_half_word(s) for s in pos_text.split('$$$')]

words = []
tags = []
for sent in pos_text:
    tagtext = [w.split('#') for w in sent.split() if w]
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
