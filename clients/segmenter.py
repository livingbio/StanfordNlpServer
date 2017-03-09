# -*- coding: utf-8 -*-
from suds.client import Client

segClient = Client('http://localhost:9999/seg?wsdl')
text = u'''希拉里关注的是美国人的薪资。
她想要把最低时薪从7.25美元上调至至少12美元，但这只会影响到一小部分工人。
经济学家指出，为了能真正让工资上涨，
美国经济的增长幅度要比现任总统奥巴马执政期间增长的更大。'''
segClient.service.getSegmentResult(text)
