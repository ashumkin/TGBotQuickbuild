package ru.zapped.tgbot.quickbuild;

import org.apache.commons.lang3.StringUtils;

import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.context.environment.Environment;
import org.cfg4j.source.files.FilesConfigurationSource;
import org.cfg4j.source.system.EnvironmentVariablesConfigurationSource;

import java.io.File;
import java.util.NoSuchElementException;

public class TGBotQuickbuildConfigImpl implements TGBotQuickbuildConfig {
    private ConfigurationProvider fileConfigProvider;
    private ConfigurationProvider envConfigProvider ;

    public TGBotQuickbuildConfigImpl() {
        super();
        envConfigProvider = getConfigProviderBuilder(new EnvironmentVariablesConfigurationSource(), new TGBotEnvironment())
                .build();
    }

    private String defaultConfigFile() {
        String home = System.getenv("HOME");
        if (StringUtils.isEmpty(home)) {
            home = "";
        }
        return home + "/.config/tgbotquickbuild/config";
    };

    private String getConfigValue(String key) {
        try {
            return envConfigProvider.getProperty(key.toUpperCase(), String.class);
        } catch (NoSuchElementException e) {
            try {
                return fileConfigProvider.getProperty("tgbot." + key, String.class);
            } catch (NoSuchElementException e2) {
                return "";
            }
        }
    }

    public String getFile() {
        try {
            return envConfigProvider.getProperty("CONFIG", String.class);
        } catch (NoSuchElementException e) {
            return defaultConfigFile();
        }
    };

    public String botToken() {
        return getConfigValue("quickbuild.token");
    };

    public String botName() {
        return getConfigValue("quickbuild.name");
    };

    public String botTGEndpointURL() {
        return getConfigValue("quickbuild.endpoint_url");
    };

    private ConfigurationProviderBuilder getConfigProviderBuilder(ConfigurationSource source, Environment environment) {
        return new ConfigurationProviderBuilder()
                .withConfigurationSource(source)
                .withEnvironment(environment);
    }

    public void read() {
        fileConfigProvider = getConfigProviderBuilder(
                    new FilesConfigurationSource(new TGConfigFilesProvider(this.getFile())),
                    new TGBotFileEnvironment(new File(this.getFile()).getParent()))
                .build();
    }
}
