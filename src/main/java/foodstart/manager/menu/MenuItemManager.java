package foodstart.manager.menu;

import foodstart.manager.Managers;
import foodstart.model.menu.Menu;
import foodstart.model.menu.MenuItem;
import foodstart.model.menu.PermanentRecipe;

import java.util.*;

/**
 * Acts as a controller, storing and managing the menu items in the model.
 */
public class MenuItemManager {

	/**
	 * The map of all menu items modeled in the system.
	 */
	private Map<Integer, MenuItem> menuItems;

	/**
	 * A buffer for putting MenuItems in before writing them to the manager
	 */
	private Map<Integer, MenuItem> buffer;

	/**
	 * Constructs an instance of a menu item manager.
	 */
	public MenuItemManager() {
		this.menuItems = new HashMap<Integer, MenuItem>();
		this.buffer = new HashMap<Integer, MenuItem>();
	}

	/**
	 * Constructs and adds a menu item to the map of menu items.
	 *
	 * @param id          the UID of the menu item
	 * @param name        the name of the menu item
	 * @param description a description of the menu item
	 * @param variants    a set of all recipes that make up the menu item
	 * @param defaultVariant the default recipe for the menu item
	 */
	public void addMenuItem(int id, String name, String description, Set<PermanentRecipe> variants, PermanentRecipe defaultVariant) {
		MenuItem menuItem = new MenuItem(id, name, description, variants, defaultVariant);
		this.menuItems.put(id, menuItem);
	}

	/**
	 * Gets a menu item from the map of menu items by its UID
	 *
	 * @param id the UID of the menu item
	 * @return The menu item that the UID refers to, or null
	 */
	public MenuItem getMenuItem(int id) {
		return this.menuItems.get(id);
	}

	/**
	 * Gets a menu item by its display name, or null if the menu item is not defined
	 *
	 * @param displayName The unique display name of the menu item
	 * @return recipe The menu item that the name refers to, or null
	 */
	public MenuItem getMenuItemByDisplayName(String displayName) {
		for (MenuItem menuItem : getMenuItems().values()) {
			if (menuItem.getName().equals(displayName)) {
				return menuItem;
			}
		}
		return null;
	}

	/**
	 * Returns the map of all menu items modeled.
	 *
	 * @return the map of all menu items modeled
	 */
	public Map<Integer, MenuItem> getMenuItems() {
		return this.menuItems;
	}

	/**
	 * Returns the set of menu items from the IDs specified.
	 *
	 * @param ids the IDs of the menu items to fetch.
	 * @return the set of menu items requested.
	 */
	public Set<MenuItem> getMenuItems(Collection<Integer> ids) {
		Set<MenuItem> items = new HashSet<MenuItem>();
		for (int id : ids) {
			MenuItem item = this.menuItems.get(id);
			if (item != null) {
				items.add(item);
			}
		}
		return items;
	}

	/**
	 * Returns the set of all menu items stored in the map
	 *
	 * @return the set of all menu items stored in the map
	 */
	public Set<MenuItem> getMenuItemSet() {
		return new HashSet<MenuItem>(this.menuItems.values());
	}

	/**
	 * Returns the approximate price for a menu item of a given id
	 *
	 * @param id the id of the menu item
	 * @return the mean price of the item
	 */
	public float getApproxPrice(int id) {
		MenuItem item = this.menuItems.get(id);
		return item.getDefault().getPrice();
	}

	/**
	 * Removes a menu item from the system. Cascades this deletion through the menu model
	 *
	 * @param id the id of the menu item to remove
	 */
	public void removeMenuItem(int id) {
		MenuItem removed = this.menuItems.remove(id);
		Set<Menu> menus = Managers.getMenuManager().getMenuSet();
		for (Menu menu : menus) {
			menu.removeMenuItem(removed);
		}
	}

	/**
	 * Generates a new unique id for a menu item
	 *
	 * @return a new unique id for a menu item
	 */
	public int generateNewId() {
		return menuItems.keySet().size() == 0 ? 0 : Collections.max(menuItems.keySet()) + 1;
	}

	/**
	 * Mutates a menu item with a given id to have the given parameters
	 * @param id the id of the menu item to change
	 * @param name the name of the menu item
	 * @param description the description of the menu item
	 * @param recipes the recipes that make up the menu item
	 * @param defaultVariant the default recipe of the menu item
	 */
	public void mutateMenuItem(int id, String name, String description, Set<PermanentRecipe> recipes, PermanentRecipe defaultVariant) {
		MenuItem menuItem2 = this.menuItems.get(id);
		if (menuItem2 != null) {
			menuItem2.setName(name);
			menuItem2.setDescription(description);
			menuItem2.setVariants(recipes);
			menuItem2.setDefaultVariant(defaultVariant);
		}
	}

	/**
	 * Adds a recipe to the menu item with a given id
	 * @param id the id of the menu item
	 * @param recipe the recipe to add to the menu item
	 */
	public void addRecipeToMenuItem(int id, PermanentRecipe recipe) {
		MenuItem menuItem = this.menuItems.get(id);
		menuItem.addVariant(recipe);
	}
	/**
	 * Removes a recipe from the menu item with a given id
	 * @param id the id of the menu item
	 * @param recipe the recipe to remove from the menu item
	 */
	public void removeRecipeFromMenuItem(int id, PermanentRecipe recipe) {
		MenuItem menuItem = this.menuItems.get(id);
		menuItem.removeVariant(recipe);
	}

	/**
	 * Pushes a new menu item to the buffer
	 *
	 * @param id          the UID of the menu item
	 * @param name        the name of the menu item
	 * @param description a description of the menu item
	 * @param variants    a set of all recipes that make up the menu item
	 * @param defaultVariant the default recipe for the menu item
	 */
	public void pushToBuffer(int id, String name, String description, Set<PermanentRecipe> variants, PermanentRecipe defaultVariant) {
		MenuItem menuItem = new MenuItem(id, name, description, variants, defaultVariant);
		this.buffer.put(id, menuItem);
	}

	/**
	 * Adds the current data in the buffer to the modeled menus
	 */
	public void writeBuffer() {
		this.menuItems.putAll(this.buffer);
		buffer.clear();
	}

	/**
	 * Drops the current data in the buffer
	 */
	public void dropBuffer() {
		this.buffer.clear();
	}

	/**
	 * Returns the set of menu items from the IDs specified.
	 * Also checks the buffer for items, checking it before the model
	 *
	 * @param ids the IDs of the menu items to fetch.
	 * @return the set of menu items requested.
	 */
	public Set<MenuItem> getMenuItemsBuffered(Collection<Integer> ids) {
		Set<MenuItem> items = new HashSet<MenuItem>();
		for (int id : ids) {
			MenuItem item = this.buffer.get(id);
			if (item != null) {
				items.add(item);
			} else {
				item = this.menuItems.get(id);
			}
			if (item != null) {
				items.add(item);
			}
		}
		return items;
	}
}
