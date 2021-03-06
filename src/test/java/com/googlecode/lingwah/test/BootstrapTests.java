package com.googlecode.lingwah.test;


import static com.googlecode.lingwah.Parsers.anyChar;
import static com.googlecode.lingwah.Parsers.choice;
import static com.googlecode.lingwah.Parsers.excluding;
import static com.googlecode.lingwah.Parsers.opt;
import static com.googlecode.lingwah.Parsers.rep;
import static com.googlecode.lingwah.Parsers.seq;
import static com.googlecode.lingwah.Parsers.str;

import java.math.BigDecimal;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.Match;
import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.Parsers;
import com.googlecode.lingwah.parser.ChoiceParser;
import com.googlecode.lingwah.parser.FirstParser;
import com.googlecode.lingwah.parser.RepetitionParser;
import com.googlecode.lingwah.parser.StringParser;
import com.googlecode.lingwah.util.MatchNavigation;

public class BootstrapTests
extends TestCase
{
	public static class ExprGrammar extends Grammar {
		public static final ExprGrammar INSTANCE= new ExprGrammar();
		
		public final Parser num= range('0', '9');
		public final ChoiceParser expr= choice();
		{
			expr.addChoice(num);
			expr.addChoice(seq(expr, str("-"), expr));
		}
		
		private ExprGrammar() {
			init();
		}
	}
	public static Parser four= Parsers.str("4");
	public static Parser three= Parsers.str("3");
	
	public void testBasics() throws Exception {
		String txt;
		ParseContext ctx;
		Parser parser; 
		ParseResults results; 
		
		// test a terminal
		txt= "4-3";
		ctx= new ParseContext(txt);
		parser= new StringParser("4-3"); 
		results= ctx.getParseResults(parser, 0); 
		assertTrue(results.success());
		assertEquals(txt.length(), results.longestLength());
		
		// test a sequence
		txt= "43";
		ctx= new ParseContext(txt);
		parser= seq(str("4"), str("3")); 
		results= ctx.getParseResults(parser, 0); 
		assertTrue(results.success());
		assertEquals(txt.length(), results.longestLength());
		
		// test a choice
		txt= "4";
		ctx= new ParseContext(txt);
		parser= choice(four, three); 
		results= ctx.getParseResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		List<Match> nodes= MatchNavigation.findAllByType(results.getLongestMatch(), four);
		Assert.assertEquals(1, nodes.size());
		
		txt= "3";
		ctx= new ParseContext(txt);
		parser= choice(four, three); 
		results= ctx.getParseResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		// test repetition
		
		txt= "3";
		ctx= new ParseContext(txt);
		parser= rep(str("3")); 
		results= ctx.getParseResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());

		txt= "3";
		ctx= new ParseContext(txt);
		parser= opt(rep(str("3"))); 
		results= ctx.getParseResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "43";
		ctx= new ParseContext(txt);
		parser= seq(str("4"), opt(rep(str("3")))); 
		results= ctx.getParseResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		assertEquals(2, results.getMatches().size());

		txt= "4";
		ctx= new ParseContext(txt);
		Parser four= new StringParser("4");
		four.setLabel("FOUR");
		parser= new RepetitionParser(new ChoiceParser(four, four), false); 
		results= ctx.getParseResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "4";
		parser=seq(four, opt(four));
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "4";
		Parser COMMA= new StringParser(",");
		COMMA.setLabel("COMMA");
		parser=seq(four, opt(rep(seq(opt(str("3")), COMMA, four, opt(str("3"))))));
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());

		// optional sequences should produce a 'success' result and return one
		// match of zero length
		txt= "";
		ctx= new ParseContext(txt);
		parser= opt(rep(str("3"))); 
		results= ctx.getParseResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "4";
		ctx= new ParseContext(txt);
		parser= seq(four, opt(rep(seq(opt(str("2")), four)))); 
		results= ctx.getParseResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		txt= " ";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(opt(rep(choice(str(" "), str("\t")))), 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "T";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(anyChar(), 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "T";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(excluding(anyChar(), four), 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		// test first 
		txt= "4";
		ctx= new ParseContext(txt);
		parser= new FirstParser(three, four); 
		results= ctx.getParseResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
	}
	
	
	public void testLeftRecursion() throws Exception {
		
		String txt= "4-3";
		ParseContext ctx= new ParseContext(txt);
		ParseResults results= ctx.getParseResults(ExprGrammar.INSTANCE.expr, 0); 
		assertTrue(results.success());
		assertEquals(txt.length(), results.longestLength());
	}
	
	public void testCalculator() throws Exception {
		String txt;
		ParseContext ctx;
		ParseResults results; 
		CalculatorGrammar grammar= CalculatorGrammar.INSTANCE;

		txt= "9";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(grammar.decimal, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());

		txt= "9*9";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(grammar.decimal, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(1, results.longestLength());

		txt= "9*9";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(grammar.multiplication, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());

		txt= "9*9";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(grammar.expr, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "9+9";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(grammar.addition, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "9+9";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(Parsers.first(grammar.multiplication, grammar.division, grammar.addition, grammar.subtraction), 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "9+9";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(grammar.expr, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "6543.56";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(grammar.expr, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "1+6543.56";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(grammar.addition, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "1+6543.56";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(Parsers.first(grammar.addition), 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "1+6543.56";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(grammar.expr, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "6543.56-1";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(grammar.expr, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "6543.56 - 1";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(grammar.expr, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "(6543.56 - 1)";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(grammar.expr, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "(6543.56 - 1)/ 3.14159265";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(grammar.expr, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());
		
		txt= "(6543.56 - 1)/ /*Pi*/3.14159265";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(grammar.expr, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(txt.length(), results.longestLength());

		assertEquals(new BigDecimal("81"), Calculator.parse("9 * 9"));
		assertEquals(new BigDecimal("41"), Calculator.parse("1 + 5 * 8"));
		assertEquals(new BigDecimal("2082.5615313302951609592032881793"), Calculator.parse("(6543.56 - 1)/ /*Pi*/3.14159265"));
	}
	
	
}
