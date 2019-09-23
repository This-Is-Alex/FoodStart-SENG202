Feature: Inventory feature
    Scenarios involving Ingredients

    @skip_scenario
    Scenario: View inventory(Functional Requirement 13)
        Given There are 2 ingredients in the inventory
        And An employee wants to view the 2 ingredients stored inventory
        When The inventory is displayed
        Then The ID, name, truck stock, kitchen stock and dietary requirements for all 2 ingredients are displayed


    @skip_scenario # This scenario is not currently relevant
    Scenario: Check ingredients that are low in quantity (FR13)
        Given The list of ingredients are shown
        When Ingredients are filtered out by stock
        Then The ingredients will be displayed in ascending order of stocks available


    Scenario: Adding an ingredient to the list (FR14)
        Given That ingredient "Relish" is not found in the inventory
        And Its unit type is "GRAMS"
        And Its kitchen stock is 200
        And Its truck stock is 50
        And Its dietary requirement is "VEGAN"
        When Ingredient "Relish" is manually added to the inventory
        Then It will be stored under "Relish" in the inventory


    Scenario: Removing an ingredient (FR14)
        Given There are 3 ingredients in the inventory
        And The ingredient "Cucumber" is in the inventory
        And The ingredient's ID number is 3
        When The ingredient "Cucumber" is manually removed
        Then The ingredient ID 3 no longer exists in the inventory


    Scenario: Decreasing the truck stock quantity of an ingredient in the inventory when an order is placed
        Given There are 2 ingredients in the inventory
        And The ingredient "Mayo" is in the inventory
        And Its truck stock is 20
        When An order is placed that needs 5 of the ingredient "Mayo"
        Then The truck stock for "Mayo" is 15


    @skip_scenario
    Scenario: Make an item unavailable due to an ingredient being sold out
        Given That ingredient "Lettuce" is in the inventory
        And Its truck stock is 0
        When An order is placed containing the ingredient "Lettuce"
        Then An InsufficientStockException is thrown


    @skip_scenario
    Scenario: Editing an item in the inventory (FR16)
        Given That ingredient "Mushroom" is in the inventory
        And Its unit type is "GRAMS"
        And Its truck stock is 50
        And Its kitchen stock is 250
        And Its dietary requirement is "VEGETARIAN"
        And Its dietary requirement is "VEGAN"
        When "Mushroom" is selected and its name is edited to "Fungus"
        And Its unit type is changed to "UNITS"
        And Its truck stock is changed to 25
        And Its kitchen stock is changed to 200
        And Its dietary requirement is changed to "NUT-FREE"
        Then It will be stored under "Fungus" in the inventory with the changed fields

 