package service.impl;
import service.impl.NerService;

import javax.jws.WebService;
import javax.jws.WebMethod;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import edu.stanford.nlp.util.Triple;

import java.io.PrintStream;
import java.util.List;

//Service Implementation
@WebService(endpointInterface = "service.impl.NerService")
public class NerServiceImpl implements NerService {

	private String en_model_fn = "classifiers/english.muc.7class.distsim.crf.ser.gz";
	private String zh_model_fn = "edu/stanford/nlp/models/ner/chinese.misc.distsim.crf.ser.gz";
	private AbstractSequenceClassifier<CoreLabel> en_ner = null;
	private AbstractSequenceClassifier<CoreLabel> zh_ner = null;

	public void init() throws Exception {
		System.setOut(new PrintStream(System.out, true, "utf-8"));
		en_ner = CRFClassifier.getClassifier(en_model_fn);
		zh_ner = CRFClassifier.getClassifier(zh_model_fn);
	}

	@Override
	public String getNerResult(String text) throws Exception {
		if (en_ner == null) {
			init();
		}
		int ascii = 0;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) < 255) ascii += 1;
		}
		boolean isASCII = ((float) ascii / (float) text.length() > 0.7);
		if (isASCII)
			return en_ner.classifyToString(text, "slashTags", false);
		else
			return zh_ner.classifyToString(text, "slashTags", false);
	}
}
