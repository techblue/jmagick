package magicktest;

import java.awt.Dimension;
import java.awt.Rectangle;
import magick.ImageInfo;
import magick.MagickImage;
import magick.MagickException;
import magick.QuantizeInfo;
import magick.ColorspaceType;
import magick.util.MagickWindow;
import magick.MagickApiException;
import magick.PixelPacket;
import magick.DrawInfo;
import magick.ResolutionType;
import magick.ProfileInfo;
import magick.MontageInfo;
import magick.Magick;
import magick.MagickInfo;
import java.io.IOException;
import java.io.FileOutputStream;

/**
 * For testing JMagick functions.
 * This 'test' should be run with 'make simpletest'. Output images will then go in
 * These tests are also available as real JUnit test cases in the class MainTestCase
 *
 * @see MainTestCase
 * @author Eric Yeo
 */
public class OldSimpleTest {

    /**
     * Display the information about the profile supplied.
     *
     * @param profile
     *            the profile for which to display
     */
    private static void displayProfile(ProfileInfo profile) {
			  MagickTesttools.displayProfile(profile);
    }

    public static void main(String[] args) {
        try {

            Rectangle rect = new Rectangle(0, 0, 80, 40);
            int flags = Magick.parseImageGeometry("60x50", rect);
            System.out.println("Scaled to " + rect.width + "x" + rect.height);

            // Copy an image.
            ImageInfo info = new ImageInfo("pics.jpg");
            info.setPage("50x50+0+0");
            info.setUnits(ResolutionType.PixelsPerInchResolution);
            info.setColorspace(ColorspaceType.RGBColorspace);
            info.setBorderColor(PixelPacket.queryColorDatabase("red"));
            info.setDepth(8);
            System.out.println("Depth "+info.getDepth());
            info.setDepth(0);
            MagickImage image = new MagickImage(info);
            image.setImageAttribute("Comment", "Processed by JMagick");
            System.out.println("Quality is "+image.getQuality());
            System.out.println("Colorspace is " + image.getColorspace());
            System.out.println("Resolution units is " + image.getUnits());
            System.out.println("X resolution is " + image.getXResolution());
            System.out.println("Y resolution is " + image.getYResolution());
            System.out.println("Size blob is " + image.sizeBlob());
            System.out.println("Colors " + image.getColors());
            System.out.println("Total colors " + image.getTotalColors());
            System.out.println("Depth is "+image.getDepth());
            System.out.println("Matte is "+image.getMatte());
            image.setMatte(false);
            image.signatureImage();
            image.setFileName("copy.jpg");
            image.writeImage(info);

            // Background Color
            System.out.println("Old colour " + image.getBackgroundColor());
            image.setBackgroundColor(PixelPacket.queryColorDatabase("red"));
            System.out.println("New colour " + image.getBackgroundColor());

            // Border image
            image.setBorderColor(PixelPacket.queryColorDatabase("green"));
            MagickImage borderedImage = image.borderImage(new Rectangle(0, 0,
                    10, 20));
            borderedImage.setFileName("border.jpg");
            borderedImage.writeImage(info);

            // Raise image
            MagickImage raisedImage = new MagickImage(new ImageInfo("pics.jpg"));
            raisedImage.raiseImage(new Rectangle(0, 0, 10, 20), true);
            raisedImage.setFileName("raised.jpg");
            raisedImage.writeImage(info);

            // Profile test
            System.out.println("Number of generic profiles "
                    + image.getGenericProfileCount());
            displayProfile(image.getColorProfile());
            image.setColorProfile(new ProfileInfo());
            displayProfile(image.getColorProfile());
            image.setColorProfile(new ProfileInfo("Test", new byte[20]));
            displayProfile(image.getColorProfile());

            // Montage test
            MagickImage images[] = new MagickImage[2];
            images[0] = image;
            images[1] = image;
            MagickImage seqImage = new MagickImage(images);
            MontageInfo montageInfo = new MontageInfo(new ImageInfo());
            montageInfo.setFileName("montage.jpg");
            montageInfo.setTitle("Melbourne");
            montageInfo.setBorderWidth(5);
            MagickImage montage = seqImage.montageImages(montageInfo);
            montage.writeImage(new ImageInfo());

            // Converting the montage into a blob
            montage.setMagick("JPG");
            byte[] mblob = montage.imageToBlob(new ImageInfo());
            System.out.println("Length "+mblob.length);

            // Test average
            MagickImage average = seqImage.averageImages();
            average.setFileName("average.jpg");
            average.writeImage(new ImageInfo());

            // Image to blob
            info = new ImageInfo();
            byte[] blob = image.imageToBlob(info);

            // Blob to image
            info = new ImageInfo();
            MagickImage blobImage = new MagickImage(info, blob);
            Dimension imageDim = blobImage.getDimension();
            System.out.println("Blob width is " + imageDim.width);
            System.out.println("Blob heght is " + imageDim.height);
            System.out.println(imageDim);
            blobImage.setFileName("blob.jpg");
            blobImage.writeImage(info);

            // JPEG Image to GIF blob
            image.setMagick("GIF");
            blob = image.imageToBlob(info);
            try {
                FileOutputStream out = new FileOutputStream("blob.gif");
                out.write(blob);
                out.close();
            }
            catch (IOException ex) {
                System.out.println("Unable to write blob to file: " + ex);
            }

            // Rotation and shear
            image = new MagickImage(new ImageInfo("pics.jpg"));
            MagickImage rotated = image.rotateImage(45.0);
            rotated.setFileName("rotated.jpg");
            rotated.writeImage(info);
            MagickImage sheared = image.shearImage(50.0, 10.0);
            sheared.setFileName("sheared.jpg");
            sheared.writeImage(info);
            MagickImage scaled = image.scaleImage(100, 80);
            scaled.setFileName("scaled.jpg");
            scaled.writeImage(info);

            // Cloning
            imageDim = image.getDimension();
            System.out.println("Width is " + imageDim.width);
            System.out.println("Height is " + imageDim.height);
            System.out.println("Depth is " + image.getDepth());
            System.out.println("Storage class is " + image.getStorageClass());
            System.out.println("Comment is \""
                    + image.getImageAttribute("Comment") + "\"");
            MagickImage clonedImage = image.cloneImage(0, 0, false);
            clonedImage.setFileName("clone.jpg");
            clonedImage.writeImage(info);

            // Quantization
            MagickImage quantizedImage = new MagickImage(new ImageInfo(
                    "pics.jpg"));
            QuantizeInfo quantizeInfo = new QuantizeInfo();
            quantizeInfo.setColorspace(ColorspaceType.GRAYColorspace);
            quantizeInfo.setNumberColors(256);
            quantizeInfo.setTreeDepth(4);
            System.out.println("QuantizeImage "
                    + quantizedImage.quantizeImage(quantizeInfo));
            System.out.println("Colors " + quantizedImage.getColors());
            System.out.println("Total colors "
                    + quantizedImage.getTotalColors());
            quantizedImage.setFileName("quantized.png");
            quantizedImage.writeImage(info);
//            for (int i = 0; i < quantizedImage.getColors(); i++) {
//                PixelPacket pp = quantizedImage.getColormap(i);
//                System.out.println("Colormap[" + i + "] = (" + pp.getRed()
//                        + ", " + pp.getGreen() + ", " + pp.getBlue() + ")");
//            }
//            PixelPacket[] ppArray = quantizedImage.getColormap();
//            for (int i = 0; i < quantizedImage.getColors(); i++) {
//                System.out.println("Colormap2[" + i + "] = ("
//                        + ppArray[i].getRed() + ", " + ppArray[i].getGreen()
//                        + ", " + ppArray[i].getBlue() + ")");
//            }

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

            // Annotate the image with a green Hello
            ImageInfo blankImageInfo = new ImageInfo();
            DrawInfo annotateInfo = new DrawInfo(blankImageInfo);
            annotateInfo.setOpacity(0);
            annotateInfo.setFont("fixed");
            annotateInfo.setFill(PixelPacket.queryColorDatabase("green"));
            annotateInfo.setText("Hello");
            annotateInfo.setGeometry("+30+30");
            blankImage.annotateImage(annotateInfo);

            blankImage.setFileName("blank.jpg");
            blankImage.writeImage(info);

            // Make the white page of the image transparent
            blankImage.transparentImage(
                    PixelPacket.queryColorDatabase("white"), 65535);
            blankImage.setFileName("transparent.png");
            blankImage.writeImage(info);

            // Crop image
            rect = new Rectangle(20, 20, 150, 120);
            MagickImage cropped = image.cropImage(rect);
            cropped.setFileName("cropped.jpg");
            cropped.writeImage(info);

            // Chop image
            rect = new Rectangle(0, 0, 150, 120);
            MagickImage chopped = image.chopImage(rect);
            chopped.setFileName("chopped.jpg");
            chopped.writeImage(info);

            // Sharpen image
            MagickImage sharpened = image.sharpenImage(1.0, 5.0);
            sharpened.setFileName("sharpened.jpg");
            sharpened.writeImage(info);

            // Despeckle image
            MagickImage despeckled = image.despeckleImage();
            despeckled.setFileName("despeckled.jpg");
            despeckled.writeImage(info);

            // Convolve image
            double[] kernel = new double[9];
            for (int i = 0; i < 9; i++) {
                kernel[i] = 1.0;
            }
            kernel[4] = 2.0;
            MagickImage convolvedImage = image.convolveImage(3, kernel);
            convolvedImage.setFileName("convolved.jpg");
            convolvedImage.writeImage(info);

            // Finally display the image.
            MagickWindow window = new MagickWindow(image);
            window.setVisible(true);

            // MagickInfo test
            MagickInfo minfo = new MagickInfo("JPEG");
            System.out.println("JPG description: " + minfo.getDescription());
        }
        catch (MagickApiException ex) {
            System.err.println("MagickException: " + ex + ": " + ex.getReason()
                    + ", " + ex.getDescription());
            ex.printStackTrace();
        }
        catch (MagickException ex) {
            System.err.println("MagickException: " + ex);
            ex.printStackTrace();
        }
    }

}
