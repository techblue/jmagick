package magick;

/**
 * Corresponds to the ImageMagick enumerated type of the same name.
 * Important! Constant values should correspond to:
 * http://git.imagemagick.org/repos/ImageMagick/blob/master/MagickCore/draw.h
 * @author Eric Yeo
 */
public interface PrimitiveType {

    public static final int UndefinedPrimitive = 0;
    public static final int AlphaPrimitive = 1;
    public static final int ArcPrimitive = 2;
    public static final int BezierPrimitive = 3;
    public static final int CirclePrimitive = 4;
    public static final int ColorPrimitive = 5;
    public static final int EllipsePrimitive = 6;
    public static final int ImagePrimitive = 7;
    public static final int LinePrimitive = 8;
    public static final int PathPrimitive = 9;
    public static final int PointPrimitive = 10;
    public static final int PolygonPrimitive = 11;
    public static final int PolylinePrimitive = 12;
    public static final int RectanglePrimitive = 13;
    public static final int RoundRectanglePrimitive = 14;
    public static final int TextPrimitive = 15;

}
