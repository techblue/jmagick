package magick;


import java.awt.Rectangle;


/**
 * The sole purpose of this class is to cause the native
 * library to be loaded whenever a concrete class is used
 * and provide utility methods.
 *
 * @author Eric Yeo
 * @author Max Kollegov &lt;virtual_max@geocities.com&gt;
 */
public class Magick {

    static {
        String bprop = System.getProperty("jmagick.usingbundledlib");
        if (!"yes".equalsIgnoreCase(bprop)) {
            String clprop = System.getProperty("jmagick.systemclassloader");
            if (clprop == null || clprop.equalsIgnoreCase("yes")) {
                try {
                    ClassLoader.getSystemClassLoader()
                        .loadClass("magick.MagickLoader").newInstance();
                }
                catch(ClassNotFoundException e) {
                    throw new RuntimeException("Can't load MagickLoader " +
                                               "(class not found)");
                }
                catch(IllegalAccessException e) {
                    throw new RuntimeException("Access to SystemClassLoader "+
                                               "denied (IllegalAccessException)");
                }
                catch(InstantiationException e) {
                    throw new RuntimeException("Can't instantiate MagicLoader " +
                                               "(InstantiationException)");
                }
            }
            else {
                System.loadLibrary("JMagick");
            }
        }
        init();
    }


    /**
     * Initializes the ImageMagic system
     */
    private static native void init();


    /**
     * Parses a geometry specification and returns the
     * width, height, x, and y values in the rectangle.
     * It also returns flags that indicates which of the
     * four values (width, height, xoffset, yoffset) were
     * located in the string, and whether the x and y values
     * are negative.  In addition, there are flags to report
     * any meta characters (%, !, &lt;, and &gt;).
     * @param geometry String containing the geometry specifications
     * @param rect The rectangle of values x, y, width and height
     * @return bitmask indicating the values in the geometry string
     * @see magick.GeometryFlags
     */
    public static native int parseImageGeometry(String geometry,
                                                Rectangle rect);

    /**
     * Gets an array of the names of the fonts that ImageMagick has that match the pattern
     *
     * @param pattern The query pattern
     *
     * @return array of font names.
     */
    public static native String[] queryFonts(String pattern);
}
