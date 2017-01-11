package magick;


/**
 * Corresponds to the ImageMagick enumerated type of the same name.
 * Important! Constant values should correspond to:
 * http://git.imagemagick.org/repos/ImageMagick/blob/master/MagickCore/effect.h
 * @author Eric Yeo
 */
public interface PreviewType {

    public static final int UndefinedPreview = 0;
    public static final int RotatePreview = 1;
    public static final int ShearPreview = 2;
    public static final int RollPreview = 3;
    public static final int HuePreview = 4;
    public static final int SaturationPreview = 5;
    public static final int BrightnessPreview = 6;
    public static final int GammaPreview = 7;
    public static final int SpiffPreview = 8;
    public static final int DullPreview = 9;
    public static final int GrayscalePreview = 10;
    public static final int QuantizePreview = 11;
    public static final int DespecklePreview = 12;
    public static final int ReduceNoisePreview = 13;
    public static final int AddNoisePreview = 14;
    public static final int SharpenPreview = 15;
    public static final int BlurPreview = 16;
    public static final int ThresholdPreview = 17;
    public static final int EdgeDetectPreview = 18;
    public static final int SpreadPreview = 19;
    public static final int SolarizePreview = 20;
    public static final int ShadePreview = 21;
    public static final int RaisePreview = 22;
    public static final int SegmentPreview = 23;
    public static final int SwirlPreview = 24;
    public static final int ImplodePreview = 25;
    public static final int WavePreview = 26;
    public static final int OilPaintPreview = 27;
    public static final int CharcoalDrawingPreview = 28;
    public static final int JPEGPreview = 29;
}
