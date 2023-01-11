import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;


import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void testSetUp(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        assertTrue(get_restaurant_open_status_based_on_mock_time(LocalTime.parse("10:31:00")));
        assertTrue(get_restaurant_open_status_based_on_mock_time(LocalTime.parse("21:59:00")));
        assertTrue(get_restaurant_open_status_based_on_mock_time(LocalTime.parse("11:00:00")));
        assertTrue(get_restaurant_open_status_based_on_mock_time(LocalTime.parse("15:00:00")));
        assertTrue(get_restaurant_open_status_based_on_mock_time(LocalTime.parse("17:00:00")));
        assertTrue(get_restaurant_open_status_based_on_mock_time(LocalTime.parse("20:59:00")));
        assertTrue(get_restaurant_open_status_based_on_mock_time(LocalTime.parse("21:55:57")));
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        assertFalse(get_restaurant_open_status_based_on_mock_time(LocalTime.parse("10:30:00")));
        assertFalse(get_restaurant_open_status_based_on_mock_time(LocalTime.parse("22:00:00")));
        assertFalse(get_restaurant_open_status_based_on_mock_time(LocalTime.parse("10:29:59")));
        assertFalse(get_restaurant_open_status_based_on_mock_time(LocalTime.parse("22:00:01")));
        assertFalse(get_restaurant_open_status_based_on_mock_time(LocalTime.parse("01:00:00")));
        assertFalse(get_restaurant_open_status_based_on_mock_time(LocalTime.parse("23:00:00")));
        assertFalse(get_restaurant_open_status_based_on_mock_time(LocalTime.parse("09:55:00")));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public boolean get_restaurant_open_status_based_on_mock_time(LocalTime currentTime) {
        Restaurant restaurantSpy = Mockito.spy(restaurant);
        when(restaurantSpy.getCurrentTime()).thenReturn(currentTime);
        return restaurantSpy.isRestaurantOpen();
    }


    // Below is the failing unit test case needed to validate get value of order items
    // We are passing array of menu items as parameter for this method
    //  returns total value of selected menu item prices as integer
    @Test
    public void get_total_order_value_of_selected_menu_items() {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        List<String> itemNames = Arrays.asList("Sweet corn soup", "Vegetable lasagne");
        int expectedOrderValue = 388;
        int actualOrderValue = restaurant.getOrderValue(itemNames);
        assertEquals(expectedOrderValue, actualOrderValue);
    }
}