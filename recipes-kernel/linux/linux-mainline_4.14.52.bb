SECTION = "kernel"
DESCRIPTION = "Mainline Linux kernel"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d7810fab7487fb0aad327b76f1be7cd7"
COMPATIBLE_MACHINE = "(sun4i|sun5i|sun7i|sun8i|sun50i)"

inherit kernel

PV = "4.14.52"
PR = "r1"
SRCREV_pn-${PN} = "7e5420c352680fc7af329828f4155481bd5c11ad"

MACHINE_KERNEL_PR_append = "a"

SRC_URI += "git://github.com/friendlyarm/linux.git;branch=sunxi-4.14.y;protocol=git \
        file://defconfig \
        "

S = "${WORKDIR}/git"
