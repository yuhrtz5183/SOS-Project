package sprint0;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ CountATest.class, SquareTest.class, GUITest.class })
public class AllTests {

}
