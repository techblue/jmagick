package magicktest;

import junit.framework.*;
import junit.extensions.*;

public class TestsRepeated
    extends TestCase {

  public TestsRepeated(String s) {
    super(s);
  }

  public static Test suite() {
    TestSuite suite = new TestSuite();
    TestJMagick.popupWindow = false;
    for (int i=0; i<100; i++)
      suite.addTestSuite(TestJMagick.class);
    //suite.addTest(new RepeatedTest(new TestJMagick(), 100));
    return suite;
  }
}
