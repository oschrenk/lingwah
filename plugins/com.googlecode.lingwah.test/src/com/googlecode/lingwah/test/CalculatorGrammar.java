package com.googlecode.lingwah.test;

import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.parser.ParserReference;

public class CalculatorGrammar extends Grammar {
	public final Parser inline_comment = seq(str("/*"), anyChar(), str("/*")); 
	public final Parser ws	= cho(oneOrMore(regex("[ \t\n\f\r]")), inline_comment);
	public final Parser digit = regex("[0-9]");
	public final Parser number = oneOrMore(digit);
	public final Parser decimal = seq(opt(str('-')), seq(number, opt(seq(str('.'), number))));
	public final ParserReference expr = ref();
	public final Parser addition = seq(expr, str('+'), expr).separatedBy(opt(ws));
	public final Parser subtraction = seq(expr, str('-'), expr).separatedBy(opt(ws));
	public final Parser multiplication = seq(expr, str('*'), expr).separatedBy(opt(ws));
	public final Parser division = seq(expr, str('\\'), expr).separatedBy(opt(ws));
	public final Parser group = seq(str('('), expr, str(')')).separatedBy(opt(ws));
	{
		expr.set(cho(decimal, addition, subtraction, multiplication, division, group));
	}
	
	public CalculatorGrammar() {
		init();
	}
}