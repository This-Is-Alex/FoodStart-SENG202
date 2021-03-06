package foodstart.manager.menu;

import foodstart.manager.Managers;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.OnTheFlyRecipe;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.menu.Recipe;
import foodstart.model.stock.Ingredient;

import java.util.*;

/**
 * Acts as a controller, storing and managing the recipes in the model
 */
public class RecipeManager {
	/**
	 * A buffer for putting permanent recipes in before writing them to the system
	 */
	private Map<Integer, PermanentRecipe> buffer;

	/**
	 * The map of all permanent recipes modeled
	 */
	private Map<Integer, PermanentRecipe> recipes;

	/**
	 * Constructs an instance of a recipe manager
	 */
	public RecipeManager() {
		this.recipes = new HashMap<Integer, PermanentRecipe>();
		this.buffer = new HashMap<Integer, PermanentRecipe>();
		this.otfManager = new OTFManager();
	}

	/**
	 * An instance of the inner OTF manager
	 */
	public final OTFManager otfManager;

	/**
	 * Pushes a new permanent recipe to the buffer
	 *
	 * @param id           the UID of the recipe
	 * @param name         the display name of the recipe
	 * @param instructions the instructions to make the recipe
	 * @param price        the price of the recipe
	 * @param ingredients  the ingredients that make up the recipe
	 */
	public void pushToBuffer(int id, String name, String instructions, float price, Map<Ingredient, Integer> ingredients) {
		PermanentRecipe recipe = new PermanentRecipe(id, name, instructions, price, ingredients);
		this.buffer.put(id, recipe);
	}

	/**
	 * Returns the map of all permanent recipes modeled
	 *
	 * @return the map of all permanent recipes modeled
	 */
	public Map<Integer, PermanentRecipe> getRecipes() {
		return this.recipes;
	}

	/**
	 * Constructs and adds a permanent recipe to the map of recipes
	 *
	 * @param id           the UID of the recipe
	 * @param name         the display name of the recipe
	 * @param instructions the instructions to make the recipe
	 * @param price        the price of the recipe
	 * @param ingredients  the ingredients that make up the recipe
	 */
	public void addRecipe(int id, String name, String instructions, float price, Map<Ingredient, Integer> ingredients) {
		PermanentRecipe recipe = new PermanentRecipe(id, name, instructions, price, ingredients);
		this.recipes.put(id, recipe);
	}
	
	/**
	 * Adds the given permanent recipe to the map of recipes
	 * @param recipe the permanent recipe added to the manager
	 */
	public void addRecipe(PermanentRecipe recipe) {
        this.recipes.put(recipe.getId(), recipe);
    }

	/**
	 * Gets a recipe by its id, or null if the recipe is not defined
	 *
	 * @param id The unique recipe ID
	 * @return The recipe that the UID refers to, or null
	 */
	public PermanentRecipe getRecipe(int id) {
		return this.recipes.get(id);
	}

	/**
	 * Gets a recipe by its display name, or null if the recipe is not defined
	 *
	 * @param displayName The unique display name of the recipe
	 * @return recipe The permanent recipe that the name refers to, or null
	 */
	public PermanentRecipe getRecipeByDisplayName(String displayName) {
		for (PermanentRecipe recipe : getRecipes().values()) {
			if (recipe.getDisplayName().equals(displayName)) {
				return recipe;
			}
		}
		return null;
	}

	/**
	 * Returns the set of recipes from the ids specified
	 *
	 * @param ids the ids of the recipes to fetch
	 * @return items The set of recipes requested
	 */
	public Set<PermanentRecipe> getRecipes(Collection<Integer> ids) {
		Set<PermanentRecipe> items = new HashSet<PermanentRecipe>();
		for (int id : ids) {
			PermanentRecipe item = this.recipes.get(id);
			if (item != null) {
				items.add(item);
			}
		}
		return items;
	}

	/**
	 * Returns the set of all recipes stored in the map
	 *
	 * @return recipeSet The set of all recipes stored in the map
	 */
	public Set<PermanentRecipe> getRecipeSet() {
		return new HashSet<PermanentRecipe>(this.recipes.values());
	}

