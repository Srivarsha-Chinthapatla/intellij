package org.example;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import javax.net.ssl.SSLException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
public class App extends Thread
{
    static Logger log = Logger.getLogger(App.class.getName());

    long startTime = System.nanoTime();
    public static void main( String[] args ) throws SSLException {

        BasicConfigurator.configure();
        App t1 = new App();
        App t2 = new App();


        t1.start();
        t2.start();

        log.info(t1.getName());
        log.info(t2.getName());

    }

    public void calculateTime(){
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        log.info("Execution time using threads in nanoseconds: " + timeElapsed );
        log.info("Execution time using threads in milliseconds: " + timeElapsed / 1000000);
        log.info("Execution time using threads in seconds: " + timeElapsed / 1000000000);
    }
    public void run() {
        CsvWriter cs=new CsvWriter();
        dbconnection();
        try {
            studentdata();
        } catch (SSLException e) {
            e.printStackTrace();
        }
 calculateTime();


    }
    public static void studentdata() throws SSLException {

        String csvFilePath = "C:\\Users\\c.srivarsha\\OneDrive - Entain Group\\Desktop\\Student1.csv";
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            CSVParser records = CSVParser.parse(lineReader, CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

            ArrayList<Student> students = new ArrayList<Student>();
            for (CSVRecord record : records) {
                Student student = new Student();
                student.setStudentId(Integer.parseInt(record.get(0)));
                student.setStudentName(record.get(1));
                students.add(student);
            }
            PreparedStatement statement = null;
            Connection con = dbconnection();
            String sql = "INSERT INTO student(STUDENTID, STUDENTNAME) VALUES (?, ?)";
            statement = con.prepareStatement(sql);
            for (Student record : students) {
                statement.setInt(1, record.getStudentId());
                statement.setString(2, record.getStudentName());
                statement.addBatch();
            }
            statement.executeBatch();
            con.commit();
            con.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (SSLException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    public static Connection dbconnection() {

        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/studentdata?", "root", "Sujatha24@");
            log.info("connection sucessfull");
            connection.setAutoCommit(false);
            //here sonoo is database name, root is username and password
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Student");
            while (rs.next())
                log.info(rs.getInt(1) + "  " + rs.getString(2) );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }




}
