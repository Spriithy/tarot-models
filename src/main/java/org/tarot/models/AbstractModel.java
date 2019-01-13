package org.tarot.models;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * 
 * @author Théophile Dano
 *
 */
public abstract class AbstractModel implements Serializable {

    protected static Map<String, AbstractModel> localModels;
    private String id;

    public AbstractModel() {
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    public abstract AbstractModel get(String id);

    protected static <T extends AbstractModel> void loadLocalModels(Class<T> tClass) {
        loadLocalModels(ModelStore.getPath() + "/" + tClass.getSimpleName());
    }

    protected static <T extends AbstractModel> void loadLocalModels(String path) {
        localModels = new HashMap<>();
        ModelReader<T> modelReader = new ModelReader<T>(path);
        try (Stream<Path> paths = Files.walk(Paths.get(modelReader.getPath()))) {
            paths.filter(Files::isRegularFile).map(Path::toString).forEach(modelId -> {
                localModels.put(modelId, modelReader.read(modelId));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static <T extends AbstractModel> void loadLocalModels(String path, Class<T> tClass) {
        loadLocalModels(path + "/" + tClass.getSimpleName());
    }
}
