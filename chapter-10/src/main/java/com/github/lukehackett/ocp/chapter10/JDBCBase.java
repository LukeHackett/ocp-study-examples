package com.github.lukehackett.ocp.chapter10;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class JDBCBase {
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "sa";
    private static final String URL = "jdbc:h2:mem:chapter-10;MODE=MYSQL;DATABASE_TO_UPPER=false;DB_CLOSE_DELAY=-1";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public void executeSqlScript(String path) throws SQLException, IOException {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            String sql = readFile(getClasspathResource(path));
            statement.execute(sql);
        }
    }

    private String readFile(Path path) throws IOException {
        byte[] encoded = Files.readAllBytes(path);
        return new String(encoded, Charset.defaultCharset());
    }

    private static Path getClasspathResource(String path) {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(path);
            if (url != null) {
                return Paths.get(url.toURI());
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

}
