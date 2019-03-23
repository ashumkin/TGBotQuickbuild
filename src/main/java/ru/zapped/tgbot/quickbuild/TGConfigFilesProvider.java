package ru.zapped.tgbot.quickbuild;

import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class TGConfigFilesProvider implements ConfigFilesProvider {
    private String fFile;

    TGConfigFilesProvider(String file) {
        super();
        fFile = file;
    }

    @Override
    public Iterable<Path> getConfigFiles() {
        return Collections.singletonList(Paths.get(fFile));
    }

    @Override
    public String toString() {
        return "TGCConfigFilesProvider{}";
    }
}
