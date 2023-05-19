package org.example;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Query {

    // This function is used to create a new table into database
    public void CREATE(String query) {

        Pattern pattern = Pattern.compile("CREATE TABLE ([a-zA-Z]+) \\((.*)\\)");
        Matcher matcher = pattern.matcher(query);
        if (matcher.matches()) {
            String tableName = matcher.group(1);
            String[] columns = matcher.group(2).split(", ");
            String[] columnNames = new String[columns.length];
            for (int i = 0; i < columns.length; i++) {
                String[] parts = columns[i].split(" ");
                columnNames[i] = parts[0];
            }

            try {
                File file = new File("src/CustomFiles/" + tableName + ".txt");

                boolean res = file.createNewFile();

                if (res) {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(String.join(" | ", columnNames));
                    fileWriter.close();
                    System.out.println("Table " + tableName + " created!");
                } else {
                    throw new Exception("Error");
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    // This function is used to insert the data into the table
    public void INSERT(String query) {

        Pattern pattern = Pattern.compile("INSERT INTO ([a-zA-Z]+) \\((.*)\\) VALUES \\((.*)\\)");
        Matcher matcher = pattern.matcher(query);
        if (matcher.matches()) {
            String tableName = matcher.group(1);
            String[] values = matcher.group(3).split(", ");
            String[] columnValues = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                String[] valueParts = values[i].split(" ");
                columnValues[i] = valueParts[0];
            }

            try {
                File file = new File("src/CustomFiles/" + tableName + ".txt");

                if (file.exists()) {
                    FileWriter fileWriter = new FileWriter(file, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.newLine();
                    bufferedWriter.write(String.join(" | ", columnValues));
                    bufferedWriter.close();
                    System.out.println("Entry added into " + tableName + " !");
                } else {
                    throw new Exception("Error");
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    // This function is used to update the data into the existing record
    public void UPDATE(String query) {

        Pattern pattern = Pattern.compile("UPDATE ([a-zA-Z]+) SET \\((.*)\\) WHERE \\((.*)\\)");
        Matcher matcher = pattern.matcher(query);
        if (matcher.matches()) {
            String tableName = matcher.group(1);
            String[] columnValues = matcher.group(2).split(", ");
            String[] updateColumnName = new String[columnValues.length];
            String[] updateColumnValue = new String[columnValues.length];
            String[] conditionColumn = new String[]{matcher.group(3)};
            String[] conditionColumnName = new String[conditionColumn.length];
            String[] conditionColumnValue = new String[conditionColumn.length];
            int searchColumnIndex = 0;
            int[] updateColumnIndex = new int[updateColumnName.length];
            for (int i = 0; i < columnValues.length; i++) {
                String[] valueParts = columnValues[i].split(" = ");
                updateColumnName[i] = valueParts[0];
                updateColumnValue[i] = valueParts[1];
                System.out.println(valueParts[0] + valueParts[1]);
            }
            for(int i=0;i < conditionColumn.length; i++) {
                String[] valueParts = conditionColumn[i].split(" = ");
                conditionColumnName[i] = valueParts[0];
                conditionColumnValue[i] = valueParts[1];
            }

            try {
                File file = new File("src/CustomFiles/" + tableName + ".txt");
                File tempFile = new File("src/CustomFiles/temp.txt");

                if (file.exists()) {
                    FileWriter fileWriter = new FileWriter(file, true);
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                    String[] firstLine = bufferedReader.readLine().split(" | ");

                    for (int i=0; i<firstLine.length; i++) {
                        if(Objects.equals(firstLine[i], conditionColumnName[0])) searchColumnIndex = i;
                    }

                    for (int i=0; i<updateColumnName.length; i++) {
                        for(int j=0; j<firstLine.length; j++) {
                            if(Objects.equals(firstLine[j], updateColumnName[i])) updateColumnIndex[i] = j;
                        }
                    }

                    writer.write(String.join(" ", firstLine));
                    writer.newLine();

                    String currentLine;

                    for (int i=0; i<updateColumnName.length; i++) {
                        while((currentLine = bufferedReader.readLine()) != null) {
                            String[] line = currentLine.split(" | ");

                            if(Objects.equals(line[searchColumnIndex], conditionColumnValue[0])) {
                                for(int l=0; l<updateColumnIndex.length; l++) {
                                    line[updateColumnIndex[l]] = updateColumnValue[l];
                                }
                                currentLine = String.join(" ", line);
                            }

                            writer.write(currentLine);
                            writer.newLine();


                        }
                    }

                    writer.close();
                    bufferedReader.close();
                    bufferedWriter.close();

                    file.delete();
                    tempFile.renameTo(file);

                    System.out.println("Entry updated into " + tableName + " !");
                } else {
                    throw new Exception("Error");
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    // This function is used to perform the select query in the given table
    public void SELECT (String query) {

        Pattern pattern = Pattern.compile("SELECT ([a-zA-Z]+|\\*) FROM (\\w+)");
        Matcher matcher = pattern.matcher(query);
        if (matcher.matches()) {
            String tableName = matcher.group(2);

            try {
                File file = new File("src/CustomFiles/" + tableName + ".txt");

                if (file.exists()) {

                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

                    String currentLine;

                    while((currentLine = bufferedReader.readLine()) != null) {
                        System.out.println(currentLine);
                    }
                } else {
                    throw new Exception("Error");
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    // This function is used to delete the existing record from the given table
    public void DELETE(String query) {

        Pattern pattern = Pattern.compile("DELETE FROM ([a-zA-Z]+|\\*) WHERE \\((.*)\\)");
        Matcher matcher = pattern.matcher(query);
        if (matcher.matches()) {
            String tableName = matcher.group(1);
            String[] conditionColumn = new String[]{matcher.group(2)};
            String[] conditionColumnName = new String[conditionColumn.length];
            String[] conditionColumnValue = new String[conditionColumn.length];
            int searchColumnIndex = 0;

            for(int i=0;i < conditionColumn.length; i++) {
                String[] valueParts = conditionColumn[i].split(" = ");
                conditionColumnName[i] = valueParts[0];
                conditionColumnValue[i] = valueParts[1];
            }

            try {
                File file = new File("src/CustomFiles/" + tableName + ".txt");
                File tempFile = new File("src/CustomFiles/temp.txt");

                if (file.exists()) {
                    FileWriter fileWriter = new FileWriter(file, true);
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

                    String[] firstLine = bufferedReader.readLine().split(" | ");

                    for (int i=0; i<firstLine.length; i++) {
                        if(Objects.equals(firstLine[i], conditionColumnName[0])) searchColumnIndex = i;
                    }

                    writer.write(String.join(" ", firstLine));
                    writer.newLine();

                    String currentLine;

                        while((currentLine = bufferedReader.readLine()) != null) {
                            String[] line = currentLine.split(" | ");

                            if(Objects.equals(line[searchColumnIndex], conditionColumnValue[0])) {
                                currentLine = String.join(" ", line);
                            } else {
                                writer.write(currentLine);
                                writer.newLine();
                            }


                        }

                    writer.close();
                    bufferedReader.close();
                    bufferedWriter.close();

                    file.delete();
                    tempFile.renameTo(file);

                    System.out.println("Entry deleted into " + tableName + " !");
                } else {
                    throw new Exception("Error");
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}

