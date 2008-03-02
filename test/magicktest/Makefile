include ../../Make.def

JAVA_SRC_FILES	=	MagickTesttools.java OldSimpleTest.java Loop.java TestJMagick.java GenerateCorrectOutput.java
CLEAN_LIST	=	copy.jpg blank.jpg copy_pics.jpg rotated.jpg \
			sheared.jpg quantized.png scaled.jpg cropped.jpg \
			chopped.jpg sharpened.jpg despeckled.jpg \
			convolved.jpg blob.jpg montage.jpg average.jpg \
			blob.gif transparent.png border.jpg raised.jpg \
			clone.jpg

include ../../Make.rules

# For Solaris and Linux, LD_LIBRARY_PATH sets the shared library path
# For Mac OS X, DYLD_LIBRARY_PATH sets the shared library path

test: default-target 
	cd ../..; LD_LIBRARY_PATH=$(MAGICK_LIB_PATH):lib:$(LD_LIBRARY_PATH) DYLD_LIBRARY_PATH=$(MAGICK_LIB_PATH):lib:$(LD_LIBRARY_PATH) $(JAVA) -classpath $(JAVA_CLASS_PATH):test:lib/junit.jar junit.textui.TestRunner magicktest.TestJMagick

generateCorrect: GenerateCorrectOutput.class
	cd ../..; LD_LIBRARY_PATH=$(MAGICK_LIB_PATH):lib:$(LD_LIBRARY_PATH) DYLD_LIBRARY_PATH=$(MAGICK_LIB_PATH):lib:$(LD_LIBRARY_PATH) $(JAVA) -classpath $(JAVA_CLASS_PATH):test:lib/junit.jar magicktest.GenerateCorrectOutput

simpletest: default-target
	mkdir ../simpletest_output; cp pics.jpg ../simpletest_output; cd ../simpletest_output; LD_LIBRARY_PATH=$(MAGICK_LIB_PATH):../../lib:$(LD_LIBRARY_PATH) DYLD_LIBRARY_PATH=$(MAGICK_LIB_PATH):../../lib:$(LD_LIBRARY_PATH) $(JAVA) -classpath $(JAVA_CLASS_PATH) magicktest.OldSimpleTest

test2: default-target
	LD_LIBRARY_PATH=$(MAGICK_LIB_PATH):../../lib:$(LD_LIBRARY_PATH) DYLD_LIBRARY_PATH=$(MAGICK_LIB_PATH):../../lib:$(LD_LIBRARY_PATH) $(JAVA) -classpath $(JAVA_CLASS_PATH) magicktest.Loop pics.jpg

rutest: default-target
	LD_LIBRARY_PATH=$(MAGICK_LIB_PATH):../../lib:$(LD_LIBRARY_PATH) DYLD_LIBRARY_PATH=$(MAGICK_LIB_PATH):../../lib:$(LD_LIBRARY_PATH) $(JAVA) -classpath $(JAVA_CLASS_PATH) test.RUTest

testxx: default-target
	LD_LIBRARY_PATH=$(MAGICK_LIB_PATH):../../lib:$(LD_LIBRARY_PATH) DYLD_LIBRARY_PATH=$(MAGICK_LIB_PATH):../../lib:$(LD_LIBRARY_PATH) $(JAVA) -classpath ImageMagickTest.jar:$(JAVA_CLASS_PATH) ImageMagick.ImageMagickTest

# This target may not work on Mac OS X
lddcheck: default-target
	LD_LIBRARY_PATH=$(MAGICK_LIB_PATH):../../lib:$(LD_LIBRARY_PATH) DYLD_LIBRARY_PATH=$(MAGICK_LIB_PATH):../../lib:$(LD_LIBRARY_PATH) ldd ../../lib/libJMagick.so
