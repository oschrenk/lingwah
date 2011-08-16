package com.googlecode.lingwah.grammars;

import com.googlecode.lingwah.Grammar;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.parser.ChoiceParser;
import com.googlecode.lingwah.parser.SpacedSequenceParser;

/**
 * A grammar for the ALF (Action Language for Foundational UML) language.
 * 
 * @author Ted Stockwell
 */
public class ALFGrammar extends Grammar
{
	public static final ALFGrammar DEFINITION= new ALFGrammar();
	
	public final ChoiceParser FeatureDefinition= choice();
	public final ChoiceParser ActiveFeatureDefinition= choice();
	public final ChoiceParser FeatureStubDeclaration= choice();
	public final ChoiceParser ActiveFeatureStubDeclaration= choice();
	public final ChoiceParser NamespaceDefinition= choice(); 
	public final ChoiceParser ClassifierDefinition= choice(); 
	public final ChoiceParser ClassifierDeclaration= choice();
	public final ChoiceParser ImportVisibilityIndicator= choice();
	

	//
	// reserved words
	//
	public final ChoiceParser ReservedWord=choice();
	public final Parser ABSTRACT= str("abstract").optionFor(ReservedWord);
	public final Parser ACCEPT= str("accept").optionFor(ReservedWord);
	public final Parser ACTIVE= str("active").optionFor(ReservedWord);
	public final Parser ACTIVITY= str("activity").optionFor(ReservedWord);
	public final Parser ALL_INSTANCES= str("allInstances").optionFor(ReservedWord);
	public final Parser ANY= str("any").optionFor(ReservedWord);
	public final Parser AS= str("as").optionFor(ReservedWord);
	public final Parser ASSOC= str("assoc").optionFor(ReservedWord);
	public final Parser BREAK= str("break").optionFor(ReservedWord);
	public final Parser CASE= str("case").optionFor(ReservedWord);
	public final Parser CLASS= str("class").optionFor(ReservedWord);
	public final Parser CLASSIFY= str("classify").optionFor(ReservedWord);
	public final Parser CLEAR_ASSOC= str("clearAssoc").optionFor(ReservedWord);
	public final Parser COMPOSE= str("compose").optionFor(ReservedWord);
	public final Parser CREATE_LINK= str("createLink").optionFor(ReservedWord);
	public final Parser DATATYPE= str("datatype").optionFor(ReservedWord);
	public final Parser DEFAULT= str("default").optionFor(ReservedWord);
	public final Parser DESTROY_LINK= str("destroyLink").optionFor(ReservedWord);
	public final Parser DO= str("do").optionFor(ReservedWord);
	public final Parser ELSE= str("else").optionFor(ReservedWord);
	public final Parser ENUM= str("enum").optionFor(ReservedWord);
	public final Parser FOR= str("for").optionFor(ReservedWord);
	public final Parser FROM= str("from").optionFor(ReservedWord);
	public final Parser HAS_TYPE= str("hastype").optionFor(ReservedWord);
	public final Parser IF= str("if").optionFor(ReservedWord);
	public final Parser IMPORT= str("import").optionFor(ReservedWord);
	public final Parser IN= str("in").optionFor(ReservedWord);
	public final Parser INOUT= str("inout").optionFor(ReservedWord);
	public final Parser INSTANCE_OF= str("instanceof").optionFor(ReservedWord);
	public final Parser LET= str("let").optionFor(ReservedWord);
	public final Parser NAMESPACE= str("namespace").optionFor(ReservedWord);
	public final Parser NEW= str("new").optionFor(ReservedWord);
	public final Parser NON_UNIQUE= str("nonunique").optionFor(ReservedWord);
	public final Parser NULL= str("null").optionFor(ReservedWord);
	public final Parser OR= str("or").optionFor(ReservedWord);
	public final Parser ORDERED= str("ordered").optionFor(ReservedWord);
	public final Parser OUT= str("out").optionFor(ReservedWord);
	public final Parser PACKAGE= str("package").optionFor(ReservedWord);
	public final Parser PRIVATE= str("private").optionFor(ReservedWord);
	public final Parser PROTECTED= str("protected").optionFor(ReservedWord);
	public final Parser PUBLIC= str("public").optionFor(ReservedWord);
	public final Parser RECEIVE= str("receive").optionFor(ReservedWord);
	public final Parser REDEFINES= str("redefines").optionFor(ReservedWord);
	public final Parser REDUCE= str("reduce").optionFor(ReservedWord);
	public final Parser RETURN= str("return").optionFor(ReservedWord);
	public final Parser SEQUENCE= str("sequence").optionFor(ReservedWord);
	public final Parser SPECIALIZES= str("specializes").optionFor(ReservedWord);
	public final Parser SUPER= str("super").optionFor(ReservedWord);
	public final Parser SIGNAL= str("signal").optionFor(ReservedWord);
	public final Parser SWITCH= str("switch").optionFor(ReservedWord);
	public final Parser THIS= str("this").optionFor(ReservedWord);
	public final Parser TO= str("to").optionFor(ReservedWord);
	public final Parser WHILE= str("while").optionFor(ReservedWord);
	public final Parser SELECT= str("select").optionFor(ReservedWord);
	public final Parser REJECT= str("reject").optionFor(ReservedWord);
	public final Parser COLLECT= str("collect").optionFor(ReservedWord);
	public final Parser ITERATE= str("iterate").optionFor(ReservedWord);
	public final Parser FOR_ALL= str("forAll").optionFor(ReservedWord);
	public final Parser EXISTS= str("exists").optionFor(ReservedWord);
	public final Parser ONE= str("one").optionFor(ReservedWord);
	public final Parser IS_UNIQUE= str("isUnique").optionFor(ReservedWord);

