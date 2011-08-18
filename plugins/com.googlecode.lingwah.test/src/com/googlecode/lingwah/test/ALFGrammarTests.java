package com.googlecode.lingwah.test;

import junit.framework.TestCase;

import com.googlecode.lingwah.ParseContext;
import com.googlecode.lingwah.ParseResults;
import com.googlecode.lingwah.Parser;
import com.googlecode.lingwah.Parsers;
import com.googlecode.lingwah.grammars.ALFGrammar;



/**
 * Tests originally used to get work out details of ALF syntax 
 * and get parser working. 
 * 
 * @author Ted Stockwell
 */
public class ALFGrammarTests extends TestCase {
	
	static final ALFGrammar lohr= ALFGrammar.DEFINITION;
	
	public void testWhiteSpace() {
		testParser(lohr.optws, "");
		testParser(lohr.optws, " ");
	}
	
	
	public void testQualifiedNames() {
//		testQualifiedName("customer");

		testParser(lohr.optws, " ");
		testParser(lohr.IdentifierChars, "customer");
		testParser(lohr.Identifier, "customer");
		testParser(lohr.Name, "customer");
		testParser(lohr.NameBinding, "customer");
		testParser(lohr.UnqualifiedName, "customer");
		testQualifiedName("customer");
		testParser(lohr.ColonQualifiedName, "Ordering::Order::customer");
		testQualifiedName("Ordering::Order::customer");
		testParser(lohr.ColonQualifiedName, "Ordering:: customer");
		testQualifiedName("Ordering.Order.customer");
		testQualifiedName("FoundationalModelLibrary::BasicInputOutput");
		testQualifiedName("FoundationalModelLibrary.BasicInputOutput");
		testParser(lohr.IdentifierChars, "Integer");
		testParser(lohr.Identifier, "Integer");
		testParser(lohr.Name, "Integer");
		testParser(lohr.NameBinding, "Integer");
		testParser(lohr.UnqualifiedName, "Integer");
		testParser(lohr.QualifiedName, "Integer");
		testParser(lohr.PositionalTemplateBinding, "<Integer>");
		testParser(lohr.PositionalTemplateBinding, "<Integer>");
		testParser(lohr.TemplateBinding, "<Integer>");
		testParser(lohr.Name, "Set");
		testParser(lohr.NameBinding, "Set<Integer>");
		testParser(lohr.UnqualifiedName, "Set<Integer>");
		testQualifiedName("Set<Integer>");
		testQualifiedName("Map<K=>String,V=>Entry>");
		testQualifiedName("Map<K=>String, V=>Entry>");
		testQualifiedName("Map<String,Entry>.KeySet");
		testQualifiedName("List<List<String>>");
		testQualifiedName("List< List<String> >");
		testQualifiedName("CollectionClasses::Set<Integer>::add");
	}
	public void testBooleanLiterals() {
		testParser(lohr.BooleanLiteral, "true");
		testParser(lohr.BooleanLiteral, "false");
	}

	public void testNaturalLiterals() {
		testParser(lohr.NaturalLiteral, "0_045_133");
		
		testParser(lohr.NaturalLiteral, "1234");
		testParser(lohr.NaturalLiteral, "0");
		testParser(lohr.NaturalLiteral, "0b1010111000010000");
		testParser(lohr.NaturalLiteral, "0B0100_1010_0101_1011");
		testParser(lohr.NaturalLiteral, "0xAE10");
		testParser(lohr.NaturalLiteral, "0X4a_5b");
		testParser(lohr.NaturalLiteral, "0_045_133");
	}
	
	public void testStringLiterals() {
		testParser(Parsers.excluding(Parsers.anyChar(), Parsers.str("\n")), "T");
		testParser(lohr.InputCharacter, "T");
		testParser(Parsers.excluding(lohr.InputCharacter, lohr.DOUBLE_QUOTE), "T");
		testParser(lohr.StringCharacter, "T");
		testParser(lohr.StringLiteral, "\"This is a string.\"");
		testParser(lohr.StringLiteral, "\"This is a string with a quote character (\\\") in it.\"");
		testParser(lohr.StringLiteral, "\"This is a string with a new line (\\n) in it.\"");
	}
	
	public void testPropertyAccessExpressions() {
		testParser(lohr.PropertyAccessExpression, "this.node");
		
		testParser(lohr.FeatureTargetExpression, "this");
		testParser(lohr.FeatureReference, "this.node");
		testParser(lohr.PropertyAccessExpression, "this.node");
		testParser(lohr.PropertyAccessExpression, "poleValue.im");
		testParser(lohr.PropertyAccessExpression, "members.name");
		testParser(lohr.PropertyAccessExpression, "jack.house");
	}
	
