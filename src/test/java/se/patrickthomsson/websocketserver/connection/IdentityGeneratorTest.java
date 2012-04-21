package se.patrickthomsson.websocketserver.connection;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class IdentityGeneratorTest {

	@Test
	public void shouldGenerateId() {
		IdentityGenerator identityGenerator = new IdentityGenerator();
		assertNotNull(identityGenerator.generateId());
	}
	
	@Test
	public void shouldNotGenerateSameIdTwice() {
		Set<ConnectionId> oldIds = new HashSet<ConnectionId>();
		
		for(int i=0; i<1000; i++) {
			ConnectionId id = new IdentityGenerator().generateId();
			assertFalse(oldIds.contains(id));
			oldIds.add(id);
		}
	}
	
}
