# for ImageMagick versions < 6.4.5, use 'rpmbuild --with oldIM'
%define oldIM		0
%{?_without_oldIM:	%global oldIM 0}
%{?_with_oldIM:		%global oldIM 1}

# for Java SDKs supplied directly from Sun, use 'rpmbuild --with sunjdk'
%define sunjdk		0
%{?_without_sunjdk:	%global sunjdk 0}
%{?_with_sunjdk:	%global sunjdk 1}

# %define magick_home %(convert --version | sed -n -e '1s@^.*file:@@' -e 's@/doc/index.html.*$@@' -e 1p)
# %define magick_home "/usr/lib64/ImageMagick-6.8.6/"
%define magick_home %(pkg-config --cflags-only-I ImageMagick | sed 's/^-I//')

Summary: open source Java interface of ImageMagick
Name: jmagick
# check configure.in, too !
Version: %(awk -F[\.] '{print $1"."$2"."$3}' <<< `rpm -q --queryformat '%{version}\n' ImageMagick`)
Release: 6
License: GPL 
Group: Application/Java
URL: www.ablesky.com
Source0: jmagick-ferbar-git-src.tar.bz2
#Patch0: magickcoregenesis-error.patch
#Patch1: jmagick-getTypeMetric-kerning-spacing.patch
#Patch2: jmagick-setStringMethod-segv.patch
BuildRoot: %{_tmppath}/%{name}-%{version}-%{release}-root
%if %{oldIM}
BuildRequires: ImageMagick-devel
%else
BuildRequires: ImageMagick-devel >= %{version}
Requires: ImageMagick >= %{version}
%endif
#
# logic for java dependencies
Requires:  jre
%if %{sunjdk}
# good for Sun RPMs
BuildRequires: jdk
%else
# good for JPackage, java-*-gcj-compat, java-*-openjdk
BuildRequires: java-sdk
%endif

%description

JMagick is an open source Java interface of ImageMagick. It is
implemented in the form of a thin Java Native Interface (JNI) layer
into the ImageMagick API.

%prep
%setup -q -n jmagick-ferbar-git
%if %{oldIM}
#%patch -p1 -b .mcgenesis
%endif
#%patch1 -p1
#%patch2 -p1

%build
autoreconf --force --install
automake --add-missing
%{configure} --with-magick-home=%{magick_home} --with-magick-version=%{version}
%{__make}

%install
make DESTDIR=%{buildroot} install
%{__ln_s} libJMagick-%{version}.so %{buildroot}%{_libdir}/libJMagick.so

%clean
rm -rf $RPM_BUILD_ROOT


%files
%defattr(-,root,root,-)
%doc CREDITS Changelog.txt INSTALL 
%{_libdir}/*


%changelog
* Mon Dec 18 2017 Jonathan Horowitz <jhorowitz@firedrum.com>
- Switch autoconf to autoreconf and automake

* Thu Jun  4 2009 John Morris <jman@ablesky.com> - 6.4.0-3
- Try to compute magick_home macro automatically
- Remove configure --jdk-home switch to grab JAVA_HOME from environment
- add rpmbuild --with sunjdk switch to build against Sun-supplied RPMs

* Wed Jun  3 2009 John Morris <jman@ablesky.com> - 6.4.0-2
- add option '--with oldIM' and patch to build for ImageMagick pre-6.4.5 (undefined symbol: MagickCoreGenesis)
- add libJMagick.so symlink

* Mon Jun  1 2009 John Morris <jman@ablesky.com> - 6.4.0-1
- Initial build