	/**
	 * Returns the ingredients in a recipe and their quantity needed as a string
	 *
	 * @param id The id of the recipe
	 * @return out The ingredients and their truck stock quantity as a string
	 */
	public String getIngredientsAsString(int id) {
		PermanentRecipe recipe = this.recipes.get(id);
		if (recipe == null) {
			return "";
		}
		String out = "";
		for (Ingredient ingredient : recipe.getIngredients().keySet()) {
			out = out.concat(String.format("%dx %s, ", recipe.getIngredients().get(ingredient), ingredient.getName()));
		}
		return out.substring(0, out.length() - 2);
	}

	/**
	 * Returns the ingredients in a recipe and their quantity needed as a string
	 * This version is intended for use when unable to differentiate between permanent and on the fly recipes
	 *
	 * @param recipe The recipe to get the ingredients of
	 * @return out The ingredients and their truck stock quantity as a string
	 */
	public String getIngredientsAsString(Recipe recipe) {
		if (recipe == null) {
			return "";
		}
		String out = "";
		for (Ingredient ingredient : recipe.getIngredients().keySet()) {
			out = out.concat(String.format("%dx %s, ", recipe.getIngredients().get(ingredient), ingredient.getName()));
		}
		return out.substring(0, out.length() - 2);
	}

	/**
	 * Removes a recipe given an ID. Cascades through the menu items model
	 *
	 * @param id the ID of the recipe to be removed
	 */
	public void removeRecipe(int id) {
		PermanentRecipe removed = this.recipes.remove(id);
		Set<MenuItem> menuItems = Managers.getMenuItemManager().getMenuItemSet();
		for (MenuItem menuItem : menuItems) {
			menuItem.remove(removed);
		}
		Managers.getOrderManager().removeRecipeFromOrders(removed);
	}
	
	/**
	 * Removes all recipes
	 */
	public void removeAllRecipes() {
	    for (Integer recipeID : new HashSet<Integer>(recipes.keySet())) {
	        this.removeRecipe(recipeID);
	    }
	}

	/**
	 * Mutates a recipe with a given id to have the given parameters
	 *
	 * @param id           the id of the recipe to mutate
	 * @param name         the name to give the recipe
	 * @param instructions the instructions to give the recipe
	 * @param price        the price to give the recipe
	 * @param ingredients  the ingredients to give the recipe
	 */
	public void mutateRecipe(int id, String name, String instructions, float price, Map<Ingredient, Integer> ingredients){
		PermanentRecipe recipe = this.recipes.get(id);
		if (recipe != null) {
			recipe.setDisplayName(name);
			recipe.setPrice(price);
			recipe.setInstructions(instructions);
			recipe.setIngredients(ingredients);
		}
	}

	/**
	 * Generates a new unique id for a recipe
	 * @return a new unique id for a recipe
	 */
	public int generateNewId() {
		return recipes.keySet().size() == 0 ? 0 : Collections.max(recipes.keySet()) + 1;
	}

	/**
	 * Checks if a recipe with a given id exists in the system
	 *
	 * @param id the id to check for
	 * @return true if there is a recipe with the given id; false otherwise
	 */
	public boolean idExists(int id) {
		if (recipes.containsKey(id)) {
			return true;
		}
		return false;
	}

	/**
	 * Adds the current data in the buffer to the modeled recipes
	 */
	public void writeBuffer() {
		this.recipes.putAll(this.buffer);
		buffer.clear();
	}

	/**
	 * Drops the current data in the buffer
	 */
	public void dropBuffer() {
		this.buffer.clear();
	}

	/**
	 * Gets a permanent recipe from the system. First checks the buffer and then the model
	 *
	 * @param id the id of the recipe to get
	 * @return the recipe with the given id
	 */
	public PermanentRecipe getRecipeBuffer(int id) {
		PermanentRecipe recipe = this.buffer.get(id);
		if (recipe == null) {
			recipe = this.recipes.get(id);
		}
		return recipe;
	}

	/**
	 * Aids in the management of on the fly recipes
	 */
	public class OTFManager {

