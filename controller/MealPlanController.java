package controller;

import model.Meal;
import model.MealPlan;
import DAO.MealPlanDAO;

import java.util.ArrayList;
import java.util.List;


public class MealPlanController {

    private final MealPlanDAO mealPlanDAO;

    public MealPlanController() {
        this.mealPlanDAO = new MealPlanDAO();
    }

    public List<MealPlan> getAllMealPlans() {
        return mealPlanDAO.getallMealPlans();
    }
    public MealPlan getMealPlanDetails(int id) {
        if (id <= 0) {
            System.err.println("Validation Error: Invalid plan id.");
            return null;
        }
        return mealPlanDAO.getMealPlanbyId(id);
    }
    public MealPlan getPlanByName(String name){
        return mealPlanDAO.getMealPlanByName(name);
    }
    public MealPlanDAO getMealPlanDAO() {
        return mealPlanDAO;
    }
    public boolean createNewMealPlan(MealPlan plan) {
        if (plan == null || plan.getPlan_name() == null || plan.getPlan_name().trim().isEmpty()) {
            System.err.println("Validation Error: Plan name cannot be empty.");
            return false;
        }

        MealPlan existing = mealPlanDAO.getMealPlanByName(plan.getPlan_name());
        if (existing != null) {
            System.err.println("Validation Error: Plan name must be unique.");
            return false;
        }
        return mealPlanDAO.addMealPlan(plan);
    }
    public List<Meal> getMealsForPlan(int planId) {
        if (planId <= 0) {
            System.err.println("Validation Error: Invalid plan id for meal lookup.");
            return new ArrayList<>();
        }
        return mealPlanDAO.getMealsInPlan(planId);
    }


    public String updateMealPlan(int id, String name, String description, float total_price) {    if (name == null || name.trim().isEmpty()) {
        return "FAILURE: Plan name cannot be empty.";
    }
    if (total_price < 0) {
        return "FAILURE: Total price cannot be negative.";
    }
        MealPlan existing = mealPlanDAO.getMealPlanByName(name);
        if (existing!=null && existing.getPlan_id() !=id) {
            return "FAILURE: Plan name must be unique.";
        }
    MealPlan updatedPlan = new MealPlan(id, name, description, total_price);
        if (mealPlanDAO.updateMealPlan(updatedPlan)) {
        return "SUCCESS";
    } else {
        return "Failed to update plan in database.";
    }
    }
}
