package se.patrickthomsson.websocketserver.connection;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class IdentityGeneratorTest {

	@Test
	public void shouldGenerateId() {
		IdentityGenerator identityGenerator = new IdentityGenerator();
		String id = identityGenerator.generateId();
		assertNotNull(id);
		assertTrue(id.length() > 0);
	}
	
	@Test
	public void shouldNotGenerateSameIdTwice() {
		Set<String> oldIds = new HashSet<String>();
		
		for(int i=0; i<1000; i++) {
			String id = new IdentityGenerator().generateId();
			assertFalse(oldIds.contains(id));
			oldIds.add(id);
		}
	}
	
}
