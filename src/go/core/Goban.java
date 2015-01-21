package go.core;

import go.core.exceptions.InvalidGameTurnEncounteredException;
import go.core.exceptions.OutOfGobanException;

import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Goban is a class containing the board of the game, this abstract board also handles the basic rules
 * of the game
 * Created by Thomas on 12/4/2014.
 */
public class Goban {
    /**
     * The width of the goban in number of intersections.
     */
    private final int width;

    /**
     * The height of the goban in number of intersections.
     */
    private final int height;

    /**
     * An table of containing all the intersections of the goban.
     */
    private final Intersection[][] intersections;

    /**
     * A record of the turns of a game played on the goban.
     */
    private GameRecord gameRecord;

    /**
     * Holds the set of the last captured stones, for the GUI
     */
    private Set<Intersection> lastCaptured;

    private Player P1;
    private Player P2;
    private Player actualPlayer;

    private final int initialHandicap;
    private int handicap;

    /**
     * Constructor for a Goban with dimensions width x height.
     * @param width
     * @param height
     */
    public Goban(int width, int height) {
        this.width = width;
        this.height = height;
        this.initialHandicap = 0;

        intersections = new Intersection[width][height];

        initGoban();

        gameRecord = new GameRecord(width, height, handicap);
    }

    /**
     * Constructor with handicap
     * @param width
     * @param height
     * @param handicap
     */
    public Goban(int width, int height, int handicap) {
        this(width,height);
        this.gameRecord = new GameRecord(width, height, handicap);
    }

