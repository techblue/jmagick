dnl Copyright (C) 1999-2005 Eric Yeo. All rights reserved.
dnl This is the autoconf input file for Java Magick.
AC_PREREQ(2.6.1)

dnl The version number must change with every release
AC_INIT([jmagick], [7.0.4])
AC_CANONICAL_BUILD
AC_CANONICAL_HOST
AC_CANONICAL_TARGET

AM_INIT_AUTOMAKE([-Wno-portability])
VER_MAJOR=$(echo "$PACKAGE_VERSION" | cut -d . -f 1)
VER_MINOR=$(echo "$PACKAGE_VERSION" | cut -d . -f 2)
VER_MICRO=$(echo "$PACKAGE_VERSION" | cut -d . -f 3)
VER_JMSUB="0"

AC_SUBST(VER_MAJOR, ${VER_MAJOR})
AC_SUBST(VER_MINOR, ${VER_MINOR})
AC_SUBST(VER_MICRO, ${VER_MICRO})
AC_SUBST(VER_JMSUB, ${VER_JMSUB})

LIB=lib
if test -x "/usr/lib64" ; then
  LIB=lib64
fi

SHARED_LIB_EXT=.so
DARWIN=false
AM_CONDITIONAL(USE_CYGPATH, false)

dnl Need the -G flag on Solaris when creating shared libraries.
case "$target" in
*solaris*)
	SHARED_LDFLAGS="-G"
	;;
*freebsd*|*linux*)
	SHARED_LDFLAGS="-shared"
	;;
dnl Make sure we use -dynamiclib parameter for OS X Leopard
*darwin9.*|*darwin10.*)
	SHARED_LDFLAGS="-dynamiclib"
	SHARED_LIB_EXT=.jnilib
	DARWIN=true
	;;
*darwin1.[0-2])
	DARWIN=true
	;;
*darwin*)
	LIBTOOL_LINK_FLAG='-module'
	DARWIN=true
	;;
*-*-cygwin|*-*-mingw32)
	SHARED_LDFLAGS="-shared"
	SHARED_LIB_EXT=.dll
	JFLAGS="-Xlint:deprecation -Xlint:unchecked"
	AM_CONDITIONAL(USE_CYGPATH, true)
	JAVA_HOME=$(cygpath -C UTF8 "$(cygpath -C UTF8 -d "${JAVA_HOME}")")
	MAGICK_HOME=$(cygpath -C UTF-8 "$(cygpath -C UTF-8 -d "$(dirname "$(which convert)")")")
	;;
esac
AC_SUBST(DARWIN)

dnl By default, we look at /usr/local. Need to explicitly do this because
dnl some compilers don't.
LDFLAGS="${LDFLAGS} -L/usr/local/${LIB}"
CFLAGS="${CFLAGS} -I/usr/local/include"

dnl Get the full path of the source directory.
PROJ_BASE_DIR=`cd ${srcdir} && pwd`
AC_SUBST(PROJ_BASE_DIR)

dnl Determine if we have gcc
AC_PROG_CC
if test -z "${CC}"; then
    AC_MSG_ERROR('No suitable C compiler found')
fi
if test "${GCC}" != yes; then
    AC_MSG_WARN('You don't have the GNU GCC. Should\'t you be using one?')
fi
AC_SUBST(CC)

dnl Determine how to run the preprocessor
AC_PROG_CPP

dnl Check for install program
AC_PROG_INSTALL

dnl Check for large file support. This test was taken from ImageMagick.
dnl If this step is removed, there will be a mismatch of structures in
dnl the ImageMagick headers.
AC_SYS_LARGEFILE

dnl Determine the X11 install paths. We don't really need this
dnl if we are using shared libraries.
AC_PATH_X
if (test -n "${x_includes}"); then
    X11_INCLUDES=-I${x_includes}
fi
if (test -n "${x_libraries}"); then
    X11_LIBS=-L${x_libraries}
fi

dnl Determine the OS we are running on and set the class path
dnl separator accordingly.
case ${build_os} in
*-*-cygwin|*-*-mingw32)
	CLASSSEP=;
	;;
