package org.tarot.models;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Théophile Dano
 *
 */
public class ModelStore<T extends AbstractModel> {

    public static final String DEFAULT_PATH = ".";
    private static String path;
    private static Preferences preferences;
    static {
        preferences = Preferences.systemNodeForPackage(ModelStore.class);
        setPath(preferences.get("path", DEFAULT_PATH));
    }
    private String storePath;
    private List<T> modelList;
    private ModelReader<T> reader;
    private ModelWriter<T> writer;

    public ModelStore(Class<T> tClass) {
        this(path, tClass);
    }

    public ModelStore(String path, Class<T> tClass) {
        storePath = path + "/" + tClass.getSimpleName();
        modelList = new ArrayList<>();
        reader = new ModelReader<>(storePath);
        writer = new ModelWriter<>(storePath);
    }

    public boolean delete(String id) {
        File fileToDelete = FileUtils.getFile(storePath + "/" + id);
        return FileUtils.deleteQuietly(fileToDelete);
    }

    public boolean delete(T t) {
        return delete(t.getId());
    }

    public void write(T t) {
        writer.write(t);
    }

    public T read(String id) {
        return reader.read(id);
    }

    public List<T> getAll() {
        return getAll(false);
    }

    public List<T> getAll(boolean forceRead) {
        if (!forceRead && !modelList.isEmpty()) {
            return modelList;
        }
        modelList = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(reader.getPath()))) {
            paths.filter(Files::isRegularFile) //
                            .map(Path::toString).map(x -> x.substring(x.lastIndexOf(File.separatorChar))) //
                            .map(reader::read) //
                            .forEach(modelList::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return modelList;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String newPath) {
        if (StringUtils.isBlank(newPath)) {
            setPath(DEFAULT_PATH);
            return;
        }
        path = newPath;
        preferences.put("path", newPath);
    }
}
