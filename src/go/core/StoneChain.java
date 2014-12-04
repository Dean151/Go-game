package go.core;

/**
 * Represents a stone chain, of horizontally and vertically connected stones of a same color
 * It also keeps track ov the horizontal and vertical vacancies;
 * Created by Thomas on 12/4/2014.
 */
public class StoneChain {
    IntersectionCollection positions;
    IntersectionCollection liberties;
    Player owner;
}