*)
	CLASSSEP=:
esac
AC_SUBST(CLASSSEP)

dnl Determine if we have a decent Java distribution
AC_ARG_WITH([java-home],
  AS_HELP_STRING([--with-java-home=PATH],
    [Java installation path]),
    [JAVA_HOME="${withval}"])
AC_ARG_WITH([java-includes],
  AS_HELP_STRING([--with-java-includes=PATH],
    [Java includes path]),
    [JAVA_INCLUDE_PATH="${withval}"])

if test -n "${JAVA_INCLUDE_PATH}" -a -d "${JAVA_INCLUDE_PATH}" ; then
	dnl Java include path supplied
	JAVA_INCLUDES=-I${JAVA_INCLUDE_PATH}
elif test -d "${JAVA_HOME}/include" ; then
	JAVA_INCLUDES=-I${JAVA_HOME}/include
	dnl The Blackdown JDK seems to have genunix.
	if test -d "${JAVA_HOME}/include/genunix" ; then
		JAVA_INCLUDES="$JAVA_INCLUDES -I${JAVA_HOME}/include/genunix"
	fi
	dnl The Sun Linux JDK seems to use linux
	if test -d "${JAVA_HOME}/include/linux" ; then
		JAVA_INCLUDES="$JAVA_INCLUDES -I${JAVA_HOME}/include/linux"
	fi
	dnl The Sun Solaris JDK seems to use solaris
	if test -d "${JAVA_HOME}/include/solaris" ; then
		JAVA_INCLUDES="$JAVA_INCLUDES -I${JAVA_HOME}/include/solaris"
	fi
	dnl The Sun FreeBSD JDK seems to use freebsd
	if test -d "${JAVA_HOME}/include/freebsd" ; then
		JAVA_INCLUDES="$JAVA_INCLUDES -I${JAVA_HOME}/include/freebsd"
	fi
	dnl The Sun Windows JDK seems to use win32
	if test -d "${JAVA_HOME}/include/win32" ; then
		JAVA_INCLUDES="$JAVA_INCLUDES -I${JAVA_HOME}/include/win32"
	fi
	if test -d "${JAVA_HOME}/include/bsd" ; then
		JAVA_INCLUDES="$JAVA_INCLUDES -I${JAVA_HOME}/include/bsd"
	fi
	dnl Modern Oracle JDK on OS X
	if test -d "${JAVA_HOME}/include/darwin" ; then
		JAVA_INCLUDES="$JAVA_INCLUDES -I${JAVA_HOME}/include/darwin"
	fi
dnl check for Mac OS X series of systems with old Apple JDKs
elif test -d "${JAVA_HOME}/Headers" ; then
	JAVA_INCLUDES=-I${JAVA_HOME}/Headers
else
	AC_MSG_ERROR('Unable to locate Java directories')
fi

dnl Determine if there is a shared library extension supplied
AC_ARG_WITH([shared_lib_ext],
  AS_HELP_STRING([--with-shared-lib-ext],
    [Shared library file extension]),
    [SHARED_LIB_EXT="${withval}"],
    [SHARED_LIB_EXT="${SHARED_LIB_EXT}"])

dnl Determine if jni.h is available
OLD_CPPFLAGS=${CPPFLAGS}
CPPFLAGS="${JAVA_INCLUDES} ${CPPFLAGS}"
AC_CHECK_HEADER(jni.h, HAVE_JNI_HEADER=yes, HAVE_JNI_HEADER=no)
if test ${HAVE_JNI_HEADER} = no ; then
	AC_MSG_ERROR('Unable to locate jni.h')
fi
CPPFLAGS=${OLD_CPPFLAGS}

dnl Determine where the Java interpreter is
OLDPATH=${PATH}
PATH=${JAVA_HOME}/bin:${PATH}
export PATH
AC_PATH_PROG(JAVA, java, not_found)
if test ${JAVA} = not_found; then
	AC_MSG_ERROR('No Java interpreter found')
