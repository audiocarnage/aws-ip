package ch.georgiou.util.aws.ip.di;

import ch.georgiou.util.aws.ip.common.Config;
import ch.georgiou.util.aws.ip.input.Parser;
import dagger.Module;
import dagger.Provides;

import javax.annotation.Nonnull;

@Module
public class ParserModule {

    @Provides
    public Parser provideConfig(@Nonnull Config config) {
        return new Parser(config);
    }
}
