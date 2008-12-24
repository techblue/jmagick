package magick;

public interface CompressionType {
/*
	Important! Constant values should correspond to:
	http://trac.imagemagick.org/browser/ImageMagick/trunk/magick/compress.h?annotate=blame&rev=HEAD
	see IM which version in http://www.imagemagick.org/script/changelog.php or
	http://trac.imagemagick.org/browser/ImageMagick/trunk/version.sh?rev=10867
*/
		public final static int UndefinedCompression = 0;
		public final static int NoCompression = 1;
		public final static int BZipCompression = 2;

		public final static int DXT1Compression = 3;  // Revision 10867 - 05/10/08 - version 6.4.1-2
		public final static int DXT3Compression = 4;  // IM Revision 10867
		public final static int DXT5Compression = 5;  // IM Revision 10867

		public final static int FaxCompression = 6;
		public final static int Group4Compression = 7;
		public final static int JPEGCompression = 8;
		public final static int JPEG2000Compression = 9;
		public final static int LosslessJPEGCompression = 10;
		public final static int LZWCompression = 11;
		public final static int RLECompression = 12;
		public final static int ZipCompression = 13;  // IM Revision 10867

}
