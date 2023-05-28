package com.example.teste6;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private Button queryButton;
    private TextView ageTextView;

    // Falta configurar os dados do SQL Server com o CONNECTION_STRING
    private static final String CONNECTION_STRING = "jdbc:jtds:sqlserver://<server_address>:<port>;DatabaseName=<database_name>;user=<username>;password=<password>";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queryButton = findViewById(R.id.query_button);
        ageTextView = findViewById(R.id.age_text_view);

        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                executeQuery();
            }
        });
    }

    private void executeQuery() {
        try {
            Connection connection = DriverManager.getConnection(CONNECTION_STRING);

            if (connection != null) {
                Statement statement = connection.createStatement();

                String sql = "DECLARE @nasc as varchar(11) = '17/04/2008';" +
                        "SELECT DATEDIFF(year, CONVERT(datetime, @nasc, 103), GETDATE()) AS age;";

                ResultSet resultSet = statement.executeQuery(sql);

                if (resultSet.next()) {
                    int age = resultSet.getInt("age");
                    ageTextView.setText("Idade: " + age + " anos");
                }

                resultSet.close();
                statement.close();
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}