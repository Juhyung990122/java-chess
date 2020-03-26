package chess.domain.piece;

import java.util.Arrays;
import java.util.List;

import chess.domain.Direction;
import chess.domain.Position;
import chess.exception.IllegalMoveException;

public class King extends Piece {
	public static final String ILLEGAL_MOVE = "말이 움직일 수 없는 자리입니다.";
	private static List<Direction> possibleDirections = Arrays.asList(Direction.NORTH, Direction.EAST, Direction.WEST,
		Direction.SOUTH, Direction.NORTHEAST, Direction.NORTHWEST, Direction.SOUTHEAST, Direction.SOUTHWEST);

	public King(Position position, Team team) {
		super(position, team);
		this.representation = 'K';
		this.score = 0;
	}

	public void validateMove(Position destination) {
		Direction direction = position.calculateDirection(destination);
		if (!possibleDirections.contains(direction)) {
			throw new IllegalMoveException(ILLEGAL_MOVE);
		}
	}
}
