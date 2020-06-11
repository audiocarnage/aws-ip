package ch.georgiou.util.aws.ip.di;

import ch.georgiou.util.aws.ip.Application;
import ch.georgiou.util.aws.ip.common.Config;
import ch.georgiou.util.aws.ip.input.Parser;
import ch.georgiou.util.aws.ip.output.OutputManager;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = { ConfigModule.class, ParserModule.class, OutputManagerModule.class })
public interface AppComponent {

    Config provideConfig();

    Parser provideParser();

    OutputManager provideOutputManager();

    void inject(Application application);
}