	//
	// Operators
	//
	public final ChoiceParser Operators= choice();
	public final Parser ASSIGN= str("=").optionFor(Operators);
	public final Parser GT= str(">").optionFor(Operators);
	public final Parser LT= str("<").optionFor(Operators);
	public final Parser TILDE= str("~").optionFor(Operators);
	public final Parser HOOK= str("?").optionFor(Operators);
	public final Parser AT= str("@").optionFor(Operators);
	public final Parser DOLLAR= str("$").optionFor(Operators);
	public final Parser EQ= str("==").optionFor(Operators);
	public final Parser LE= str("<=").optionFor(Operators);
	public final Parser GE= str(">=").optionFor(Operators);
	public final Parser NE= str("!=").optionFor(Operators);
	public final Parser SC_OR= str("||").optionFor(Operators);
	public final Parser SC_AND= str("&&").optionFor(Operators);
	public final Parser INCR= str("++").optionFor(Operators);
	public final Parser DECR= str("--").optionFor(Operators);
	public final Parser PLUS= str("+").optionFor(Operators);
	public final Parser MINUS= str("-").optionFor(Operators);
	public final Parser STAR= str("*").optionFor(Operators);
	public final Parser SLASH= str("/").optionFor(Operators);
	public final Parser LOGICAL_AND= str("&").optionFor(Operators);
	public final Parser LOGICAL_OR= str("|").optionFor(Operators);
	public final Parser XOR= str("^").optionFor(Operators);
	public final Parser REM= str("%").optionFor(Operators);
	public final Parser LSHIFT= str("<<").optionFor(Operators);
	public final Parser RSHIFT= str(">>").optionFor(Operators);
	public final Parser URSHIFT= str(">>>").optionFor(Operators);
	public final Parser PLUSASSIGN= str("+=").optionFor(Operators);
	public final Parser MINUSASSIGN= str("-=").optionFor(Operators);
	public final Parser STARASSIGN= str("*=").optionFor(Operators);
	public final Parser SLASHASSIGN= str("/=").optionFor(Operators);
	public final Parser ANDASSIGN= str("&=").optionFor(Operators);
	public final Parser ORASSIGN= str("|=").optionFor(Operators);
	public final Parser XORASSIGN= str("^=").optionFor(Operators);
	public final Parser REMASSIGN= str("%=").optionFor(Operators);
	public final Parser LSHIFTASSIGN= str("<<=").optionFor(Operators);
	public final Parser RSHIFTASSIGN= str(">>=").optionFor(Operators);
	public final Parser URSHIFTASSIGN= str(">>>=").optionFor(Operators);
	public final Parser NOT= str("!").optionFor(Operators);

	//
	// Punctuation
	//
	public final Parser LPAREN= str("("); 
	public final Parser RPAREN= str(")");  
	public final Parser LBRACE= str("{"); 
	public final Parser RBRACE= str("}");  
	public final Parser LBRACKET= str("[");  
	public final Parser RBRACKET= str("]"); 
	public final Parser SEMICOLON= str(";"); 
	public final Parser COMMA= str(","); 
	public final Parser DOT= str("."); 
	public final Parser COLON= str(":"); 
	public final Parser DOUBLE_DOT= str(".."); 
	public final Parser DOUBLE_COLON= str("::"); 
	public final Parser THICK_ARROW= str("=>"); 
	public final Parser ARROW= str("->");
	public final Parser DOUBLE_QUOTE= str("\"");
	public final Parser SINGLE_QUOTE= str("\'");
	public final Parser BACKSLASH= str("\\");
	

	public final Parser LineTerminator= choice(str("\n"), str("\r"), str("\n\r"), str("\r\n")); 
	public final Parser InputCharacter= excluding(anyChar(), LineTerminator);
	public final Parser EscapedCharacter= choice(SINGLE_QUOTE, DOUBLE_QUOTE, str("b"), str("f"), str("t"), str("n"), str("\\"));
	public final Parser EscapeSequence= seq(BACKSLASH, EscapedCharacter);
	public final Parser NonzeroDigit= range('1', '9');
	public final Parser Digit= choice(str('0'), NonzeroDigit);
	public final Parser NameCharacter= choice(excluding(InputCharacter, SINGLE_QUOTE, BACKSLASH), EscapeSequence);
	public final Parser IdentifierLetter= choice(range('a', 'z'), range('A', 'Z'), str('_'));
	public final Parser IdentifierLetterOrDigit= choice(IdentifierLetter, Digit);
	public final Parser IdentifierChars= seq(IdentifierLetter, zeroOrMore(IdentifierLetterOrDigit));
	
	
	// lexical comments
	public final ChoiceParser CommentText= choice(); 
	
   	public final Parser NotAt = excluding(InputCharacter, str("@"));
   	public final Parser EndOfLineComment= 
   		seq(str("//"), opt(seq(NotAt, zeroOrMore(InputCharacter))), LineTerminator);
   	public final Parser NotStar=
		choice(LineTerminator, excluding(InputCharacter, str("*")));
	public final Parser NotStarNotAt=
		choice(LineTerminator, excluding(InputCharacter, str("*"), str("@")));
   	public final Parser InLineComment= 
   		seq(str("/*"), opt(seq(NotStarNotAt, CommentText)), str("*/"));
	public final Parser NotStarNotSlash= 
		choice(LineTerminator, excluding(InputCharacter, str("*"), str("/")));
	public final Parser StarCommentText= 
		seq(str("*"), opt(seq(NotStarNotSlash, CommentText)));
	public final ChoiceParser LexicalComment= 
		choice(EndOfLineComment, InLineComment); 

	public final Parser DocumentationComment= seq(str("/**"), CommentText, str("*/"));
	
	{
		CommentText.addChoice(seq(zeroOrMore(NotStar), opt(StarCommentText)));
	}
	
	// whitespace
	public final Parser ws= 
		rep(choice(str(" "), str("\t"), str("\f"), LineTerminator, InLineComment));
	public final Parser optws= opt(ws); 
	
