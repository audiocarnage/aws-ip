package ch.georgiou.util.aws.ip.di;

import ch.georgiou.util.aws.ip.common.Config;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class ConfigModule {

    @Provides
    @Singleton
    public Config provideConfig() {
        return new Config();
    }
}