fi
AC_SUBST(JAVA)

dnl Determine if we prefer to use Jikes.
AC_ARG_WITH([jikes],
  AS_HELP_STRING([--with-jikes],
    [use the IBM Jikes compiler]),
    [PREFER_JIKES="yes"],
    [PREFER_JIKES="no"])

if test ${PREFER_JIKES} = yes; then
	dnl Determine if Jikes is available.
	AC_PATH_PROG(JIKES, jikes, not_found)
	if test ${JIKES} = not_found; then
		dnl Jikes not found, so we check for javac
		AC_PATH_PROG(JAVAC, javac, not_found)
		if test ${JAVAC} = not_found; then
			AC_MSG_ERROR('No Java compiler found')
		else
			AC_MSG_WARN('Jikes not found. Use javac.')
		fi
	else
		JAVAC=${JIKES}
	fi
else
	dnl Determine if we have the Java compiler.
	AC_PATH_PROG(JAVAC, javac, not_found)
	if test ${JAVAC} = not_found; then
		AC_PATH_PROG(JIKES, jikes, not_found)
		if test ${JIKES} = not_found; then
			AC_MSG_ERROR('No Java compiler found')
		else
			JAVAC=${JIKES}
		fi
	fi
fi
AC_SUBST(JAVAC)

dnl Determine if we have the Java header file generator
dnl if Java major version >= 9, use `javac -h` instead of `javah -d`
AC_SUBST([JAVA_MAJOR_VERSION],
        [$( ${JAVAC} -version 2>&1 | awk '{print $2}' | awk -F. '{print $1}' )])

if test $JAVA_MAJOR_VERSION -ge 9; then
	JAVAH="${JAVAC}"
	JAVAH_OPT="-h"
else
	AC_PATH_PROG(JAVAH, javah, not_found)
	if test ${JAVAH} = not_found; then
		AC_MSG_ERROR('No Java header generator (javah) found')
	fi
	JAVAH_OPT="-d"
fi
AC_SUBST(JAVAH)
AC_SUBST(JAVAH_OPT)

dnl Determine if we have jar
AC_PATH_PROG(JAR, jar, not_found)
if test ${JAR} = not_found; then
	AC_MSG_ERROR('No jar found')
fi
AC_SUBST(JAR)

dnl Determine if we have javadoc
AC_PATH_PROG(JAVADOC, javadoc, )
AC_SUBST(JAVADOC)

PATH=${OLDPATH}
export PATH

dnl Check if javadoc destination specified
JAVADOC_DIR='${prefix}/share/doc/jmagick'
AC_ARG_WITH([javadoc-dir],
  AS_HELP_STRING([--with-javadoc-dir=PATH],
    [where Javadoc is generated]),
    [JAVADOC_DIR="${withval}"],
    [JAVADOC_DIR="${withval}"])
AC_SUBST(JAVADOC_DIR)

dnl Do you print extra diagnostic?
AC_ARG_ENABLE([diagnostic],
  AS_HELP_STRING([--enable-diagnostic],
    [enable C diagnostic output (default off)]),
    [C_DEBUG=-DDIAGNOSTIC],
    [C_DEBUG=])
AC_SUBST(C_DEBUG)

AC_ARG_WITH([magick-version],
  AS_HELP_STRING([--with-magick-version=VERSION],
    [ImageMagick version]),
    [MAGICK_VERSION="${withval}"])
if (test -n "$MAGICK_VERSION"); then
  VER_MAJOR=$(echo "$MAGICK_VERSION" | cut -d . -f 1)
  VER_MINOR=$(echo "$MAGICK_VERSION" | cut -d . -f 2)
  VER_MICRO=$(echo "$MAGICK_VERSION" | cut -d . -f 3)
fi

dnl Check if ImageMagick home specified
AC_ARG_WITH([magick-home],
  AS_HELP_STRING([--with-magick-home=PATH],
    [home directory of ImageMagick]),
    [MAGICK_HOME="${withval}"])

