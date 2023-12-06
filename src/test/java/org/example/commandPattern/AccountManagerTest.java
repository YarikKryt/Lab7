package org.example.commandPattern;

import org.example.Main;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountManagerTest {

    private Statement statement;
    private AccountManager accountManager;

    @BeforeEach
    void setUp() {
        // Set up the database connection and statement
        try {
            Connection connection = DriverManager.getConnection(Main.url);
            statement = connection.createStatement();
            accountManager = new AccountManager(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void logInAccount() {
        AccountManager accountManager = new AccountManager(statement);

        System.setIn(new java.io.ByteArrayInputStream("User1\n1234\n".getBytes()));

        accountManager.logInAccount();

        assertTrue(accountManager.getID() > 0);
    }

    @Test
    void showUserRentCars() throws SQLException {
        // Mock the necessary objects
        Statement statement = Mockito.mock(Statement.class);
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        when(statement.executeQuery(any())).thenReturn(resultSet);

        // Create an instance of AccountManager
        AccountManager accountManager = new AccountManager(statement);

        accountManager.showUserRentCars(1);

        // Redirect System.out to capture printed output
        // (in this case, the println statements in showUserRentCars method)
        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStreamCaptor);
        System.setOut(printStream);

        // Execute the showUserRentCars method
        accountManager.showUserRentCars(1);

        // Assert or verify the printed content as needed
        String printedContent = outputStreamCaptor.toString().trim();
        assertEquals("- You don't rent any cars currently -", printedContent);

        // Reset System.out
        System.setOut(System.out);
    }
}
