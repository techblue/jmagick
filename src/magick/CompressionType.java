package magick;

public interface CompressionType {
	/*
		Important! Constant values should correspond to:
		http://trac.imagemagick.org/browser/ImageMagick/branches/ImageMagick-6.6.2/magick/compress.h
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

	// The following have been added after ImageMagick-6.4.1-2 and before 6.5.7-6
	// (exact time not known). Using them is untested.
	public final static int ZipSCompression = 14;
	public final static int PizCompression = 15;
	public final static int Pxr24Compression = 16;
	public final static int B44Compression = 17;
	public final static int B44ACompression = 18;

}
