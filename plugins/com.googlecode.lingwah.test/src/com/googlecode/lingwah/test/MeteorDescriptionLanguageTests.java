package com.googlecode.lingwah.test;

import static com.googlecode.lingwah.Parsers.rep;
import static com.googlecode.lingwah.Parsers.seq;
import static com.googlecode.lingwah.Parsers.str;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.Matcher;
import com.googlecode.lingwah.node.Match;
import com.googlecode.lingwah.node.MatchNavigation;

public class MeteorDescriptionLanguageTests
extends TestCase
{
	private static MatchResults match(Matcher matcher, String input) {
		return MatchContext.match(matcher, input);
	}
	

	public void testBasicSyntaxParts() throws Exception {
		MatchResults matchResults;
		
		Matcher literalMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.literal;		
		Assert.assertEquals(1, match(literalMatcher, "\"A Customer defined in a Meteor manifest\"").getMatches().size());
		
		Matcher urirefMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.uriref;		
		Assert.assertEquals(1, match(urirefMatcher, "<meteor:com.googlecode.meteorlang.TestBindType.instance>").getMatches().size());
		
		Matcher subjectMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.subject;		
		Assert.assertEquals(1, match(subjectMatcher, "<meteor:com.googlecode.meteorlang.TestBindType.instance>").getMatches().size());
		Assert.assertEquals(1, match(subjectMatcher, "CustomerFromManifest").getMatches().size());
		
		Matcher verbMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.verb;	
		Assert.assertEquals(1, match(verbMatcher, "type").getMatches().size());
		Assert.assertEquals(1, match(verbMatcher, "<meteor:com.googlecode.meteorlang.rdf.Type>").getMatches().size());
		
		Matcher objectMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.object;		
		Assert.assertEquals(1, match(objectMatcher, "WebbenchModule").getMatches().size());
		
		Matcher objectListMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.objectList;		
		Assert.assertEquals(1, match(objectListMatcher, "WebbenchModule").getMatches().size());
		
		Matcher valuesMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.propertyValuePair;		
		Assert.assertEquals(1, match(valuesMatcher, "type WebbenchModule").getMatches().size());
		Assert.assertEquals(1, match(valuesMatcher, "description \"A Customer defined in a Meteor manifest\"").getMatches().size());
		Assert.assertEquals(1, match(valuesMatcher, "type Customer ;\n\t\t\trdf:description \"A Customer defined in a Meteor manifest\"").getMatches().size());
		
		Matcher triplesMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.triple;
		String tripleDoc= "<meteor:com.googlecode.meteorlang.TestBindType.instance> type WebbenchModule;"; 
		Assert.assertEquals(1, match(triplesMatcher, tripleDoc).getMatches().size());
		tripleDoc= "CustomerFromManifest type Customer ;\n\t\t\tdescription \"A Customer defined in a Meteor manifest\"";
		Assert.assertEquals(1, match(triplesMatcher, tripleDoc).getMatches().size());
		
		Matcher statementMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.statement;
		String statementDoc= tripleDoc+" ."; 
		Assert.assertEquals(1, match(statementMatcher, statementDoc).getMatches().size());
		
		Matcher importMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.importt;
		String importDoc= "@import <meteor:com.googlecode.meteorlang.Resource#>;";
		Assert.assertEquals(1, match(importMatcher, importDoc).getMatches().size());
		
		Matcher blockTypeMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.blockType;
		String blockTypeDoc= "Customer CustomerFromManifest";
		matchResults= match(blockTypeMatcher, blockTypeDoc);
		Assert.assertEquals(1, matchResults.getMatches().size());
		Assert.assertEquals(blockTypeDoc.length(), matchResults.getMatches().get(0).getEnd());
		
		Matcher pvMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.propertyValuePair;
		String pvDoc= "description \"A Customer defined in a Meteor manifest\"";
		matchResults= match(pvMatcher, pvDoc);
		Assert.assertEquals(1, matchResults.getMatches().size());
		Assert.assertEquals(pvDoc.length(), matchResults.getMatches().get(0).getEnd());
		
		Matcher blockBodyMatcher= seq(pvMatcher, str(";"));
		String blockBodyDoc= "description \"A Customer defined in a Meteor manifest\";";
		Assert.assertEquals(1, match(blockBodyMatcher, blockBodyDoc).getMatches().size());
		
		blockBodyMatcher= rep(seq(pvMatcher, str(";")));
		blockBodyDoc= "description \"A Customer defined in a Meteor manifest\";";
		matchResults= match(blockBodyMatcher, blockBodyDoc);
		Assert.assertEquals(1, matchResults.getMatches().size());
		
		blockBodyMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.blockBody;
		blockBodyDoc= "description \"A Customer defined in a Meteor manifest\";";
		Assert.assertEquals(1, match(blockBodyMatcher, blockBodyDoc).getMatches().size());
		
		blockBodyMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.blockBody;
		blockBodyDoc= "description \"A Customer defined in a Meteor manifest\" ;";
		Assert.assertEquals(1, match(blockBodyMatcher, blockBodyDoc).getMatches().size());
		
		Matcher blockMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.block;
		String blockDoc= "Customer CustomerFromManifest {\n" +
		"			description \"A Customer defined in a Meteor manifest\" ; \n" +
		"}";
		matchResults= match(blockMatcher, blockDoc);
		Assert.assertEquals(1, matchResults.getMatches().size());
		Assert.assertEquals(blockDoc.length(), matchResults.getMatches().get(0).getEnd());
		

		// test nested blocks
		String document= 
				"@base <meteor:cars#>;" +
				"Class PuddleJumper {" +
				"	Class OptionPackageA {}" +
				" 	Class OptionPackageB {}" +
				"	extends Car;" +
				"	interior \"burlap\";" +
				"}";
		matchResults= match(blockMatcher, document);
		Assert.assertEquals(1, matchResults.getMatches().size());
		Assert.assertEquals(document.length(), matchResults.getMatches().get(0).getEnd());
		
		document= 	
			"@import <meteor:com.googlecode.meteorlang.Resource#>;\n" +
			"@import <meteor:com.googlecode.meteorlang.parser.test#>;\n" +
			"\n" +
			"Customer CustomerFromManifest {\n" +
			"			description \"A Customer defined in a Meteor manifest\" ; \n" +
			"}\n\n\n\n\n";
		
		Matcher matcher= MeteorDescriptionLanguageGrammar.DEFINITION.document;		
		MatchResults results= match(matcher, document);
		Assert.assertEquals(1, results.getMatches().size());
		Match rootNode= results.getMatches().get(0);
		
		// the AST should have 3 statements in it
		List<Match> statements= MatchNavigation.findAllByType(rootNode, statementMatcher);
		Assert.assertEquals(3, statements.size());
		

		Matcher predicateValuesMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.propertyValuePair;
		document= "meteor:Resource.type jdbc:JDBCDriverDescriptor"; 
		results= match(predicateValuesMatcher, document);
		Assert.assertFalse(results.success());
		Assert.assertEquals(15, results.getError().position);
		Assert.assertEquals("Expected whitespace", results.getError().errorMsg);
		

		document= "h2:H2Driver meteor:Resource.type jdbc:JDBCDriverDescriptor"; 
		results= match(triplesMatcher, document);
		Assert.assertFalse(results.success());
		Assert.assertEquals(27, results.getError().position);
		Assert.assertEquals("Expected whitespace", results.getError().errorMsg);
		

		document= "h2:H2Driver meteor:Resource.type jdbc:JDBCDriverDescriptor."; 
		results= match(statementMatcher, document);
		Assert.assertFalse(results.success());
		Assert.assertEquals(27, results.getError().position);
		Assert.assertEquals("Expected whitespace", results.getError().errorMsg);
		
		document= "h2:H2Driver meteor:Resource.type jdbc:JDBCDriverDescriptor ;\n" +
			"jdbc:JDBCDriverDescriptor.protocol \"jdbc:h2\" ;\n" +
			"jdbc:JDBCDriverDescriptor.driverClass \"org.h2.Driver\" .\n\n\n\n\n\n\n"; 
		results= match(matcher, document);
		Assert.assertFalse(results.success());
		Assert.assertEquals(27, results.getError().position);
		Assert.assertEquals("Expected whitespace", results.getError().errorMsg);
		
	}
}
