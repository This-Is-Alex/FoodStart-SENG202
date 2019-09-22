package foodstart.model;

/**
 * Represents the possible dietary requirements that a customer might have
 */
public enum DietaryRequirement {
	VEGAN("vegan", "Vegan"), VEGETARIAN("vegetarian", "Vegetarian"),
	NUT_ALLERGY("nut_free", "Nut Free"), GLUTEN_FREE("gluten_free", "Gluten Free"), 
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