for MPATH in ${MAGICK_HOME} /usr /usr/local /usr/local/ImageMagick /usr/local/ImageMagick-${VER_MAJOR}.${VER_MINOR}.${VER_MICRO} ; do
	# Location before IM 6.3.8.something
	if  test -f "${MPATH}/include/magick/api.h" ; then
		MAGICK_LIB_PATH=${MPATH}/lib
		case "$target" in
			*-*-cygwin|*-*-mingw32)
				MAGICK_LIBS="-L${MPATH}/lib -lCORE_RL_wand_ -lCORE_RL_magick_"
				MAGICK_CHECK_LIBS="CORE_RL_wand_ CORE_RL_magick_"
				;;
			*)
				MAGICK_LIBS="-L${MPATH}/lib -lWand -lMagick"
				MAGICK_CHECK_LIBS="Wand Magick"
				;;
		esac
		MAGICK_INCLUDES=-I${MPATH}/include
		MAGICK_REALHOME=${MPATH}
		MAGICK_VERSION=6
	fi
	# Location after IM 6.3.8.something
	if  test -f "${MPATH}/include/ImageMagick/magick/api.h" ; then
		MAGICK_LIB_PATH=${MPATH}/lib
		case "$target" in
			*-*-cygwin|*-*-mingw32)
				MAGICK_LIBS="-L${MPATH}/lib -lCORE_RL_wand_ -lCORE_RL_magick_"
				MAGICK_CHECK_LIBS="CORE_RL_wand_ CORE_RL_magick_"
				;;
			*)
				MAGICK_LIBS="-L${MPATH}/lib -lWand -lMagick"
				MAGICK_CHECK_LIBS="Wand Magick"
				;;
		esac
		MAGICK_INCLUDES=-I${MPATH}/include/ImageMagick
		MAGICK_REALHOME=${MPATH}
		MAGICK_VERSION=6
	fi
  # Location after Ubuntu 16.04
	if  test -f "${MPATH}/include/ImageMagick-6/magick/api.h" ; then
		MAGICK_LIB_PATH=${MPATH}/lib
		case "$target" in
			*-*-cygwin|*-*-mingw32)
				MAGICK_LIBS="-L${MPATH}/lib -lCORE_RL_wand_ -lCORE_RL_magick_"
				MAGICK_CHECK_LIBS="CORE_RL_wand_ CORE_RL_magick_"
				;;
			*)
				MAGICK_LIBS="-L${MPATH}/lib -lWand -lMagick"
				MAGICK_CHECK_LIBS="Wand Magick"
				;;
		esac
		MAGICK_INCLUDES=-I${MPATH}/include/ImageMagick-6
		MAGICK_REALHOME=${MPATH}
		MAGICK_VERSION=6
	fi
	# Location after IM 7.something
	if  test -f "${MPATH}/include/MagickCore/MagickCore.h" ; then
		MAGICK_LIB_PATH=${MPATH}/lib
		case "$target" in
			*-*-cygwin|*-*-mingw32)
				MAGICK_LIBS="-L${MPATH}/lib -lCORE_RL_MagickWand_ -lCORE_RL_MagickCore_"
				MAGICK_CHECK_LIBS="CORE_RL_MagickWand_ CORE_RL_MagickCore_"
				;;
			*)
				MAGICK_LIBS="-L${MPATH}/lib -lMagickWand -lMagickCore"
				MAGICK_CHECK_LIBS="MagickWand MagickCore"
				;;
		esac
		MAGICK_INCLUDES=-I${MPATH}/include
		MAGICK_REALHOME=${MPATH}
		MAGICK_VERSION=7
	fi
	# Location for some Linux distros
	if  test -f "${MPATH}/include/ImageMagick-7/MagickCore/MagickCore.h" ; then
		MAGICK_LIB_PATH=${MPATH}/lib
		case "$target" in
			*-*-cygwin|*-*-mingw32)
				MAGICK_LIBS="-L${MPATH}/lib -lCORE_RL_MagickWand_ -lCORE_RL_MagickCore_"
				MAGICK_CHECK_LIBS="CORE_RL_MagickWand_ CORE_RL_MagickCore_"
				;;
			*)
				MAGICK_LIBS="-L${MPATH}/lib -lMagickWand -lMagickCore"
				MAGICK_CHECK_LIBS="MagickWand MagickCore"
				;;
		esac
		MAGICK_INCLUDES=-I${MPATH}/include/ImageMagick-7
		MAGICK_REALHOME=${MPATH}
		MAGICK_VERSION=7
	fi
