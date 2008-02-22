package magick.util;

import java.lang.reflect.*;

import magick.*;

/**
 * A generic solution that can display information about a
 * MagickImage or ImageInfo object by using reflection.
 * It look at the xxxType-classes (ColorspaceType, GeometryFlags) and replaces
 * the numbers with the variable names given there.
 * License: You decide, I give it to the public domain. Use if for whatever you want.
 * @author Jacob Nordfalk
 */
public class DisplayImageMetaData {

    /**
     * Searches through class's <code>public static final int</code> fields
     * until it finds a field with a specific value.
     * Examples:<pre>
     * nameOfpublicstaticintField(ColorspaceType.class, 0) returns "UndefinedColorspace"
     * nameOfpublicstaticintField(ColorspaceType.class, 1) returns "RGBColorspace"
     * nameOfpublicstaticintField(ColorspaceType.class, 2) returns "GRAYColorspace"
     *
     * nameOfpublicstaticintField(GeometryFlags.class, 0) returns "NoValue"
     * nameOfpublicstaticintField(GeometryFlags.class, 1) returns "PsiValue"
     *
     * Given that these classes are declared as:

     public abstract interface ColorspaceType {
      public static final int UndefinedColorspace = 0;
      public static final int RGBColorspace = 1;
      public static final int GRAYColorspace = 2;
      ...

     and

     public abstract interface GeometryFlags {
      public static final int NoValue = 0;
      public static final int PsiValue = 1;
      public static final int XValue = 1;
      public static final int XiValue = 2;
     </pre>
     * @param clazz Class to search in
     * @param value int value to search for
     * @return name of fiels
     */

    public static String nameOfpublicstaticintField(Class clazz, int value) {
        Field[] f = clazz.getFields();
        for (int i = 0; i < f.length; i++) {
            try {
                //System.out.println(""+f[i].toString());
                if (f[i].getType() == Integer.TYPE && f[i].getInt(null) == value) {
                    return f[i].getName();
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return value + "(unknown)";
    }

    /**
     * As nameOfpublicstaticintField, but returns also the value (convenience method)
     * nameAndValueOfpublicstaticintField(ColorspaceType.class, 1) returns "1 (RGBColorspace)"
     * @param clazz Class to search in
     * @param value int value to search for
     * @return name of fiels
     */
    private static String nameAndValueOfpublicstaticintField(Class clazz, int value) {
        return value + "(" + nameOfpublicstaticintField(clazz, value) + ")";
    }

    /**
     * Returns the name of a public static final int field in class colorspaceType.
     * Example:  colorspaceTypeAsString(3) returns "TransparentColorspace"
     * @param value field value
     * @return field name
     */
    public static String colorspaceTypeAsString(int value) {
        return nameAndValueOfpublicstaticintField(magick.ColorspaceType.class, value);
    }

    public static String classTypeAsString(int value) {
        return nameAndValueOfpublicstaticintField(magick.ClassType.class, value);
    }

    public static String compositeOperatorAsString(int value) {
        return nameAndValueOfpublicstaticintField(magick.CompositeOperator.class, value);
    }

    public static String compressionTypeAsString(int value) {
        return nameAndValueOfpublicstaticintField(magick.CompressionType.class, value);
    }

    public static String decorationTypeAsString(int value) {
        return nameAndValueOfpublicstaticintField(magick.DecorationType.class, value);
    }

    public static String drawInfoAsString(int value) {
        return nameAndValueOfpublicstaticintField(magick.DrawInfo.class, value);
    }

    public static String exceptionTypeAsString(int value) {
        return nameAndValueOfpublicstaticintField(magick.ExceptionType.class, value);
    }

    public static String filterTypeAsString(int value) {
        return nameAndValueOfpublicstaticintField(magick.FilterType.class, value);
    }

    public static String geometryFlagsAsString(int value) {
        return nameAndValueOfpublicstaticintField(magick.GeometryFlags.class, value);
    }

    public static String gravityTypeAsString(int value) {
        return nameAndValueOfpublicstaticintField(magick.GravityType.class, value);
    }

    public static String imageTypeAsString(int value) {
        return nameAndValueOfpublicstaticintField(magick.ImageType.class, value);
    }

    public static String interlaceTypeAsString(int value) {
        return nameAndValueOfpublicstaticintField(magick.InterlaceType.class, value);
    }

    public static String noiseTypeAsString(int value) {
        return nameAndValueOfpublicstaticintField(magick.NoiseType.class, value);
    }

    public static String previewTypeAsString(int value) {
        return nameAndValueOfpublicstaticintField(magick.PreviewType.class, value);
    }

    public static String primitiveTypeAsString(int value) {
        return nameAndValueOfpublicstaticintField(magick.PrimitiveType.class, value);
    }

    public static void displayImageInfo(ImageInfo info) {
        try {
            //info.setVerbose(1);
            System.out.println("Info PreviewType is " + previewTypeAsString(info.getPreviewType()));
            System.out.println("Info Monochrome is " + info.getMonochrome());
            System.out.println("Info Colorspace is " + colorspaceTypeAsString(info.getColorspace()));
            System.out.println("Info Resolution units is " + info.getUnits());
            System.out.println("Info Compression is " + compressionTypeAsString(info.getCompression()));
            System.out.println("Info Density is " + info.getDensity());
            System.out.println("Info magick is " + info.getMagick());
            System.out.println("Info filename is " + info.getFileName());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void displayMagickImage(MagickImage image) {
        try {
            System.out.println("ImageName is " + image.getFileName());
            System.out.println("ImageFormat is " + image.getImageFormat());
            System.out.println("Imagetype is " + imageTypeAsString(image.getImageType()));
            System.out.println("Compression is " + compressionTypeAsString(image.getCompression()));
            System.out.println("Colorspace is " + colorspaceTypeAsString(image.getColorspace()));
            System.out.println("Resolution units is " + image.getUnits());
            System.out.println("Depth is " + image.getDepth());
            System.out.println("X resolution is " + image.getXResolution());
            System.out.println("Y resolution is " + image.getYResolution());
            System.out.println("Size blob is " + image.sizeBlob());
            System.out.println("Colors " + image.getColors());
            System.out.println("Total colors " + image.getTotalColors());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
