package go.core;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        GameRecordTest.class,
        GameTurnTest.class,
        GobanTest.class,
        IntersectionTest.class,
        PlayerTest.class,
        StoneChainTest.class
})
public class AllTests {

}



