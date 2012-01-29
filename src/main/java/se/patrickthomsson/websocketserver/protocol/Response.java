package se.patrickthomsson.websocketserver.protocol;

import java.util.Collection;

public interface Response {
	
	public String getMessage();
	public Collection<String> getReceiverIds();

}