done

dnl Check if MagickWand-conf exists
OLDPATH=${PATH}
# MagickWand-config isn't installed in /usr/bin in Ubuntu 16.04+ due
# to changes in libmagickcore-dev package.
AC_ARG_WITH([magickwand-config],
  AS_HELP_STRING([--with-magickwand-config=PATH],
    [path to magickwand-config tool]),
  [magickwand_config="$withval"],
  [magickwand_config=""])

if test -n "$magickwand_config"; then
    MAGICKCONF="$magickwand_config"
else
    PATH=${MAGICK_REALHOME}/bin:/usr/${LIB}/${build_os}/ImageMagick-${VER_MAJOR}.${VER_MINOR}.${VER_MICRO}-Q64HDRI/bin:/usr/${LIB}/${build_os}/ImageMagick-${VER_MAJOR}.${VER_MINOR}.${VER_MICRO}-Q64/bin:/usr/${LIB}/${build_os}/ImageMagick-${VER_MAJOR}.${VER_MINOR}.${VER_MICRO}-Q32HDRI/bin:/usr/${LIB}/${build_os}/ImageMagick-${VER_MAJOR}.${VER_MINOR}.${VER_MICRO}-Q32/bin:/usr/${LIB}/${build_os}/ImageMagick-${VER_MAJOR}.${VER_MINOR}.${VER_MICRO}-Q16HDRI/bin:/usr/${LIB}/${build_os}/ImageMagick-${VER_MAJOR}.${VER_MINOR}.${VER_MICRO}-Q16/bin:/usr/${LIB}/${build_os}/ImageMagick-${VER_MAJOR}.${VER_MINOR}.${VER_MICRO}-Q8HDRI/bin:/usr/${LIB}/${build_os}/ImageMagick-${VER_MAJOR}.${VER_MINOR}.${VER_MICRO}-Q8/bin:/usr/${LIB}/${build_os}/ImageMagick-${VER_MAJOR}.${VER_MINOR}.${VER_MICRO}/bin:${PATH}
    export PATH
    AC_PATH_PROG(MAGICKCONF, MagickWand-config, not_found)
    PATH=${OLDPATH}
    export PATH
fi

dnl Added $MAGICK_LIB_PATH and $MAGICK_INCLUDES here because of
dnl a possible bug in Magick-conf for ImageMagick 5.4.6
if (test $MAGICKCONF != not_found); then
	LDFLAGS="${LDFLAGS} `$MAGICKCONF --ldflags`"
	MAGICK_LIBS="`$MAGICKCONF --libs`"
	MAGICK_INCLUDES=`$MAGICKCONF --cppflags`
	MAGICK_LIB_PATH=`$MAGICKCONF --exec-prefix`/lib
	MAGICK_REALHOME=`$MAGICKCONF --exec-prefix`
	MAGICK_CHECK_LIBS=`$MAGICKCONF --libs | awk 'function ltrim(s) { sub(/^[ \t\r\n]+/, "", s); return s } function rtrim(s) { sub(/[ \t\r\n]+$/, "", s); return s } function trim(s) { return rtrim(ltrim(s)); } END { for (i = 1; i <= NF; i++) { if ( substr($i, 0, 2) != "-l") {$i=""} else {$i=substr($i,3)}} print trim($0)}'`
	MAGICK_VERSION=`$MAGICKCONF --version | cut -c1`
fi

