/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Jul 3, 2017
 */
package com.ianmann.mind.exceptions;

/**
 * @TODO: TODO
 *
 * @author Ian
 * Created: Jul 3, 2017
 *
 */
public class FileNotDeletedError extends Error {

	public FileNotDeletedError(String _path) {
		super("File at \"" + _path + "\" could not be deleted.");
	}
}
