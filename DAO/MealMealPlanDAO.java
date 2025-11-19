package DAO;

import app.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MealMealPlanDAO {

    public boolean addMealToPlan(int mealId, int planId, String remarks) {
        String query = "INSERT INTO meal_meal_plan (meal_id, plan_id, remarks) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, mealId);
            preparedStatement.setInt(2, planId);
            preparedStatement.setString(3, remarks);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding meal " + mealId + " to plan " + planId + ": " + e.getMessage());
            return false;
        }
    }
    public boolean removeMealFromPlan(int mealId, int planId) {
        String query = "DELETE FROM meal_meal_plan WHERE meal_id = ? AND plan_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, mealId);
            preparedStatement.setInt(2, planId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error removing meal " + mealId + " from plan " + planId + ": " + e.getMessage());
            return false;
        }
    }
    public boolean updateRemarks(int planId, int mealId, String remarks) {
        String query = "UPDATE meal_meal_plan SET remarks = ? WHERE plan_id = ? AND meal_id = ?";
        
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setString(1, remarks);
            preparedStatement.setInt(2, planId);
            preparedStatement.setInt(3, mealId);
            
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating remarks for plan " + planId + ", meal " + mealId + ": " + e.getMessage());
            return false;
        }
    }


    public List<Integer> getMealIdsByPlan(int planId) {
        List<Integer> mealIds = new ArrayList<>();
        String query = "SELECT meal_id FROM meal_meal_plan WHERE plan_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, planId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    mealIds.add(resultSet.getInt("meal_id"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving meals for plan " + planId + ": " + e.getMessage());
        }
        return mealIds;
    }


    public boolean deleteByMealPlanId(int planId) {
        String query = "DELETE FROM meal_meal_plan WHERE plan_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, planId);
            
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting associations for plan " + planId + ": " + e.getMessage());
            return false;
        }
    }


    public boolean isMealInAnyPlan(int mealId) {
        String query = "SELECT 1 FROM meal_meal_plan WHERE meal_id = ? LIMIT 1";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, mealId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); 
            }
        } catch (SQLException e) {
            System.err.println("Error checking meal plan dependency: " + e.getMessage());
            return true; 
        }
    }

}