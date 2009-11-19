package magicktest;

import junit.framework.*;
import junit.extensions.*;
import junit.textui.TestRunner;

/**
 * Loops through the tests in TestJMagick 100 time, to check for memory corruption
 * @author Jacob Nordfalk
 */
public class LoopTestJMagick {

  public static void main(String[] args) throws Throwable {
    TestSuite suite = new TestSuite();
    suite.addTestSuite(TestJMagick.class);
    TestRunner aTestRunner = new TestRunner();

    TestJMagick.popupWindow = false;
    MagickTesttools.dont_compare_output = true;


    for (int i = 0; i < 100; i++) {
      long now = System.currentTimeMillis();
      suite.run(new TestResult());
      System.out.println("\n=======" + i + "======= took "+(System.currentTimeMillis()-now)*0.001);
      if (i % 10 == 0)
        System.gc();
    }
    System.out.println("DONE");
  }
}
