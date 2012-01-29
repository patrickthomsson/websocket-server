package se.patrickthomsson.websocketserver.connection;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class IdentityGenerator {

	private static final Set<UUID> generatedIds = new HashSet<UUID>();
	
	public String generateId() {
		UUID id = UUID.randomUUID();
		
		if(!generatedIds.contains(id)) {
			generatedIds.add(id);
			return id.toString();
		}
		
		return generateId();
	}
	
}
