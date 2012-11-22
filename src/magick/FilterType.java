package magick;

public interface FilterType {
	/*
		Important! Constant values should correspond to:
		http://trac.imagemagick.org/browser/ImageMagick/branches/ImageMagick-6.6.9/magick/resample.h
	*/
	public final static int UndefinedFilter = 0;
	public final static int PointFilter = 1;
	public final static int BoxFilter = 2;
	public final static int TriangleFilter = 3;
	public final static int HermiteFilter = 4;
	public final static int HanningFilter = 5;
	public final static int HammingFilter = 6;
	public final static int BlackmanFilter = 7;
	public final static int GuassianFilter = 8;
	public final static int QuadraticFilter = 9;
	public final static int CubicFilter = 10;
	public final static int CatromFilter = 11;
	public final static int MitchellFilter = 12;
	// Values after this point were changed in an incompatible fashion in IM 6.6.5.
	public final static int JincFilter = 13;
	@Deprecated
	public final static int BesselFilter = 13;
	public final static int SincFilter = 14;
	public final static int SincFastFilter = 15;
	public final static int KaiserFilter = 16;
	public final static int WelshFilter = 17;
	public final static int ParzenFilter = 18;
	public final static int BohmanFilter = 19;
	public final static int BartlettFilter = 20;
	public final static int LagrangeFilter = 21;
	public final static int LanczosFilter = 22;
	public final static int LanczosSharpFilter = 23;
	public final static int Lanczos2Filter = 24;
	public final static int Lanczos2SharpFilter = 25;
	public final static int RobidouxFilter = 26;
	public final static int SentinelFilter = 27;
}


