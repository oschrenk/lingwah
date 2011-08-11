package com.googlecode.lingwah.grammars;

import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.Matcher;
import com.googlecode.lingwah.matcher.common.BooleanMatcher;
import com.googlecode.lingwah.matcher.common.DecimalMatcher;
import com.googlecode.lingwah.matcher.common.DoubleMatcher;
import com.googlecode.lingwah.matcher.common.IntegerMatcher;
import com.googlecode.lingwah.matcher.common.NameMatcher;
import com.googlecode.lingwah.matcher.common.QuotedStringMatcher;
import com.googlecode.lingwah.matcher.common.SimpleBlockMatcher;
import com.googlecode.lingwah.matcher.common.WhitespaceMatcher;

/**
 * A grammar for the Terse RDF Triple Language (Turtle) language.
 * @see http://www.w3.org/TeamSubmission/turtle/
 * 
 * @author Ted Stockwell
 */
public class TurtleGrammar extends Grammar
{
	public static final TurtleGrammar DEFINITION= new TurtleGrammar();
	
	/**
	 * Elements of this grammar
	 */
	public final Matcher lower = range('a', 'z');
	public final Matcher digit = range('0', '9');
	public final Matcher ws = rep(new WhitespaceMatcher());
	public final Matcher optws = opt(ws);
	public final Matcher name= new NameMatcher();
	
	public final Matcher QUOTED_STRING= new QuotedStringMatcher();
	public final Matcher QNAME= seq(opt(name), string(":"), opt(name));
	public final Matcher NODE_ID= seq(string("_:"), name);
	public final Matcher PREFIX_NAME= choice(name);
	public final Matcher URIREF= new URIRefMatcher();
	public final Matcher RESOURCE=choice(URIREF, QNAME);
	public final Matcher SUBJECT=choice(RESOURCE, NODE_ID, string("[]"));
	public final Matcher BASE=seq(string("@base"), ws, URIREF);
	public final Matcher PREFIX_ID=seq(string("@prefix"), ws, opt(PREFIX_NAME), string(":"), ws, URIREF);
	public final Matcher BOOL=new BooleanMatcher();
	public final Matcher DECIMAL=new DecimalMatcher();
	public final Matcher DOUBLE=new DoubleMatcher();
	public final Matcher INTEGER=new IntegerMatcher();
	public final Matcher DATATYPE_STRING=seq(QUOTED_STRING, string("^^"), RESOURCE);
	public final Matcher LANGUAGE=seq(rep(lower), opt(rep(seq(string("-"), rep(choice(lower, digit))))));
	public final Matcher STRING=seq(QUOTED_STRING, opt(seq(string("@"), LANGUAGE)));
	public final Matcher LITERAL=choice(STRING, DATATYPE_STRING, INTEGER, DOUBLE, DECIMAL, BOOL);
	public final Matcher OBJECT=choice(RESOURCE, LITERAL, NODE_ID, string("[]"));
	public final Matcher OBJECT_LIST=seq(OBJECT, opt(rep(seq(optws, string(","), optws, OBJECT))));
	public final Matcher VERB=choice(RESOURCE, string("a"));
	public final Matcher PREDICATE_VALUES=seq(VERB, ws, OBJECT_LIST);
	public final Matcher TRIPLES=seq(SUBJECT, ws, PREDICATE_VALUES, opt(rep(seq(optws, string(";"), optws, PREDICATE_VALUES))));
	public final Matcher DIRECTIVE=choice(PREFIX_ID, BASE);
	public final Matcher STATEMENT=seq(choice(DIRECTIVE, TRIPLES), optws, string("."));
	public final Matcher DOCUMENT=seq(optws, rep(seq(STATEMENT, optws)));
	
	
	
	public static class URIRefMatcher extends SimpleBlockMatcher 
	{
		public URIRefMatcher() {
			super("<", ">");
		}
		
		@Override
		public String getDefaultLabel()
		{
			return "URI";
		}
	}
	
	

	private TurtleGrammar() {
		init();
	}
}
