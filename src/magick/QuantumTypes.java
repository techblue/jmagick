package magick;

/**
 * Corresponds to the ImageMagick enumerated type of the same name.
 * Important! Constant values should correspond to:
 * http://trac.imagemagick.org/browser/ImageMagick/branches/ImageMagick-6.6.2/magick/quantum.h
 * @author Eric Yeo
 */
public interface QuantumTypes {

    public static final int UndefinedQuantum = 0;
    public static final int AlphaQuantum = 1;
    public static final int BlackQuantum = 2;
    public static final int BlueQuantum = 3;
    public static final int CMYKAQuantum = 4;
    public static final int CMYKQuantum = 5;
    public static final int CyanQuantum = 6;
    public static final int GrayAlphaQuantum = 7;
    public static final int GrayQuantum = 8;
    public static final int GreenQuantum = 9;
    public static final int IndexAlphaQuantum = 10;
    public static final int IndexQuantum = 11;
    public static final int MagentaQuantum = 12;
    public static final int OpacityQuantum = 13;
    public static final int RedQuantum = 14;
    public static final int RGBAQuantum = 15;
    public static final int BGRAQuantum = 16;  // Added some time between 6.2.6 and 6.6.2
    public static final int RGBOQuantum = 17;
    public static final int RGBQuantum = 18;
    public static final int YellowQuantum = 19;
    public static final int GrayPadQuantum = 20;
    public static final int RGBPadQuantum = 21;

    // The following have been added after ImageMagick-6.4.1-2 and before 6.5.7-6
    // (exact time not known). Using them is untested.

    public static final int CbYCrYQuantum = 22;
    public static final int CbYCrQuantum = 23;
    public static final int CbYCrAQuantum = 24;
    public static final int CMYKOQuantum = 25;

}
