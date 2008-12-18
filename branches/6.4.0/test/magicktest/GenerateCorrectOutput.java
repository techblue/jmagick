package magicktest;

import java.io.*;

import javax.swing.*;

/**
 * This class is makes all file comparisons pass so that files in
 * magictest/actual_output/ can be made represent the "correct" output.
 * After running copy all files from magictest/actual_output/ to
 * magictest/correct_output/ to define
 * the 'correct' result files which the test should conform to.
 * @see Testtools.generate_correct_output
 * @author Jacob Nordfalk
 */
public class GenerateCorrectOutput {
	public static void main(String[] args) {

		String msg = "GenerateCorrectOutput: After the program have runned you must copy all files in \n"
				+MagickTesttools.path_actual_output + " to \n"+MagickTesttools.path_correct_output
				+"\n(it's a bad idea to move the directories if you intend to use Subversion)";
		System.err.println(msg );
		JOptionPane.showMessageDialog(null, msg);


		// first delete all in actual_output
		File[] f = new File(MagickTesttools.path_actual_output).listFiles();
		if (f!=null) for (int i=0; i<f.length; i++) f[i].delete();

		// then run tests without comparing
		MagickTesttools.generate_correct_output = true;

		// Hack: Activate JUnit. This method might not return
		junit.textui.TestRunner.main(new String[] {magicktest.TestJMagick.class.getName()});
	}
}