	/**
	 * Creates a sequence matcher that expects white space between all the 
	 * elements 
	 */
	public final Parser sseq(Parser...matchers) {
		return new SpacedSequenceParser(optws, matchers);
	}
	

	//
	// Names
	//
	public final Parser UnrestrictedName= 
		seq(SINGLE_QUOTE, zeroOrMore(NameCharacter), SINGLE_QUOTE);

	public final Parser BooleanLiteral= 
		choice(string("true"), string("false"));

	public final Parser Identifier=  
		excluding(IdentifierChars, BooleanLiteral, ReservedWord);

	public final Parser Name= choice(Identifier, UnrestrictedName);

	//
	// Literals
	//
	public final Parser OctalPrefix= str("0");
	public final Parser OctalDigit= range('0', '7');
	public final Parser OctalLiteral= seq(OctalPrefix, opt(str("_")), OctalDigit, zeroOrMore(seq(opt(str("_")), OctalDigit))); 

	public final Parser HexPrefix= choice(str("0x"), str("0X"));
	public final Parser HexDigit= choice(Digit, range('a', 'f'), range('A', 'F'));
	public final Parser HexLiteral= seq(HexPrefix, HexDigit, zeroOrMore(seq(opt(str("_")), HexDigit))); 

	public final Parser BinaryPrefix= choice(str("0b"), str("0B"));
	public final Parser BinaryDigit= choice(str("0"), str("1"));
	public final Parser BinaryLiteral= seq(BinaryPrefix, BinaryDigit, zeroOrMore(seq(opt(str("_")), BinaryDigit))); 

	public final Parser DecimalLiteral= choice(seq(NonzeroDigit, zeroOrMore( seq(opt(str("_")), Digit ))), string("0"));

	public final Parser NaturalLiteral= choice(BinaryLiteral, HexLiteral, OctalLiteral, DecimalLiteral );

	public final Parser UnboundedValueLiteral= str("*");

	public final Parser StringCharacter= choice(EscapeSequence, excluding(InputCharacter, DOUBLE_QUOTE, str("\\")));
	public final Parser StringLiteral= seq(DOUBLE_QUOTE, zeroOrMore(StringCharacter), DOUBLE_QUOTE);

	public final Parser PrimitiveLiteral= choice(BooleanLiteral, NaturalLiteral, UnboundedValueLiteral, StringLiteral);

	//
	// expressions
	//
	public final ChoiceParser PotentiallyAmbiguousQualifiedName= choice();
	public final ChoiceParser Expression= choice();
	public final ChoiceParser QualifiedName= choice();
	public final ChoiceParser InvocationTarget= choice();
	public final ChoiceParser NonNamePrimaryExpression= choice();

	public final Parser BehaviorInvocationTarget= seq(PotentiallyAmbiguousQualifiedName);

	public final Parser TupleExpressionList= sseq(Expression, zeroOrMore(sseq(COMMA, Expression)));
	public final Parser PositionalTuple= sseq(LPAREN, zeroOrMore(TupleExpressionList), RPAREN);

	public final Parser NamedExpression= sseq(Name, THICK_ARROW, Expression);
	public final Parser NamedTuple= sseq(LPAREN, NamedExpression, zeroOrMore(sseq(COMMA, NamedExpression)), RPAREN);
	public final Parser PositionalTemplateBinding= sseq(LT, QualifiedName, zeroOrMore(sseq(COMMA, QualifiedName)), GT);
	public final Parser TemplateParameterSubstitution= sseq(Name, THICK_ARROW, QualifiedName);
	public final Parser NamedTemplateBinding= sseq(LT, TemplateParameterSubstitution, zeroOrMore(sseq(COMMA, TemplateParameterSubstitution)), GT);
	public final Parser TemplateBinding= choice(PositionalTemplateBinding, NamedTemplateBinding);
	public final Parser NameBinding= sseq(Name, zeroOrMore(TemplateBinding));

	public final Parser UnqualifiedName= choice(NameBinding);
	public final Parser DotQualifiedName= sseq(NameBinding, zeroOrMore(sseq(DOT, NameBinding)));
	public final Parser ColonQualifiedName= sseq(NameBinding, zeroOrMore(sseq(DOUBLE_COLON, NameBinding)));
	
	{ 
		PotentiallyAmbiguousQualifiedName.addChoices(
				ColonQualifiedName, 
				DotQualifiedName, 
				UnqualifiedName);
		
		QualifiedName.addChoices(
				ColonQualifiedName, 
				DotQualifiedName, 
				UnqualifiedName);
	}

	public final Parser TypeName= choice(QualifiedName, ANY);

	public final Parser BooleanLiteralExpression= choice(BooleanLiteral);
	public final Parser NaturalLiteralExpression= choice(NaturalLiteral);
	public final Parser UnboundedLiteralExpression= choice(UnboundedValueLiteral);
	public final Parser StringLiteralExpression= choice(StringLiteral);
	public final Parser LiteralExpression= choice(
			BooleanLiteralExpression,
			NaturalLiteralExpression,
			UnboundedLiteralExpression,
			StringLiteralExpression);
	
	public final Parser ThisExpression= choice(THIS);
	public final Parser NameExpression= choice(PotentiallyAmbiguousQualifiedName);
	public final Parser NameTargetExpression= choice(QualifiedName);
	public final Parser PrimaryExpression= choice(
			NameExpression,
			NonNamePrimaryExpression);

	public final Parser Tuple= choice(PositionalTuple, NamedTuple);
	public final Parser ParenthesizedExpression= sseq(LPAREN, Expression, RPAREN);
	public final Parser InvocationExpression= sseq(InvocationTarget, Tuple);

	public final Parser FeatureTargetExpression= choice(NameTargetExpression, NonNamePrimaryExpression);
	public final Parser FeatureReference= choice(sseq(FeatureTargetExpression, DOT, NameBinding));
	

	public final Parser PropertyAccessExpression= choice(FeatureReference);	