		/**
		 * The map of all on the fly recipes modelled in the system
		 */
		private HashMap<Integer, OnTheFlyRecipe> onTheFlyRecipes;

		/**
		 * A buffer for putting on the fly recipes in before writing them to the manager
		 */
		private HashMap<Integer, OnTheFlyRecipe> buffer;

		/**
		 * Counter for assigning ids to recipes
		 */
		private int counter;


		/**
		 * Constructs an instance of an OTFManager
		 */
		OTFManager() {
			this.onTheFlyRecipes = new HashMap<Integer, OnTheFlyRecipe>();
			this.buffer = new HashMap<Integer, OnTheFlyRecipe>();
			counter = 0;
		}

		/**
		 * Adds a recipe to the map of all on the fly recipes
		 * @param basis the permanent recipe that the OTF recipe is based on
		 * @param ingredients the ingredients in the OTF recipe
		 * @param price the price of the OTF recipe
		 * @return the id of the recipe created
		 */
		public Integer addRecipe(int basis, Map<Ingredient, Integer> ingredients, float price) {
			PermanentRecipe basisRecipe = recipes.get(basis);
			if (basisRecipe == null) {
				return null;
			}
			OnTheFlyRecipe recipe = new OnTheFlyRecipe(basisRecipe, ingredients, price);
			int id = counter;
			counter++;
			onTheFlyRecipes.put(id, recipe);
			return id;
		}

		/**
		 * Adds a new on the fly recipe to the OTF manager
		 * @param recipe the recipe to add to the manager
		 */
		public void addRecipe(OnTheFlyRecipe recipe) {
			if (recipe != null) {
				this.onTheFlyRecipes.put(recipe.getId(), recipe);
			}
		}

		/**
		 * Returns the on the fly recipe with the given value, or null if it does not exist
		 * @param id the internal id of the otf recipe
		 * @return the requested on the fly recipe
		 */
		public OnTheFlyRecipe getRecipe(int id) {
			return this.onTheFlyRecipes.get(id);
		}

		/**
		 * Returns the map of all OTF recipes modeled
		 *
		 * @return the map of all OTF recipes modeled
		 */
		public Map<Integer, OnTheFlyRecipe> getRecipes() {
			return this.onTheFlyRecipes;
		}

		/**
		 * Returns the set of all OTF recipes modeled
		 *
		 * @return the set of all OTF recipes modeled
		 */
		public Set<OnTheFlyRecipe> getRecipeSet() {
			return new HashSet<OnTheFlyRecipe>(this.onTheFlyRecipes.values());
		}

		/**
		 * Pushes a new on the fly recipe to the buffer
		 *
		 * @param basis       the permanent recipe that the OTF recipe is based on
		 * @param ingredients the ingredients in the OTF recipe
		 * @param price       the price of the OTF recipe
		 * @return the id of the recipe created
		 */
		public Integer pushToBuffer(int basis, Map<Ingredient, Integer> ingredients, float price) {
			PermanentRecipe basisRecipe = getRecipeBuffer(basis);
			if (basisRecipe == null) {
				return null;
			}
			OnTheFlyRecipe recipe = new OnTheFlyRecipe(basisRecipe, ingredients, price);
			int id = counter;
			counter++;
			onTheFlyRecipes.put(id, recipe);
			return id;
		}

		/**
		 * Adds the current data in the buffer to the modeled on the fly recipes
		 */
		public void writeBuffer() {
			this.onTheFlyRecipes.putAll(this.buffer);
			buffer.clear();
		}

		/**
		 * Drops the current data in the buffer
		 */
		public void dropBuffer() {
			this.buffer.clear();
		}

		/**
		 * Returns the otf recipe with the given id.
		 * Also checks the buffer for recipes, checking it before the model
		 * @param id the id of the recipe to find
		 * @return the recipe with the given id
		 */
		public OnTheFlyRecipe getOTFRecipeBuffered(int id) {
			OnTheFlyRecipe recipe = this.buffer.get(id);
			if (recipe == null) {
				recipe = this.onTheFlyRecipes.get(id);
			}
			return recipe;
		}
	}
}