	public void testInvocationExpressions() {
//		testParser(lohr.ConditionalExpression, "1");
		testParser(lohr.Expression, "1");
		testParser(lohr.SequenceElement, "1");
		//testParser(lohr.SequenceElementList, "1,2");
		
		// behavior invocations
		testParser(lohr.BehaviorInvocationTarget, "ComputeInterest");
		testParser(lohr.Identifier, "amount");
		testParser(lohr.UnaryExpression, "amount");
		testParser(lohr.UnaryOrArithmeticExpression, "amount");
		testParser(lohr.ArithmeticOrShiftExpression, "amount");
		testParser(lohr.ShiftOrRelationalExpression, "amount");
		testParser(lohr.RelationalOrClassificationExpression, "amount");
		testParser(lohr.ClassificationOrEqualityExpression, "amount");

		testParser(lohr.ClassificationOrEqualityExpression, "amount");
		testParser(lohr.EqualityOrAndExpression, "amount");
		testParser(lohr.AndOrExclusiveOrExpression, "amount");
		testParser(lohr.ExclusiveOrOrInclusiveOrExpression, "amount");
		testParser(lohr.InclusiveOrOrConditionalAndExpression, "amount");
		testParser(lohr.ConditionalAndOrConditionalOrExpression, "amount");
		testParser(lohr.ConditionalExpression, "amount");
		testParser(lohr.Expression, "amount");
		testParser(lohr.TupleExpressionList, "amount");
		testParser(Parsers.rep(lohr.TupleExpressionList), "amount");
		testParser(Parsers.rep(lohr.TupleExpressionList), "amount");
		testParser(lohr.PositionalTuple, "(a)");
		testParser(lohr.PositionalTuple, "(amount)");
		testParser(lohr.InvocationExpression, "ComputeInterest(amount)");
		
		testParser(lohr.InvocationExpression, "Start(monitor => systemMonitor)");
		testParser(lohr.SequenceElement, "1");
		testParser(lohr.SequenceElement, "2");
		testParser(lohr.SequenceElement, "3");
		testParser(lohr.SequenceElementList, "1,2,3");
		testParser(lohr.MultiplicityIndicator, "[]");
		testParser(lohr.TypeName, "Integer");
		testParser(lohr.SequenceElementsTypePart, "Integer[]");
		testParser(lohr.SequenceElementsExpression, "Integer[]{1,2,3}");
		testParser(lohr.Expression, "Integer[]{1,2,3}");
		testParser(lohr.PositionalTuple, "(Integer[]{1,2,3}, 4)");
		testParser(lohr.InvocationExpression, "including<Integer>(Integer[]{1,2,3}, 4)");
		testParser(lohr.InvocationExpression, "Roster::player(team=>t, season=>y)");
		testParser(lohr.InvocationExpression, "Roster.player(t,y)");
		testParser(lohr.InvocationExpression, "Owns::house(jack)");
		
		// feature invocations
		testParser(lohr.InvocationExpression, "group.activate(nodes, edges)");
		testParser(lohr.NamedTuple, "(monitorRef=>systemMonitor)");
		testParser(lohr.FeatureTargetExpression, "actuator");
		testParser(lohr.Identifier, "Initialize");
		testParser(lohr.Name, "Initialize");
		testParser(lohr.NameBinding, "Initialize");
		testParser(lohr.FeatureTargetExpression, "actuator.Initialize");
		testParser(lohr.FeatureReference, "actuator.Initialize");
		testParser(lohr.FeatureInvocationTarget, "actuator.Initialize");
		testParser(lohr.InvocationExpression, "actuator.Initialize(monitorRef=>systemMonitor)");
		testParser(lohr.InvocationExpression, "actuator.Initialize(monitorRef => systemMonitor)");
		
		// super invocation expressions
		testParser(lohr.InvocationExpression, "super.run()");
		testParser(lohr.InvocationExpression, "super.initialize(startValue)");
		testParser(lohr.InvocationExpression, "super.Person::setName(name)");
	}
	