	public final Parser FeatureInvocationTarget= choice(THIS, FeatureReference);
	public final Parser SuperInvocationTarget= seq(SUPER, zeroOrMore(sseq(DOT, choice(THIS, QualifiedName))));

	public final Parser InstanceCreationExpression= sseq(NEW, QualifiedName, Tuple);

	{
		InvocationTarget.addChoices(
				BehaviorInvocationTarget, 
				FeatureInvocationTarget, 
				SuperInvocationTarget);
	}
	

	// link operation expressions
	public final Parser Index= sseq(LBRACKET, Expression, RBRACKET);
	public final Parser IndexedNamedExpression= sseq(Name, opt(Index), THICK_ARROW, Expression);
	public final Parser IndexedNamedTuple= sseq(LPAREN, IndexedNamedExpression, zeroOrMore(sseq(COMMA, IndexedNamedExpression)), RPAREN);

	public final Parser LinkOperationTuple= 
		choice(PositionalTuple, IndexedNamedTuple);
	public final Parser LinkOperation= 
		choice(CREATE_LINK, DESTROY_LINK, CLEAR_ASSOC);
	public final Parser LinkOperationExpression= 
		sseq(QualifiedName, DOT, LinkOperation, LinkOperationTuple); 
		
	// assignment expressions
	public final Parser AssignmentOperator= choice(
			ASSIGN, PLUSASSIGN, MINUSASSIGN, STARASSIGN, REMASSIGN, SLASHASSIGN,
			ANDASSIGN, ORASSIGN, XORASSIGN, LSHIFTASSIGN, RSHIFTASSIGN, URSHIFTASSIGN);
	public final Parser FeatureLeftHandSide= 
		seq(FeatureReference);
	public final Parser NameLeftHandSide= 
		seq(PotentiallyAmbiguousQualifiedName);
	public final Parser LeftHandSide= 
		sseq(choice(NameLeftHandSide, FeatureLeftHandSide), opt(Index));
	public final Parser AssignmentExpression= 
		sseq(LeftHandSide, AssignmentOperator, Expression);

	// class extend expressions
	public final Parser ClassExtentExpression= 
		sseq(QualifiedName, DOT, ALL_INSTANCES, LPAREN, RPAREN);
	
	// sequence construction expressions
	public final ChoiceParser SequenceElements= choice(); 
	public final Parser SequenceRange= 
		sseq(Expression, DOUBLE_DOT, Expression);
	
	public final Parser SequenceInitializationExpression= 
		sseq(opt(NEW), zeroOrMore(sseq(LBRACE, SequenceElements, RBRACE)));
	
	public final Parser SequenceElement= 
		choice(Expression, SequenceInitializationExpression);
	
	public final Parser SequenceElementList= 
		sseq(SequenceElement, zeroOrMore(sseq(COMMA, SequenceElement)), opt(COMMA));
	
	public final Parser MultiplicityIndicator= 
		sseq(LBRACKET, RBRACKET);
	public final Parser SequenceElementsTypePart= 
		sseq(TypeName, zeroOrMore(MultiplicityIndicator));
	public final Parser SequenceElementsExpression=
		sseq(opt(NEW), SequenceElementsTypePart, LBRACE, SequenceElements, RBRACE);
	public final Parser NullExpression= 
		sseq(NULL);
	public final Parser SequenceConstructionExpression=
		choice(NullExpression, SequenceElementsExpression);

	{
		SequenceElements.addChoices( 
			SequenceElementList, SequenceRange);
	}
	
	// sequence action expressions
	public final Parser SequenceAccessExpression= 
		sseq(PrimaryExpression, Index);	
	
	// sequence operation expressions
	public final Parser ExtentOrExpression= choice(
			QualifiedName, NonNamePrimaryExpression);	
	public final Parser SequenceOperationExpression= 
		sseq(ExtentOrExpression, ARROW, QualifiedName, Tuple);
	
	// sequence reduction expressions
	public final Parser SequenceReductionExpression= 
		sseq(ExtentOrExpression, ARROW, REDUCE, opt(ORDERED), QualifiedName);
	
	// sequence expansion expressions
	public final Parser SelectOrRejectOperation= 
		choice(SELECT, REJECT);
	public final Parser CollectOrIterateOperation= 
		choice(COLLECT, ITERATE);
	public final Parser ForAllOrExistsOrOneOperation= 
		choice(FOR_ALL, EXISTS, ONE);
	public final Parser IsUniqueOperation= 
		choice(IS_UNIQUE);
	public final ChoiceParser ExpansionOperation= choice(
			SelectOrRejectOperation,
			CollectOrIterateOperation,
			ForAllOrExistsOrOneOperation,
			IsUniqueOperation);
	public final Parser SequenceExpansionExpression= 
		sseq(ExtentOrExpression, ARROW, ExpansionOperation, Name, LPAREN, Expression, RPAREN);
	
	// increment and decrement expressions
	public final Parser AffixOperator= choice(
			INCR, DECR); 
	public final Parser PrefixExpression= 
		sseq(AffixOperator, LeftHandSide);
	public final Parser PostfixExpression= 
		sseq(LeftHandSide, AffixOperator);
	public final Parser IncrementOrDecrementExpression= choice(
			PostfixExpression,
			PrefixExpression);
	
	// unary expressions
	public final ChoiceParser UnaryExpression= choice();
	public final ChoiceParser NonNumericUnaryExpression= choice();
	public final Parser BooleanUnaryExpression= 
		sseq(NOT, UnaryExpression);
	public final Parser BitStringUnaryExpression= 
		sseq(TILDE, UnaryExpression);
	public final Parser NumericUnaryOperator= 
		choice(PLUS, MINUS);
	public final Parser NumericUnaryExpression= 
		sseq(NumericUnaryOperator, UnaryExpression);
	public final Parser CastExpression= 
		sseq(LPAREN, TypeName, RPAREN, NonNumericUnaryExpression);
	public final Parser IsolationExpression= 
		sseq(DOLLAR, UnaryExpression);
	
