package com.googlecode.lingwah.test;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.Matcher;
import com.googlecode.lingwah.Parsers;
import com.googlecode.lingwah.grammars.TurtleGrammar;
import com.googlecode.lingwah.node.Match;
import com.googlecode.lingwah.node.MatchNavigation;
import com.googlecode.lingwah.node.MatchUtils;

public class TurtleTests
extends TestCase
{
	private static MatchResults match(Matcher matcher, String input) {
		MatchResults results= MatchContext.match(matcher, input);
		Assert.assertEquals(results.getContext().getInput().length(), results.longestLength());
		return results;
	}
	
	public void testBasicTriples() throws Exception {
		TurtleGrammar turtle= TurtleGrammar.DEFINITION;
		MatchResults results;
		//Assert.assertEquals(1, match(Parsers.seq(turtle.OBJECT, Parsers.opt(Parsers.str(","))), "webbench:WebbenchModule").getMatches().size());
		
		String document= 	
			"@prefix resource: <meteor:com.googlecode.meteorframework.Resource.>.\n" +
			"@prefix test: <meteor:com.googlecode.meteorlang.parser.test.>.\n" +
			"\n" +
			"test:CustomerFromManifest resource:type test:Customer ;\n" +
			"			resource:description \"A Customer defined in a Meteor manifest\" .\n\n\n\n\n";
		
		Matcher literalMatcher= turtle.LITERAL;		
		Assert.assertEquals(1, match(literalMatcher, "\"A Customer defined in a Meteor manifest\"").getMatches().size());
		
		Matcher urirefMatcher= turtle.URIREF;		
		Assert.assertEquals(1, match(urirefMatcher, "<meteor:com.googlecode.meteorframework.TestBindType.instance>").getMatches().size());
		
		Matcher subjectMatcher= turtle.SUBJECT;		
		match(subjectMatcher, "<meteor:com.googlecode.meteorframework.TestBindType.instance>");
		match(subjectMatcher, "test:CustomerFromManifest");
		
		Matcher verbMatcher= turtle.VERB;	
		match(verbMatcher, "resource:type");
		
		match(turtle.RESOURCE, "webbench:WebbenchModule");
		match(turtle.OBJECT, "webbench:WebbenchModule");
		match(Parsers.seq(turtle.OBJECT, Parsers.opt(Parsers.rep(Parsers.str(",")))), "webbench:WebbenchModule");
		match(turtle.OBJECT_LIST, "webbench:WebbenchModule");
		
		Matcher valuesMatcher= turtle.PREDICATE_VALUES;		
		match(valuesMatcher, "resource:type webbench:WebbenchModule");
		match(valuesMatcher, "resource:description \"A Customer defined in a Meteor manifest\"");
		
		Matcher triplesMatcher= turtle.TRIPLES;
		String tripleDoc= "<meteor:com.googlecode.meteorframework.TestBindType.instance> resource:type webbench:WebbenchModule"; 
		match(triplesMatcher, tripleDoc);
		tripleDoc= "test:CustomerFromManifest resource:type test:Customer ;\n\t\t\tresource:description \"A Customer defined in a Meteor manifest\"";
		match(triplesMatcher, tripleDoc);
		
		Matcher statementMatcher= turtle.STATEMENT;
		String statementDoc= tripleDoc+" ."; 
		match(statementMatcher, statementDoc);
		
		Matcher documentMatcher= turtle.DOCUMENT;		
		results= match(documentMatcher, document);
		Match rootNode= results.getLongestMatch();
		
		// the AST should have 3 statements in it
		List<Match> nodes= MatchNavigation.findAllByType(rootNode, statementMatcher);
		Assert.assertEquals(3, nodes.size());
		// .. 2 directives
		nodes= MatchNavigation.findAllByType(rootNode, turtle.DIRECTIVE);
		System.out.println("------- xml --------------\n"+MatchUtils.toXML(rootNode));
		Assert.assertEquals(2, nodes.size());
		// .. 1 TRIPLES
		nodes= MatchNavigation.findAllByType(rootNode, turtle.TRIPLES);
		Assert.assertEquals(1, nodes.size());
		

		document= "meteor:Resource.type jdbc:JDBCDriverDescriptor"; 
		results= MatchContext.match(turtle.PREDICATE_VALUES, document);
		Assert.assertFalse(results.success());
		Assert.assertEquals(15, results.getError().position);
		Assert.assertEquals("Expected whitespace", results.getError().errorMsg);
		

		document= "h2:H2Driver meteor:Resource.type jdbc:JDBCDriverDescriptor"; 
		results= MatchContext.match(triplesMatcher, document);
		Assert.assertFalse(results.success());
		Assert.assertEquals(27, results.getError().position);
		Assert.assertEquals("Expected whitespace", results.getError().errorMsg);
		

		document= "h2:H2Driver meteor:Resource.type jdbc:JDBCDriverDescriptor."; 
		results= MatchContext.match(statementMatcher, document);
		Assert.assertFalse(results.success());
		Assert.assertEquals(27, results.getError().position);
		Assert.assertEquals("Expected whitespace", results.getError().errorMsg);
		
		document= "h2:H2Driver meteor:Resource.type jdbc:JDBCDriverDescriptor.\n\n\n\n\n\n\n";
		results= MatchContext.match(documentMatcher, document);
		Assert.assertFalse(results.success());
		
		document= "h2:H2Driver meteor:Resource.type jdbc:JDBCDriverDescriptor ;\n" +
			"jdbc:JDBCDriverDescriptor.protocol \"jdbc:h2\" ;\n" +
			"jdbc:JDBCDriverDescriptor.driverClass \"org.h2.Driver\" .\n\n\n\n\n\n\n"; 
		results= MatchContext.match(documentMatcher, document);
		Assert.assertFalse(results.success());
		Assert.assertEquals(27, results.getError().position);
		Assert.assertEquals("Expected whitespace", results.getError().errorMsg);
		
	}
	
}
