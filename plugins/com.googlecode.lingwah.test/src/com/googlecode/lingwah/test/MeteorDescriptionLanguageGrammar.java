package com.googlecode.lingwah.test;

import java.util.HashMap;

import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.MatchContext;
import com.googlecode.lingwah.MatchResults;
import com.googlecode.lingwah.Matcher;
import com.googlecode.lingwah.matcher.MutableMatcher;
import com.googlecode.lingwah.matcher.TerminalMatcher;
import com.googlecode.lingwah.matcher.common.BooleanMatcher;
import com.googlecode.lingwah.matcher.common.DecimalMatcher;
import com.googlecode.lingwah.matcher.common.DoubleMatcher;
import com.googlecode.lingwah.matcher.common.IntegerMatcher;
import com.googlecode.lingwah.matcher.common.NameMatcher;
import com.googlecode.lingwah.matcher.common.QuotedStringMatcher;
import com.googlecode.lingwah.matcher.common.SimpleBlockMatcher;
import com.googlecode.lingwah.matcher.common.SingleLineCommentMatcher;
import com.googlecode.lingwah.matcher.common.WhitespaceMatcher;

/**
 * A grammar for the Meteor Description Language.
 * 
 * @author Ted Stockwell
 */
public class MeteorDescriptionLanguageGrammar extends Grammar
{
	public static MeteorDescriptionLanguageGrammar DEFINITION= new MeteorDescriptionLanguageGrammar();
	
	static final String UTF8= "UTF-8";

	public static class URIRefMatcher extends TerminalMatcher 
	{
		@Override
		public void startMatching(MatchContext ctx, int start, MatchResults matchResults) 
		{
			final String input= ctx.getInput();
			if (input.length() <= start || input.charAt(start) != '<') {
				matchResults.setError("Expected '<'");
				return;
			}
			
			int pos = input.indexOf('>', start);
			if (pos < 0) {
				matchResults.setError("End of URI reference not found");
				return;
			}
			
			pos++;
			matchResults.addMatch(pos);
		}
		
		@Override
		public String getDefaultLabel()
		{
			return "URI reference";
		}
	}
	
	static HashMap<String, Matcher> __matchersById= new HashMap<String, Matcher>(); 
	
	
	
	public final Matcher lower = range('a', 'z');
	public final Matcher digit = range('0', '9');
	public final Matcher singleLineComment= new SingleLineCommentMatcher("//");
	public final Matcher multiLineComment= new SimpleBlockMatcher("/*", "*/");
	public final Matcher ws = rep(choice(new WhitespaceMatcher(), singleLineComment, multiLineComment));
	public final Matcher optws = opt(ws);
	public final Matcher name= new NameMatcher();
		
	public final Matcher quotedString= new QuotedStringMatcher();
	public final Matcher nodeID= seq(str("_:"), name);
	public final Matcher prefixName= name;
	public final Matcher qname= cho( seq(prefixName,str(":"),name), seq(str(":"),name), str(":"));
	public final Matcher uriref= new URIRefMatcher();
	public final Matcher resource= cho(name, uriref, qname);
	public final Matcher subject= cho(resource, nodeID, str("[]"));
	public final Matcher base= seq(str("@base"), ws, uriref);
	public final Matcher importt= seq(str("@import"), ws, opt(seq(prefixName, str(":"), ws)), uriref);
	public final Matcher bool= new BooleanMatcher();
	public final Matcher decimal= new DecimalMatcher();
	public final Matcher doubl= new DoubleMatcher();
	public final Matcher integer= new IntegerMatcher();
	public final Matcher datatypeString= seq(quotedString, str("^^"), resource);
	public final Matcher language= seq(rep(lower), opt(rep(seq(str("-"), rep(seq(cho(lower, digit)))))));
	public final Matcher literal= cho(seq(quotedString, opt(seq(str("@"), language))), datatypeString, integer, doubl, decimal, bool);
	public final Matcher object= cho(resource, literal, nodeID, str("[]"));
	public final Matcher objectList= seq(object, opt(rep(seq(optws, str(","), optws, object))));
	public final Matcher verb= choice(resource, str("a"));
	public final Matcher propertyValuePair= seq(verb, ws, objectList);
	public final Matcher triple= seq(subject, ws, propertyValuePair, optws, str(";"));
	public final Matcher directive= seq(cho(importt, base), optws, str(";"));
		
	public final Matcher blockType= cho( seq(resource, ws, resource), seq(str("@external"), ws, resource), resource);
	public final MutableMatcher blockBody= define(rep(seq(propertyValuePair, optws, str(";"))));
	public final Matcher block= seq(blockType, optws, str("{"), optws, blockBody, optws, str("}"));
	{
		blockBody.define(seq(cho(block, blockBody.getDefinition()), opt(rep(seq(optws, cho(block, blockBody.getDefinition())))))); // blocks can be nested
	}
		
	public final Matcher statement= seq(cho(directive, triple, block));
	public final Matcher document= seq(optws, rep(seq(statement, optws)));
	
	
	private MeteorDescriptionLanguageGrammar() {
		init();
	}
}
