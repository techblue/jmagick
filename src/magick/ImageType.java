package magick;

/**
 * Corresponds to the ImageMagick enumerated type of the same name.
 * Important! Constant values should correspond to:
 * http://trac.imagemagick.org/browser/ImageMagick/branches/ImageMagick-6.6.2/magick/image.h
 * @see MagickImage#getImageType()
 * @author Eric Yeo
 */
public interface ImageType {

	public final static int UndefinedType = 0;
	public final static int BilevelType = 1;
	public final static int GrayscaleType = 2;
	public final static int GrayscaleMatteType = 3;
	public final static int PaletteType = 4;
	public final static int PaletteMatteType = 5;
	public final static int TrueColorType = 6;
	public final static int TrueColorMatteType = 7;
	public final static int ColorSeparationType = 8;
	public final static int ColorSeparationMatteType = 9;
	public final static int OptimizeType = 10;
	public final static int PaletteBilevelMatteType = 11;
}
