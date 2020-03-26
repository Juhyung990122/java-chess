package chess.domain.piece;

import static chess.domain.Direction.*;

import java.util.Arrays;
import java.util.List;

import chess.domain.Direction;
import chess.domain.Position;
import chess.exception.IllegalMoveException;

public class Knight extends Piece {
	public static final String ILLEGAL_MOVE = "말이 움직일 수 없는 자리입니다.";
	private static List<Direction> possibleDirections = Arrays.asList(NNE, NEE, NNW, NWW, SSE, SEE, SSW, SWW);

	public Knight(Position position, Team team) {
		super(position, team);
		this.representation = 'N';
		this.score = 2.5;
	}

	@Override
	public void validateMove(Position destination) {
		Direction direction = position.calculateDirection(destination);
		if (!possibleDirections.contains(direction)) {
			throw new IllegalMoveException(ILLEGAL_MOVE);
		}
	}

}
