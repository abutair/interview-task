package com.progressoft.tools;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvController {

    private final List<String []> columns = new ArrayList<>();
    private final String title;
    private  int selectedColumnIndex = 0;

    public CsvController(Path path, String title) {
        this.title = title;
        readFromCsvFile(path);
    }

    private   void readFromCsvFile(Path csvPath)
    {
        if(!checkIfFileExist(csvPath)){
            throw  new IllegalArgumentException("source file not found");
        }

        String line ;

        try {

            BufferedReader reader = new BufferedReader(new FileReader(csvPath.toString()));
            while((line =reader.readLine() )!=null)
            {
                columns.add(line.split(","));
            }


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    } // end method


    public   void saveCsvFile(Path path , List<BigDecimal> data ,String s)
    {
        String [] row ;
        try (  PrintWriter writer = new PrintWriter(new File(path.toString()))) {
            System.out.println(path);
           for (int i = 0; i<columns.size(); i++)
           {
               row = columns.get(i);

               if (i==0)
               {
                   //Add titles to Csv file + the new column title

                   row[selectedColumnIndex] = row[selectedColumnIndex]+ ","+title+s;

               }
              else
               {
                   //Add column data to Csv file + the new column data

                   row[selectedColumnIndex] = row[selectedColumnIndex]+ ","+data.get(i-1);

               }


               writer.write((Arrays.toString(row)).replaceAll("[\\[\\]]", "").replaceAll(", ",","));
               writer.append(System.lineSeparator());


           }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    } // end method


    public  List<BigDecimal> getSpecificColumnByTitleName()
    {
        boolean columnIsFound = false;
        List<BigDecimal> column = new ArrayList<>() ;

        // looping on  column titles

        for (int i = 0; i< columns.get(0).length;i++)
        {
            if (columns.get(0)[i].equals(title))
            {

                columnIsFound = true;
                selectedColumnIndex = i;
            }
        }
        if (!columnIsFound)
        {
            throw  new IllegalArgumentException("column " + title +" not found");

        }
        for(int i = 1; i< columns.size(); i++)
        {
            column.add(new BigDecimal(columns.get(i)[selectedColumnIndex]));
        }
        return  column;
    }// end method

    private boolean checkIfFileExist( Path csvPath)
    {    File tempFile = new File(csvPath.toString());
        return tempFile.exists();
    } // end method

}
