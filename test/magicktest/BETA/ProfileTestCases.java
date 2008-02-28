package magicktest.BETA;

import junit.framework.*;
import magick.*;
import magicktest.*;

public class ProfileTestCases extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
		MagickTesttools.allocateAndFreeSomeMem((int)(1000000*Math.random()));
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		MagickTesttools.allocateAndFreeSomeMem((int)(1000000*Math.random()));
	}


	/**
	 * This crashes on Jmagick-6.2.8
	 * See http://www.yeo.id.au/mailman/private/jmagick/2007-June/002606.html
	 */
	public void testIptcJM626Crash() throws Exception
	{
		for (int i=0; i<50; i++)
		{
			ImageInfo info = new ImageInfo(MagickTesttools.path_input+ "img_iptc8006_exif2829_icc560.jpg");
			MagickImage image = new MagickImage(info);
			ProfileInfo profile = image.getIptcProfile();
			image.setIptcProfile(profile);
			System.gc();
			MagickTesttools.displayProfile(profile);
			// identify -verbose gives: Profile-8bim: 8006 bytes
			assertEquals(8006, profile.getInfo().length);
			MagickTesttools.allocateAndFreeSomeMem((int)(1000000*Math.random()));
		}
	}

	/**
	 * This crashes on Jmagick-6.2.8
	 * See http://www.yeo.id.au/mailman/private/jmagick/2007-June/002606.html
	 */
	public void testIccJM626Crash() throws Exception {
		for (int i=0; i<50; i++)
		{
			ImageInfo info = new ImageInfo(MagickTesttools.path_input+ "img_iptc8006_exif2829_icc560.jpg");
			MagickImage image = new MagickImage(info);
			ProfileInfo profile = image.getColorProfile();
			//image.setColorProfile(profile);
			System.gc();
			MagickTesttools.displayProfile(profile);
			// identify -verbose gives:   Profile-icc: 560 bytes
			assertEquals(560, profile.getInfo().length);
			MagickTesttools.allocateAndFreeSomeMem((int)(1000000*Math.random()));
		}
	}


	/**
	 * This test will fail unless this fix is installed:
	 *
	 */
	public void testReadBigIccJpg() throws Exception {
		ImageInfo info = new ImageInfo(MagickTesttools.path_input + "big_icc_1827908_bytes.jpg");
		MagickImage image = new MagickImage(info);
		ProfileInfo profile = image.getColorProfile();
		MagickTesttools.displayProfile(profile);
		// identify -verbose should give:   Profile-icc: 1827908 bytes
		assertEquals(1827908, profile.getInfo().length);
	}


	/**
	 */
	public void testReadBigIptcJpg() throws Exception {
		ImageInfo info = new ImageInfo(MagickTesttools.path_input + "big_iptc_1477154_bytes_clippath.jpg");
		MagickImage image = new MagickImage(info);
		ProfileInfo profile = image.getIptcProfile();
		MagickTesttools.displayProfile(profile);
		// identify -verbose should give:   Profile-iptc: 1477154 bytes
		assertEquals(1477154, profile.getInfo().length);
	}


	/**
	 * This test will fail unless this fix is installed:
	 *
	 */
	public void testReadWriteBigIptcProfilesJpgTif() throws Exception {
		MagickImage image = new MagickImage(new ImageInfo(MagickTesttools.path_input + "pics.jpg"));

		MagickImage image_iptc = new MagickImage(new ImageInfo(MagickTesttools.path_input + "big_iptc_1477154_bytes_clippath.jpg"));
		ProfileInfo profile_iptc = image_iptc.getIptcProfile();
		MagickTesttools.displayProfile(profile_iptc);

		image.setIptcProfile(profile_iptc);

		// Test it has been stored
		ProfileInfo profile = image.getIptcProfile();
		MagickTesttools.displayProfile(profile);
		//assertTrue( Arrays.equals(profile_iptc.getInfo(), profile.getInfo()) );

		// Test TIF write
		// Not working on IM 6.2.6 becaurse of IM bug:
		// http://www.imagemagick.org/discourse-server/viewtopic.php?f=3&t=9067&hilit=
		MagickTesttools.writeAndCompare(image, new ImageInfo(), "pics_w_1477154_bytes_clippath.tif");
		MagickTesttools.writeAndCompare(image, new ImageInfo(), "pics_w_1477154_bytes_clippath.jpg");


		MagickImage writtenimage = new MagickImage(new ImageInfo(MagickTesttools.path_actual_output + "pics_w_1477154_bytes_clippath.tif"));
		assertEquals(1477154,writtenimage.getIptcProfile().getInfo().length);


		assertTrue(MagickTesttools.compareImageProfiles(writtenimage, image));

		// Test JPG write
		// Not working on IM 6.2.6 becaurse of IM bug:
		// http://www.imagemagick.org/discourse-server/viewtopic.php?f=3&t=9067&hilit=
		// Testtools.writeAndCompare(image, new ImageInfo(), "pics_w_1477154_bytes_clippath.jpg");
	}




	/**
	 * This test will fail unless this fix is installed:
	 *
	 */
	public void testReadWriteBigIccProfilesJpgTif() throws Exception {
		MagickImage image = new MagickImage(new ImageInfo(MagickTesttools.path_input + "pics.jpg"));

		MagickImage image_icc = new MagickImage(new ImageInfo(MagickTesttools.path_input + "big_icc_1827908_bytes.jpg"));
		ProfileInfo profile_icc = image_icc.getColorProfile();
		MagickTesttools.displayProfile(profile_icc);

		image.setColorProfile(profile_icc);

		// Test it has been stored
		ProfileInfo profile = image.getColorProfile();
		MagickTesttools.displayProfile(profile);

		// Test TIF write
		// Not working on IM 6.2.6 becaurse of IM bug:
		// http://www.imagemagick.org/discourse-server/viewtopic.php?f=3&t=9067&hilit=
		MagickTesttools.writeAndCompare(image, new ImageInfo(), "pics_w_icc_1827908_bytes.tif");

		MagickImage writtenimage = new MagickImage(new ImageInfo(MagickTesttools.path_actual_output + "pics_w_icc_1827908_bytes.tif"));

		assertEquals(1827908,writtenimage.getColorProfile().getInfo().length);
		assertTrue(MagickTesttools.compareImageProfiles(writtenimage, image));

		// Test JPG write
		// Not working on IM 6.2.6 becaurse of IM bug:
		// http://www.imagemagick.org/discourse-server/viewtopic.php?f=3&t=9067&hilit=
		MagickTesttools.writeAndCompare(image, new ImageInfo(), "pics_w_icc_1827908_bytes.jpg");
	}



	public static void main(String[] args) throws Exception {
		ProfileTestCases o = new ProfileTestCases();
		o.testReadBigIccJpg();

		for (int i=0; i<5000; i++)
		{
			o.testReadBigIptcJpg();
			o.testReadBigIccJpg();
			MagickTesttools.allocateAndFreeSomeMem((int)(1000000*Math.random()));
		}

	}



}
