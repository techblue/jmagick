package magick;

/**
 * Corresponds to ImageMagick enumerated type of the same name.
 * Important! Constant values should correspond to:
 * http://trac.imagemagick.org/browser/ImageMagick/branches/ImageMagick-6.6.2/magick/blob.h
 * @author Eric Yeo
 */
public interface MapMode {

    public final int ReadMode = 0;
    public final int WriteMode = 1;
    public final int IOMode = 2;

}
