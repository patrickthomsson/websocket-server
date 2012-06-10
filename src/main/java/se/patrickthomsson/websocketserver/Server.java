package se.patrickthomsson.websocketserver;

import java.io.IOException;

import se.patrickthomsson.websocketserver.protocol.Response;

public interface Server {

	void start() throws IOException;
	void processResponse(Response response);

}