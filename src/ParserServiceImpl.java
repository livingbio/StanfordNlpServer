package service.impl;
import service.impl.ParserService;

import javax.jws.WebService;
import javax.jws.WebMethod;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.List;
import java.util.LinkedList;

import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

//Service Implementation
@WebService(endpointInterface = "service.impl.ParserService")
public class ParserServiceImpl implements ParserService {

    private LexicalizedParser lp = null;
     private GrammaticalStructureFactory gsf = null;

    public void init() throws Exception {
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/chineseFactored.ser.gz");
        gsf = lp.treebankLanguagePack().grammaticalStructureFactory();
    }

    @Override
    public String getParserResult(String text) throws Exception {
        if (gsf == null) {
            init();
        }
        // String text = "俄国 希望 伊朗 没有 制造 核武器 计划 。$$$俄国 希望 伊朗 没有 制造 核武器 计划 。##NR VV NR AD VV NN NN PU$$$NR VV NR AD VV NN NN PU";
        String[] text_tag = text.split("###");
        String[] sentences = text_tag[0].split("\\$\\$\\$");
        String[] postags = text_tag[1].split("\\$\\$\\$");

        List<String> result = new LinkedList<String>();
        for (int i = 0; i < sentences.length; i++) {
            String[] words = sentences[i].split(" ");
            String[] tags = postags[i].split(" ");
            List<TaggedWord> tagsent = new ArrayList<>();
            for (int j = 0; j < words.length; j++) {
                tagsent.add(new TaggedWord(words[j], tags[j]));
            }
            Tree parse = lp.apply(tagsent);
            GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);
            Collection<TypedDependency> tdl = gs.typedDependencies();
            // System.out.println(tdl);

            List<String> tmp = new LinkedList<String>();
            for (TypedDependency link : tdl) {
                tmp.add(String.format("%s,%s,%s", link.reln(), link.gov().index(), link.dep().index()));
            }
            result.add(String.join(" ", tmp));
        }
        return String.join("$$$", result);
    }
}
