package magicktest;

import java.io.*;

import java.awt.*;

import junit.framework.*;
import magick.*;
import magick.util.*;

/**
 * Standard tests from Eric Yeos original Test.java converted to JUnit test cases.
 * All these test should pass to certify a JMagick of acceptable quality.
 * The test suite should be possible to run with 'make test'
 * @author Jacob Nordfalk
 * @see Test
 */
public class TestJMagick extends TestCase {

	ImageInfo info;
	MagickImage image;

	/** The version of ImageMagick against which the tests are run.
	 *  This version number is intended to allow for IM version differences */
	int IMver = 638;


	protected void setUp() throws Exception {
		super.setUp();
		info = new ImageInfo();
		image = new MagickImage(new ImageInfo(Testtools.path_input + "pics.jpg"));
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		image.destroyImages();
	}

	/**
	 * Test if diverse operations on a small image is processed correctly
	 * Values on Linux with JMagick 6.2.8 and IM 6.2.8:
	 <pre>
	 Scaled to 60x30
	 Depth 8
	 Quality is 75
	 Colorspace is 1
	 Resolution units is 1
	 X resolution is 72.0
	 Y resolution is 72.0
	 Size blob is 4538
	 Colors 0
	 Total colors 0
	 Depth is 8
	 Old colour PixelPacket(255,255,255,0)
	 New colour PixelPacket(255,0,0,0)
	 </pre>
	 */
	public void testDiverseOperations() throws Exception {
		Rectangle rect = new Rectangle(0, 0, 80, 40);
		int flags = Magick.parseImageGeometry("60x50", rect);
		assertEquals("Scaled to ", 60, rect.width);
		assertEquals("Scaled to ", 30, rect.height);

		// Copy an image.
		ImageInfo info = new ImageInfo(Testtools.path_input + "pics.jpg");
		info.setPage("50x50+0+0");
		info.setUnits(ResolutionType.PixelsPerInchResolution);
		info.setColorspace(ColorspaceType.RGBColorspace);
		info.setBorderColor(PixelPacket.queryColorDatabase("red"));
		if (IMver > 557) { // Doesent exists in IM557
			info.setDepth(8);
			assertEquals("Depth ", 8, info.getDepth());
			info.setDepth(0);
		}
		image = new MagickImage(info);
		image.setImageAttribute("Comment", "Processed by JMagick");
		if (IMver > 557) {
			assertEquals("Quality is ", 75, image.getQuality());
		}
		assertEquals("Colorspace is ", 1, image.getColorspace());
		assertEquals("Resolution units is ", 1, image.getUnits());
		assertEquals("X resolution is ", 72, image.getXResolution(), 0.1);
		assertEquals("Y resolution is ", 72, image.getYResolution(), 0.1);
		assertEquals("Size blob is ", 4538, image.sizeBlob());
		assertEquals("Colors ", 0, image.getColors());
		assertEquals("Total colors ", 0, image.getTotalColors());
		assertEquals("Depth is ", 8, image.getDepth());
		image.signatureImage();
		Testtools.writeAndCompare(image, info, "copy.jpg");

		// Background Color
		if (IMver > 557) { // As .equals() is not implementet we compare the strings
			assertEquals("Old colour ", image.getBackgroundColor().toString(),
									 new PixelPacket(255, 255, 255, 0).toString());
			image.setBackgroundColor(PixelPacket.queryColorDatabase("red"));
			assertEquals("New colour ", image.getBackgroundColor().toString(),
									 new PixelPacket(255, 0, 0, 0).toString());
		}

		// Profile test
		assertEquals("Number of generic profiles ", 0, image.getGenericProfileCount());
		image.setColorProfile(new ProfileInfo("Test", new byte[20]));
		//assertEquals( "Test", image.getColorProfile().getName()); // names are not stored in IM any more
		assertEquals(20, image.getColorProfile().getInfo().length);

		// Border image
		image.setBorderColor(PixelPacket.queryColorDatabase("green"));
		MagickImage borderedImage = image.borderImage(new Rectangle(0, 0, 10, 20));
		// In IM 557 the profile wont load from file
		if (IMver > 557) {
			Testtools.writeAndCompare(borderedImage, info, "border.jpg");
		}

	}

	public void testRaise() throws Exception {
		// Raise image
		image.raiseImage(new Rectangle(0, 0, 10, 20), true);
		Testtools.writeAndCompare(image, info, "raised.jpg");
	}


