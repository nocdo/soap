package com.example.springsoap;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


import io.foodmenu.cs.webservice.*;


import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class MealRepository {
    private static final Map<String, Meal> meals = new HashMap<String, Meal>();
    private static final Map<Integer, Order> orders = new HashMap<Integer, Order>();
    private static int idCounter = 0;

    @PostConstruct
    public void initData() {

        Meal a = new Meal();
        a.setName("Steak");
        a.setDescription("Steak with fries");
        a.setMealtype(Mealtype.MEAT);
        a.setKcal(1100);
        a.setPrice(new BigDecimal(30.5));


        meals.put(a.getName(), a);

        Meal b = new Meal();
        b.setName("Portobello");
        b.setDescription("Portobello Mushroom Burger");
        b.setMealtype(Mealtype.VEGAN);
        b.setKcal(637);
        b.setPrice(new BigDecimal(20.5));


        meals.put(b.getName(), b);

        Meal c = new Meal();
        c.setName("Fish and Chips");
        c.setDescription("Fried fish with chips");
        c.setMealtype(Mealtype.FISH);
        c.setKcal(950);
        c.setPrice(new BigDecimal(15.5));


        meals.put(c.getName(), c);
    }

    public Meal findMeal(String name) {
        Assert.notNull(name, "The meal's code must not be null");
        return meals.get(name);
    }

    public Meal findBiggestMeal() {

        if (meals == null) return null;
        if (meals.size() == 0) return null;

        var values = meals.values();
        return values.stream().max(Comparator.comparing(Meal::getKcal)).orElseThrow(NoSuchElementException::new);

    }

    public Meal findCheapestMeal() {

        if (meals == null) return null;
        if (meals.size() == 0) return null;

        var values = meals.values();
        return values.stream().min(Comparator.comparing(Meal::getPrice)).orElseThrow(NoSuchElementException::new);

    }

    public OrderConfirmation addOrder(Order order) {
        OrderConfirmation oc = new OrderConfirmation();
        try {
            order.setId(this.idCounter++);
            orders.put(order.getId(), order);

            oc.setOrder(order);
            oc.setOrderStatus(1);
            return oc;
        }
        catch(Exception e) {
            oc.setOrder(order);
            oc.setOrderStatus(0);
            return oc;
        }

    }


}