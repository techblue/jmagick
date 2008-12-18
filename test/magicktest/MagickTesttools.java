package magicktest;

import java.io.*;
import java.util.*;

import javax.swing.*;

import magick.*;


/**
 * Utility class intended for testing purposes.
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author Jacob Nordfalk
 */
public class MagickTesttools
{
	public static String path_input = "test"+File.separator+"magicktest"+File.separator;
	public static String path_actual_output = "test"+File.separator+"actual_output"+File.separator;
	public static String path_correct_output = "test"+File.separator+"correct_output"+File.separator;

	/**
	 * This flag makes all file comparisons pass and avoids that files are deleted in magictest/actual_output/
	 * Set to true and run tests to generate examples of "correct" output.
	 * Copy then all files from magictest/actual_output/ to magictest/correct_output/ to define
	 * the 'correct' result files which the test should conform to.
	 */
	public static boolean generate_correct_output = false;


	private static boolean generate_correct_output_warned;

	/**
	 * Compares two image files pixel by pixel (by invoking the IM command 'compare')
	 * @param file1 First image file name
	 * @param file2 Second image file name
	 * @return double difference The max allowed pixel difference (MSE metric)
	 * @throws IOException If the difference is unacceptable big
	 */
	public static void compareImage(String file1, String file2, double maxDiff) throws IOException
	{
		//maxDiff = 2; // uncomment to make very strict test!
		if (generate_correct_output)
		{
			System.err.println("************************************** WARNING ************************************************");
			System.err.println("* With generate_correct_output = true you are generating 'correct' output, instead of testing *");
			System.err.println("***********************************************************************************************");
			// show popup once
			if (!generate_correct_output_warned) {
				int ret = JOptionPane.showConfirmDialog(null, "generate_correct_output is on, so you are a generating 'correct' output, instead of comparing files");
				System.out.println("ret="+ret);
				if (ret != JOptionPane.OK_OPTION) {
					System.err.println("generate_correct_output cancelled by user");
					System.exit(-1);
				}
				generate_correct_output_warned = true;
			}
			return;
		}


		if (!new File(file1).exists()) throw new java.io.FileNotFoundException("Fil 1 not found: " + file1);
		if (!new File(file2).exists()) throw new java.io.FileNotFoundException("Fil 2 not found: " + file2);


	 /*
			compare -metric MSE rose.jpg reconstruct.jpg difference.png 29.5615 dB

			MetricOptions[] =
			{
				{ "Undefined", (long) UndefinedMetric },
				{ "MAE", (long) MeanAbsoluteErrorMetric },
				{ "MSE", (long) MeanSquaredErrorMetric },
				{ "PAE", (long) PeakAbsoluteErrorMetric },
				{ "PSNR", (long) PeakSignalToNoiseRatioMetric },
				{ "RMSE", (long) RootMeanSquaredErrorMetric },
				{ (char *) NULL, (long) UndefinedMetric }
			}
	 */

		//String cmd = "compare -metric MSE "+file1+" "+file2+" tempImageDifference.jpg";
		String[] cmd = new String[] {"compare","-metric","MSE",file1,file2,"tempcompareImage.jpg"};


		// Some may need an absolute path for IM 'compare' command.
		// Here are some examples:
		// if (System.getProperty("user.dir").startsWith("E:")) cmd = "E:\\user\\caramba\\magick-6.2.6-Q8\\"+cmd;
		// cmd = "C:\\Program Files\\ImageMagick-6.3.5-Q8\\"+cmd;

		Process compProces = Runtime.getRuntime().exec(cmd, new String[0]);
		InputStream std = compProces.getInputStream();
		InputStream err = compProces.getErrorStream();

		StringBuffer outsb = new StringBuffer(20);
		StringBuffer errsb = new StringBuffer(20);

		do {
			int ch =std.read();
			if (ch==-1) break;
			outsb.append( (char) ch);
		} while (true);

		do {
			int ch =err.read();
			if (ch==-1) break;
			errsb.append( (char) ch);
		} while (true);

		compProces.getOutputStream().close();
		std.close();
		err.close();

		String ret = outsb.toString() + errsb.toString();
		// parse the first number in output
		int n = ret.indexOf(" ");
		try {
			double difference = Double.parseDouble(n > 0 ? ret.substring(0, n) : ret.toString());

			if (difference > maxDiff) {
				throw new RuntimeException("Images had a difference of " + difference +
																	 " which is bigger than max allowed of " + maxDiff + ":\n  " + file1 + " " + file2);
			}
		} catch (NumberFormatException ex) {
			System.err.println("Output of command "+Arrays.asList(cmd)+" could not be parsed.");
			System.err.println("Perhaps you need to give full path to IMs 'compare' command?");
			System.err.println(cmd[0] + " gave stdout: '"+outsb.toString().trim()+"'");
			System.err.println(cmd[0] + " gave stderr: '"+errsb.toString().trim()+"'");
			throw new RuntimeException(ret);
		}
		// delete temporary file if no significan difference was found
		new File("tempImageDifference.jpg").delete();
	}






