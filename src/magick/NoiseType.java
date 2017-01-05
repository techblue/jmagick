package magick;

/**
 * Corresponds to ImageMagick enumerated type of the same name.
 * Important! Constant values should correspond to:
 * http://git.imagemagick.org/repos/ImageMagick/blob/master/MagickCore/fx.h
 */
public interface NoiseType {

    public static final int UndefinedNoise = 0;
    public static final int UniformNoise = 1;
    public static final int GaussianNoise = 2;
    public static final int MultiplicativeGaussianNoise = 3;
    public static final int ImpulseNoise = 4;
    public static final int LaplacianNoise = 5;
    public static final int PoissonNoise = 6;
    public static final int RandomNoise = 7;

}
