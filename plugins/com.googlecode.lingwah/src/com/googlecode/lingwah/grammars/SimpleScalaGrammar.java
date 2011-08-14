package com.googlecode.lingwah.grammars;

import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.parser.ParserReference;

/**
 * A simple grammar similar to Scala
 * @author Ted Stockwell
 */
public class SimpleScalaGrammar extends Grammar {
	public final Parser ID = regex("[a-zA-Z]([a-zA-Z0-9]|_[a-zA-Z0-9])*");
	public final Parser NUM = regex("[1-9][0-9]*");
		 
	public final ParserReference expr = ref();
	
	public final Parser formals = rep(seq(ID, str(":"), ID)).separatedBy(str(","));
	public final Parser actuals = opt(rep(expr));
	
	public final Parser member = choice(
		      seq(str("val"), ID, str(":"), ID, str("="), expr),
		      seq(str("var"), ID, str(":"), ID, str("="), expr),
		      seq(str("def"), ID, str("("), formals, str(")"), str(":"), ID, str("="), expr),
		      seq(str("def"), ID, str(":"), ID, str("="), expr),
		      seq(str("type"), ID, str("="), ID));
	
	public final Parser term = choice(
			seq(str("("), expr, str(")")),
			ID,
			NUM);
	
	public final Parser factor = seq(term, opt(rep(seq(str("."), ID, str("("), actuals, str(")")))));
	
	public final Parser classExt = seq(str("extends"), ID, str("("), actuals, str(")"));
	public final Parser classPrefix = seq(string("class"), ID, str("("), formals, str(")"));
	public final Parser clazz = seq(classPrefix, opt(classExt), str("{"), opt(rep(member)), str("}"));
	
	public final Parser program = rep(clazz);
		 
	{
		expr.set(seq(factor, opt(rep(choice(seq(str("+"), factor), seq(str("-"), factor))))));
	}
	
	public SimpleScalaGrammar() {
		init();
	}
}
