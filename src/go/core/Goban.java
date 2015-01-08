package go.core;

import java.util.HashSet;
import java.util.Set;

/**
 * Goban is a class containing the board of the game, this abstract board also handles the basic rules
 * of the game
 * Created by Thomas on 12/4/2014.
 */
public class Goban {
    private final int width;
    private final int height;
    private final Intersection[][] intersections;
    private final GameRecord gameRecord;

    public Goban(int width, int height) {
        this.width = width;
        this.height = height;
        intersections = new Intersection[width][height];

        for(int x=0; x<this.width; x++) {
            for (int y=0; y<this.height; y++) {
                intersections[x][y] = new Intersection(this, x, y);
            }
        }

        gameRecord = new GameRecord(width,height);
    }

    /**
     * Check if (x,y) is inside the Goban
     * @param x x coord
     * @param y y coord
     * @return boolean true if in Goban, false otherwise
     */
    public boolean isInGoban(int x, int y) {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    /**
     * Check if intersection is inside the goban
     * @param intersection intersection to check
     * @return boolean true if in Goban, false otherwise
     */
    public boolean isInGoban(Intersection intersection) {
        int x = intersection.getX();
        int y = intersection.getY();
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    /**
     * Intersection getter at (x,y)
     * @param x x coord
     * @param y y coord
     * @return the Intersection
     */
    public Intersection getIntersection(int x, int y) {
        if (isInGoban(x, y)) {
            return intersections[x][y];
        } else {
            return null;
        }
    }

    /**
     * Game record getter
     * @return game record of the goban
     */
    public GameRecord getGameRecord() { return gameRecord ; }

    /**
     * check if intersection is a valid move for the player and record the move if valid
     * @param intersection position where the player want to play
     * @param player the player playing this move
     * @param handleKo flag to enable or disable ko checking
     * @return true if the move is valid, false otherwise
     */
    public boolean play(Intersection intersection, Player player, boolean handleKo) {

        boolean ko = false;
        GameTurn currentTurn = null;

        // Should be in goban
        if (!isInGoban(intersection)) return false;

        // Preventing playing over another stone
        if (intersection.getStoneChain() != null) return false;

        Set<Intersection> capturedStones = null;
        Set<StoneChain> capturedStoneChains = null;

        if (handleKo) {
            capturedStones = new HashSet<Intersection>();
            capturedStoneChains = new HashSet<StoneChain>();
        }

        Set<StoneChain> adjStoneChains = intersection.getAdjacentStoneChains();
        StoneChain newStoneChain = new StoneChain(intersection, player);
        for (StoneChain stoneChain : adjStoneChains) {
            if (stoneChain.getOwner() == player) {
                newStoneChain.add(stoneChain, intersection);
            } else {
                stoneChain.removeLiberty(intersection);
                if (stoneChain.getLiberties().size() == 0) {
                    if(handleKo) {
                        capturedStones.addAll(stoneChain.getStones());
                        capturedStoneChains.add(new StoneChain(stoneChain));
                    }
                    stoneChain.die();
                }
            }
        }

        if (handleKo) {
            currentTurn = gameRecord.getLastTurn().toNext(intersection.getX(),intersection.getY(),player.getIdentifier(),capturedStones);
            for (GameTurn turn : gameRecord.getTurns()) {
                if (turn.equals(currentTurn)) {
                    ko = true;
                    break;
                }
            }
            if (ko) {
                for (StoneChain chain : capturedStoneChains) {
                    chain.getOwner().removeCapturedStones(chain.getStones().size());
                    for (Intersection stone : chain.getStones()) {
                        stone.setStoneChain(chain);
                    }
                }
            }
        }

        // Preventing suicide or ko and re-adding liberty
        if (newStoneChain.getLiberties().size() == 0 | ko) {
            for (StoneChain chain : intersection.getAdjacentStoneChains()) {
                chain.getLiberties().add(intersection);
            }
            return false;
        }

        // Move is valid, applying changes
        for (Intersection stone : newStoneChain.getStones()) {
            stone.setStoneChain(newStoneChain);
        }
        if (handleKo) {
            gameRecord.apply(currentTurn);
        }
        return true;
    }

    /**
     * check if intersection is a valid move for the player and record the move if valid
     * always handles ko
     * @param intersection position where the player plays
     * @param player player playing this move
     * @return true if move is valid, false otherwise
     */
    public boolean play(Intersection intersection, Player player) {
        return play(intersection,player,true);
    }

    /**
     *
     * removes the stones from the goban
     */
    public void freeIntersections() {
        for(Intersection[] intersectionColumn : intersections) {
            for(Intersection intersection : intersectionColumn) {
                intersection.setStoneChain(null);
            }
        }
    }

    /**
     * Takes a game turn, and fills the goban using the information stored in the Game turn
     * @param gameTurn the passed GameTurn
     * @param one Player one, needed to "play" the stones on the goban, identifier should be 1
     * @param two Player two, needed to "play" the stones on the goban, identifier should be 2
     * @throws Exception
     */
    public void takeGameTurn(GameTurn gameTurn,Player one, Player two) throws Exception {
        this.freeIntersections();
        if(gameTurn == null || one == null || two == null) throw new Exception("Parameteres should not be null.");
        if(one.getIdentifier() != 1 || two.getIdentifier() != 2) throw new Exception("Incorrect Players entered.");
        if(gameTurn.getGobanState().length != width || gameTurn.getGobanState()[0].length != height ) throw new Exception("Incompatible board dimensions between goban and given GameTurn");

        int[][] gobanState = gameTurn.getGobanState();
        for (int i = 0; i < width ; i++) {
            for (int j = 0; j < height ; j++) {
                switch (gobanState[i][j]) {
                    case 2:
                        play(getIntersection(i,j),two,false);
                        break;
                    case 1:
                        play(getIntersection(i,j),one,false);
                        break;
                    case 0:
                        //DO NOTHING
                        break;
                    default:
                        throw new Exception("Unexpected intersection state encountered in the GameTurn");
                }
            }
        }

    }
}
