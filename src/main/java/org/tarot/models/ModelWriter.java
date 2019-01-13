package org.tarot.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 
 * @author Théophile Dano
 *
 */
public class ModelWriter<T extends AbstractModel> {

    private String path;

    public ModelWriter(Class<T> tClass) {
        path = ModelStore.getPath() + "/" + tClass.getSimpleName();
        new File(path).mkdirs();
    }

    public ModelWriter(String path) {
        this.path = path;
        new File(path).mkdirs();
    }

    public void write(T tObject) {
        String objectPath = path + "/" + tObject.getId();
        try (FileOutputStream f = new FileOutputStream(new File(objectPath))) {
            try (ObjectOutputStream o = new ObjectOutputStream(f)) {
                o.writeObject(tObject);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