	//
	// binary expressions
	//
	
	// arithmetic expressions
	public final ChoiceParser UnaryOrArithmeticExpression= choice();
	
	public final Parser AdditiveOperator= choice(PLUS, MINUS);
	public final Parser MultiplicativeOperator= choice(STAR, SLASH, REM);
	
	public final Parser AdditiveExpression= 
		sseq(UnaryExpression, zeroOrMore(sseq(AdditiveOperator, UnaryOrArithmeticExpression)));
	public final Parser MultiplicativeExpression= 
		sseq(UnaryExpression, zeroOrMore(sseq(MultiplicativeOperator, UnaryExpression)));
	
	{
		UnaryOrArithmeticExpression.addChoices( 
				UnaryExpression, AdditiveExpression, MultiplicativeExpression);
	}
	
	// shift expressions
	public final ChoiceParser ArithmeticOrShiftExpression= choice();
	public final Parser ShiftOperator= choice(LSHIFT, RSHIFT, URSHIFT);
	public final Parser ShiftExpression= 
		sseq(ArithmeticOrShiftExpression, zeroOrMore(sseq(ShiftOperator, UnaryOrArithmeticExpression)));
	{
		ArithmeticOrShiftExpression.addChoices(
				UnaryOrArithmeticExpression, ShiftExpression);
	}
	
	// relational expressions
	public final ChoiceParser ShiftOrRelationalExpression= choice();
	public final Parser RelationalOperator= choice(LT, GT, LE, GE);
	public final Parser RelationalExpression= 
		sseq(ArithmeticOrShiftExpression, zeroOrMore(sseq(RelationalOperator, ArithmeticOrShiftExpression)));
	{
		ShiftOrRelationalExpression.addChoices(
				ArithmeticOrShiftExpression, RelationalExpression);
	}
	
	// classification expressions
	public final Parser ClassificationOperator= choice(INSTANCE_OF, HAS_TYPE);
	public final ChoiceParser RelationalOrClassificationExpression= choice();
	public final Parser ClassificationExpression= 
		sseq(RelationalOrClassificationExpression, zeroOrMore(sseq(ClassificationOperator, QualifiedName)));
	{
		RelationalOrClassificationExpression.addChoices(
				ShiftOrRelationalExpression, ClassificationExpression);
	}
	
	// equality expressions
	public final Parser EqualityOperator= choice(EQ, NE);
	public final ChoiceParser ClassificationOrEqualityExpression= choice();
	public final Parser EqualityExpression= 
		sseq(ClassificationOrEqualityExpression, zeroOrMore(sseq(EqualityOperator, RelationalOrClassificationExpression)));
	{
		ClassificationOrEqualityExpression.addChoices(
				RelationalOrClassificationExpression, EqualityExpression);
	}
	
	// logical expressions
	public final ChoiceParser EqualityOrAndExpression= choice();
	public final ChoiceParser AndOrExclusiveOrExpression= choice();
	public final ChoiceParser ExclusiveOrOrInclusiveOrExpression= choice();
	public final Parser AndExpression= 
		sseq(EqualityOrAndExpression, zeroOrMore(sseq(ANY, ClassificationOrEqualityExpression)));
	public final Parser ExclusiveOrExpression= 
		sseq(AndOrExclusiveOrExpression, zeroOrMore(sseq(XOR, EqualityOrAndExpression)));
	public final Parser InclusiveOrExpression= 
		sseq(ExclusiveOrOrInclusiveOrExpression, zeroOrMore(sseq(OR, AndOrExclusiveOrExpression)));
	{
		EqualityOrAndExpression.addChoices(
				ClassificationOrEqualityExpression, AndExpression);
		AndOrExclusiveOrExpression.addChoices(
				EqualityOrAndExpression, ExclusiveOrExpression);
		ExclusiveOrOrInclusiveOrExpression.addChoices(
				AndOrExclusiveOrExpression, InclusiveOrExpression);
	}
	
	// conditional logical expressions
	public final ChoiceParser InclusiveOrOrConditionalAndExpression= choice();
	public final ChoiceParser ConditionalAndOrConditionalOrExpression= choice();
	public final Parser ConditionalAndExpression= 
		sseq(InclusiveOrOrConditionalAndExpression, zeroOrMore(sseq(SC_AND, ExclusiveOrOrInclusiveOrExpression)));
	public final Parser ConditionalOrExpression= 
		sseq(ConditionalAndOrConditionalOrExpression, zeroOrMore(sseq(SC_OR, InclusiveOrOrConditionalAndExpression)));
	{
		InclusiveOrOrConditionalAndExpression.addChoices(
				ExclusiveOrOrInclusiveOrExpression, ConditionalAndExpression);
		ConditionalAndOrConditionalOrExpression.addChoices(
				InclusiveOrOrConditionalAndExpression, ConditionalOrExpression);
	}

	// condition-test expressions
	public final ChoiceParser ConditionalExpression= choice();
	public final Parser ConditionalTestExpression= 
		sseq(ConditionalAndOrConditionalOrExpression, HOOK, Expression, COLON, ConditionalExpression);
	{
		ConditionalExpression.addChoices(
				ConditionalAndOrConditionalOrExpression, ConditionalTestExpression);
	}

	//
	// statements
	//
	public final ChoiceParser Statement= choice();
	
	public final Parser DocumentedStatement= 
		sseq(opt(DocumentationComment), Statement);
	public final Parser StatementSequence= 
		zeroOrMore(DocumentedStatement);
	public final Parser Block= 
		sseq(LBRACE, StatementSequence, RBRACE);
	
	// annotated statements
	public final Parser Annotation= 
		sseq(Identifier, opt(sseq(LPAREN, Name, zeroOrMore(sseq(COMMA, Name)), RPAREN)));
	
