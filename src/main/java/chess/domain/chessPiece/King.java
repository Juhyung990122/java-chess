package chess.domain.chessPiece;

import java.util.List;

public class King implements ChessPiece {

    private static final String NAME = "K";
    private static final King INSTANCE = new King();

    private King() {
    }

    public static King getInstance() {
        return INSTANCE;
    }

    @Override
    public List<String> getInitWhitePosition() {
        return List.of("e1");
    }

    @Override
    public List<String> getInitBlackPosition() {
        return List.of("e8");
    }

    @Override
    public String getName() {
        return NAME;
    }

}
