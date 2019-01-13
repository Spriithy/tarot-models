package org.tarot.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 
 * @author Théophile Dano
 *
 */
public class ModelReader<T extends AbstractModel> {

    private String path;

    public ModelReader(Class<T> tClass) {
        path = ModelStore.getPath() + "/" + tClass.getSimpleName();
    }

    public ModelReader(String path) {
        this.path = path;
    }

    public ModelReader(String path, Class<T> tClass) {
        this.path = path + "/" + tClass.getSimpleName();
    }

    public String getPath() {
        return path;
    }

    @SuppressWarnings("unchecked")
    public T read(String fileName) {
        String objectPath = path + "/" + fileName;
        try (FileInputStream f = new FileInputStream(new File(objectPath))) {
            try (ObjectInputStream o = new ObjectInputStream(f)) {
                return (T) o.readObject();
            }
        } catch (ClassNotFoundException e) {
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
