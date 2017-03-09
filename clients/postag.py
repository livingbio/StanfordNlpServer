# -*- coding: utf-8 -*-
from suds.client import Client
import re


def to_half_word(text):
    '''Stanford POS tagger會自動將英文及數字轉換成全形字元
    ，這個函式用來將全形字元轉換成半形'''

    return ''.join([chr(ord(ch) - 0xff00 + 0x20)
                    if ord(ch) >= 0xff01 and ord(ch) <= 0xff5e else ch
                    for ch in text])

segClient = Client('http://localhost:9999/seg?wsdl')
posClient = Client('http://localhost:9998/pos?wsdl')

text = u'''希拉里关注的是美国人的薪资。
她想要把最低时薪从7.25美元上调至至少12美元，但这只会影响到一小部分工人。
经济学家指出，为了能真正让工资上涨，
美国经济的增长幅度要比现任总统奥巴马执政期间增长的更大。'''

seg_text = segClient.service.getSegmentResult(text)
# 用 $$$ 作為分行符號，這個符號可以在 PosServiceImpl.java 中指定
seg_text = re.sub('(。|？|；|！|\!|\?|;)', '\\1$$$', seg_text)
pos_text = posClient.service.getPostagResult(seg_text)
# 傳回來的字串也是以 $$$ 作為分行符號
pos_text = [to_half_word(s) for s in pos_text.split('$$$')]
