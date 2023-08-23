import com.orientechnologies.orient.core.db.*;
import com.orientechnologies.common.log.OLogManager;
import com.orientechnologies.orient.core.record.impl.ODocument;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class Zinchenko{
    public static void main(String[] args) {
        try
        {
            OLogManager.instance().setConsoleLevel("OFF");

            OrientDB server = new OrientDB("remote:localhost", "root", "123456", OrientDBConfig.defaultConfig());
            if (server.exists("OrientDB"))
                server.drop("OrientDB");
            server.create("OrientDB", ODatabaseType.PLOCAL);

            try (ODatabaseSession db = server.open("OrientDB", "root", "123456")){
                ODocument document = new ODocument("DBooks");
                document.field("json", new String(Files.readAllBytes(Paths.get("C:/DB/BOOKS/books.json"))));
                document.field("xml", new String(Files.readAllBytes(Paths.get("C:/DB/BOOKS/books.xml"))));
                document.field("csv", new String(Files.readAllBytes(Paths.get("C:/DB/BOOKS/books.csv"))));
                db.save(document);
                db.commit();
            }

            Properties info = new Properties();
            info.put("user", "root");
            info.put("password", "123456");
            info.put("db.usePool", "true");
            info.put("db.poolmin", "3");
            info.put("db.poolmsc", "30");
            Connection conn = DriverManager.getConnection("jdbc:orient:remote:localhost/OrientDB", info);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select * From DBooks");
            while (rs.next()) {
                System.out.println("XML:");
                System.out.println(rs.getObject(1));
                System.out.println("JSON:");
                System.out.println(rs.getObject(2));
                System.out.println("CSV:");
                System.out.println(rs.getObject(3));
            }
            rs.close();
            stmt.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}