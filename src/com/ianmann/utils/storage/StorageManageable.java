/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Jun 6, 2017
 */
package com.ianmann.utils.storage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Provides an interface with methods for CRUD operations on a given class.
 * 
 * @TODO: TODO
 *
 * @author Ian
 * Created: Jun 6, 2017
 */
public interface StorageManageable<T> {

	/**
	 * Create and save to storage an instance of T.
	 * The instance will have values of _params.
	 * @return
	 */
	public T create(Object... _params);
	
	/**
	 * Save an instance of T specified by _object. This
	 * is specifically meant to be used when updating
	 * an object and it needs to take affect in the
	 * place of storage where the instance is stored.
	 * @param _object
	 */
	public void save(T _object);
	
	/**
	 * Delete an instance of T specified by _object. This
	 * will remove this instance from storage.
	 * @param _object
	 * @return
	 * true - if the object was successfuly deleted. <br>
	 * false - if  the object was not deleted.
	 */
	public boolean delete(T _object);
	
	/**
	 * Get a list of instances of T. The instances will
	 * fulfill the conditions stated in _params. For
	 * example if one parameter is "name": "Johnson",
	 * then any instance with the "name" equal to
	 * "Johnson" will be returned.
	 * @param _params
	 * @return
	 */
	public ArrayList<T> get(HashMap<String, Object> _params);
	
	/**
	 * Returns all instance of T that are valid. The
	 * implementation of this will decide which objects
	 * are valid.
	 * @return
	 */
	public ArrayList<T> getAll();
}
