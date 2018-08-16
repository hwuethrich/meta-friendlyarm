DESCRIPTION="Upstream's U-boot configured for sunxi devices"

require recipes-bsp/u-boot/u-boot.inc

DEPENDS += " bc-native dtc-native swig-native python-native "
DEPENDS_append_sun50i = " atf-sunxi "

LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=a2c678cfd4a4d97135585cad908541c6"

COMPATIBLE_MACHINE = "(sun4i|sun5i|sun7i|sun8i|sun50i)"

DEFAULT_PREFERENCE_sun4i="1"
DEFAULT_PREFERENCE_sun5i="1"
DEFAULT_PREFERENCE_sun7i="1"
DEFAULT_PREFERENCE_sun8i="1"
DEFAULT_PREFERENCE_sun50i="1"

SRC_URI = "git://github.com/friendlyarm/u-boot.git;protocol=git;branch=sunxi-v2017.x \
           file://boot.cmd \
           "

SRCREV = "859ef0e5df08e68482fa3e9263a441be174b90d4"

PV = "v2017.11+git${SRCPV}"
PE = "1"

S = "${WORKDIR}/git"

UBOOT_ENV_SUFFIX = "scr"
UBOOT_ENV = "boot"

EXTRA_OEMAKE += ' HOSTLDSHARED="${BUILD_CC} -shared ${BUILD_LDFLAGS} ${BUILD_CFLAGS}" \
                  PYTHON=${STAGING_DIR_NATIVE}/usr/bin/python-native/python STAGING_INCDIR=${STAGING_INCDIR_NATIVE} \
                  STAGING_LIBDIR=${STAGING_LIBDIR_NATIVE}'

EXTRA_OEMAKE_append_sun50i = " BL31=${DEPLOY_DIR_IMAGE}/bl31.bin "

do_compile_sun50i[depends] += "atf-sunxi:do_deploy"

do_compile_append() {
    ${B}/tools/mkimage -C none -A arm -T script -d ${WORKDIR}/boot.cmd ${WORKDIR}/${UBOOT_ENV_BINARY}
    oe_runmake -C ${S} O=${B} u-boot-sunxi-with-spl.bin
}

do_deploy_append() {
  install -m 644 ${B}/u-boot-sunxi-with-spl.bin ${DEPLOYDIR}/u-boot-sunxi-with-spl.bin
}
