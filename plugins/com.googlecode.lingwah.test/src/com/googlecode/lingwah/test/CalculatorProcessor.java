package com.googlecode.lingwah.test;

import java.math.BigDecimal;
import java.util.List;

import com.googlecode.lingwah.AbstractProcessor;
import com.googlecode.lingwah.Match;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.annotations.Processes;

@Processes(CalculatorGrammar.class)
public class CalculatorProcessor extends AbstractProcessor {
	
	static final CalculatorGrammar grammar= CalculatorGrammar.INSTANCE;
	BigDecimal _result;
	
	public void leaveExpr(Match expr) {
		BigDecimal result;
		Parser parser= expr.getParser();
		if (parser == grammar.decimal) { 
			result= new BigDecimal(expr.getText());
		}
		else if (parser == grammar.group) {
			result= getResult(expr.getChildByType(grammar.expr));
		}
		else {
			List<Match> children= expr.getChildrenByType(grammar.expr);
			BigDecimal left= getResult(children.get(0));
			BigDecimal right= getResult(children.get(1));
			if (parser == grammar.addition) {
				result= left.add(right);
			}
			else if (parser == grammar.subtraction) {
				result= left.subtract(right);
			}
			else if (parser == grammar.multiplication) {
				result= left.multiply(right);
			}
			else if (parser == grammar.division) {
				result= left.divide(right);
			}
			else {
				throw new RuntimeException("Internal error, unrecognised operator:"+parser.getLabel());
			}
		}
		putResult(result);	
	}

	public static BigDecimal process(Match longestMatch) {
		return new CalculatorProcessor().getResult(longestMatch);
	}

}
