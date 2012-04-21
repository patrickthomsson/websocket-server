package se.patrickthomsson.websocketserver.protocol.connectfour.domain;

import java.util.LinkedList;
import java.util.Queue;

public class Game {

	private static final int GOAL_TO_HAVE_IN_A_ROW = 4;
	
	private Queue<Player> players = new LinkedList<Player>();
	private Board board;
	
	public Game(Dimension dimension) {
		board = new Board(dimension);
	}
	
	public void addPlayer(Player player) {
		players.offer(player);
	}
	
	public Player getActivePlayer() {
		return players.peek();
	}

	public Player shiftTurns() {
		Player player = players.poll();
		players.offer(player);
		return player;
	}
	
	public boolean isPlayable(int columnIndex) {
		return board.isPlayable(columnIndex);
	}
	
	public void play(int columnIndex) {
		Player player = players.peek();
		board.play(player, columnIndex);
	}
	
	public boolean isWonBy(Player player) {
		return board.countCellsInARow(player) >= GOAL_TO_HAVE_IN_A_ROW;
	}
	
}
