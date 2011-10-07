package com.googlecode.lingwah.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.googlecode.lingwah.AbstractProcessor;
import com.googlecode.lingwah.Match;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.annotations.Processes;

@Processes(CalculatorGrammar.class)
public class CalculatorProcessor extends AbstractProcessor {
	
	static final CalculatorGrammar grammar= CalculatorGrammar.INSTANCE;
	
	public void completeAddition(Match expr) {
		List<Match> children= expr.getChildrenByType(grammar.expr);
		BigDecimal left= getResult(children.get(0));
		BigDecimal right= getResult(children.get(1));
		putResult(left.add(right));
	}
	public void completeSubtraction(Match expr) {
		List<Match> children= expr.getChildrenByType(grammar.expr);
		BigDecimal left= getResult(children.get(0));
		BigDecimal right= getResult(children.get(1));
		putResult(left.subtract(right));
	}
	public void completeMultiplication(Match expr) {
		List<Match> children= expr.getChildrenByType(grammar.expr);
		BigDecimal left= getResult(children.get(0));
		BigDecimal right= getResult(children.get(1));
		putResult(left.multiply(right));
	}
	public void completeDivision(Match expr) {
		List<Match> children= expr.getChildrenByType(grammar.expr);
		BigDecimal left= getResult(children.get(0));
		BigDecimal right= getResult(children.get(1));
		putResult(left.divide(right, 28, RoundingMode.HALF_UP));
	}
	public void completeOperator(Match op) {
		putResult(getResult(op.getChildren().get(0)));
	}
	public void completeGroup(Match expr) {
		putResult(getResult(expr.getChildByType(grammar.expr)));
	}
	public void completeDecimal(Match expr) {
		putResult(new BigDecimal(expr.getText()));
	}
	public void completeExpr(Match expr) {
		putResult(getResult(expr.getChildren().get(0)));
	}

	public static BigDecimal process(ParseResults results) {
		return new CalculatorProcessor().getResult(results.getLongestMatch());
	}

}
