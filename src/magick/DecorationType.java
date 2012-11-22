package magick;

/** 
 * Interface to contain definition for Image Magick's DecorationType.
 * Must always correspond to http://trac.imagemagick.org/browser/ImageMagick/branches/ImageMagick-6.6.9/magick/draw.h
 * @author Eric Yeo
 */
public interface DecorationType {

    public final static int UndefinedDecoration = 0;
    public final static int NoDecoration = 1;
    public final static int UnderlineDecoration = 2;
    public final static int OverlineDecoration = 3;
    public final static int LineThroughDecoration = 4;

}
