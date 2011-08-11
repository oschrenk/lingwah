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
import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.Matcher;
import com.googlecode.lingwah.Parsers;
import com.googlecode.lingwah.matcher.ChoiceMatcher;
import com.googlecode.lingwah.matcher.RepetitionMatcher;
import com.googlecode.lingwah.matcher.StringMatcher;
import com.googlecode.lingwah.node.Match;
import com.googlecode.lingwah.node.MatchNavigation;

public class BootstrapTests
extends TestCase
{
	public static class ExprGrammar extends Grammar {
		public static final ExprGrammar INSTANCE= new ExprGrammar();
		
		public final Matcher num= range('0', '9');
		public final ChoiceMatcher expr= choice();
		{
			expr.addChoice(num);
			expr.addChoice(seq(expr, str("-"), expr));
		}
		
		private ExprGrammar() {
			init();
		}
	}
	public static Matcher four= Parsers.str("4");
	public static Matcher three= Parsers.str("3");
	
	public void testBasics() throws Exception {
		String txt;
		MatchContext ctx;
		Matcher matcher; 
		MatchResults results; 
		
		// test a terminal
		txt= "4-3";
		ctx= new MatchContext(txt);
		matcher= new StringMatcher("4-3"); 
		results= ctx.getMatchResults(matcher, 0); 
		assertTrue(results.success());
		assertEquals(results.longestLength(), txt.length());
		
		// test a sequence
		txt= "43";
		ctx= new MatchContext(txt);
		matcher= seq(str("4"), str("3")); 
		results= ctx.getMatchResults(matcher, 0); 
		assertTrue(results.success());
		assertEquals(results.longestLength(), txt.length());
		
		// test a choice
		txt= "4";
		ctx= new MatchContext(txt);
		matcher= choice(four, three); 
		results= ctx.getMatchResults(matcher, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		List<Match> nodes= MatchNavigation.findAllByType(results.getLongestMatch(), four);
		Assert.assertEquals(1, nodes.size());
		
		txt= "3";
		ctx= new MatchContext(txt);
		matcher= choice(four, three); 
		results= ctx.getMatchResults(matcher, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		// test repetition
		
		txt= "3";
		ctx= new MatchContext(txt);
		matcher= rep(str("3")); 
		results= ctx.getMatchResults(matcher, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());

		txt= "3";
		ctx= new MatchContext(txt);
		matcher= opt(rep(str("3"))); 
		results= ctx.getMatchResults(matcher, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		txt= "43";
		ctx= new MatchContext(txt);
		matcher= seq(str("4"), opt(rep(str("3")))); 
		results= ctx.getMatchResults(matcher, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		assertEquals(2, results.getMatches().size());

		txt= "4";
		ctx= new MatchContext(txt);
		Matcher four= new StringMatcher("4");
		four.setLabel("FOUR");
		matcher= new RepetitionMatcher(new ChoiceMatcher(new Matcher[] { four, four })); 
		results= ctx.getMatchResults(matcher, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		txt= "4";
		matcher=seq(four, opt(four));
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		txt= "4";
		Matcher COMMA= new StringMatcher(",");
		COMMA.setLabel("COMMA");
		matcher=seq(four, opt(rep(seq(opt(str("3")), COMMA, four, opt(str("3"))))));
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());

		// optional sequences should produce a 'success' result and return one
		// match of zero length
		txt= "";
		ctx= new MatchContext(txt);
		matcher= opt(rep(str("3"))); 
		results= ctx.getMatchResults(matcher, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		txt= "4";
		ctx= new MatchContext(txt);
		matcher= seq(four, opt(rep(seq(opt(str("2")), four)))); 
		results= ctx.getMatchResults(matcher, 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		txt= " ";
		ctx= new MatchContext(txt);
		results= ctx.getMatchResults(opt(rep(choice(str(" "), str("\t")))), 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		txt= "T";
		ctx= new MatchContext(txt);
		results= ctx.getMatchResults(anyChar(), 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
		txt= "T";
		ctx= new MatchContext(txt);
		results= ctx.getMatchResults(excluding(anyChar(), four), 0); 
		assertTrue(results.getErrorMessage(), results.success());
		assertEquals(results.longestLength(), txt.length());
		
	}
	
	
	public void testLeftRecursion() throws Exception {
		
		String txt= "4-3";
		MatchContext ctx= new MatchContext(txt);
		MatchResults results= ctx.getMatchResults(ExprGrammar.INSTANCE.expr, 0); 
		assertTrue(results.success());
		assertEquals(results.longestLength(), txt.length());
	}
	
}
