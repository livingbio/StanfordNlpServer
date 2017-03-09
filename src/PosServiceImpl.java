package service.impl;
import service.impl.PosService;

import javax.jws.WebService;
import javax.jws.WebMethod;

import java.io.*;
import java.util.List;
import java.util.LinkedList;

import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

//Service Implementation
@WebService(endpointInterface = "service.impl.PosService")
public class PosServiceImpl implements PosService {

 	private MaxentTagger tagger = null;

	public void init() throws Exception {
		System.setOut(new PrintStream(System.out, true, "utf-8"));
		tagger = new MaxentTagger("models/chinese-distsim.tagger");
	}

	@Override
	public String getPostagResult(String text) throws Exception {
		if (tagger == null) {
			init();
		}

		String[] sentences = text.split("\\$\\$\\$");
		List<String> tagged = new LinkedList<String>();
		for (String sent : sentences) {
			String tagged_sent = tagger.tagTokenizedString(sent.trim());
			tagged.add(tagged_sent);
			System.out.println(sent);
			System.out.println("----------");
			System.out.println(tagged_sent);
			System.out.println("=====================");
		}
		return String.join("$$$", tagged);
	}
}
