package chess.domain.game.board;

import chess.domain.game.Color;
import chess.domain.game.GameStatus;
import chess.domain.game.Score;
import chess.domain.piece.ChessPiece;
import chess.domain.position.Direction;
import chess.domain.position.Position;

import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class ChessBoard {

    private final Map<Position, ChessPiece> chessBoard;
    private Color currentTurn = Color.WHITE;
    private GameStatus gameStatus = GameStatus.READY;

    public ChessBoard(Map<Position, ChessPiece> board) {
        this.chessBoard = board;
    }

    public void move(Position from, Position to) {
        ChessPiece me = checkPiece(from, to);

        if (isEmptyDestination(to)) {
            moveEmptyPosition(from, to, me);
            return;
        }

        if (enemyExist(me, to)) {
            catchAndMove(from, to, me);
        }
    }

    private ChessPiece checkPiece(Position from, Position to) {
        ChessPiece me = findPiece(from)
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다."));

        checkMove(from, to, me);
        return me;
    }

    private void checkMove(Position from, Position to, ChessPiece me) {
        validateTurn(me);
        me.checkMovable(from, to);
        Stack<Position> routes = me.findRoute(from, to);

        while (!routes.isEmpty()) {
            checkHurdle(routes.pop());
        }
    }

    private void validateTurn(ChessPiece me) {
        if (currentTurn != me.getColor()) {
            throw new IllegalArgumentException(currentTurn.name() + "의 차례입니다.");
        }
    }

    private void checkHurdle(Position position) {
        if (findPiece(position).isPresent()) {
            throw new IllegalArgumentException("이동 경로 사이에 다른 기물이 있습니다.");
        }
    }

    private boolean isEmptyDestination(Position to) {
        return findPiece(to).isEmpty();
    }

    private void moveEmptyPosition(Position from, Position to, ChessPiece me) {
        checkPawnStraightMove(from, to, me);
        movePiece(from, to, me);
    }

    private void catchAndMove(Position from, Position to, ChessPiece me) {
        checkPawnCrossMove(from, to, me);
        movePiece(from, to, me);
    }

    private void checkPawnStraightMove(Position from, Position to, ChessPiece me) {
        if (me.isPawn() && isCross(from, to)) {
            throw new IllegalArgumentException("폰은 대각선에 상대 기물이 존재해야합니다");
        }
    }

    private boolean isCross(Position from, Position to) {
        return to.findDirection(from) != Direction.N && to.findDirection(from) != Direction.S;
    }

    private void checkPawnCrossMove(Position from, Position to, ChessPiece me) {
        if (me.isPawn() && isStraight(from, to)) {
            throw new IllegalArgumentException("폰은 대각선 이동으로 적을 잡을 수 있습니다.");
        }
    }

    private boolean isStraight(Position from, Position to) {
        return to.findDirection(from) == Direction.N || to.findDirection(from) == Direction.S;
    }

    private void movePiece(Position from, Position to, ChessPiece me) {
        checkMate(to);
        chessBoard.put(to, me);
        chessBoard.remove(from);
        currentTurn = currentTurn.toOpposite();
    }

    private void checkMate(Position to) {
        ChessPiece pieceOfTo = chessBoard.get(to);
        if (pieceOfTo != null && pieceOfTo.isKing()) {
            gameStatus = GameStatus.END;
        }
    }


    public int countPiece() {
        return chessBoard.size();
    }

    public Optional<ChessPiece> findPiece(Position position) {
        return Optional.ofNullable(chessBoard.get(position));
    }

    public boolean enemyExist(ChessPiece me, Position to) {
        Optional<ChessPiece> possiblePiece = findPiece(to);
        if (possiblePiece.isEmpty()) {
            throw new IllegalArgumentException("폰은 대각선에 상대 기물이 존재해야합니다");
        }

        ChessPiece piece = possiblePiece.get();
        if (piece.isSameColorPiece(me)) {
            throw new IllegalArgumentException("같은색 기물입니다.");
        }

        return true;
    }

    public Map<Color, Double> calculateScore() {
        Score score = new Score(chessBoard);
        return score.calculateScore();
    }

    public Color decideWinner() {
        return currentTurn.toOpposite();
    }

    public boolean isReady() {
        return gameStatus.isReady();
    }

    public boolean isEnd() {
        return gameStatus.isEnd();
    }

    public boolean isPlaying() {
        return gameStatus.isPlaying();
    }

    public void start() {
        gameStatus = GameStatus.PLAYING;
    }
}
