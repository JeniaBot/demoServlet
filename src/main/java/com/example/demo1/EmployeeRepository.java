package com.example.demo1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

   public static void main(String[] args) {
        getConnection();

        Employee employee = new Employee();

        employee.setName("Ievgen");
        employee.setEmail("kirsstia@gmail.com");
        employee.setCountry("Ukraine");
        employee.setGender("male");
        save(employee);
        delete(8);


    }

    public static Connection getConnection() {
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/employee";
        String user = "root";
        String password = "7890";

        try {
            connection = DriverManager.getConnection(url, user, password);
            if (connection != null) {
                System.out.println("Connected to the MySQL server successfully.");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            getSQLExceptionInfo(e);
        }

        return connection;
    }

    public static int save(Employee employee) {
        int status = 0;
        try {
            Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("insert into users(name,email,country,gender) values (?,?,?,?)");
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getCountry());
            ps.setString(4, employee.getGender());

            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return status;
    }

    public static int update(Employee employee) {

        int status = 0;

        try {
            Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("update users set name=?,email=?,country=?,gender=? where id=?");
            ps.setString(1, employee.getName());
            ps.setString(2, employee.getEmail());
            ps.setString(3, employee.getCountry());
            ps.setString(4, employee.getGender());
            ps.setInt(5, employee.getId());

            status = ps.executeUpdate();
            connection.close();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return status;
    }

    public static int delete(int id) {

        int status = 0;

        try {
            Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("delete from users where id=?");
            ps.setInt(1, id);
            status = ps.executeUpdate();

            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return status;
    }

    public static Employee getEmployeeById(int id) {

        Employee employee = new Employee();

        try {
            Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from users where id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setEmail(rs.getString(3));
                employee.setCountry(rs.getString(4));
                employee.setGender(rs.getString(5));
            }
            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return employee;
    }

    public static List<Employee> getAllEmployees() {

        List<Employee> listEmployees = new ArrayList<>();

        try {
            Connection connection = EmployeeRepository.getConnection();
            PreparedStatement ps = connection.prepareStatement("select * from users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Employee employee = new Employee();

                employee.setId(rs.getInt(1));
                employee.setName(rs.getString(2));
                employee.setEmail(rs.getString(3));
                employee.setCountry(rs.getString(4));
                employee.setGender(rs.getString(5));

                listEmployees.add(employee);
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listEmployees;
    }
}
