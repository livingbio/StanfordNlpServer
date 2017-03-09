# -*- coding: utf-8 -*-
from suds.client import Client
import re

nerClient = Client('http://localhost:9996/ner?wsdl')

text = u"古亭 、 新店 及 土城 地区 臭氧 偏 高 ， 环保署 提醒 敏感 族群 减少 在 户外 剧烈 活动 。 而 劳动 部长 郭芳煜 14 日 出席 立法院 时 受访 坦 言 ， 对于 蔡英文 的 说法 感 同 身 受"

ner_text = nerClient.service.getNerResult(text)
tagword = [w.split('/')[::-1] for w in ner_text.split()]
ner = []
prev_tag = 'O'
prev_words = []
for tag, word in tagword:
    if tag != prev_tag and len(prev_words):
        ner.append((''.join(prev_words), prev_tag))
        prev_words = []
    prev_tag = tag
    if tag != 'O':
        prev_words.append(word)
