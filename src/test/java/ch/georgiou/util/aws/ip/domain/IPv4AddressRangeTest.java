package ch.georgiou.util.aws.ip.domain;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;

@Log4j2
class IPv4AddressRangeTest {

    @Test
    void testRoutingMask() {
        log.info(Integer.parseUnsignedInt("c0", 16));
        log.info(Integer.toHexString(224));
        log.info(Integer.toHexString(255));
        log.info(Integer.toHexString(255));
        log.info(Integer.toHexString(192));
        log.info(Integer.parseUnsignedInt("7fffff00", 16));
        log.info(Integer.parseUnsignedInt("7fffffff", 16) - Integer.parseUnsignedInt("7ffffffe", 16));
        log.info(getIntLittleEndian(new int[] {0x7f, 0xff, 0xff, 0xff}));
    }

    int getIntLittleEndian(@Nonnull int[] bytes) {
        return (bytes[3] & 0xff)
                | ((bytes[2] & 0xff) << 8)
                | ((bytes[1] & 0xff) << 16)
                | ((bytes[0] & 0xff) << 24);
    }
}