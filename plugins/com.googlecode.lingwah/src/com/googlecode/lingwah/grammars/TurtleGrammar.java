package com.googlecode.lingwah.grammars;

import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.parser.common.BooleanParser;
import com.googlecode.lingwah.parser.common.DecimalParser;
import com.googlecode.lingwah.parser.common.DoubleParser;
import com.googlecode.lingwah.parser.common.IntegerParser;
import com.googlecode.lingwah.parser.common.NameParser;
import com.googlecode.lingwah.parser.common.QuotedStringParser;
import com.googlecode.lingwah.parser.common.SimpleBlockParser;
import com.googlecode.lingwah.parser.common.WhitespaceParser;

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
	public final Parser lower = range('a', 'z');
	public final Parser digit = range('0', '9');
	public final Parser ws = rep(new WhitespaceParser());
	public final Parser optws = opt(ws);
	public final Parser name= new NameParser();
	
	public final Parser QUOTED_STRING= new QuotedStringParser();
	public final Parser QNAME= seq(opt(name), string(":"), opt(name));
	public final Parser NODE_ID= seq(string("_:"), name);
	public final Parser PREFIX_NAME= choice(name);
	public final Parser URIREF= new URIRefMatcher();
	public final Parser RESOURCE=choice(URIREF, QNAME);
	public final Parser SUBJECT=choice(RESOURCE, NODE_ID, string("[]"));
	public final Parser BASE=seq(string("@base"), ws, URIREF);
	public final Parser PREFIX_ID=seq(string("@prefix"), ws, opt(PREFIX_NAME), string(":"), ws, URIREF);
	public final Parser BOOL=new BooleanParser();
	public final Parser DECIMAL=new DecimalParser();
	public final Parser DOUBLE=new DoubleParser();
	public final Parser INTEGER=new IntegerParser();
	public final Parser DATATYPE_STRING=seq(QUOTED_STRING, string("^^"), RESOURCE);
	public final Parser LANGUAGE=seq(rep(lower), opt(rep(seq(string("-"), rep(choice(lower, digit))))));
	public final Parser STRING=seq(QUOTED_STRING, opt(seq(string("@"), LANGUAGE)));
	public final Parser LITERAL=choice(STRING, DATATYPE_STRING, INTEGER, DOUBLE, DECIMAL, BOOL);
	public final Parser OBJECT=choice(RESOURCE, LITERAL, NODE_ID, string("[]"));
	public final Parser OBJECT_LIST=seq(OBJECT, opt(rep(seq(optws, string(","), optws, OBJECT))));
	public final Parser VERB=choice(RESOURCE, string("a"));
	public final Parser PREDICATE_VALUES=seq(VERB, ws, OBJECT_LIST);
	public final Parser TRIPLES=seq(SUBJECT, ws, PREDICATE_VALUES, opt(rep(seq(optws, string(";"), optws, PREDICATE_VALUES))));
	public final Parser DIRECTIVE=choice(PREFIX_ID, BASE);
	public final Parser STATEMENT=seq(choice(DIRECTIVE, TRIPLES), optws, string("."));
	public final Parser DOCUMENT=seq(optws, rep(seq(STATEMENT, optws)));
	
	
	
	public static class URIRefMatcher extends SimpleBlockParser 
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
