package org.jxls.transform;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Leonid Vysochyn on 7/23/2015.
 */
public class TransformationConfigTest {
    @Test
    public void defaultValuesAfterCreation(){
        TransformationConfig config = new TransformationConfig();
        assertEquals("Default expression notation begin part is wrong", "${", config.getExpressionNotationBegin());
        assertEquals("Default expression notation end part is wrong", "}", config.getExpressionNotationEnd());
    }

    @Test
    public void buildExpressionNotation(){
        TransformationConfig config = new TransformationConfig();
        String expressionBegin = "[[";
        String expressionEnd = "]]";
        config.buildExpressionNotation(expressionBegin, expressionEnd);
        assertEquals("Expression notation begin part is wrong after buildExpressionNotation()", expressionBegin, config.getExpressionNotationBegin());
        assertEquals("Expression notation end part is wrong after buildExpressionNotation()", expressionEnd, config.getExpressionNotationEnd());
    }
}
