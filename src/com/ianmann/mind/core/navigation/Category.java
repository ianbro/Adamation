package com.ianmann.mind.core.navigation;

import java.io.File;

import com.ianmann.mind.Neuron;
import com.ianmann.mind.core.Constants;
import com.ianmann.mind.emotions.EmotionUnit;

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
	
	public Category(EmotionUnit _associated, String _label, Category _parentCategory) {
		super(null, _associated, _label, _parentCategory);
		this.categoryPath = this.getCategoryLocation();
		this.associate(this.parentCategory);
	}
	
	public Category(EmotionUnit _associated, Category _parentCategory) {
		super(null, _associated, _parentCategory);
		this.categoryPath = this.getCategoryLocation();
		this.associate(this.parentCategory);
	}
	
	public Category(EmotionUnit _associated) {
		super(null, _associated, null);
		this.categoryPath = this.getCategoryLocation();
		this.associate(this.parentCategory);
	}
	
	public Category(EmotionUnit _associated, String _label) {
		super(null, _associated, _label, null);
		this.categoryPath = this.getCategoryLocation();
		this.associate(this.parentCategory);
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
			return Constants.NEURON_ROOT + this.parentCategory.categoryPath + this.associatedMorpheme + "/";
		}
	}
	
	/**
	 * Association on categories simply sets the parentCategory for
	 * this category.
	 */
	public void associate(Category _category) {
		if (this.parentCategory != null && !this.parentCategory.equals(_category)) {
			// Remove this file from this.parentCategory
			this.parentCategory.removeNeuralPathway(_category);
			this.removeCategoryFolder();
		}
		this.parentCategory = _category;
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
	
	protected void removeCategoryFolder() {
		new File(this.categoryPath).delete();
	}
	
	/**
	 * @Override
	 * Add the folder that is represented by this category.
	 */
	protected void save() {
		super.save();
		new File(this.categoryPath).mkdirs();
	}

}
