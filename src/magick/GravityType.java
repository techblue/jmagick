package magick;

/**
 * Corresponds to the ImageMagick enumerated type of the same name.
 * Important! Constant values should correspond to:
 * http://trac.imagemagick.org/browser/ImageMagick/branches/ImageMagick-6.6.2/magick/geometry.h
 *
 * @author Eric Yeo
 */
public interface GravityType {

    public final static int UndefinedGravity = 0;
    public final static int ForgetGravity = 0;
    public final static int NorthWestGravity = 1;
    public final static int NorthGravity = 2;
    public final static int NorthEastGravity = 3;
    public final static int WestGravity = 4;
    public final static int CenterGravity = 5;
    public final static int EastGravity = 6;
    public final static int SouthWestGravity = 7;
    public final static int SouthGravity = 8;
    public final static int SouthEastGravity = 9;
    public final static int StaticGravity = 10;
}