dnl Check if ImageMagick library path specified
AC_ARG_WITH([magick-lib-dir],
  AS_HELP_STRING([--with-magick-lib-dir=PATH],
    [library path of ImageMagick]),
    [MAGICK_LIB_PATH="${withval}"],
    [MAGICK_LIBS="-L${withval}"])

dnl Check if ImageMagick include path specified
AC_ARG_WITH([magick-inc-dir],
  AS_HELP_STRING([--with-magick-inc-dir=PATH],
    [path include path of ImageMagick]),
    [MAGICK_INCLUDES="-I${withval}"])

if ( test -f "${MAGICK_INCLUDE_PATH}/magick/api.h" ) ; then
	MAGICK_INCLUDES=-I${MAGICK_INCLUDE_PATH}
	MAGICK_VERSION=6
fi
if ( test -f "${MAGICK_INCLUDE_PATH}/MagickCore/MagickCore.h" ) ; then
	MAGICK_INCLUDES=-I${MAGICK_INCLUDE_PATH}
	MAGICK_VERSION=7
fi

dnl Ensure proper includes in source files
CFLAGS="${CFLAGS} -DIMAGEMAGICK_HEADER_STYLE_${MAGICK_VERSION}"

dnl Check for the ImageMagick headers
OLD_CPPFLAGS=${CPPFLAGS}
if test -n "${MAGICK_INCLUDES}"; then
	CPPFLAGS="${MAGICK_INCLUDES} ${CPPFLAGS}"
fi

if test "${MAGICK_VERSION}" = 7 ; then
	MAGICK_CHECK_HEADERS="MagickWand/MagickWand.h MagickCore/MagickCore.h"
elif test "${MAGICK_VERSION}" = 6 ; then
	MAGICK_CHECK_HEADERS="wand/magick-wand.h magick/api.h"
fi

for MHEADER in ${MAGICK_CHECK_HEADERS} ; do
	AC_CHECK_HEADER(${MHEADER}, HAVE_MAGICK_HEADER=yes, HAVE_MAGICK_HEADER=no)
	if test "${HAVE_MAGICK_HEADER}" = no ; then
		AC_MSG_ERROR('Unable to find ImageMagick header file: ${MHEADER}')
	fi
done

dnl Check for the ImageMagick libraries
OLD_LDFLAGS=${LDFLAGS}
if test -n "${MAGICK_LIBS}"; then
	LDFLAGS="${MAGICK_LIBS} ${LDFLAGS}"
fi

MAGICK_CHECK_LIB_FUNCTIONS="NewPixelWand ConstituteImage"
MLIB_INDEX=1
for MLIB in ${MAGICK_CHECK_LIBS} ; do
	MLIBFUNCTION=`echo ${MAGICK_CHECK_LIB_FUNCTIONS} | awk "{print \\$$MLIB_INDEX}"`
	AC_CHECK_LIB(${MLIB}, ${MLIBFUNCTION},
             HAVE_MAGICK_LIB=yes, HAVE_MAGICK_LIB=no)
	if test ${HAVE_MAGICK_LIB} = no; then
		AC_MSG_ERROR('Unable to find ImageMagick library: ${MLIB}')
	fi
	(( MLIB_INDEX++ ))
done

CPPFLAGS=${OLD_CPPFLAGS}
LDFLAGS=${OLD_LDFLAGS}



dnl Configure libtool
AC_DISABLE_STATIC
LT_INIT

AC_SUBST(JAVA_INCLUDES)
AC_SUBST(X11_LIBS)
AC_SUBST(X11_INCLUDES)
AC_SUBST(MAGICK_LIB_PATH)
AC_SUBST(MAGICK_LIBS)
AC_SUBST(MAGICK_INCLUDES)
AC_SUBST(CFLAGS)
AC_SUBST(LDFLAGS)
AC_SUBST(JFLAGS)
AC_SUBST(LIBS)
AC_SUBST(SHARED_LDFLAGS)
AC_SUBST(LIBTOOL_LINK_FLAG)
AC_SUBST(SHARED_LIB_EXT)

AC_CONFIG_FILES([Makefile Make.def])
AC_OUTPUT
