package magicktest;

import magick.ImageInfo;
import magick.MagickImage;

public class Loop {

    static {
	System.loadLibrary("JMagick");
    }

    public static void main(String[] args) throws Exception {
	while(true) {

            //  java.lang.Runtime.getRuntime().gc();
            //  java.lang.Runtime.getRuntime().runFinalization();

	    ImageInfo info = new ImageInfo(args[0]);
	    MagickImage image = new MagickImage(info);
	    image.setFileName("copy_" + args[0]);
	    image.writeImage(info);
            MagickImage thumb = image.scaleImage(10, 10);
	    Thread.sleep(1000);

            // Force finalization and garbage collection.
            System.runFinalization();
            System.gc();
	}
    }
}