    private void initGoban() {
        lastCaptured = new HashSet<Intersection>();

        // Initializing players
        P1 = new Player(1);
        P2 = new Player(2);
        actualPlayer = P1;

        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                intersections[x][y] = new Intersection(this, x, y);
            }
        }

        handicap = 0;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    /**
     * Check if the coordinate couple (x,y) is inside the Goban.
     * @param x x coordinate.
     * @param y y coordinate.
     * @return {@code true} if it is,
     * {@code false} otherwise.
     */
    public boolean isInGoban(int x, int y) {
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    /**
     * Check if intersection is inside the goban
     * @param intersection intersection to check
     * @return {@code true} if it is,
     * {@code false} otherwise.
     */
    public boolean isInGoban(Intersection intersection) {
        int x = intersection.getX();
        int y = intersection.getY();
        return (x >= 0 && x < width && y >= 0 && y < height);
    }

    /**
     * Intersection getter for the coordinate couple (x,y).
     * @param x x coordinate.
     * @param y y coordinate.
     * @return the Intersection.
     */
    public Intersection getIntersection(int x, int y) {
        if (isInGoban(x, y)) {
            return intersections[x][y];
        } else {
            return null;
        }
    }

    public int getHandicap() {
        return initialHandicap;
    }

    /**
     *
     * @return game record of the goban.
     */
    public GameRecord getGameRecord() { return gameRecord ; }

    /**
     *
     * @return the set of last captured stones for the GUI
     */
    public Set<Intersection> getLastCaptured() {
        return lastCaptured;
    }

    /**
     * Lets a given player pass.
     * @param player the player that is passing is turn.
     */
    public void pass(Player player) {
        gameRecord.apply(gameRecord.getLastTurn().toNext(-1,-1,player.getIdentifier(), handicap, Collections.<Intersection>emptySet()));
        nextPlayer(false);
    }

    /**
     * Check if intersection is a valid move for the player and record the move if valid.
     * @param intersection position where the player want to play
     * @param player the player playing this move
     * @param handleKo flag to enable or disable ko checking
     * @return {@code true} if it is,the move is valid,
     * {@code false} otherwise.
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
        intersection.setStoneChain(newStoneChain);
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
            currentTurn = gameRecord.getLastTurn().toNext(intersection.getX(),intersection.getY(),player.getIdentifier(),getHandicap(),capturedStones);
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
        if (newStoneChain.getLiberties().size() == 0 || ko) {
            for (StoneChain chain : intersection.getAdjacentStoneChains()) {
                chain.getLiberties().add(intersection);
            }
            intersection.setStoneChain(null);
            return false;
        }

        // Move is valid, applying changes
        for (Intersection stone : newStoneChain.getStones()) {
            stone.setStoneChain(newStoneChain);
        }
        if (handleKo) {
            gameRecord.apply(currentTurn);
        }

        lastCaptured = capturedStones;
        return true;
    }

    /**
     * Check if intersection is a valid move for the player and record the move if valid.
     * Always handles ko.
     * @param intersection position where the player plays.
     * @param player player playing this move.
     * @return {@code true} if it the move is valid,
     * {@code false} otherwise.
     */
    public boolean play(Intersection intersection, Player player) {
        return play(intersection,player,true);
    }

    /**
     * Check if an (x,y) couple is a valid move for the player and record the move if valid.
     * Always handles ko.
     * @param x x coordinate of the position where the player plays.
     * @param y y coordinate of the position where the player plays.
     * @param player player playing this move.
     * @return {@code true} if it the move is valid,
     * {@code false} otherwise.
     */
    public boolean play(int x, int y, Player player) throws OutOfGobanException {
        Intersection intersection = getIntersection(x, y);
        if (intersection == null) {
            throw new OutOfGobanException("Intersection is out of range: x=" + x + " y=" + y);
        }
        return play(intersection, player);
    }

    /**
     *
     * Removes all the stones from the goban
     */
    public void freeIntersections() {
        for(Intersection[] intersectionColumn : intersections) {
            for(Intersection intersection : intersectionColumn) {
                intersection.setStoneChain(null);
            }
        }
    }

    /**
     * Takes a game turn, and fills the goban using the information stored in the Game turn.
     * @param gameTurn the passed GameTurn.
     * @param one Player one, needed to "play" the stones on the goban, identifier should be 1.
     * @param two Player two, needed to "play" the stones on the goban, identifier should be 2.
     * @throws InvalidParameterException | InvalidGameTurnEncounteredException
     */
    public void takeGameTurn(GameTurn gameTurn,Player one, Player two) throws InvalidGameTurnEncounteredException, InvalidParameterException {
        this.freeIntersections();
        if(gameTurn == null || one == null || two == null) throw new InvalidParameterException("None of the Parameters should not be null.");
        if(one.getIdentifier() != 1 || two.getIdentifier() != 2) throw new InvalidParameterException("Incorrect Players entered. One should have an id == 1 and two an id == 2, here one.id = "+one.getIdentifier()+" and two.id = "+two.getIdentifier());
        if(gameTurn.getGobanState().length != width || gameTurn.getGobanState()[0].length != height ) throw new InvalidGameTurnEncounteredException("Incompatible board dimensions between Goban and given GameTurn");

        int[][] gobanState = gameTurn.getGobanState();
        for (int x = 0; x < width ; x++) {
            for (int y = 0; y < height ; y++) {
                switch (gobanState[x][y]) {
                    case 2:
                        play(getIntersection(x,y),two,false);
                        break;
                    case 1:
                        play(getIntersection(x,y),one,false);
                        break;
                    case 0:
                        //DO NOTHING
                        break;
                    default:
                        throw new InvalidGameTurnEncounteredException("Unexpected intersection state encountered: "+gobanState[x][y]);
                }
            }
        }

    }

    /**
     * Undo method for goban
     * @return true if successful, false otherwise
     */
    public boolean undo () {
        if (gameRecord.hasPreceding()) {
            gameRecord.undo();
            try {
                takeGameTurn(gameRecord.getLastTurn(),P1,P2);
                actualPlayer.removeCapturedStones(gameRecord.getLastTurn().getCountCapturedStones());
                precedentPlayer();
                return true;
            } catch (InvalidGameTurnEncounteredException ex) {
                return false;
            }
        } else {
            return false;
        }

    }

    /**
     * Redo method for goban
     * @return true if successful, false otherwise
     */
    public boolean redo() {
        if (gameRecord.hasFollowing()) {
            gameRecord.redo();
            try {
                takeGameTurn(gameRecord.getLastTurn(),P1,P2);
                nextPlayer();
                actualPlayer.addCapturedStones(gameRecord.getLastTurn().getCountCapturedStones());
                return true;
            } catch (InvalidGameTurnEncounteredException ex) {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Get the player that is playing now
     * @return Player that is going to play
     */
    public Player getPlayer() {
        return actualPlayer;
    }

    public boolean nextPlayer() {
        return changePlayer(false);
    }

    public boolean precedentPlayer() {
        return changePlayer(true);
    }

    public boolean changePlayer(boolean undo) {
        if (handicap < initialHandicap && !undo) {
            handicap++;
            return false;
        } else if (undo && this.gameRecord.nbrPreceding() < initialHandicap ) {
            handicap--;
            return false;
        } else {
            if (actualPlayer == P1) {
                actualPlayer = P2;
                System.out.println("Changing player for P2");
            } else {
                actualPlayer = P1;
                System.out.println("Changing player for P1");
            }
            return true;
        }
    }

    /**
     *
     * @return String representation of a goban.
     */
    @Override
    public String toString() {
        String board = "";
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Intersection cross =intersections[x][y];
                if (cross.getStoneChain() == null) {
                    board += "0 ";
                } else {
                    board += cross.getStoneChain().getOwner().getIdentifier()+ " ";
                }
            }
            board += "\n";
        }
        return board;
    }
}
