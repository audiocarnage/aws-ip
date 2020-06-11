package ch.georgiou.util.aws.ip.input;

import ch.georgiou.util.aws.ip.common.Config;
import ch.georgiou.util.aws.ip.domain.IPv4AddressRange;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.stream.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;
import java.util.TreeSet;

@Log4j2
public class Parser {

    private final Config config;

    @Inject
    public Parser(@NonNull Config config) {
        this.config = config;
    }

    @Nonnull
    public Set<IPv4AddressRange> getAddressRanges() {
        Set<IPv4AddressRange> addressRanges = new TreeSet<>();

        try (InputStream in = new URL(config.getProperty("ip-ranges.url")).openStream()) {
            JsonParser parser = Json.createParser(in);
            while (parser.hasNext()) {
                if (parser.next() == JsonParser.Event.KEY_NAME) {
                    String key = parser.getString();
                    if (key.equals(config.getProperty("json.key")) && parser.next() == JsonParser.Event.VALUE_STRING) {
                        String[] cidr = parser.getString().split("/");
                        addressRanges.add(new IPv4AddressRange(cidr[0], cidr[1]));
                    }
                }
            }
            log.info(String.format("found %d address ranges", addressRanges.size()));
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return addressRanges;
    }
}
