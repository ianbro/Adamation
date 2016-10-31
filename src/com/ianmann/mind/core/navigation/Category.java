package com.ianmann.mind.core.navigation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.core.Constants;
import com.ianmann.mind.emotions.EmotionUnit;
import com.ianmann.utils.utilities.Files;

public class Category extends Neuron {
	
	/*
	 * These categories always exist. They are
	 * part of the basic functionality of the AI.
	 */
	public static Category LANGUAGE;
	public static Category PATTERN;
	
	/**
	 * path to folder that this category represents.
	 * All neurons in this category will be stored in
	 * this folder.
	 * <br><br>
	 * This is the string representations of a folder. It
	 * is different than {@code this.location}
	 */
	private String categoryPath;
	
	/**
	 * Used for parsing json into a category object.
	 */
	protected Category() {
		super();
	}
	
	public Category(EmotionUnit _associated, String _label, Category _parentCategory) {
		super(null, _associated, _label, _parentCategory);
		this.categoryPath = this.getCategoryLocation();
		if (this.parentCategory != null) {
			try {
				this.assimilate(Category.parse(this.parentCategory));
			} catch (FileNotFoundException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.save();
		}
	}
	
	public Category(EmotionUnit _associated, Category _parentCategory) {
		super(null, _associated, _parentCategory);
		this.categoryPath = this.getCategoryLocation();
		if (this.parentCategory != null) {
			try {
				this.assimilate(Category.parse(this.parentCategory));
			} catch (FileNotFoundException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.save();
		}
	}
	
	public Category(EmotionUnit _associated) {
		super(null, _associated, null);
		this.categoryPath = this.getCategoryLocation();
		if (this.parentCategory != null) {
			try {
				this.assimilate(Category.parse(this.parentCategory));
			} catch (FileNotFoundException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.save();
		}
	}
	
	public Category(EmotionUnit _associated, String _label) {
		super(null, _associated, _label, null);
		this.categoryPath = this.getCategoryLocation();
		if (this.parentCategory != null) {
			try {
				this.assimilate(Category.parse(this.parentCategory));
			} catch (FileNotFoundException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.save();
		}
	}
	
	/**
	 * Returns the path to the folder that will store
	 * the neurons in this category.
	 * <br><br>
	 * Does the logic for where to store this category's folder.
	 * @return
	 */
	protected String getCategoryLocation() {
		if (this.parentCategory == null) {
			return Constants.NEURON_ROOT + this.associatedMorpheme + "/";
		} else {
			Category c = this.getParentCategory();
			return c.categoryPath + this.associatedMorpheme + "/";
		}
	}
	
	/**
	 * Assimilation on categories simply sets the parentCategory for
	 * this category.
	 */
	public void assimilate(Category _category) {
		// If this neuron has a parent category already and it's not the same as _category
		if (this.parentCategory != null && !this.parentCategory.equals(_category)) {
			// Remove this file from this.parentCategory
			Category c = null;
			try {
				c = Category.parse(this.parentCategory);
			} catch (FileNotFoundException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c.removeNeuralPathway(this);
			c.save();
			this.removeCategoryFolder();
		}
		// Set the new parent category
		this.parentCategory = _category.location;
		this.categoryPath = this.getCategoryLocation();
		this.save();
	}

	/**
	 * @Override
	 * This is a category neuron so the file location for
	 * this neuron will be stored in {@code Constants.PATH_TO_CATEGORIES_FOLDER}
	 */
	protected String getFileLocation() {
		return Constants.PATH_TO_CATEGORIES_FOLDER + this.associatedMorpheme + ".ctgry";
	}
	
	/**
	 * Return the path to the category folder that this
	 * neuron represents.
	 * @return
	 */
	public String getCategoryPath() {
		return this.categoryPath;
	}
	
	/**
	 * @Override
	 * Remove the folder that is represented or once was
	 * represented by this category.
	 */
	protected void destroy() {
		super.destroy();
		this.removeCategoryFolder();
	}
	
	/**
	 * Delete the folder that this category stores it's neurons in.
	 */
	protected void removeCategoryFolder() {
		new File(this.categoryPath).delete();
		this.categoryPath = null;
	}
	
	/**
	 * @Override
	 * Add the folder that is represented by this category.
	 */
	public void save() {
		super.save();
		new File(this.categoryPath).mkdirs();
	}
	
	/**
	 * Parse json data in a file into a Neuron object
	 * @param _categoryFile
	 * @return
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	public static Category parse(File _categoryFile) throws FileNotFoundException, ParseException {
		JSONObject jsonNeuron = (JSONObject) Files.json(_categoryFile);
		
		Category n = new Category();
		
		n.categoryPath = (String) jsonNeuron.get("categoryPath");
		
		n.parentCategory = new File(Constants.STORAGE_ROOT + (String) jsonNeuron.get("parentCategory"));
		
		n.SynapticEndings = new ArrayList<File>();
		JSONArray synaptics = (JSONArray) jsonNeuron.get("synapticEndings");
		for (Object path : synaptics) {
			String filePath = Constants.STORAGE_ROOT + (String) path;
			n.SynapticEndings.add(new File(filePath));
		}
		
		n.location = new File(Constants.STORAGE_ROOT + (String) jsonNeuron.get("location"));
		
		n.associatedEmotion = EmotionUnit.getEmotion((String) jsonNeuron.get("associatedEmotion"));
		
		n.associatedMorpheme = (String) jsonNeuron.get("associatedMorpheme");
		
		return n;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject jsonify() {
		JSONObject neuronJson = super.jsonify();
		neuronJson.put("categoryPath", this.categoryPath);
		
		return neuronJson;
	}

}
