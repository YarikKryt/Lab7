package org.example.commandPattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class ShowUserDataTest {

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void execute() throws SQLException {
        // Create a ShowUserData instance
        ShowUserData showUserData = new ShowUserData(statement);

        // Mock the ID for the test
        showUserData.setID(123);

        // Mock the result set for the command
        when(statement.executeQuery(anyString())).thenReturn(resultSet);

        // Mock the result set data (adjust as needed based on your actual data)
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString("SNP")).thenReturn("John Doe");
        when(resultSet.getString("City")).thenReturn("City");
        when(resultSet.getString("BirthDate")).thenReturn("2000-01-01");
        when(resultSet.getString("ExperienceDriving")).thenReturn("2010-01-01");
        when(resultSet.getString("CategoryB")).thenReturn("1");
        when(resultSet.getString("ExperienceTaxiDriverB")).thenReturn("1");
        when(resultSet.getString("ExperienceTaxiDriver")).thenReturn("2015-01-01");
        when(resultSet.getString("PhoneNumber")).thenReturn("123456789");
        when(resultSet.getString("SelfDescription")).thenReturn("Description");

        // Execute the command
        assertDoesNotThrow(() -> showUserData.execute());

        // Verify that the statement.executeQuery method is called with the correct SQL command
        verify(statement, times(1)).executeQuery("SELECT SNP, BirthDate, City, ExperienceDriving, CategoryB, " +
                "ExperienceTaxiDriverB, ExperienceTaxiDriver, PhoneNumber, SelfDescription " +
                "FROM UserInformation where ID = 123");
    }
}
