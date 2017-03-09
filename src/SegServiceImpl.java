package service.impl;
import service.impl.SegService;

import java.io.*;
import java.util.List;
import java.util.Properties;

import javax.jws.WebService;
import javax.jws.WebMethod;

import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

//Service Implementation
@WebService(endpointInterface = "service.impl.SegService")
public class SegServiceImpl implements SegService {

 	private CRFClassifier<CoreLabel> segmenter = null;

	public void init() throws Exception {
		System.setOut(new PrintStream(System.out, true, "utf-8"));

		Properties props = new Properties();
		props.setProperty("sighanCorporaDict", "data");
		// props.setProperty("NormalizationTable", "data/norm.simp.utf8");
		// props.setProperty("normTableEncoding", "UTF-8");
		// below is needed because CTBSegDocumentIteratorFactory accesses it
		props.setProperty("serDictionary", "data/dict-chris6.ser.gz");
		props.setProperty("inputEncoding", "UTF-8");
		props.setProperty("sighanPostProcessing", "true");

		segmenter = new CRFClassifier<>(props);
		segmenter.loadClassifierNoExceptions("data/ctb.gz", props);
	}

	@Override
	public String getSegmentResult(String text) throws Exception {
		if (segmenter == null) {
			init();
		}
		System.out.println(text);
		List<String> segmented = segmenter.segmentString(text);
		System.out.println(segmented);
		System.out.println(String.join(" ", segmented));
		return String.join(" ", segmented);
	}

}
