package chess.domain.piece;

import chess.domain.game.Color;
import chess.domain.position.Position;

import java.util.List;
import java.util.Stack;

public class Knight extends ChessPiece {

    private static final String NAME = "N";
    private static final Double SCORE = 2.5;
    private static final int SUM_OF_MOVABLE_DISTANCE = 3;

    public Knight(Color color) {
        super(color, NAME);
    }

    @Override
    public List<Position> getInitWhitePosition() {
        return List.of(new Position("b1"), new Position("g1"));
    }

    @Override
    public List<Position> getInitBlackPosition() {
        return List.of(new Position("b8"), new Position("g8"));
    }

    @Override
    public void checkMovable(Position from, Position to) {
        int fileDistance = Math.abs(from.fileDistance(to));
        int rankDistance = Math.abs(from.rankDistance(to));

        if (rankDistance + fileDistance != SUM_OF_MOVABLE_DISTANCE) {
            throw new IllegalArgumentException("해당 기물이 갈 수 없는 위치입니다.");
        }

        if (from.isSameFile(to) || from.isSameRank(to)) {
            throw new IllegalArgumentException("해당 기물이 갈 수 없는 위치입니다.");
        }
    }

    @Override
    public Stack<Position> findRoute(final Position from, Position to) {
        return new Stack<>();
    }

    @Override
    public double getScore() {
        return SCORE;
    }
}
