package ch.georgiou.util.aws.ip;

import ch.georgiou.util.aws.ip.common.Config;
import ch.georgiou.util.aws.ip.di.DaggerAppComponent;
import ch.georgiou.util.aws.ip.domain.IPv4AddressRange;
import ch.georgiou.util.aws.ip.input.Parser;
import ch.georgiou.util.aws.ip.output.OutputManager;
import lombok.extern.log4j.Log4j2;
import org.jooq.lambda.Unchecked;

import javax.inject.Inject;
import java.util.Set;

@Log4j2
public class Application {

    @Inject
    Config config;

    @Inject
    Parser parser;

    @Inject
    OutputManager outputManager;

    public static void main(String[] args) {
        Application application = new Application();
        DaggerAppComponent.builder().build().inject(application);
        application.run();
    }

    private void run() {
        log.info("starting " + config.getProperty("app.name"));
        Set<IPv4AddressRange> addressRanges = parser.getAddressRanges();
        addressRanges.forEach(log::debug);
        outputManager.writeToFile(writer -> addressRanges.forEach(Unchecked.consumer(addressRange ->
                writer.write(addressRange.toCIDR() + "\n"))));
    }
}
