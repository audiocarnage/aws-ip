package ch.georgiou.util.aws.ip.di;

import ch.georgiou.util.aws.ip.common.Config;
import ch.georgiou.util.aws.ip.output.OutputManager;
import dagger.Module;
import dagger.Provides;

import javax.annotation.Nonnull;
import javax.inject.Singleton;

@Module
public class OutputManagerModule {

    @Provides
    @Singleton
    public OutputManager provideOutputManager(@Nonnull Config config) {
        return new OutputManager(config);
    }
}
