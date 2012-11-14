package magick;

/**
 * Corresponds to ImageMagick enumerated type of the same name.
 * Important! Constant values should correspond to:
 * http://trac.imagemagick.org/browser/ImageMagick/branches/ImageMagick-6.6.2/magick/fx.h
 */
public interface NoiseType {

    public final static int UndefinedNoise = 0;
    public final static int UniformNoise = 1;
    public final static int GaussianNoise = 2;
    public final static int MultiplicativeGaussianNoise = 3;
    public final static int ImpulseNoise = 4;
    public final static int LaplacianNoise = 5;
    public final static int PoissonNoise = 6;

    // The following have been added after ImageMagick-6.4.1-2 and before 6.5.7-6
    // (exact time not known). Using them is untested.
    public final static int RandomNoise = 7;

}
