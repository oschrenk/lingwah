package com.googlecode.lingwah.test;

import java.util.HashMap;

import com.googlecode.lingwah.Document;
import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.parser.ParserReference;
import com.googlecode.lingwah.parser.TerminalParser;
import com.googlecode.lingwah.parser.common.BooleanParser;
import com.googlecode.lingwah.parser.common.DecimalParser;
import com.googlecode.lingwah.parser.common.DoubleParser;
import com.googlecode.lingwah.parser.common.IntegerParser;
import com.googlecode.lingwah.parser.common.NameParser;
import com.googlecode.lingwah.parser.common.QuotedStringParser;
import com.googlecode.lingwah.parser.common.SimpleBlockParser;
import com.googlecode.lingwah.parser.common.SingleLineCommentParser;
import com.googlecode.lingwah.parser.common.WhitespaceParser;

/**
 * A grammar for the Meteor Description Language.
 * 
 * @author Ted Stockwell
 */
public class MeteorDescriptionLanguageGrammar extends Grammar
{
	public static MeteorDescriptionLanguageGrammar DEFINITION= new MeteorDescriptionLanguageGrammar();
	
	static final String UTF8= "UTF-8";

	public static class URIRefMatcher extends TerminalParser 
	{
		@Override
		public void startMatching(ParseContext ctx, int start, ParseResults parseResults) 
		{
			final Document input= ctx.getDocument();
			if (input.length() <= start || input.charAt(start) != '<') {
				parseResults.setError("Expected '<'");
				return;
			}
			
			int pos = input.indexOf('>', start);
			if (pos < 0) {
				parseResults.setError("End of URI reference not found");
				return;
			}
			
			pos++;
			parseResults.addMatch(pos);
		}
		
		@Override
		public String getDefaultLabel()
		{
			return "URI reference";
		}
	}
	
	static HashMap<String, Parser> __matchersById= new HashMap<String, Parser>(); 
	
	
	
	public final Parser lower = range('a', 'z');
	public final Parser digit = range('0', '9');
	public final Parser singleLineComment= new SingleLineCommentParser("//");
	public final Parser multiLineComment= new SimpleBlockParser("/*", "*/");
	public final Parser ws = rep(choice(new WhitespaceParser(), singleLineComment, multiLineComment));
	public final Parser optws = opt(ws);
	public final Parser name= new NameParser();
		
	public final Parser quotedString= new QuotedStringParser();
	public final Parser nodeID= seq(str("_:"), name);
	public final Parser prefixName= name;
	public final Parser qname= cho( seq(prefixName,str(":"),name), seq(str(":"),name), str(":"));
	public final Parser uriref= new URIRefMatcher();
	public final Parser resource= cho(name, uriref, qname);
	public final Parser subject= cho(resource, nodeID, str("[]"));
	public final Parser base= seq(str("@base"), ws, uriref);
	public final Parser importt= seq(str("@import"), ws, opt(seq(prefixName, str(":"), ws)), uriref);
	public final Parser bool= new BooleanParser();
	public final Parser decimal= new DecimalParser();
	public final Parser doubl= new DoubleParser();
	public final Parser integer= new IntegerParser();
	public final Parser datatypeString= seq(quotedString, str("^^"), resource);
	public final Parser language= seq(rep(lower), opt(rep(seq(str("-"), rep(seq(cho(lower, digit)))))));
	public final Parser literal= cho(seq(quotedString, opt(seq(str("@"), language))), datatypeString, integer, doubl, decimal, bool);
	public final Parser object= cho(resource, literal, nodeID, str("[]"));
	public final Parser objectList= seq(object, opt(rep(seq(optws, str(","), optws, object))));
	public final Parser verb= choice(resource, str("a"));
	public final Parser propertyValuePair= seq(verb, ws, objectList);
	public final Parser triple= seq(subject, ws, propertyValuePair, optws, str(";"));
	public final Parser directive= seq(cho(importt, base), optws, str(";"));
		
	public final Parser blockType= cho( seq(resource, ws, resource), seq(str("@external"), ws, resource), resource);
	public final ParserReference blockBody= ref(rep(seq(propertyValuePair, optws, str(";"))));
	public final Parser block= seq(blockType, optws, str("{"), optws, blockBody, optws, str("}"));
	{
		blockBody.set(seq(cho(block, blockBody.getDefinition()), opt(rep(seq(optws, cho(block, blockBody.getDefinition())))))); // blocks can be nested
	}
		
	public final Parser statement= seq(cho(directive, triple, block));
	public final Parser document= seq(optws, rep(seq(statement, optws)));
	
	
	private MeteorDescriptionLanguageGrammar() {
		init();
	}
}