	public final Parser AnnotatedStatement= 
		sseq(str("//@"), Annotation, zeroOrMore(sseq(str("@"), Annotation)), opt(sseq(str("//"), zeroOrMore(InputCharacter))), LineTerminator, Statement);
	

	// in-line statements
	public final Parser InLineHeader= 
		sseq(str("/*@"), str("inline"), LPAREN, Name, RPAREN, opt(sseq(str("//"), zeroOrMore(InputCharacter))), LineTerminator);	
	public final Parser InLineStatement= 
		sseq(InLineHeader, CommentText, str("*/"));
	
	// block statements
	public final Parser BlockStatement= sseq(Block);
	
	// empty statements
	public final Parser EmptyStatement= SEMICOLON;
	
	// local name declaration statements
	public final ChoiceParser InitializationExpression= choice();
	public final Parser NameDeclaration=
		choice(
			sseq(LET, Name, COLON, TypeName, opt(MultiplicityIndicator)),
			sseq(TypeName, opt(MultiplicityIndicator), Name));
	public final Parser LocalNameDeclarationStatement= 
		sseq(NameDeclaration, ASSIGN, InitializationExpression, SEMICOLON);
	public final Parser InstanceInitializationExpression= 
		sseq(NEW, Tuple);
	{
		InitializationExpression.addChoices(
				Expression, SequenceInitializationExpression, InstanceInitializationExpression);
	}
	
	// expression statements
	public final Parser ExpressionStatement= 
		sseq(Expression, SEMICOLON);
	
	// if statements
	public final Parser FinalClause= 
		sseq(ELSE, Block);	
	public final Parser NonFinalClause= 
		sseq(LPAREN, Expression, RPAREN, Block);
	public final Parser ConcurrentClauses= 
		sseq(NonFinalClause, zeroOrMore(sseq(OR, IF, NonFinalClause)));
	public final Parser SequentialClauses= 
		sseq(ConcurrentClauses, zeroOrMore(sseq(ELSE, IF, ConcurrentClauses)));
	public final Parser IfStatement= 
		sseq(IF, SequentialClauses, opt(FinalClause));
	
	// switch statements
	public final Parser NonEmptyStatementSequence= 
		sseq(zeroOrMore(DocumentedStatement));	
	public final Parser SwitchDefaultClause= 
		sseq(DEFAULT, COLON, NonEmptyStatementSequence);
	public final Parser SwitchCase= 
		sseq(CASE, Expression, COLON);	
	public final Parser SwitchClause= 
		sseq(zeroOrMore(SwitchCase), NonEmptyStatementSequence);
	public final Parser SwitchStatement= 
		sseq(SWITCH, LPAREN, Expression, RPAREN, LBRACE, zeroOrMore(SwitchClause), opt(SwitchDefaultClause), RBRACE);
	
	// while statements
	public final Parser WhileStatement= 
		sseq(WHILE, LPAREN, Expression, RPAREN, Block);
	
	// do statements
	public final Parser DoStatement= 
		sseq(DO, Block, WHILE, LPAREN, Expression, RPAREN, SEMICOLON);
	
	// for statements
	public final Parser LoopVariableDefinition=
		choice(
				sseq(Name, IN, Expression, opt(sseq(DOUBLE_DOT, Expression))),
				sseq(TypeName, Name, COLON, Expression));	
	public final Parser ForControl= 
		sseq(LoopVariableDefinition, zeroOrMore(sseq(COMMA, LoopVariableDefinition)));
	public final Parser ForStatement= 
		sseq(FOR, LPAREN, ForControl, RPAREN, Block);
	
	// break statements
	public final Parser BreakStatement= 
		sseq(BREAK, SEMICOLON);
	
	// return statements
	public final Parser ReturnStatement= 
		sseq(RETURN, Expression, SEMICOLON);
	
	// accept statements
	public final Parser QualifiedNameList= 
		sseq(QualifiedName, zeroOrMore(sseq(COMMA, QualifiedName)));	
	public final Parser AcceptClause= 
		sseq(ACCEPT, LPAREN, opt(sseq(Name, COLON)), QualifiedNameList, RPAREN); 
	public final Parser AcceptBlock= 
		sseq(AcceptClause, Block);

	public final ChoiceParser AcceptStatement= choice();
	public final Parser SimpleAcceptStatement=
		sseq(AcceptClause, SEMICOLON);
	public final Parser CompoundAcceptStatement=
		sseq(AcceptBlock, zeroOrMore(sseq(OR, AcceptBlock)));
	{
		AcceptStatement.addChoices(
				SimpleAcceptStatement, CompoundAcceptStatement);
	}
	
	
	// classify statements
	public final Parser ReclassifyAllClause= 
		sseq(FROM, STAR);
	public final Parser ClassificationToClause= 
		sseq(TO, QualifiedNameList);
	public final Parser ClassificationFromClause=
		sseq(FROM, QualifiedNameList);
	public final Parser ClassificationClause= 
		choice(
			sseq(ClassificationFromClause, opt(ClassificationToClause)),
			sseq(opt(ReclassifyAllClause), ClassificationToClause));
	public final Parser ClassifyStatement= 
		sseq(CLASSIFY, Expression, zeroOrMore(ClassificationClause), SEMICOLON);
	
	{
		Statement.addChoices(
				AnnotatedStatement,
				InLineStatement,
				BlockStatement,
				EmptyStatement,
				LocalNameDeclarationStatement,
				ExpressionStatement,
				IfStatement,
				SwitchStatement,
				WhileStatement,
				DoStatement,
				ForStatement,
				BreakStatement,
				ReturnStatement,
				AcceptStatement,
				ClassifyStatement);
	}
	
	
	//
	// features
	//
	