	public void testInstanceCreationExpressions() {
		// object creation
		testParser(lohr.InstanceCreationExpression, "new Employee(id, name)");
		testParser(lohr.InstanceCreationExpression, "new Employee::transferred(employeeInfo)");
		testParser(lohr.InstanceCreationExpression, "new Set<Integer>(Integer[]{1,2,3})");
		
		// data value creation
		testParser(lohr.InstanceCreationExpression, "new Position(1,2)");
		testParser(lohr.InstanceCreationExpression, "new Position(x=>1, y=>2)");
	}
	
	public void testLinkOperationsExpressions() {
		testParser(lohr.LinkOperationExpression, "Owns.createLink(jack, newHouse)");
		testParser(lohr.LinkOperationExpression, "Owns.createLink(owner=>jack, house=>newHouse)");
		testParser(lohr.LinkOperationExpression, "Owns.createLink(owner=>jack, house[1]=>newHouse)");
		testParser(lohr.LinkOperationExpression, "Owns.destroyLink(owner=>jack, house=>newHouse)");
		testParser(lohr.LinkOperationExpression, "Owns.clearAssoc(jack)");
	}
	
	public void testClassExtendExpressions() {
		testParser(lohr.ClassExtentExpression, "Customers.allInstances()");
	}
	
	public void testSequenceConstructionExpressions() {
		testParser(lohr.SequenceConstructionExpression, "Integer[]{1, 3, 45, 2, 3}");
		testParser(lohr.SequenceConstructionExpression, "Set<Integer>{1, 3, 45, 2, 3}");
		testParser(lohr.SequenceConstructionExpression, "new String[]{\"apple\",\"orange\",\"strawberry\",}");
		testParser(lohr.SequenceConstructionExpression, "new List< List<String> >{{\"apple\",\"orange\"},{\"strawberry\",\"raspberry\"}}");
		testParser(lohr.AdditiveExpression, "6+4");
		testParser(lohr.UnaryOrArithmeticExpression, "6+4");
		testParser(lohr.ArithmeticOrShiftExpression, "6+4");
		testParser(lohr.ShiftOrRelationalExpression, "6+4");
		testParser(lohr.RelationalOrClassificationExpression, "6+4");
		testParser(lohr.ClassificationOrEqualityExpression, "6+4");
		testParser(lohr.ExclusiveOrOrInclusiveOrExpression, "6+4");
		testParser(lohr.ConditionalAndOrConditionalOrExpression, "6+4");
		testParser(lohr.ConditionalExpression, "6+4");
		testParser(lohr.Expression, "6+4");
		testParser(lohr.SequenceRange, "1..6+4");
		testParser(lohr.SequenceElements, "1..6+4");
		testParser(lohr.SequenceConstructionExpression, "Integer[]{1..6+4}");
		testParser(lohr.SequenceConstructionExpression, "null");
	}
	
	public void testSequenceAccessExpressions() {
		//testParser(lohr.NonNamePrimaryExpression, "this.getTypes()");
		
		testParser(lohr.Tuple, "()");
		testParser(lohr.FeatureTargetExpression, "this");
		testParser(lohr.FeatureReference, "this.getTypes"); // works
		testParser(lohr.FeatureInvocationTarget, "this.getTypes"); // returns "this", not "this.getTypes"  
		testParser(lohr.InvocationExpression, "this.getTypes()");
		testParser(lohr.NonNamePrimaryExpression, "this.getTypes()");
		testParser(lohr.PrimaryExpression, "this.getTypes()");
		testParser(lohr.SequenceAccessExpression, "this.getTypes()[1]");
	}
	
	public void testSequenceOperationsExpressions() {
		testParser(lohr.SequenceOperationExpression, "selectedCustomers->notEmpty()");
		testParser(lohr.SequenceOperationExpression, "memberList->includes(possibleMember)");
		testParser(lohr.SequenceOperationExpression, "memberList->including(newMember)");
		testParser(lohr.SequenceOperationExpression, "products->removeAll(rejects)");
	}
	
	public void testSequenceReductionExpressions() {
		testParser(lohr.SequenceReductionExpression, "subtotals->reduce '+'");
		testParser(lohr.SequenceReductionExpression, "rotationMatrices->reduce ordered MatMult");
	}
	
