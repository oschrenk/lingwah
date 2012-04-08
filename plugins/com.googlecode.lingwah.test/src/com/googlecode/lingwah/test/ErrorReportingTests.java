package com.googlecode.lingwah.test;


import junit.framework.TestCase;

import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.Parsers;
import com.googlecode.lingwah.parser.ChoiceParser;
import com.googlecode.lingwah.parser.StringParser;

/**
 * Tests used to improve ligwah's error reporting.
 * @author ted stockwell
 *
 */
public class ErrorReportingTests
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
		txt= "5";
		ctx= new ParseContext(txt);
		parser= four; 
		results= ctx.getParseResults(parser, 0); 
		assertFalse(results.success());
		assertEquals("Expected '4'", results.getErrorMessage());
		
		// test sequence
		parser= Parsers.seq(four, four);
		txt= "45";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(parser, 0); 
		assertFalse(results.success());
		assertNotNull(results.getError());
		assertEquals(1, results.getError().position);
		assertEquals("Expected '4'", results.getErrorMessage());
		
		// test choice
		parser= Parsers.choice(three, four);
		txt= "5";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(parser, 0); 
		assertFalse(results.success());
		assertNotNull(results.getError());
		assertEquals(0, results.getError().position);
		assertEquals("Expected '3'", results.getErrorMessage());
		
		// test repetition
		parser= Parsers.rep(four);
		txt= "45";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(parser, 0); 
		assertTrue(results.success()); // the test succeeds but does not match the entire input.
		assertEquals(1, results.longestLength());
		assertNotNull(results.getError()); // however, there is an error that denotes where the last match failed.
		assertEquals(1, results.getError().position);
		assertEquals("Expected '4'", results.getErrorMessage());
		
		// test optional
		parser= Parsers.opt(four);
		txt= "5";
		ctx= new ParseContext(txt);
		results= ctx.getParseResults(parser, 0); 
		assertTrue(results.success()); // the test succeeds but does not match the entire input.
		assertEquals(0, results.longestLength());
		assertNotNull(results.getError()); // however, there is an error that denotes where the last match failed.
		assertEquals(0, results.getError().position);
		assertEquals("Expected '4'", results.getErrorMessage());
	}
	
	
}