	public static void writeAndCompare(MagickImage image, ImageInfo info, String fileName) throws Exception
	{
		new File(path_actual_output).mkdirs();
		// Delete old files to make sure they dont interfere
		new File(path_actual_output+fileName).delete();

		image.setFileName(path_actual_output+fileName);
		image.writeImage(info);

		// compare the pixel data (allowing for a small difference)
		compareImage(path_actual_output+fileName, path_correct_output+fileName, 20);

		if (!generate_correct_output) {

			// Check that IPCT and ICC data are exactly the same
			MagickImage correct = new MagickImage(new ImageInfo(path_correct_output+fileName));
			if (!compareImageProfiles(image, correct)) {
				throw new RuntimeException("Image "+fileName+" had a difference in profiles.");
			}
		}
	}

	public static boolean compareImageProfiles(MagickImage image,  MagickImage correct) throws Exception {
		if (!equals(image.getIptcProfile(), correct.getIptcProfile())) {
			System.out.println("Images had a IPTC profile difference");
			System.out.println("Correct : "+profileInfoToString(correct.getIptcProfile()));
			System.out.println("Actual : "+profileInfoToString(image.getIptcProfile()));
			return false;
		}

		if (!equals(image.getColorProfile(), correct.getColorProfile())) {
			System.out.println("Images had a ICC profile difference");
			System.out.println("Correct : "+profileInfoToString(correct.getColorProfile()));
			System.out.println("Actual : "+profileInfoToString(image.getColorProfile()));
			return false;
		}
		return true;
	}


	/**
	 * Class ProfileInfo is missing the .equals()-method, so we make it here
	 * @param p1 ProfileInfo
	 * @param p2 ProfileInfo
	 * @return boolean false if different content, true otherwise
	 */
	public static boolean equals(ProfileInfo p1, ProfileInfo p2) {
		if (p1==p2) return true;
		if (p1==null || p2==null) return false; // one but not both null
		return (p1.getName()==p2.getName() ||
					 (p1.getName()!=null &&	p1.getName().equals(p2.getName())))
				&& Arrays.equals(p1.getInfo(), p2.getInfo());
	}





	/**
	 * Display the information about the profile supplied.
	 *
	 * @param profile the profile for which to display
	 */
	public static void displayProfile(ProfileInfo profile) {
		System.out.println(profileInfoToString(profile));
	}

	/**
	 * Returns information about the profile supplied.
	 *
	 * @param profile the profile for which to display
	 */
	public static String profileInfoToString(ProfileInfo profile) {
			if (profile == null) {
				return "(null)";
			}

			if (profile.getInfo() == null) {
					return "profile info=null";
			}
			else {
					return "profile info.length=" + profile.getInfo().length+" hash="+Arrays.hashCode(profile.getInfo());
			}
	}



	public static void allocateAndFreeSomeMem(int n) {
		byte[] b = new byte[n];
		for (int i=0; i<n; i++) b[i] = (byte) i;
		System.out.println("alloker "+n+" "+ (b[0] + b[(n-1)/2]));
		b = null;
		System.gc();
	}


}