	public void testSequenceExpansionExpressions() {
		Parser matcher= lohr.SequenceExpansionExpression;
		testParser(matcher, "c->select x (x>1)");
		
		
		testParser(lohr.ClassificationOrEqualityExpression, "c.name");
		testParser(lohr.EqualityExpression, "c.name == customerName");
		testParser(lohr.Expression, "c.name == customerName");
		testParser(matcher, "customerList->select c (c.name == customerName)");
		testParser(matcher, "Customer.toSequence()->select c (c.name == customerName)");
		
		testParser(matcher, "employees->select e (e.age>50)");
		testParser(matcher, "employees->reject e (e.isMarried)");
		
		testParser(matcher, "employees->collect e (e.birthDate)");
		testParser(matcher, "processSteps->iterate step (step.execute())");
		
		testParser(matcher, "employees->forAll e (e.age<=65)");
		testParser(matcher, "employees->exists e (e.firstName==\"Jack\")");
		testParser(matcher, "employees->one e (e.title==\"President\")");
		
		testParser(matcher, "employees->isUnique e (e.employeeIdentificationNumber)");
	}
	
	public void testIncrementOrDecrementExpression() {
		Parser matcher= lohr.IncrementOrDecrementExpression;
		testParser(matcher, "count++");
		testParser(matcher, "size--");
		testParser(matcher, "total[i]++");
		testParser(matcher, "++count");
		testParser(matcher, "--numberWaiting[queueIndex]");
	}
		

	public void testBooleanUnaryExpression() {
		testParser(lohr.UnaryExpression, "this.r");
		
		Parser matcher= lohr.BooleanUnaryExpression;
		testParser(lohr.NameBinding, "r");
		testParser(lohr.FeatureTargetExpression, "this");
		testParser(lohr.FeatureReference, "this.r");
		testParser(lohr.PropertyAccessExpression, "this.r");
		testParser(lohr.NonNamePrimaryExpression, "this.r");
		testParser(lohr.PrimaryExpression, "this.r");
		testParser(lohr.PrimaryExpression, "this.running");
		testParser(lohr.UnaryExpression, "this.running");
		testParser(matcher, "!this.running");
		testParser(matcher, "!isActive");
	}
		

	public void testBitStringUnaryExpression() {
		Parser matcher= lohr.BitStringUnaryExpression;
		testParser(matcher, "~registerContext");
		testParser(matcher, "~memory.getByte(address)");
	}
	

	public void testNumericUnaryExpression() {
		testParser(lohr.UnaryOrArithmeticExpression, "a*b");
		testParser(lohr.ShiftExpression, "a*b");
		testParser(lohr.ArithmeticOrShiftExpression, "a*b");
		testParser(lohr.ConditionalExpression, "a*b");
		testParser(lohr.Expression, "a*b");
		testParser(lohr.ParenthesizedExpression, "(a*b)");
		testParser(lohr.UnaryExpression, "(a*b)");
		
		testParser(lohr.NumericUnaryExpression, "+1234");
		testParser(lohr.NumericUnaryExpression, "-42");
		testParser(lohr.UnaryExpression, "(a*b)");
		testParser(lohr.NumericUnaryExpression, "+(a*b)");
		testParser(lohr.NumericUnaryExpression, "-absoluteValue");
	}

	public void testCastExpression() {
		Parser matcher= lohr.CastExpression;
		testParser(matcher, "(fUML::Syntax::Activity)this.getTypes()");
		testParser(matcher, "(Person)invoice.payingParty");
		testParser(matcher, "(any)this");
	}

	public void testIsolationExpression() {
		testParser(lohr.FeatureReference, "this.monitor");
		testParser(lohr.PropertyAccessExpression, "this.monitor");
		testParser(lohr.NonNamePrimaryExpression, "this.monitor");
		testParser(lohr.FeatureTargetExpression, "this.monitor");
		testParser(lohr.FeatureReference, "this.monitor.getActiveSensor");
		testParser(lohr.FeatureInvocationTarget, "this.monitor.getActiveSensor");
		testParser(lohr.InvocationExpression, "this.monitor.getActiveSensor()");
		testParser(lohr.FeatureInvocationTarget, "this.monitor.getActiveSensor().getReading");
		
		
		testParser(lohr.InvocationExpression, "this.monitor.getActiveSensor()");
		testParser(lohr.NonNamePrimaryExpression, "this.monitor.getActiveSensor()");
		testParser(lohr.FeatureTargetExpression, "this.monitor.getActiveSensor()");
		testParser(lohr.FeatureReference, "this.monitor.getActiveSensor().getReading");
		testParser(lohr.FeatureInvocationTarget, "this.monitor.getActiveSensor().getReading");
		testParser(lohr.InvocationTarget, "this.monitor.getActiveSensor().getReading");
		testParser(lohr.InvocationExpression, "this.monitor.getActiveSensor().getReading()");
		testParser(lohr.NonNamePrimaryExpression, "this.monitor.getActiveSensor().getReading()");
		testParser(lohr.UnaryExpression, "this.monitor.getActiveSensor().getReading()");
		
		Parser matcher= lohr.IsolationExpression;
		testParser(matcher, "$this.monitor.getActiveSensor().getReading()");
	}

