package com.frank.lucene;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ExtendWordFilter extends TokenFilter {
    private int hadMatchedWordLength = 0;

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);

    private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);

    private final List<String> extendWords;


    public ExtendWordFilter(TokenStream in, List<String> extendWords) {
        super(in);
        this.extendWords = extendWords;
    }

    @Override
    public final boolean incrementToken() throws IOException {
        int skippedPositions = 0;
        while (input.incrementToken()) {
            if(termAtt.length() ==1){
                return false;
            }
            if (containsExtendWord()) {
                if (skippedPositions != 0) {
                    posIncrAtt.setPositionIncrement(posIncrAtt.getPositionIncrement() + skippedPositions);
                }
                return true;
            }
            skippedPositions += posIncrAtt.getPositionIncrement();
        }

        return false;
    }

    protected boolean containsExtendWord() {
        System.out.println("111111111111111111111  "+termAtt.toString());
        Optional<String> matchedWordOptional = extendWords.stream()
                .filter(word -> word.contains(termAtt.toString()))
                .findFirst();
        if (matchedWordOptional.isPresent()) {
            hadMatchedWordLength += termAtt.length();

            if (hadMatchedWordLength == matchedWordOptional.get().length()) {
                termAtt.setEmpty();
                termAtt.append(matchedWordOptional.get());
                return true;
            }
        } else {
            hadMatchedWordLength = 0;
        }
        return  true;
    }
}
