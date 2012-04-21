package se.patrickthomsson.websocketserver.connection;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class IdentityGenerator {

	private static final Set<UUID> generatedIds = new HashSet<UUID>();
	
	public ConnectionId generateId() {
		return new ConnectionIdImpl(uniqueIdentifier());
	}

	private String uniqueIdentifier() {
		UUID id = UUID.randomUUID();
		
		if(!generatedIds.contains(id)) {
			generatedIds.add(id);
			return id.toString();
		}
		
		return uniqueIdentifier();
	}
	
}