	// properties
	public final Parser UnlimitedNaturalLiteral= 
		choice(DecimalLiteral, UnboundedValueLiteral);	
	public final Parser MultiplicityRange=
		choice(MultiplicityIndicator, LBRACKET, opt(sseq(DecimalLiteral, DOUBLE_DOT)), UnlimitedNaturalLiteral, RBRACKET);
	public final Parser OrderingAndUniqueness= 
		choice(
			sseq(ORDERED, opt(NON_UNIQUE)),
			sseq(NON_UNIQUE, opt(ORDERED)),
			sseq(SEQUENCE));
	public final Parser Multiplicity= 
		sseq(MultiplicityRange, opt(OrderingAndUniqueness));
	public final Parser TypePart= 
		sseq(TypeName, opt(Multiplicity));
	public final Parser PropertyDeclaration= 
		sseq(Name, COLON, opt(COMPOSE), TypePart);
	public final Parser PropertyDefinition=
		sseq(PropertyDeclaration, SEMICOLON);
	public final Parser AttributeInitializer= 
		sseq(ASSIGN, InitializationExpression);
	public final Parser AttributeDefinition= 
		sseq(PropertyDeclaration, zeroOrMore(AttributeInitializer), SEMICOLON);
	
	
	
	// activities
	public final Parser ParameterDirection=
		choice(IN, OUT, INOUT);
	public final Parser ReturnParameter= 
		sseq(COLON, TypePart);
	
	public final Parser LiteralValue= 
		choice(
			BooleanLiteral,
			seq(opt(NumericUnaryOperator), NaturalLiteral),
			UnboundedValueLiteral,
			StringLiteral);
	public final Parser TaggedValue= 
		sseq(Name, THICK_ARROW, LiteralValue);
	public final Parser TaggedValueList= 
		sseq(TaggedValue, zeroOrMore(sseq(COMMA, TaggedValue)));
	public final Parser TaggedValues= 
		choice(
			QualifiedNameList,
			TaggedValueList);
	
	// packages
	// classifiers
	public final Parser StereotypeAnnotation=
		sseq(AT, QualifiedName, opt(sseq(LPAREN, TaggedValues, RPAREN)));
	
	public final Parser PackageDeclaration=
		sseq(PACKAGE, Name);
	public final Parser PackageStubDeclaration=
		sseq(PackageDeclaration, SEMICOLON);
	public final Parser ClassifierStubDeclaration=
		sseq(ClassifierDeclaration, SEMICOLON);
	public final Parser NamespaceStubDeclaration= 
		choice(PackageStubDeclaration, ClassifierStubDeclaration);
	public final Parser PackagedElementDefinition= 
		choice(NamespaceDefinition, NamespaceStubDeclaration);
	public final Parser PackagedElement=
		sseq(opt(DocumentationComment), zeroOrMore(StereotypeAnnotation), ImportVisibilityIndicator, PackagedElementDefinition);
	public final Parser PackageDefinition=
		sseq(PackageDeclaration, LBRACE, zeroOrMore(PackagedElement), RBRACE);
	public final Parser TemplateParameterConstraint=
		sseq(SPECIALIZES, QualifiedName);
	public final Parser ClassifierTemplateParameter=
		sseq(opt(DocumentationComment), Name, opt(TemplateParameterConstraint)); 
	public final Parser TemplateParameters=
		sseq(LT, ClassifierTemplateParameter, zeroOrMore(sseq(COMMA, ClassifierTemplateParameter)), GT);
	public final Parser SpecializationClause=
		sseq(SPECIALIZES, QualifiedNameList);	  
	public final Parser ClassifierSignature=
		sseq(Name, opt(TemplateParameters), opt(SpecializationClause));
	

	
	
	public final Parser FormalParameter=
		sseq(zeroOrMore(DocumentationComment), zeroOrMore(StereotypeAnnotation), ParameterDirection, Name, COLON, TypePart);	
	public final Parser FormalParameterList=
		sseq(FormalParameter, zeroOrMore(FormalParameter));
	public final Parser FormalParameters=
		sseq(LPAREN, opt(FormalParameterList), RPAREN);
	public final Parser ActivityDeclaration= 
		sseq(ACTIVITY, Name, opt(TemplateParameters), FormalParameters, opt(ReturnParameter));
	public final Parser ActivityDefinition=
		sseq(ActivityDeclaration, Block);

	// operations
	public final Parser RedefinitionClause= 
		sseq(REDEFINES, QualifiedNameList);	
	public final Parser OperationDeclaration=
		sseq(opt(ABSTRACT), Name, FormalParameters, opt(ReturnParameter), opt(RedefinitionClause));
	public final Parser OperationDefinition=
		sseq(OperationDeclaration, Block);
	public final Parser OperationStubDeclaration=
		sseq(OperationDeclaration, SEMICOLON);
	
	// data types
	public final Parser DataTypeDeclaration=
		sseq(opt(ACTIVE), DATATYPE, ClassifierSignature);
	public final Parser StructuredElement=
		sseq(opt(DocumentationComment), zeroOrMore(StereotypeAnnotation), opt(PUBLIC), PropertyDefinition);	
	public final Parser DataTypeDefinition=
		sseq(DataTypeDeclaration, LBRACE, zeroOrMore(StructuredElement), RBRACE);
	
	// receptions
	public final Parser ReceptionDefinition= 
		sseq(RECEIVE, QualifiedName, SEMICOLON); 
	public final Parser SignalReceptionDeclaration=
		sseq(RECEIVE, SIGNAL, Name, opt(SpecializationClause));
	public final Parser SignalReceptionDefinition=
		sseq(SignalReceptionDeclaration, LBRACE, zeroOrMore(StructuredElement), RBRACE);
	public final Parser SignalReceptionStubDeclaration=
		sseq(SignalReceptionDeclaration, SEMICOLON);	
	
	
	{
		FeatureDefinition.addChoices(AttributeDefinition, OperationDefinition);
		ActiveFeatureDefinition.addChoices(ReceptionDefinition, SignalReceptionDefinition);
		FeatureStubDeclaration.addChoices(OperationStubDeclaration);
		ActiveFeatureStubDeclaration.addChoices(SignalReceptionStubDeclaration);
	}
		
	
	
	
	//
	// units
	//

