package ch.georgiou.util.aws.ip.output;

import ch.georgiou.util.aws.ip.common.Config;
import lombok.extern.log4j.Log4j2;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;

@Log4j2
public class OutputManager {

    private final Config config;

    @Inject
    public OutputManager(@Nonnull Config config) {
        this.config = config;
    }

    public void writeToFile(@Nonnull Consumer<BufferedWriter> writerConsumer) {
        String outputFile = config.getProperty("output.file");
        deleteFileIfExists(outputFile);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile, StandardCharsets.UTF_8))) {
            writerConsumer.accept(bw);
            bw.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void deleteFileIfExists(@Nonnull String fileName) {
        Path file = Path.of(fileName);
        try {
            if (Files.exists(file)) {
                Files.delete(file);
                log.debug(fileName + " deleted");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
