package test;

import static org.junit.Assert.*;

import model.EmailEvaluator;
import model.FieldEvaluator;
import model.NumberEvaluator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class EvaluatorTest {
	private FieldEvaluator num = new FieldEvaluator(new NumberEvaluator());
	private FieldEvaluator mail = new FieldEvaluator(new EmailEvaluator());
	

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIsValidNum() {
		assertTrue(num.evaluate("123"));
		assertTrue(num.evaluate(".123"));
		assertTrue(num.evaluate("1.123E33"));
		assertTrue(num.evaluate("0"));
		assertTrue(num.evaluate("-0.0"));
		assertFalse(num.evaluate(""));
		assertFalse(num.evaluate("-"));
		assertFalse(num.evaluate("0,0123"));
	}
	
	@Test
	public void testIsValidMail() {
		assertTrue(mail.evaluate("user@host"));
		assertTrue(mail.evaluate("first.last@host"));
		assertTrue(mail.evaluate("first.last@host.domain"));
		assertFalse(mail.evaluate(""));
		assertFalse(mail.evaluate(" "));
		assertFalse(mail.evaluate("user"));
		assertFalse(mail.evaluate("user."));
		assertFalse(mail.evaluate("user@"));
		assertFalse(mail.evaluate("user@."));
		assertFalse(mail.evaluate("user.@"));
		assertFalse(mail.evaluate("user@domain."));

		
	}

}
