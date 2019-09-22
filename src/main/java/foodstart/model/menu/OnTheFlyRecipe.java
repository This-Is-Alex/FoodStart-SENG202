package foodstart.model.menu;

import foodstart.model.stock.Ingredient;

import java.util.Map;

/**
 * Models an on the fly recipe, a recipe used to model a customised recipe
 */
public class OnTheFlyRecipe extends Recipe {

	/**
	 * The permanent recipe that the recipe is based on
	 */
	private PermanentRecipe basedOn;

	/**
	 * Constructs an instance of an on the fly recipe
	 *
	 * @param basis       the recipe that the recipe is based on
	 * @param ingredients the ingredients that the recipe contains
	 * @param price       the price of the recipe
	 */
	public OnTheFlyRecipe(PermanentRecipe basis, Map<Ingredient, Integer> ingredients, float price) {
		super(price, ingredients);
		this.basedOn = basis;
	}

	/**
	 * Returns the permanent recipe that this recipe is based on
	 *
	 * @return the permanent recipe that this recipe is based on
	 */
	public PermanentRecipe getBasedOn() {
		return basedOn;
	}

	/**
	 * Sets the permanent recipe that this recipe is based on
	 *
	 * @param basedOn the permanent recipe that this recipe is based on
	 */
	public void setBasedOn(PermanentRecipe basedOn) {
		this.basedOn = basedOn;
	}

	@Override
	/**
	 * Returns the display name of the recipe this recipe is based on with the ending " (modified)"
	 *
	 * @return the display name of the recipe
	 */
	public String getDisplayName() {
		return this.basedOn.getDisplayName().concat(" (modified)");
	}

	@Override
	/**
	 * Returns the id of the recipe that this is based on
	 * @return the id of the recipe that this is based on
	 */
	public int getId() {
		return this.basedOn.getId();
	}
}

