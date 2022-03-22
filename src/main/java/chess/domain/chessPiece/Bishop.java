package chess.domain.chessPiece;

import java.util.List;

public class Bishop implements ChessPiece {

    private static final String NAME = "B";
    private static final Bishop INSTANCE = new Bishop();

    private Bishop() {
    }

    public static Bishop getInstance() {
        return INSTANCE;
    }

    @Override
    public List<String> getInitWhitePosition() {
        return List.of("c1","f1");
    }

    @Override
    public List<String> getInitBlackPosition() {
        return List.of("c8","f8");
    }

    @Override
    public String getName() {
        return NAME;
    }
}
