package magick;

/**
 * Corresponds to the ImageMagick enumerated type of the same name.
 * Important! Constant values should correspond to:
 * http://trac.imagemagick.org/browser/ImageMagick/branches/ImageMagick-6.6.9/magick/image.h
 *
 * @author Eric Yeo
 */
public interface InterlaceType {

    public final static int UndefinedInterlace = 0;
    public final static int NoInterlace = 1;
    public final static int LineInterlace = 2;
    public final static int PlaneInterlace = 3;
    public final static int PartitionInterlace = 4;


    // The following have been added after ImageMagick-6.4.1-2 and before 6.5.7-6
    // (exact time not known). Using them is untested.
    public final static int GIFInterlace = 5;
    public final static int JPEGInterlace = 6;
    public final static int PNGInterlace = 7;
}
