Feature: Menu feature
    Scenarios involving the menu and their menu items and recipes
    # Scenarios relating to the displaying of menus, menu items, and recipes
    @skip_scenario # Manual testing
    Scenario: Displaying all the menus (UC1)
        Given That there are 2 different menu sets in the system
        When The employee goes to view All Menus
        Then The 2 different menu sets are displayed on the screen
       
    @skip_scenario # Manual testing
    Scenario: Displaying a specific menu (UC1)
        Given An employee is looking through different menu sets
        When The employee selects the "Default Menu" menu set
        Then The "Default Menu" is displayed including its name and of its all recipes
        
    @skip_scenario # Manual testing
    Scenario: Displaying all menu items (UC2)
        Given An employee wants to see all the available menu items
        And There are 7 menu items in the system
        When The employee displays all menu items
        Then All 7 menu items are displayed with their names and the price of the default recipe
        
    @skip_scenario # Manual testing
    Scenario: Displaying a specific recipe when adding/removing menu items in a recipe (UC3)
        Given An employee wants to see the recipes of a menu item when viewing a menu
        And The menu has menu items with recipes
        When The employee chooses to display the recipes of a menu item
        Then The correct recipe is displayed for that menu item
        
    @skip_scenario # Manual testing
    Scenario: Displaying all recipes (UC3)
        Given An employee wants to see all the available recipes
        And There are 12 recipes in the system
        When The employee displays all recipes
        Then All 12 recipes are displayed with their IDs, name, price, and the ingredients required

    # Scenarios relating to the editing of menus 

    @skip_scenario # Manual testing
    Scenario: Setting the current menu (UC9)
        Given There are menus in the system
        And The menu "Default Menu" is the current set menu
        When The user selects "Summer Menu" to be the current set menu
        Then The current set menu will be "Summer Menu"
        And The corresponding menu items from "Summer Menu" will be shown when creating an order

    @skip_scenario # Manual testing
    Scenario: Menu editing (UC9)
        Given There are menus in the system
        And The menu "Default Menu" exists
        And The menu has ID 0
        When The employee edits and applies changes to a specific menu set by removing "Baked Beans" and adding "Hot Dog" and changing the name to "Festival Menu"
        Then The menu set is updated with the new information

    @skip_scenario # Manual testing
    Scenario: Attempting to close or set the current menu with unapplied changes (UC9)
        Given Changes have been made to the menu
        When The user attempts to close or set the current menu
        Then The user is notified that there are unapplied changes
        
    @skip_scenario # Manual testing
    Scenario: Menu editing when there are no menus (UC9)
        Given There are no menus in the system
        When An attempt is made to edit a menu
        Then The user is unable to edit a menu


    # Scenarios relating to the editing of menu items
        
    @skip_scenario # Manual testing
    Scenario: Menu item editing (UC10)
        Given There are menu items in the system
        And The menu item "Chicken Burger" exists
        And The menu item has ID 4
        When The menu item's editable attributes with ID 4 is edited
        Then It will either be the same or changed to their edited value
	    	
    @skip_scenario # Manual testing
    Scenario: Menu item editing when there are no menu items (UC10)
        Given There are no menu items in the system
        When An attempt is made to edit a menu item
        Then The user is unable to edit a menu item

    # Scenarios relating to the adding of menu items

    Scenario: Adding a recipe to e menu item (UC10)
        Given The menu item "HotDog" exists
        And The menu item "HotDog" does not have the recipe "Test Recipe"
        When The recipe "Test Recipe" is manually added to the menu item "HotDog"
        Then The menu item "HotDog" has the recipe "Test Recipe"

    # Scenarios relating to the removing of menu items

    Scenario: A menu item from the system (UC10)
        Given The menu item "Magic Food" exists
        When The menu item "Magic Food" is removed from the system
        Then The menu item "Magic Food" does not exist

    # Scenarios relating to adding recipes
    
    Scenario Outline: Adding recipes (UC11)
        Given A recipe <name> does not exist
        And The recipe has an ID of <id>
        When The recipe <name> is manually added
        Then The recipe <name> exists in the recipes list
        And The recipe <name> will have an ID of <id>
        Examples:
        | name            | id  |
        | "Large HotDog"  | 100 |
        | "Buffalo Wings" | 105 |
        | "Mango Slushie" | 110 |

        
    # Scenarios relating to the editing of recipes
        
    @skip_scenario # Manual testing
    Scenario: Recipe editing (UC11)
        Given There are recipes in the system
        And The recipe "Small Edam Cheeseburger" exists
        And The recipe has ID 0
        When The recipe's editable attributes with ID 0 is edited
        Then It will either be the same or changed to their edited value
    	
  	@skip_scenario # Manual testing
  	Scenario: Recipe editing when there are no recipes (UC11)
        Given There are no recipes in the system
        And An attempt is made to remove a recipe
        Then The user is notified that no recipe has been selected to edit
  			
    Scenario: Editing recipe price (UC11)
        Given A recipe "Small HotDog" exists
        When The price is changed to 7.50
        Then The recipe costs 7.50

    Scenario: Adding/removing ingredients from recipe (UC11)
        Given A recipe "Small HotDog" exists
        And The ingredient "wrap" is added with the unit quantity 1
        When The ingredient "bun" is removed
        Then The ingredients in the recipe "Small HotDog" are "wrap" and "sausage"

    Scenario: Editing recipe name and instuctions (UC11)
        Given A recipe "Small HotDog" exists
        And The recipe "Small HotDog" is changed to "AmericanWiener"
        When The recipe "AmericanWiener", instructions are changed to "Slot into bread hole"
        Then The recipe "AmericanWiener" has the instructions "Slot into bread hole"

   # Scenarios relating to the removing of recipes
    Scenario: Removing a recipe (UC11)
        Given A recipe "Small HotDog" exists
        When The recipe "Small HotDog" is removed
        Then The recipe "Small HotDog" does not exist
