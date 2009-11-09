package magick;

public interface ClassType {
  /*
          Important! Constant values should correspond to:
          http://trac.imagemagick.org/browser/ImageMagick/trunk/magick/magick-type.h?annotate=blame&rev=HEAD
  */

		public final static int UndefinedClass = 0;
		/** DirectClass for true color images */
		public final static int DirectClass = 1;
		/** PseudoClass for colormapped images. */
		public final static int PseudoClass = 2;

}
