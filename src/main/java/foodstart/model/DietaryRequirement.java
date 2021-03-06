package foodstart.model;

/**
 * Represents the possible dietary requirements that a customer might have
 */
public enum DietaryRequirement {
	/**
	 * Vegan food
	 */
	VEGAN("vegan", "Vegan"),
	/**
	 * Vegetarian food
	 */
	VEGETARIAN("vegetarian", "Vegetarian"),
	/**
	 * Nut free food
	 */
	NUT_ALLERGY("nut_free", "Nut Free"),
	/**
	 * Gluten free food
	 */
	GLUTEN_FREE("gluten_free", "Gluten Free"),
	/**
	 * Lactose free food
	 */
	LACTOSE_INTOLERANT("dairy_free", "Dairy Free");

	/**
	 * Locally stored dbName
	 */
	private final String dbName;

	/**
	 * Human friendly name
	 */
	private final String name;

	/**
	 * Constructor for Enum
	 *
	 * @param dbName The name as it should appear in the database (eg XML)
     * @param name the name/description of the dietary requirement
	 */
	DietaryRequirement(String dbName, String name) {
		this.dbName = dbName;
		this.name = name;
	}

	/**
	 * Matches a given string to a unit
	 *
	 * @param string The string to match
	 * @return The unit it matched to, or null if it didn't match
	 */
	public static DietaryRequirement matchDietaryRequirement(String string) {
		for (DietaryRequirement requirement : values()) {
			if (requirement.getDBName().equalsIgnoreCase(string)) {
				return requirement;
			}
		}
		return null;
	}

	/**
	 * Gets the name of the unit as it should appear in the database
	 *
	 * @return Database name of the unit
	 */
	public String getDBName() {
		return this.dbName;
	}

	/**
	 * Gets the name of the unit in a human friendly way
	 *
	 * @return Human friendly name of the dietary requirement
	 */
	public String getHumanName() {
		return this.name;
	}
}
