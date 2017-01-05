#
# Toplevel Makefile for JMagick.
#
# NOTE: Define DESTDIR if you are not installing JMagick
#       to its final directories. This is for the purpose
#       of packaging.
#       Contributed by Adam Heath <doogie@brainfood.com>
#


MAKE_PATH = .
include $(MAKE_PATH)/Make.def

CYGPATH := $(shell command -v cygpath 2> /dev/null)

DIRS = src
ifdef CYGPATH
JAR_FILE = "$(shell cygpath -C UTF8 -w "$(LIB_DIR)/jmagick-$(MAJOR).$(MINOR).$(MICRO).jar")"
CLEAN_LIST = $(LIB_DIR)/jmagick-$(MAJOR).$(MINOR).$(MICRO).jar
JAVADOC_DEST = "$(shell cygpath -C UTF8 -s -w "$(DEST)$(JAVADOC_DIR)")"
JAVADOC_SRC = "$(shell cygpath -C UTF8 -s -w "$(JAVA_SRC_DIR)")"
else
JAR_FILE = $(LIB_DIR)/jmagick-$(MAJOR).$(MINOR).$(MICRO).jar
CLEAN_LIST = $(JAR_FILE)
JAVADOC_DEST = $(DEST)$(JAVADOC_DIR)
JAVADOC_SRC = $(JAVA_SRC_DIR)
endif

all: dir default-target
	cd classes; $(JAR) cvf $(JAR_FILE) magick resources

include $(MAKE_PATH)/Make.rules

dir:
	@-mkdir -p $(PROJ_BASE_DIR)classes
	@-mkdir -p $(PROJ_BASE_DIR)obj
	@-mkdir -p $(PROJ_BASE_DIR)generated
	@-mkdir -p $(PROJ_BASE_DIR)lib

distclean: clean
	@-rm Make.def config.cache config.log config.status
	@-rm -rf $(JAVADOC_DIR)
	@-rm -rf classes
	@-rm -rf autom4te.cache

javadoc:
	@-if [ -x "$(JAVADOC)" ]; then					\
		echo Generating Javadoc files ... ;			\
		$(INSTALL) -d $(DESTDIR)$(JAVADOC_DIR) ;				\
		"$(JAVADOC)" -author -version -d $(JAVADOC_DEST)    \
                      -sourcepath $(JAVADOC_SRC) magick magick.util ;	\
	else								\
		echo Unable to generate javadoc;			\
	fi

extra-install::
	$(INSTALL) -d $(DESTDIR)$(libdir)
	$(INSTALL) $(JAR_FILE) $(DESTDIR)$(libdir)

simpletest::
	cd test/magicktest; make simpletest

test::
	cd test/magicktest; CLASSPATH=../../lib/junit.jar make test

looptest::
	cd test/magicktest; CLASSPATH=../../lib/junit.jar make looptest

generateCorrect::
	cd test/magicktest; CLASSPATH=../../lib/junit.jar make generateCorrect