	public void testMontage() throws Exception {
		// Montage test
		MagickImage images[] = new MagickImage[2];
		images[0] = image;
		images[1] = image.rotateImage(180.0);
		MagickImage seqImage = new MagickImage(images);
		MontageInfo montageInfo = new MontageInfo(new ImageInfo());
		montageInfo.setFileName("montage.jpg");
		montageInfo.setBorderWidth(5);
		MagickImage montage = seqImage.montageImages(montageInfo);
		Testtools.writeAndCompare(montage, new ImageInfo(), "montage.jpg");
	}


	/**
	 * Test montage.
	 * Expect this test to fail when the font set change
	 */
	public void testMontageWithText_failMightBeOk() throws Exception {
		// Montage test
		MagickImage images[] = new MagickImage[2];
		images[0] = image;
		images[1] = image.rotateImage(180.0);
		MagickImage seqImage = new MagickImage(images);
		MontageInfo montageInfo = new MontageInfo(new ImageInfo());
		montageInfo.setTitle("Melbourne");
		montageInfo.setBorderWidth(5);
		MagickImage montage = seqImage.montageImages(montageInfo);
		Testtools.writeAndCompare(montage, new ImageInfo(), "montage_w_text.jpg");
	}

	/**
	 * Test if diverse operations on a small image is processed correctly
	 * Values on Linux with JMagick 6.2.8 and IM 6.2.8:
	 <pre>
	 Length 22687
	 Blob width is 198
	 Blob heght is 134
	 java.awt.Dimension[width=198,height=134]
	 </pre>
	 */

	public void testAverage() throws Exception {
		MagickImage images[] = new MagickImage[2];
		images[0] = image;
		images[1] = image.rotateImage(180.0);

		MagickImage seqImage = new MagickImage(images);

		// Test average
		MagickImage average = seqImage.averageImages();
		Testtools.writeAndCompare(average, new ImageInfo(), "average.jpg");
	}

	public void testBlob() throws Exception {
		// Converting the montage into a blob
		image.setMagick("JPG");
		byte[] mblob = image.imageToBlob(new ImageInfo());
		System.out.println("Blob length " + mblob.length);

		// Image to blob
		info = new ImageInfo();
		byte[] blob = image.imageToBlob(info);

		// Blob to image
		info = new ImageInfo();
		MagickImage blobImage = new MagickImage(info, blob);
		Dimension imageDim = blobImage.getDimension();
		assertEquals("Blob width is ", 198, imageDim.width);
		assertEquals("Blob heght is ", 134, imageDim.height);
		Testtools.writeAndCompare(blobImage, info, "blob.jpg");

		// JPEG Image to GIF blob
		image.setMagick("GIF");
		blob = image.imageToBlob(info);
		FileOutputStream out = new FileOutputStream(Testtools.path_actual_output + "blob.gif");
		out.write(blob);
		out.close();
		Testtools.compareImage(Testtools.path_actual_output + "blob.gif", Testtools.path_correct_output + "blob.gif", 20);
	}

	/**
	 * Test of diverse operations on a small is processed  correctly
	 */
	public void testRotShearScale() throws Exception {
		// Rotation and shear
		MagickImage rotated = image.rotateImage(45.0);
		Testtools.writeAndCompare(rotated, info, "rotated.jpg");
		MagickImage sheared = image.shearImage(50.0, 10.0);
		Testtools.writeAndCompare(sheared, info, "sheared.jpg");
		MagickImage scaled = image.scaleImage(100, 80);
		Testtools.writeAndCompare(scaled, info, "scaled.jpg");
	}

	public void testCloning() throws Exception {
		// Cloning
		Dimension imageDim = image.getDimension();
		assertEquals("Width is ", 198, imageDim.width);
		assertEquals("Height is ", 134, imageDim.height);
		assertEquals("Depth is ", 8, image.getDepth());
		assertEquals("Storage class is ", 1, image.getStorageClass());
		assertEquals("Comment is ", "CREATOR: XV Version 3.10a  Rev: 12/29/94 (PNG patch 1.2)  Quality = 100, Smoothing = 0\n", image.getImageAttribute("Comment"));
		MagickImage clonedImage = image.cloneImage(0, 0, false);
		Testtools.writeAndCompare(clonedImage, info, "clone.jpg");
	}

