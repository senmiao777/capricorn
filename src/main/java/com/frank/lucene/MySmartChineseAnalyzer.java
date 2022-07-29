package com.frank.lucene;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.cn.smart.HMMChineseTokenizer;
import org.apache.lucene.analysis.core.LowerCaseFilter;
import org.apache.lucene.analysis.core.StopFilter;
import org.apache.lucene.analysis.en.PorterStemFilter;

import java.util.List;
import java.util.Objects;

public class MySmartChineseAnalyzer extends Analyzer {

    private CharArraySet stopWords;
    private CharArraySet extendWords;

    private List<String> words;

    public MySmartChineseAnalyzer() {
    }
    public MySmartChineseAnalyzer(CharArraySet stopWords) {
        this.stopWords = stopWords;
    }

    public MySmartChineseAnalyzer(CharArraySet stopWords, List<String> words) {
        this.stopWords = stopWords;
        this.words = words;
    }

    public MySmartChineseAnalyzer( List<String> words) {
        this.words = words;
    }


    @Override
    public Analyzer.TokenStreamComponents createComponents(String fieldName) {
        final Tokenizer tokenizer = new HMMChineseTokenizer();
        TokenStream result = tokenizer;


        result = new PorterStemFilter(result);

        if (Objects.nonNull(stopWords) && !stopWords.isEmpty()) {
            result = new StopFilter(result, stopWords);
        }
        if (CollectionUtils.isNotEmpty(words)) {
            result = new ExtendWordFilter(result, words);
        }

        return new TokenStreamComponents(tokenizer, result);
    }

    @Override
    protected TokenStream normalize(String fieldName, TokenStream in) {
        return new LowerCaseFilter(in);
    }


}
