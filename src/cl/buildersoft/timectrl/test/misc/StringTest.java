package cl.buildersoft.timectrl.test.misc;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StringTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSubstring() {
		String out = "a,b,c,";

		System.out.println(out.lastIndexOf(",") + " " + (out.length()-1));
		
		
		out = out.length() > 0 ? out.substring(0, out.length() - 1) : "";

		assertTrue(out.equals(""));
	}

}
