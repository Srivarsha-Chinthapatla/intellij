package org.example;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.nio.charset.*;
public class CsvWriter {
    public CsvWriter() {
        int numberOfRows = 10000;
        List<String> StudentIdList=new ArrayList<String>();
        List<String> StudentNameList=new ArrayList<String>();
        for(int i=0;i<numberOfRows;i++)
        {
            StudentIdList.add(getAlphaNumericInteger(i));
        }

        for(int i=0;i<numberOfRows;i++)
        {
            StudentNameList.add(getAlphaNumericString(10));
        }
        try (PrintWriter writer = new PrintWriter(  "C:\\Users\\c.srivarsha\\OneDrive - Entain Group\\Desktop\\Student1.csv")) {
            StringBuilder sb = new StringBuilder();
            sb.append("StudentId");
            sb.append(',');
            sb.append("StudentName");
            sb.append('\n');
            for(int i=0;i<numberOfRows;i++)
            {
                sb.append(StudentIdList.get(i));
                sb.append(',');
                sb.append(StudentNameList.get(i));
                sb.append('\n');
            }
            writer.write(sb.toString());
           // System.out.println("created csv file!");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    static String getAlphaNumericInteger(int n)
    {// length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);
        Random num = new Random();
        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();
        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        r.append(n+1);
                n--;
                // return the resultant string
        return r.toString();
    }
    static String getAlphaNumericString(int n)
    {// length is bounded by 256 Character
        byte[] array = new byte[256];
        new Random().nextBytes(array);
        String randomString = new String(array, Charset.forName("UTF-8"));
        // Create a StringBuffer to store the result
        StringBuffer r = new StringBuffer();
        // Append first 20 alphanumeric characters
        // from the generated random String into the result
        for (int k = 0; k < randomString.length(); k++) {
            char ch = randomString.charAt(k);
            if (((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')) && (n > 0)) {
                r.append(ch);
                n--;
            }
        }
        // return the resultant string
        return r.toString();
    }
}

