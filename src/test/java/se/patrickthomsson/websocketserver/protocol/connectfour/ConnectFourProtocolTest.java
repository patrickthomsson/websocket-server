package se.patrickthomsson.websocketserver.protocol.connectfour;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import se.patrickthomsson.util.CollectionUtil;
import se.patrickthomsson.websocketserver.WebSocketServer;
import se.patrickthomsson.websocketserver.connection.ConnectionId;
import se.patrickthomsson.websocketserver.connection.ConnectionIdImpl;
import se.patrickthomsson.websocketserver.protocol.Request;
import se.patrickthomsson.websocketserver.protocol.RequestImpl;
import se.patrickthomsson.websocketserver.protocol.Response;
import se.patrickthomsson.websocketserver.protocol.ResponseImpl;
import static java.util.Arrays.asList;

import static org.mockito.Mockito.verify;

import static org.mockito.MockitoAnnotations.initMocks;

public class ConnectFourProtocolTest {

	private static final String MESSAGE = "messageType:hello";

	private static final Player p1 = new Player("player1");
	private static final Player p2 = new Player("player2");

	@Mock
	private WebSocketServer webSocketServer;

	@InjectMocks
	private ConnectFourProtocol protocol;

	@Before
	public void setUp() {
		protocol = new ConnectFourProtocol();
		initMocks(this);
	}

	@Test
	public void shouldBroadcastResponseToAllConnections() {
		protocol.addConnection(p1.connectionId);
		Request request = new RequestBuilder().withMessage(MESSAGE).withSender(p1.connectionId).build();
		protocol.processRequest(request);
		Response expectedResponse = ResponseImpl.builder()
				.message(MESSAGE)
				.receivers(p1.connectionId).build();
		verify(webSocketServer).processResponse(expectedResponse);
	}

	@Test
	public void shouldBroadcastResponseToAllConnections2() {
		protocol.addConnection(p1.connectionId);
		protocol.addConnection(p2.connectionId);
		Request request = new RequestBuilder().withMessage(MESSAGE).withSender(p1.connectionId).build();
		protocol.processRequest(request);
		Response expectedResponse = ResponseImpl.builder()
				.message(MESSAGE)
				.receivers(p1.connectionId, p2.connectionId).build();
		verify(webSocketServer).processResponse(expectedResponse);
	}

	@Test
	public void shouldBroadcastUserListWhenAUserJoins() {
		protocol.addConnection(p1.connectionId);
		Request request = new RequestBuilder().withNewUserMessage(p1.userName).withSender(p1.connectionId).build();
		protocol.processRequest(request);
		Response expectedResponse = ResponseImpl.builder()
				.message(userListMessage(p1.userName))
				.receivers(p1.connectionId).build();
		verify(webSocketServer).processResponse(expectedResponse);
	}

	@Test
	public void shouldBroadcastUserListWhenASecondUserJoins() {
		join(p1);
		protocol.addConnection(p2.connectionId);
		Request p2Request = new RequestBuilder().withNewUserMessage(p2.userName).withSender(p2.connectionId).build();
		protocol.processRequest(p2Request);
		Response expectedResponse = ResponseImpl.builder()
				.message(userListMessage(p1.userName, p2.userName))
				.receivers(p1.connectionId, p2.connectionId).build();
		verify(webSocketServer).processResponse(expectedResponse);
	}

	@Test(expected = ConnectFourException.class)
	public void userNamesShouldBeUnique() {
		final String sameName = "sameName";
		protocol.addConnection(p1.connectionId);
		protocol.addConnection(p2.connectionId);
		Request p1Request = new RequestBuilder().withNewUserMessage(sameName).withSender(p1.connectionId).build();
		Request p2Request = new RequestBuilder().withNewUserMessage(sameName).withSender(p2.connectionId).build();

		protocol.processRequest(p1Request);
		protocol.processRequest(p2Request);
	}

	@Test
	public void shouldSendGameInvitation() {
		join(p1, p2);
		Request invitation = new RequestBuilder().withInvitationMessage(p2.userName).withSender(p1.connectionId)
				.build();
		protocol.processRequest(invitation);
		Response expectedResponse = ResponseImpl.builder()
				.message("INVITATION:FROM:" + p1.userName)
				.receivers(p2.connectionId).build();
		verify(webSocketServer).processResponse(expectedResponse);
	}

	@Test
	public void shouldRespondToInvitationAccept() {
		join(p1, p2);

		// imagine scenario: player1 has previously sent an invitation to player2. now player2 accepts the invitation:

		Request responseToInvitaton = new RequestBuilder().withInvitationAcceptedMessage(p1.userName)
				.withSender(p2.connectionId).build();
		protocol.processRequest(responseToInvitaton);
		Response expectedResponse = ResponseImpl.builder()
				.message("INVITATION:TO:" + p2.userName + ":ACCEPTED")
				.receivers(p1.connectionId).build();
		verify(webSocketServer).processResponse(expectedResponse);
	}

	@Test
	public void shouldRespondToInvitationDecline() {
		join(p1, p2);

		// imagine scenario: player1 has previously sent an invitation to player2. now player2 declines the invitation:

		Request responseToInvitaton = new RequestBuilder().withInvitationDeclinedMessage(p1.userName)
				.withSender(p2.connectionId).build();
		protocol.processRequest(responseToInvitaton);
		Response expectedResponse = ResponseImpl.builder()
				.message("INVITATION:TO:" + p2.userName + ":DECLINED")
				.receivers(p1.connectionId).build();
		verify(webSocketServer).processResponse(expectedResponse);
	}

	public void join(Player... players) {
		for (Player player : players) {
			protocol.addConnection(player.connectionId);
			Request p1Request = new RequestBuilder().withNewUserMessage(player.userName)
					.withSender(player.connectionId).build();
			protocol.processRequest(p1Request);
		}
	}

	private String userListMessage(String... userNames) {
		final String userListPrefix = "USERLIST:";
		return userListPrefix + CollectionUtil.asDelimitedString(asList(userNames));
	}

	private static class RequestBuilder {

		private String message;
		private ConnectionId connectionId;

		public RequestBuilder withMessage(String message) {
			this.message = message;
			return this;
		}

		public RequestBuilder withNewUserMessage(String userName) {
			return withMessage("NEW_USER:" + userName);
		}

		public RequestBuilder withInvitationMessage(String userNameToInvite) {
			return withMessage("INVITATION:" + userNameToInvite);
		}

		public RequestBuilder withInvitationAcceptedMessage(String userName) {
			return withMessage("INVITATION:FROM:" + userName + ":ACCEPTED");
		}

		public RequestBuilder withInvitationDeclinedMessage(String userName) {
			return withMessage("INVITATION:FROM:" + userName + ":DECLINED");
		}

		public RequestBuilder withSender(ConnectionId connectionId) {
			this.connectionId = connectionId;
			return this;
		}

		public Request build() {
			return new RequestImpl(message, connectionId);
		}
	}

	private static class Player {

		public final String userName;
		public final ConnectionId connectionId;

		Player(String userName) {
			this.userName = userName;
			this.connectionId = new ConnectionIdImpl(userName);
		}
	}

}
