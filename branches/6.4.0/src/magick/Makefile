#
# Source level Makefile for JMagick.
#
# NOTE: Define DESTDIR if you are not installing JMagick
#       to its final directories. This is meant for the
#       purpose of packaging.
#       Contributed by Adam Heath <doogie@brainfood.com>
#

include ../../Make.def

JAVA_SRC_FILES =				\
			Magick.java		\
			MagickException.java	\
			MagickApiException.java	\
			ExceptionType.java	\
			ProfileInfo.java	\
			PixelPacket.java	\
			ImageInfo.java		\
			ClassType.java		\
			ColorspaceType.java	\
			CompositeOperator.java	\
			CompressionType.java	\
			FilterType.java		\
			GeometryFlags.java	\
			GravityType.java	\
			DrawInfo.java		\
			ImageMagick.java	\
			DecorationType.java	\
			ImageType.java		\
			InterlaceType.java	\
			QuantizeInfo.java	\
			MontageInfo.java	\
			MagickImage.java	\
			NoiseType.java		\
			PaintMethod.java	\
			PreviewType.java	\
			PrimitiveType.java	\
			RenderingIntent.java	\
			ResolutionType.java	\
			MagickProducer.java	\
			MagickLoader.java	\
			MagickInfo.java

# JNI specifications
JNI_LIB_NAME    =	JMagick
JNI_SRC_FILES   =	PixelPacket.java	\
			ImageInfo.java		\
			QuantizeInfo.java	\
			DrawInfo.java		\
			MagickImage.java	\
			MontageInfo.java	\
			Magick.java		\
			MagickInfo.java
JNI_LINK_LIBS   =	$(MAGICK_LIBS)
JNI_EXTRAS      =	jmagick.c
INCLUDES        =	$(JAVA_INCLUDES) $(MAGICK_INCLUDES) $(X11_INCLUDES)

DIRS = util

CLEAN_LIST	=	$(JNI_LIB) $(LIB_DIR)/.libs

include ../../Make.rules

test:
	LD_LIBRARY_PATH=. java $(CLASS_PATH) magick.Test

extra-install::
	$(INSTALL) -d $(DESTDIR)$(libdir)
	$(LIBTOOL) --mode=install $(INSTALL) $(JNI_LIB) $(DESTDIR)$(libdir)
