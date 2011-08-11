package com.googlecode.lingwah.test;


import static com.googlecode.lingwah.Parsers.anyChar;
import static com.googlecode.lingwah.Parsers.choice;
import static com.googlecode.lingwah.Parsers.excluding;
import static com.googlecode.lingwah.Parsers.opt;
import static com.googlecode.lingwah.Parsers.rep;
import static com.googlecode.lingwah.Parsers.seq;
import static com.googlecode.lingwah.Parsers.str;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.Parsers;
import com.googlecode.lingwah.node.Match;
import com.googlecode.lingwah.node.MatchNavigation;
import com.googlecode.lingwah.parser.ChoiceParser;
import com.googlecode.lingwah.parser.RepetitionParser;
import com.googlecode.lingwah.parser.StringParser;

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
		results= ctx.getMatchResults(parser, 0); 
		assertTrue(results.success());
		assertEquals(results.longestLength(), txt.length());
		
		// test a sequence
		txt= "43";
		ctx= new ParseContext(txt);
		parser= seq(str("4"), str("3")); 
		results= ctx.getMatchResults(parser, 0); 
		assertTrue(results.success());
		assertEquals(results.longestLength(), txt.length());
		
		// test a choice
		txt= "4";
		ctx= new ParseContext(txt);
		parser= choice(four, three); 
		results= ctx.getMatchResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		List<Match> nodes= MatchNavigation.findAllByType(results.getLongestMatch(), four);
		Assert.assertEquals(1, nodes.size());
		
		txt= "3";
		ctx= new ParseContext(txt);
		parser= choice(four, three); 
		results= ctx.getMatchResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		// test repetition
		
		txt= "3";
		ctx= new ParseContext(txt);
		parser= rep(str("3")); 
		results= ctx.getMatchResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());

		txt= "3";
		ctx= new ParseContext(txt);
		parser= opt(rep(str("3"))); 
		results= ctx.getMatchResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		txt= "43";
		ctx= new ParseContext(txt);
		parser= seq(str("4"), opt(rep(str("3")))); 
		results= ctx.getMatchResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		assertEquals(2, results.getMatches().size());

		txt= "4";
		ctx= new ParseContext(txt);
		Parser four= new StringParser("4");
		four.setLabel("FOUR");
		parser= new RepetitionParser(new ChoiceParser(new Parser[] { four, four })); 
		results= ctx.getMatchResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		txt= "4";
		parser=seq(four, opt(four));
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		txt= "4";
		Parser COMMA= new StringParser(",");
		COMMA.setLabel("COMMA");
		parser=seq(four, opt(rep(seq(opt(str("3")), COMMA, four, opt(str("3"))))));
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());

		// optional sequences should produce a 'success' result and return one
		// match of zero length
		txt= "";
		ctx= new ParseContext(txt);
		parser= opt(rep(str("3"))); 
		results= ctx.getMatchResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		txt= "4";
		ctx= new ParseContext(txt);
		parser= seq(four, opt(rep(seq(opt(str("2")), four)))); 
		results= ctx.getMatchResults(parser, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		txt= " ";
		ctx= new ParseContext(txt);
		results= ctx.getMatchResults(opt(rep(choice(str(" "), str("\t")))), 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		txt= "T";
		ctx= new ParseContext(txt);
		results= ctx.getMatchResults(anyChar(), 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		txt= "T";
		ctx= new ParseContext(txt);
		results= ctx.getMatchResults(excluding(anyChar(), four), 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
	}
	
	
	public void testLeftRecursion() throws Exception {
		
		String txt= "4-3";
		ParseContext ctx= new ParseContext(txt);
		ParseResults results= ctx.getMatchResults(ExprGrammar.INSTANCE.expr, 0); 
		assertTrue(results.success());
		assertEquals(results.longestLength(), txt.length());
	}
	
}
