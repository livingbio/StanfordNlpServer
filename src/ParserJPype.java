package service.jpype;

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

public class ParserJPype {

    private LexicalizedParser lp = null;
    private GrammaticalStructureFactory gsf = null;

    public void init(String lang) throws Exception {
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        String model_path = "edu/stanford/nlp/models/lexparser/";
        String model;
        if (lang.equals("zh")) {
            model = "chineseFactored.ser.gz";
        } else if (lang.equals("en")) {
            model = "englishFactored.ser.gz";
        } else if (lang.equals("fr")) {
            model = "frenchFactored.ser.gz";
        } else if (lang.equals("de")) {
            model = "germanFactored.ser.gz";
        } else {
            throw new Exception("Wrong language parameter: " + lang);
        }
        lp = LexicalizedParser.loadModel(model_path + model);
        gsf = lp.treebankLanguagePack().grammaticalStructureFactory();
    }

    public String getParserResult(String text) throws Exception {
        if (gsf == null) {
            init("zh");
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