	public final Parser PackageImportReference= 
		choice(
			sseq(ColonQualifiedName, DOUBLE_COLON, STAR),
			sseq(DotQualifiedName, DOT, STAR),
			sseq(UnqualifiedName, DOUBLE_COLON, STAR),
			sseq(UnqualifiedName, DOT, STAR));
	public final Parser AliasDefinition=
		sseq(AS, Name);
	public final Parser ElementImportReference= 
		sseq(QualifiedName, opt(AliasDefinition));
	public final Parser ImportReference= 
		choice(ElementImportReference, PackageImportReference);
	public final Parser ImportDeclaration=
		sseq(ImportVisibilityIndicator, IMPORT, ImportReference, SEMICOLON);
	public final Parser NamespaceDeclaration= 
		sseq(NAMESPACE, QualifiedName, SEMICOLON);
	
	// namespaces
	public final Parser VisibilityIndicator=
		choice(
			ImportVisibilityIndicator, 
			PROTECTED);
	
	// classes
	public final ChoiceParser ClassMemberDefinition= choice();
	public final Parser ClassDeclaration=
		sseq(opt(ABSTRACT), CLASS, ClassifierSignature);	
	public final Parser ClassMember=
		sseq(opt(DocumentationComment), zeroOrMore(StereotypeAnnotation), opt(VisibilityIndicator), ClassMemberDefinition);
	public final Parser ClassDefinition=
		sseq(ClassDeclaration, LBRACE, zeroOrMore(ClassMember), RBRACE);
	
	// active classes
	public final ChoiceParser ActiveClassMemberDefinition= choice();
	public final Parser ActiveClassMember=
		sseq(opt(DocumentationComment), zeroOrMore(StereotypeAnnotation), opt(VisibilityIndicator), ActiveClassMemberDefinition);
	
	public final Parser ActiveClassDeclaration=
		sseq(opt(ACTIVE), ACTIVE, CLASS, ClassifierSignature);
	public final Parser BehaviorClause= 
		 choice(Block, Name);	
	public final Parser ActiveClassDefinition=
		sseq(ActiveClassDeclaration, LBRACE, zeroOrMore(ActiveClassMember), RBRACE, opt(sseq(DO, BehaviorClause)));
	
	// associations
	public final Parser AssociationDeclaration=
		sseq(opt(ABSTRACT), ASSOC, ClassifierSignature);
	public final Parser AssociationDefinition=
		sseq(AssociationDeclaration, LBRACE, StructuredElement, StructuredElement, zeroOrMore(StructuredElement), RBRACE);
	
	// enumerations
	public final Parser EnumerationDeclaration=
		sseq(ENUM, Name, opt(SpecializationClause));
	public final Parser EnumerationLiteralName=
		sseq(opt(DocumentationComment), Name);	
	public final Parser EnumerationDefinition=
		sseq(EnumerationDeclaration, LBRACE, EnumerationLiteralName, zeroOrMore(sseq(COMMA, EnumerationLiteralName)), RBRACE);
	
	// signals
	public final Parser SignalDeclaration=
		sseq(opt(ABSTRACT), SIGNAL, ClassifierSignature);
	public final Parser SignalDefinition=
		sseq(SignalDeclaration, LBRACE, zeroOrMore(StructuredElement), RBRACE);

	{
		ImportVisibilityIndicator.addChoices( 
				PUBLIC, PRIVATE);
		
		ActiveClassMemberDefinition.addChoices(
				 ClassMemberDefinition,
				 ActiveFeatureDefinition,
				 ActiveFeatureStubDeclaration);
		  
		ClassMemberDefinition.addChoices(
				ClassifierDefinition,
				ClassifierStubDeclaration,
				FeatureDefinition,
				FeatureStubDeclaration);
	}
	
	public final Parser UnitDefinition= 
		sseq(
			opt(NamespaceDeclaration),
			zeroOrMore(ImportDeclaration),
			opt(DocumentationComment),
			zeroOrMore(StereotypeAnnotation),
			NamespaceDefinition);
	
	{
		
		NamespaceDefinition.addChoices(
			PackageDefinition,
			ClassifierDefinition);
		
		ClassifierDefinition.addChoices(
			ClassDefinition,
			ActiveClassDefinition,
			DataTypeDefinition,
			EnumerationDefinition,
			AssociationDefinition,
			SignalDefinition,
			ActivityDefinition);

		ClassifierDeclaration.addChoices(
			ClassDeclaration,
			ActiveClassDeclaration,
			DataTypeDeclaration,
			EnumerationDeclaration,
			AssociationDeclaration,
			SignalDeclaration,
			ActivityDeclaration);
	}
	  
	{
		NonNumericUnaryExpression.addChoices(
				PostfixExpression,
				BooleanUnaryExpression,
				BitStringUnaryExpression,
				CastExpression,
				IsolationExpression,
				PrimaryExpression);
		
		UnaryExpression.addChoices(
				IncrementOrDecrementExpression,
				BooleanUnaryExpression,
				BitStringUnaryExpression,
				NumericUnaryExpression,
				CastExpression,
				IsolationExpression,
				PrimaryExpression);	
		
		NonNamePrimaryExpression.addChoices(				
				ParenthesizedExpression,
				InvocationExpression,
				PropertyAccessExpression,
				InstanceCreationExpression,
				LinkOperationExpression,
				ClassExtentExpression,
				SequenceOperationExpression,
				SequenceConstructionExpression,
				SequenceAccessExpression,
				SequenceReductionExpression,
				SequenceExpansionExpression,
				LiteralExpression,
				ThisExpression
				);
		
		Expression.addChoices(
				ConditionalExpression,
				AssignmentExpression);
	}


	private ALFGrammar() {
		init();
	}
}
