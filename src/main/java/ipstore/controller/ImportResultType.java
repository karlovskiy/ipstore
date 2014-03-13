package ipstore.controller;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Here will be javadoc
 *
 * @author karlovsky
 * @since 1.0
 */
public class ImportResultType<T> {

    private final Collection<T> added = new ArrayList<T>();
    private final Collection<T> exists = new ArrayList<T>();

    public boolean addAdded(T newObj) {
        return added.add(newObj);
    }

    public boolean addExists(T existsObj) {
        return exists.add(existsObj);
    }

    public Collection<T> getAdded() {
        return added;
    }

    public Collection<T> getExists() {
        return exists;
    }

    public int getAddedCount() {
        return added.size();
    }

    public int getExistsCount() {
        return exists.size();
    }

}