	/**
	 * Test of diverse operations on a small is processed  correctly
	 */
	public void testQuantizeImage() throws Exception {

		// Quantization
		MagickImage quantizedImage = image;
		QuantizeInfo quantizeInfo = new QuantizeInfo();
		quantizeInfo.setColorspace(ColorspaceType.GRAYColorspace);
		quantizeInfo.setNumberColors(256);
		quantizeInfo.setTreeDepth(4);
		assertTrue("QuantizeImage ", quantizedImage.quantizeImage(quantizeInfo));
		assertEquals("Colors ", 235, quantizedImage.getColors());
		assertEquals("Total colors ", 0, quantizedImage.getTotalColors());
		Testtools.writeAndCompare(quantizedImage, info, "quantized.png");
	}

	public void testConstituteDrawTransparent() throws Exception {

		// Create an image from scratch
		MagickImage blankImage = new MagickImage();
		byte[] pixels = new byte[200 * 100 * 4];
		for (int i = 0; i < 200 * 100; i++) {
			pixels[4 * i] = (byte) 255;
			pixels[4 * i + 1] = (byte) 255;
			pixels[4 * i + 2] = (byte) 255;
			pixels[4 * i + 3] = (byte) 0;
		}
		blankImage.constituteImage(200, 100, "RGBA", pixels);

		// Put a red rectangle around the border
		DrawInfo drawInfo = new DrawInfo(new ImageInfo());
		drawInfo.setPrimitive("Rectangle 10 10 190 90");
		drawInfo.setStroke(PixelPacket.queryColorDatabase("red"));
		drawInfo.setFill(PixelPacket.queryColorDatabase("white"));
		blankImage.drawImage(drawInfo);
		Testtools.writeAndCompare(blankImage, info, "blank.jpg");

		// Make the white page of the image transparent
		blankImage.transparentImage(
				PixelPacket.queryColorDatabase("white"), 65535);

		//info.setMagick("PNG");
		Testtools.writeAndCompare(blankImage, info, "transparent.jpg");
	}

	public void testAnnotate_failMightBeOk() throws Exception {

		// Create an image from scratch
		MagickImage blankImage = new MagickImage();
		byte[] pixels = new byte[200 * 100 * 4];
		for (int i = 0; i < 200 * 100; i++) {
			pixels[4 * i] = (byte) 255;
			pixels[4 * i + 1] = (byte) 255;
			pixels[4 * i + 2] = (byte) 255;
			pixels[4 * i + 3] = (byte) 0;
		}
		blankImage.constituteImage(200, 100, "RGBA", pixels);

		// Annotate the image with a green Hello
		ImageInfo blankImageInfo = new ImageInfo();
		DrawInfo annotateInfo = new DrawInfo(blankImageInfo);
		annotateInfo.setOpacity(0);
		annotateInfo.setFont("fixed");
		annotateInfo.setFill(PixelPacket.queryColorDatabase("green"));
		annotateInfo.setText("Hello");
		annotateInfo.setGeometry("+30+30");
		blankImage.annotateImage(annotateInfo);

		Testtools.writeAndCompare(blankImage, info, "blank_w_text.jpg");
	}

	public void testCropChop() throws Exception {
		Rectangle rect;

		// Crop image
		rect = new Rectangle(20, 20, 150, 120);
		MagickImage cropped = image.cropImage(rect);
		Testtools.writeAndCompare(cropped, info, "cropped.jpg");

		// Chop image
		rect = new Rectangle(0, 0, 150, 120);
		MagickImage chopped = image.chopImage(rect);
		Testtools.writeAndCompare(chopped, info, "chopped.jpg");
	}

	public void testSharpen() throws Exception {
		// Sharpen image
		MagickImage sharpened = image.sharpenImage(1.0, 5.0);
		Testtools.writeAndCompare(sharpened, info, "sharpened.jpg");
	}

	public void testDespeckle() throws Exception {
		// Despeckle image
		MagickImage despeckled = image.despeckleImage();
		Testtools.writeAndCompare(despeckled, info, "despeckled.jpg");
	}

	public void testConvolve() throws Exception {

		// Convolve image
		double[] kernel = new double[9];
		for (int i = 0; i < 9; i++) {
			kernel[i] = 1.0;
		}
		kernel[4] = 2.0;
		MagickImage convolvedImage = image.convolveImage(3, kernel);
		Testtools.writeAndCompare(convolvedImage, info, "convolved.jpg");

		// Finally display the image.
		MagickWindow window = new MagickWindow(image);
		window.setVisible(true);

		// MagickInfo test
		MagickInfo minfo = new MagickInfo("JPEG");
		assertEquals("JPG description: ", "Joint Photographic Experts Group JFIF format", minfo.getDescription());
	}

}
