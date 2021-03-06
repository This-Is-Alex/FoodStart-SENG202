package foodstart.manager.menu;

import foodstart.model.Unit;
import foodstart.model.menu.Menu;
import foodstart.model.menu.PermanentRecipe;
import foodstart.model.stock.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class MenuManagerTest {

    private MenuManager menuManager;
    private MenuItemManager itemManager;
    private Menu testMenu;
    private PermanentRecipe recipe1;
	private Set<PermanentRecipe> recipeList;

    @Before
    public void setUp() {
        menuManager = new MenuManager();
        itemManager = new MenuItemManager();
        HashMap<Ingredient, Integer> ingredients1 = new HashMap<Ingredient, Integer>();
        Ingredient ingredient1 = new Ingredient(Unit.UNITS, "ingredient1", 0, null, 10, 20);
        ingredients1.put(ingredient1, 20);
        recipe1 = new PermanentRecipe(1, "recipe1", "Create recipe one", 5, ingredients1);
		recipeList = new HashSet<PermanentRecipe>();
        recipeList.add(recipe1);
		itemManager.addMenuItem(0, "test menu item", "a menu item test", recipeList, recipe1);
        menuManager.addMenu(itemManager.getMenuItemSet(),0, "TestMenu", "A menu for testing");
    }

    @Test
    public void testAddMenu() {
        assertFalse(menuManager.getMenus().isEmpty());
        menuManager.addMenu(null, 1, "AnotherMenu", "It's another Menu!");
        assertEquals(2, menuManager.getMenus().size());
        menuManager.addMenu(itemManager.getMenuItemSet(),0, "OverRider", "Overrides another menu");
        assertEquals(2, menuManager.getMenus().size());
    }

    @Test
    public void testGetMenuValidId() {
        Menu menu = menuManager.getMenu(0);
        assertNotNull(menu);
        assertEquals(0, menu.getId());
        assertEquals("TestMenu", menu.getTitle());
    }

    @Test
    public void testGetMenuInvalidId() {
        Menu menu = menuManager.getMenu(1);
        assertNull(menu);
    }

    @Test
    public void testGetMenuMultipleItems() {
        menuManager.addMenu(null, 2, "AnotherMenu", "It's another Menu!");
        menuManager.addMenu(null, 3, "YetAnotherMenu", "It's yet another Menu!");
        Menu menu = menuManager.getMenu(2);
        assertNotNull(menu);
        assertEquals(2, menu.getId());
        assertEquals("AnotherMenu", menu.getTitle());
    }

    @Test
    public void testGetMenusSingleId() {
        Collection<Integer> ids = new ArrayList<Integer>();
        ids.add(0);
        Set<Menu> menus = menuManager.getMenus(ids);
        assertEquals(1, menus.size());
    }

    @Test
    public void testGetMenusMultipleIds() {
        menuManager.addMenu(null, 2, "AnotherMenu", "It's another Menu!");
        menuManager.addMenu(null, 3, "YetAnotherMenu", "It's another Menu!");
        menuManager.addMenu(null, 4, "YetAnotherYetAnotherMenu", "It's another Menu!");
        Collection<Integer> ids = new ArrayList<Integer>();
        ids.add(0);
        ids.add(3);
        Set<Menu> menus = menuManager.getMenus(ids);
        assertEquals(2, menus.size());
    }

    @Test
    public void testGetMenusInvalidId() {
        menuManager.addMenu(null, 2, "AnotherMenu", "It's another Menu!");
        menuManager.addMenu(null, 3, "YetAnotherMenu", "It's another Menu!");
        menuManager.addMenu(null, 4, "YetAnotherYetAnotherMenu", "It's another Menu!");
        Collection<Integer> ids = new ArrayList<Integer>();
        ids.add(2);
        ids.add(15);
        Set<Menu> menus = menuManager.getMenus(ids);
        assertEquals(1, menus.size());
    }

    @Test
    public void testGetMenusNoIds() {
        menuManager.addMenu(null, 2, "AnotherMenu", "It's another Menu!");
        menuManager.addMenu(null, 3, "YetAnotherMenu", "It's another Menu!");
        menuManager.addMenu(null, 4, "YetAnotherYetAnotherMenu", "It's another Menu!");
        Collection<Integer> ids = new ArrayList<Integer>();
        Set<Menu> menus = menuManager.getMenus(ids);
        assertEquals(0, menus.size());
    }

    @Test
    public void testGetMenuSet() {
        assertEquals(1, menuManager.getMenuSet().size());
        menuManager.addMenu(null, 2, "AnotherMenu", "It's another Menu!");
        menuManager.addMenu(null, 3, "YetAnotherMenu", "It's another Menu!");
        assertEquals(3, menuManager.getMenuSet().size());
    }

    @Test
    public void testGetMenuSetAbuse() {
        Map<Integer, Menu> items = menuManager.getMenus();
        Menu item = new Menu(null, 2, "AnotherMenu", "It's another Menu!");
        items.put(1, item);
        items.put(2, item);
        assertEquals(3, items.size());
        assertEquals(2, menuManager.getMenuSet().size());
    }
    
    @Test
    public void testRemoveMenu() {
        menuManager.removeMenu(0);
        assertTrue(menuManager.getMenus().isEmpty());
    }
    
    @Test
    public void testGenerateIDNormal() {
        assertEquals(1, menuManager.generateNewID());
    }
    
    @Test
    public void testGenerateIDEmpty() {
        menuManager.getMenus().clear();
        assertEquals(0, menuManager.generateNewID());
    }
    
    @Test
    public void testSetCurrentMenu() {
        menuManager.setCurrentMenu(0);
        assertEquals(0, menuManager.getCurrentMenu());
    }
   
    @Test
    public void testSetCurrentMenuBadID() {
        try {
            menuManager.setCurrentMenu(1);
            fail("Exception shoud have been thrown");
        } catch (Exception e) {
            
        }
    }
    
    @Test
    public void testPushToBuffer() {
        menuManager.getMenus().clear();
        Menu expected = new Menu(itemManager.getMenuItemSet(), 0, "TestMenu", "A menu for testing");
        menuManager.pushToBuffer(itemManager.getMenuItemSet(), 0, "TestMenu", "A menu for testing");
        menuManager.writeBuffer();
        assertTrue(menuManager.getMenus().containsValue(expected));
    }
    
    @Test
    public void testDropBuffer() {
        menuManager.getMenus().clear();
        menuManager.pushToBuffer(itemManager.getMenuItemSet(), 0, "TestMenu", "A menu for testing");
        menuManager.dropBuffer();
        menuManager.writeBuffer();
        assertTrue(menuManager.getMenus().isEmpty());
    }
}
