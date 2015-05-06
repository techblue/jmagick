package magick;

/**
 * The sole purpouse of this class is to cause the native
 * library to be loaded in SYSTEM classloader whenever a
 * concrete class is used.
 *
 * @author Max Kollegov &lt;Virtual_Max@geocities.com&gt;
 */
public class MagickLoader {
    
    static {      
		String jniFile = System.getProperty("jmagick.lib");
		// defaults:
		if (jniFile == null) {
			jniFile="JMagick";
		}
		if (jniFile.contains("/") || jniFile.contains("\\")) { // if a path is specified expect something like "/tmp/libJMagick-6.5.5.so
			System.load(jniFile);
		} else {
			System.loadLibrary(jniFile);
		}
    }
    
}
