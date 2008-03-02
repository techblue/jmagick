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

DIRS = src
JAR_FILE = $(LIB_DIR)/jmagick-$(MAJOR).$(MINOR).$(MICRO).jar
CLEAN_LIST = $(JAR_FILE)

all: dir default-target
	cd classes; jar cvf $(JAR_FILE) magick

include $(MAKE_PATH)/Make.rules

dir:
	@-mkdir -p $(PROJ_BASE_DIR)classes
	@-mkdir -p $(PROJ_BASE_DIR)obj
	@-mkdir -p $(PROJ_BASE_DIR)generated
	@-mkdir -p $(PROJ_BASE_DIR)lib

distclean: clean
	@-rm Make.def config.cache config.log config.status
	@-rm -rf $(JAVADOC_DIR)
	@-rm -rf autom4te.cache

javadoc:
	@-if [ -x "$(JAVADOC)" ]; then					\
		echo Generating Javadoc files ... ;			\
		$(INSTALL) -d $(DESTDIR)$(JAVADOC_DIR) ;				\
		$(JAVADOC) -author -version -d $(DEST)$(JAVADOC_DIR)    \
                      -sourcepath $(JAVA_SRC_DIR) magick magick.util ;	\
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

generateCorrect::
	cd test/magicktest; CLASSPATH=../../lib/junit.jar make generateCorrect
