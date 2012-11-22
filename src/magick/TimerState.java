package magick;

/**
 * Used in MagickImage.createImage to specify the size 
 * of component.
 *
 * Important! Constant values should correspond to:
 * http://trac.imagemagick.org/browser/ImageMagick/branches/ImageMagick-6.6.9/magick/timer.h
 *
 * @author Eric Yeo
 */
public interface TimerState {

    public final int UndefinedTimerState = 0;
    public final int StoppedTimerState = 1;
    public final int RunningTimerState = 2;

}
