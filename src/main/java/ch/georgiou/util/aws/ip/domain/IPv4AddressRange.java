package ch.georgiou.util.aws.ip.domain;

import lombok.extern.log4j.Log4j2;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Formattable;
import java.util.Formatter;
import java.util.Objects;

@Log4j2
public class IPv4AddressRange implements Serializable, Comparable<IPv4AddressRange>, Formattable {

    private final String ipv4Address;
    private final String subnet;
    private final String subnetMask;

    public IPv4AddressRange(@Nonnull String ipv4Address, @Nonnull String networkMask) {
        this.ipv4Address = checkIpAddress(ipv4Address);
        this.subnet = networkMask;
        subnetMask = computeSubnetMask(networkMask);
    }

    @Nonnull
    public String getIpv4Address() {
        return ipv4Address;
    }

    @Nonnull
    public String getSubnet() {
        return subnet;
    }

    @Nonnull
    public String getSubnetMask() {
        return subnetMask;
    }

    @Override
    public int compareTo(@Nonnull IPv4AddressRange o) {
        return Comparator.comparing(IPv4AddressRange::getIpv4Address)
                .thenComparing(IPv4AddressRange::getSubnet)
                .thenComparing(IPv4AddressRange::getSubnetMask)
                .compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IPv4AddressRange)) {
            return false;
        }
        IPv4AddressRange iPv4AddressRange = (IPv4AddressRange) o;
        return Objects.equals(ipv4Address, iPv4AddressRange.ipv4Address)
                && Objects.equals(subnet, iPv4AddressRange.subnet)
                && Objects.equals(subnetMask, iPv4AddressRange.subnetMask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipv4Address, subnet, subnetMask);
    }

    @Override
    public String toString() {
        return "IPv4AddressRange{" +
                "ipv4Address='" + ipv4Address + '\'' +
                ", subnet='" + subnet + '\'' +
                ", subnetMask='" + subnetMask + '\'' +
                '}';
    }

    public String toCIDR() {
        return ipv4Address + "/" + subnet;
    }

    /**
     * Outputs in CIDR notation
     */
    @Override
    public void formatTo(Formatter formatter, int flags, int width, int precision) {
        log.info(formatter.format("IPv4: %s/%s", ipv4Address, subnet));
    }

    @Nonnull
    private String computeSubnetMask(@Nonnull String subnet) {
        final int routingMask = Integer.parseInt(subnet);

        StringBuilder sb = new StringBuilder();
        sb.append("1".repeat(Math.max(0, routingMask)));
        sb.append("0".repeat(Math.max(0, 32 - routingMask)));

        String s = sb.toString();
        String subnetMask = Integer.parseInt(s.substring(0, 8), 2) + ".";
        subnetMask += Integer.parseInt(s.substring(8, 16), 2) + ".";
        subnetMask += Integer.parseInt(s.substring(16, 24), 2) + ".";
        subnetMask += String.valueOf(Integer.parseInt(s.substring(24, 32), 2));

        return subnetMask;
    }

    @Nonnull
    private String checkIpAddress(@Nonnull String ipv4Address) {
        String[] byteBlocks = ipv4Address.split("\\.");
        if (byteBlocks.length != 4) {
            throwException();
        }

        final int firstByte = Integer.parseInt(byteBlocks[0]);
        if (firstByte <= 0 || firstByte > 255) {
            throwException();
        }

        for (int i = 1; i < byteBlocks.length; i++) {
            checkByte(Integer.parseInt(byteBlocks[i]));
        }

        return ipv4Address;
    }

    private void checkByte(int value) {
        if (value < 0 || value > 255) {
            throwException();
        }
    }

    private void throwException() {
        throw new IllegalArgumentException("please provide a valid IPv4 address in CIDR notation");
    }
}
