package com.googlecode.lingwah.test;

import static com.googlecode.lingwah.Parsers.rep;
import static com.googlecode.lingwah.Parsers.seq;
import static com.googlecode.lingwah.Parsers.str;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.node.Match;
import com.googlecode.lingwah.node.MatchNavigation;

public class MeteorDescriptionLanguageTests
extends TestCase
{
	private static ParseResults match(Parser parser, String input) {
		return ParseContext.match(parser, input);
	}
	

	public void testBasicSyntaxParts() throws Exception {
		ParseResults parseResults;
		
		Parser literalMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.literal;		
		Assert.assertEquals(1, match(literalMatcher, "\"A Customer defined in a Meteor manifest\"").getMatches().size());
		
		Parser urirefMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.uriref;		
		Assert.assertEquals(1, match(urirefMatcher, "<meteor:com.googlecode.meteorlang.TestBindType.instance>").getMatches().size());
		
		Parser subjectMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.subject;		
		Assert.assertEquals(1, match(subjectMatcher, "<meteor:com.googlecode.meteorlang.TestBindType.instance>").getMatches().size());
		Assert.assertEquals(1, match(subjectMatcher, "CustomerFromManifest").getMatches().size());
		
		Parser verbMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.verb;	
		Assert.assertEquals(1, match(verbMatcher, "type").getMatches().size());
		Assert.assertEquals(1, match(verbMatcher, "<meteor:com.googlecode.meteorlang.rdf.Type>").getMatches().size());
		
		Parser objectMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.object;		
		Assert.assertEquals(1, match(objectMatcher, "WebbenchModule").getMatches().size());
		
		Parser objectListMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.objectList;		
		Assert.assertEquals(1, match(objectListMatcher, "WebbenchModule").getMatches().size());
		
		Parser valuesMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.propertyValuePair;		
		Assert.assertEquals(1, match(valuesMatcher, "type WebbenchModule").getMatches().size());
		Assert.assertEquals(1, match(valuesMatcher, "description \"A Customer defined in a Meteor manifest\"").getMatches().size());
		Assert.assertEquals(1, match(valuesMatcher, "type Customer ;\n\t\t\trdf:description \"A Customer defined in a Meteor manifest\"").getMatches().size());
		
		Parser triplesMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.triple;
		String tripleDoc= "<meteor:com.googlecode.meteorlang.TestBindType.instance> type WebbenchModule;"; 
		Assert.assertEquals(1, match(triplesMatcher, tripleDoc).getMatches().size());
		tripleDoc= "CustomerFromManifest type Customer ;\n\t\t\tdescription \"A Customer defined in a Meteor manifest\"";
		Assert.assertEquals(1, match(triplesMatcher, tripleDoc).getMatches().size());
		
		Parser statementMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.statement;
		String statementDoc= tripleDoc+" ."; 
		Assert.assertEquals(1, match(statementMatcher, statementDoc).getMatches().size());
		
		Parser importMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.importt;
		String importDoc= "@import <meteor:com.googlecode.meteorlang.Resource#>;";
		Assert.assertEquals(1, match(importMatcher, importDoc).getMatches().size());
		
		Parser blockTypeMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.blockType;
		String blockTypeDoc= "Customer CustomerFromManifest";
		parseResults= match(blockTypeMatcher, blockTypeDoc);
		Assert.assertEquals(1, parseResults.getMatches().size());
		Assert.assertEquals(blockTypeDoc.length(), parseResults.getMatches().get(0).getEnd());
		
		Parser pvMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.propertyValuePair;
		String pvDoc= "description \"A Customer defined in a Meteor manifest\"";
		parseResults= match(pvMatcher, pvDoc);
		Assert.assertEquals(1, parseResults.getMatches().size());
		Assert.assertEquals(pvDoc.length(), parseResults.getMatches().get(0).getEnd());
		
		Parser blockBodyMatcher= seq(pvMatcher, str(";"));
		String blockBodyDoc= "description \"A Customer defined in a Meteor manifest\";";
		Assert.assertEquals(1, match(blockBodyMatcher, blockBodyDoc).getMatches().size());
		
		blockBodyMatcher= rep(seq(pvMatcher, str(";")));
		blockBodyDoc= "description \"A Customer defined in a Meteor manifest\";";
		parseResults= match(blockBodyMatcher, blockBodyDoc);
		Assert.assertEquals(1, parseResults.getMatches().size());
		
		blockBodyMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.blockBody;
		blockBodyDoc= "description \"A Customer defined in a Meteor manifest\";";
		Assert.assertEquals(1, match(blockBodyMatcher, blockBodyDoc).getMatches().size());
		
		blockBodyMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.blockBody;
		blockBodyDoc= "description \"A Customer defined in a Meteor manifest\" ;";
		Assert.assertEquals(1, match(blockBodyMatcher, blockBodyDoc).getMatches().size());
		
		Parser blockMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.block;
		String blockDoc= "Customer CustomerFromManifest {\n" +
		"			description \"A Customer defined in a Meteor manifest\" ; \n" +
		"}";
		parseResults= match(blockMatcher, blockDoc);
		Assert.assertEquals(1, parseResults.getMatches().size());
		Assert.assertEquals(blockDoc.length(), parseResults.getMatches().get(0).getEnd());
		

		// test nested blocks
		String document= 
				"@base <meteor:cars#>;" +
				"Class PuddleJumper {" +
				"	Class OptionPackageA {}" +
				" 	Class OptionPackageB {}" +
				"	extends Car;" +
				"	interior \"burlap\";" +
				"}";
		parseResults= match(blockMatcher, document);
		Assert.assertEquals(1, parseResults.getMatches().size());
		Assert.assertEquals(document.length(), parseResults.getMatches().get(0).getEnd());
		
		document= 	
			"@import <meteor:com.googlecode.meteorlang.Resource#>;\n" +
			"@import <meteor:com.googlecode.meteorlang.parser.test#>;\n" +
			"\n" +
			"Customer CustomerFromManifest {\n" +
			"			description \"A Customer defined in a Meteor manifest\" ; \n" +
			"}\n\n\n\n\n";
		
		Parser parser= MeteorDescriptionLanguageGrammar.DEFINITION.document;		
		ParseResults results= match(parser, document);
		Assert.assertEquals(1, results.getMatches().size());
		Match rootNode= results.getMatches().get(0);
		
		// the AST should have 3 statements in it
		List<Match> statements= MatchNavigation.findAllByType(rootNode, statementMatcher);
		Assert.assertEquals(3, statements.size());
		

		Parser predicateValuesMatcher= MeteorDescriptionLanguageGrammar.DEFINITION.propertyValuePair;
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
		results= match(parser, document);
		Assert.assertFalse(results.success());
		Assert.assertEquals(27, results.getError().position);
		Assert.assertEquals("Expected whitespace", results.getError().errorMsg);
		
	}
}
