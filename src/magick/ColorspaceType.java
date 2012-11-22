package magick;

public interface ColorspaceType {
	/*
		Important! Constant values should correspond to:
		http://trac.imagemagick.org/browser/ImageMagick/branches/ImageMagick-6.6.9/magick/colorspace.h
	*/
	public final static int UndefinedColorspace = 0;
	public final static int RGBColorspace = 1;
	public final static int GRAYColorspace = 2;
	public final static int TransparentColorspace = 3;
	public final static int OHTAColorspace = 4;
	public final static int LABColorspace = 5;
	public final static int XYZColorspace = 6;
	public final static int YCbCrColorspace = 7;
	public final static int YCCColorspace = 8;
	public final static int YIQColorspace = 9;
	public final static int YPbPrColorspace = 10;
	public final static int YUVColorspace = 11;
	public final static int CMYKColorspace = 12;
	public final static int sRGBColorspace = 13;
	public final static int HSBColorspace = 14;
	public final static int HSLColorspace = 15;
	public final static int HWBColorspace = 16;
	public final static int Rec601LumaColorspace = 17;
	public final static int Rec601YCbCrColorspace = 18;
	public final static int Rec709LumaColorspace = 19;
	public final static int Rec709YCbCrColorspace = 20;
	public final static int LogColorspace = 21;
	public final static int CMYColorspace = 22; // since 6.2.4

}