	public void testUnaryOrArithmeticExpression() {
		Parser matcher= lohr.UnaryOrArithmeticExpression;
		testParser(matcher, "amount * interestRate");
		testParser(matcher, "duration / timeStep");
		testParser(matcher, "length % unit");
		testParser(matcher, "initialPosition + positionChange");
		testParser(matcher, "basePrice - discount");
	}

	public void testShiftExpression() {
		testParser(lohr.ShiftExpression, "bitmask << wordLength");
		testParser(lohr.ShiftExpression, "wordContent >> offset");
		testParser(lohr.ShiftExpression, "(value&0xF0) >>> 8");
	}

	public void testRelationalExpression() {
		testParser(lohr.RelationalExpression, "sensorReading > threshold");
		testParser(lohr.RelationalExpression, "size < maxSize");
		testParser(lohr.RelationalExpression, "size >= minSize");
		testParser(lohr.RelationalExpression, "count <= limit");
		testParser(lohr.RelationalExpression, "3 < *");
		testParser(lohr.RelationalExpression, "(UnlimitedNatural)(+3) < *");
	}

	public void testClassificationExpression() {
		testParser(lohr.ClassificationExpression, "action instanceof ActionActivation");
		testParser(lohr.ClassificationExpression, "'signal' hastype SignalArrival");
		testParser(lohr.ClassificationExpression, "size >= minSize");
		testParser(lohr.ClassificationExpression, "count <= limit");
		testParser(lohr.ClassificationExpression, "3 < *");
		testParser(lohr.ClassificationExpression, "(UnlimitedNatural)(+3) < *");
	}

	public void testEqualityExpression() {
		testParser(lohr.EqualityExpression, "errorCount==0");
		testParser(lohr.EqualityExpression, "nextRecord!=endRecord");
		testParser(lohr.EqualityExpression, "list.next==null");
	}

	public void testLogicalExpression() {
		testParser(lohr.InclusiveOrExpression, "sensorOff | sensorError");
		testParser(lohr.InclusiveOrExpression, "i > min & i < max | unlimited");
		testParser(lohr.InclusiveOrExpression, "bitString ^ mask");
		testParser(lohr.InclusiveOrExpression, "registerContent & 0x00FF");
	}

	public void testConditionalLogicalExpression() {
		testParser(lohr.ConditionalOrExpression, "index > 0 && value[index] < limit");
		testParser(lohr.ConditionalOrExpression, "index == 0 || value[index] >= limit");
	}

	public void testConditionalTestExpression() {
		testParser(lohr.ConditionalTestExpression, "isNormalOps? readPrimarySensor(): readBackupSensor()");
	}

	public void testAssignmentExpression() {
		testParser(lohr.AssignmentExpression, "customer = new Customer()");
		testParser(lohr.AssignmentExpression, "customer[i] = new Customer()");
		testParser(lohr.AssignmentExpression, "reply = this.createReply(request,result)");
		testParser(lohr.AssignmentExpression, "customer.email = checkout.customerEmail");
		testParser(lohr.AssignmentExpression, "customer.address[i] = newAddress");
		testParser(lohr.AssignmentExpression, "x += 4");
		testParser(lohr.AssignmentExpression, "filename += \".doc\"");
	}

	public void testBlock() {
		String doc= "{'activity' = (Activity)(this.types[1]);" +
				"group = new ActivityNodeActivationGroup();" +
				"group.activityExecution = this;" +
				"this.activationGroup = group;" +
				"group.activation('activity'.node, 'activity'.edge);}";
		testParser(lohr.StatementSequence, doc);
	}
	
	protected ParseResults testQualifiedName(String qn) {
		return testParser(ALFGrammar.DEFINITION.QualifiedName, qn);
	}
	protected ParseResults testParser(Parser matcher, String text) {
		ParseContext context= new ParseContext(text);
		//context.trace(LohrGrammar.DEFINITION.Identifier, false);
		ParseResults results= context.getParseResults(matcher, 0);
		
		if (!results.success())
			assertTrue("Match failed at position "+results.getError().position+":"+results.getError().errorMsg, results.success());
		
		int length= results.longestLength();
		assertEquals("Failed to match entire text", text.length(), length);
		
		return results;
	}
	
}
