package magicktest.BETA;

import java.util.*;

import magick.*;
import magick.util.*;

public class NyeProfilmetoder {
	public static void main(String[] args) throws Exception {

		//MagickImage image = new MagickImage(new ImageInfo(Testtools.path_input+ "img_iptc6376_icc560.jpg"));
		MagickImage image = new MagickImage(new ImageInfo("/home/j/Caramba/CIS2koersel/test/inputbilleder/clip_test.tif"));

		DisplayImageMetaData.displayMagickImage(image);

		visProfil(image, "8bim");
		visProfil(image, "iptc");
		visProfil(image, "icc");
		visProfil(image, "exif");
		visProfil(image, "xmp");

		visProfil(image, "8bim");
		visProfil(image, "iptc");
		visProfil(image, "icc");
		visProfil(image, "exif");
		visProfil(image, "xmp");

	}

	/**
	 * visProfil
	 *
	 * @param image MagickImage
	 * @param string String
	 */
	private static void visProfil(MagickImage image, String navn) throws Exception {
		byte[] b = image.getImageProfile(navn);
		if (b==null) {
			System.out.println(navn+" =null");
		} else {
			//System.out.println(navn+" lgd="+b.length+Arrays.toString(b));
			System.out.println(navn+" lgd="+b.length);
		}
		image.setImageProfile(navn,b);
	}
}
