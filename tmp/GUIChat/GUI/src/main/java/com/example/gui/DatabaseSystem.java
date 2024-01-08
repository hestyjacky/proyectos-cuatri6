package com.example.gui;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.join;

public class DatabaseSystem {
    public String[] rowData;
    public String line;
    public String[] columnNames;
    public String[] selectedColumns;
    public StringBuilder commandBuilder = new StringBuilder();
    boolean multiLineInput = false;

    public String DatabaseSystemStr(String  queryDeCliente){
        String respconstr="";
        try {
            //while (true) {
                String line = queryDeCliente;
                commandBuilder.append(line).append(" ");

                if (line.trim().endsWith(";")) {
                    String command = commandBuilder.toString().trim();

                    respconstr+=evaluarQuery(command);
                    //System.out.println("Reapuwat constructor; "+respconstr);
                    commandBuilder.setLength(0);
                    multiLineInput = false;
                } else {
                    multiLineInput = true;
                }
            //}
        } catch (Exception e) {
            System.err.println(" Fallo con -> " + e.getMessage());
        }
        return respconstr;
    }
    public static String currentDatabase = "USERS";
    public static void main(String[] args) throws IOException {
        String query = "select id from usuarios;";
        DatabaseSystem BD = new DatabaseSystem();
        System.out.println(BD.DatabaseSystemStr(query));
    }
    public String selectData(String query) {
        String respuesta="";
        // Expresión regular para SELECT con WHERE
        Pattern patternWithWhere = Pattern.compile("SELECT (.+) FROM (\\w+) WHERE (.+);", Pattern.CASE_INSENSITIVE);
        Matcher matcherWithWhere = patternWithWhere.matcher(query);

        // Expresión regular para SELECT sin WHERE
        Pattern patternWithoutWhere = Pattern.compile("SELECT (.+) FROM (\\w+);", 2);
        Matcher matcherWithoutWhere = patternWithoutWhere.matcher(query);

        if (matcherWithWhere.find()) {
            try {
                // La consulta coincide con SELECT con WHERE
                String columnsPart = matcherWithWhere.group(1).trim();
                String tableName = matcherWithWhere.group(2).trim();
                String whereCondition = matcherWithWhere.group(3);

                if (whereCondition == null || whereCondition.isEmpty()) {
                    System.out.println("Error: Falta la cláusula WHERE en la consulta SELECT.");
                    respuesta+="error";
                    return respuesta;
                    //return; // Salir del método sin cerrar el programa
                }

                String tableFilePath = currentDatabase + File.separator + tableName + ".csv";

                try {
                    File tableFile = new File(tableFilePath);

                    if (!tableFile.exists()) {
                        System.out.println("La tabla '" + tableName + "' no existe.");
                        respuesta+="no existe la tabla";
                        return respuesta;
                    }

                    BufferedReader bufferedReader = new BufferedReader(new FileReader(tableFile));

                    String headerLine = bufferedReader.readLine(); // Leer el encabezado
                    String[] columnNames = headerLine.split("¬");
                    //respuesta+=columnNames+"\n";

                    //System.out.println("Datos seleccionados:"); ----------------

                    if (columnsPart.equals("*")) {
                        // Mostrar todas las columnas
                        //System.out.println(headerLine); ----------------

                        while ((line = bufferedReader.readLine()) != null) {
                            try {
                                rowData = line.split("¬");
                                if (evaluateWhereConditionSelect(headerLine, rowData, whereCondition)) {
                                    respuesta += String.join("\t", Arrays.copyOfRange(rowData, 0, rowData.length));
                                    //System.out.println(String.join("\t", Arrays.copyOfRange(rowData, 0, rowData.length)));
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                continue;
                            }

                        }
                    } else {
                        String[] selectedColumns = columnsPart.split("¬");

                        respuesta+="\n";
                        System.out.println(); // Nueva línea después de mostrar los nombres de las columnas

                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            try {
                                rowData = line.split("¬");
                                if (evaluateWhereConditionSelect(headerLine, rowData, whereCondition)) {
                                    for (String columnName : selectedColumns) {
                                        int columnIndex = Arrays.asList(columnNames).indexOf(columnName.trim());
                                        if (columnIndex >= 0) {
                                            respuesta+=rowData[columnIndex] + "\t";
                                        } else {
                                            respuesta+="\t";
                                        }
                                    }
                                    respuesta+="\n"; // ---------- aqui tambien return?
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                continue;
                            }
                        }
                    }
                    bufferedReader.close();
                } catch (IOException e) {
                    respuesta+="ERROR al seleccionar los datos de la tabla";
                    return respuesta;
                }
            } catch (Exception e) {
                respuesta+="ERROR al ejecutar select con where";
                return respuesta;
            }

        } else if (matcherWithoutWhere.find()) {
            try {
                // La consulta coincide con SELECT sin WHERE
                String columnsPart = matcherWithoutWhere.group(1).trim();
                String tableName = matcherWithoutWhere.group(2).trim();
                String tableFilePath = currentDatabase + File.separator + tableName + ".csv";

                try {
                    File tableFile = new File(tableFilePath);
                    if (!tableFile.exists()) {
                        respuesta+="no existe la tabla";
                        return respuesta;
                    }

                    BufferedReader bufferedReader = new BufferedReader(new FileReader(tableFile));

                    String headerLine = bufferedReader.readLine();
                    columnNames = headerLine.split("¬");
                    /*
                    for (int i=0; i<columnNames.length; i++){
                        respuesta+=columnNames[i]+" ";
                    }
                        respuesta+="\n";

                     */
                    // Verificar si se solicitan todas las columnas con *
                    if (columnsPart.equals("*")) {

                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            try {
                                this.rowData = line.split("¬");
                                for (String data : this.rowData) {
                                    //System.out.print(data + "\t");
                                    //respuesta+=data + "\t";
                                }
                                //respuesta+="\n";
                                //return respuesta;
                            } catch (ArrayIndexOutOfBoundsException var19) {
                            }
                        }
                    } else {
                        String[] selectedColumns = columnsPart.split("¬");
                        for (String column : selectedColumns) {
                            //respuesta+=column + "\t"; // id
                        }
                        //respuesta+="\n";

                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            try {
                                String[] rowData = line.split("¬");
                                for (String selectedColumn : selectedColumns) {
                                    String columnName = selectedColumn.trim();
                                    int columnIndex = getColumnIndex(columnNames, columnName);
                                    if (columnIndex >= 0) {
                                        respuesta+=rowData[columnIndex] + "\t";
                                    } else {
                                        respuesta+="\t";
                                    }
                                }
                                respuesta+="\n";
                            } catch (ArrayIndexOutOfBoundsException e) {
                                // Manejar la excepción si es necesario
                            }
                        }
                    }
                    bufferedReader.close();
                } catch (IOException var21) {
                    respuesta+="Error al seleccionar datos de la tabla";
                    return respuesta;
                }
            } catch (Exception e) {
                respuesta+="Error al ejecutar SELECT sin WHERE";
                return respuesta;
            }
        } else {
            respuesta+="Error: Sintaxis incorrecta para SELECT.";
            return respuesta;
        }
        return respuesta;
    }
    public boolean evaluateWhereConditionSelect(String headerLine, String[] columns, String whereCondition) {
        String[] columnNames = headerLine.split("¬");
        String[] orConditions = whereCondition.split(" or ");

        boolean atLeastOneConditionSatisfied = false;

        for (String condition : orConditions) {
            String[] andConditions = condition.split(" and ");
            boolean allAndConditionsSatisfied = true;

            for (String andCondition : andConditions) {
                String[] conditionComponents = andCondition.split("\\s+");

                if (conditionComponents.length != 3) {
                    System.out.println("Error: Sintaxis incorrecta para la condición WHERE.");
                    return false;
                }

                String leftOperand = conditionComponents[0];
                String operator = conditionComponents[1];
                String rightOperand = conditionComponents[2];

                if (!Arrays.asList(columnNames).contains(leftOperand)) {
                    System.out.println("Error: La columna especificada en la condición WHERE no existe.");
                    return false;
                }

                int columnIndex = getColumnIndex(columnNames, leftOperand);
                String columnValue = columns[columnIndex].trim();

                boolean operationResult = evaluateOperation(columnValue, operator, rightOperand);

                // Ajustar la lógica para devolver false si al menos una condición no se cumple
                if (!operationResult) {
                    allAndConditionsSatisfied = false;
                    break;
                }
            }

            if (allAndConditionsSatisfied) {
                return true;
            }
        }
        return atLeastOneConditionSatisfied;
    }
    private void enterUserFolder(String userId) {
        String userFolderPath = userId;

        System.out.println("Intentando acceder a la carpeta: " + userFolderPath);

        File userFolder = new File(userFolderPath);
        if (userFolder.exists() && userFolder.isDirectory()) {
            currentDatabase = userFolderPath;
            System.out.println("Entrando a la carpeta del usuario: " + userId);
        } else {
            System.out.println("La carpeta del usuario no existe para: " + userId);
        }
    }

    private String evaluarQuery(String command){
        if (command.equalsIgnoreCase("exit;")) {
            return "exit;";
        } else if (command.toUpperCase().startsWith("SHOW TABLES;")) {
            showTables();
        } else if (command.toUpperCase().startsWith("CREATE")) {
            createTable(command);
        } else if (command.toUpperCase().startsWith("DROP")) {
            String tableName = command.substring(10, command.length() - 1).trim();
            dropTable(tableName);
        } else if (command.toUpperCase().startsWith("INSERT INTO")) {
            insertData(command);
        } else if (command.toUpperCase().startsWith("SELECT")) {
            String respevquer="";
            respevquer=selectData(command);
            //System.out.println("Resultado de Return "+respevquer);
            return  respevquer;
        } else if (command.toUpperCase().startsWith("UPDATE")) {
            updateData(command);
        } else if (command.toUpperCase().startsWith("DELETE")) {
            deleteData(command);
        } else {
            return "Comando no válido. " +
                    "\nIntroduce 'exit' para salir, o " +
                    "\n'USE $PATH$' para establecer la ruta de trabajo.";
        }
        return "Comando no válido. " +
                "\nIntroduce 'exit' para salir, o " +
                "\n'USE $PATH$' para establecer la ruta de trabajo.";
    }




    public String showTables() {
        String respuesta = "";
        if (currentDatabase.isEmpty()) {
            respuesta+="Error: Ruta de trabajo no especificada.";
            return respuesta;
        }

        File folder = new File(currentDatabase);

        if (!folder.exists() || !folder.isDirectory()) {
            respuesta+="Error: La carpeta de trabajo especificada no existe o no es una carpeta.";
            return respuesta;
        }

        File[] files = folder.listFiles();

        if (files != null && files.length > 0) {
            //System.out.println("Tablas disponibles en la base de datos '" + currentDatabase + "':");

            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".csv")) {
                    respuesta+=(file.getName().replace(".csv", ""));
                }
            }
            return respuesta;
        } else {
            respuesta+="No hay tablas en la carpeta de trabajo.";
            return respuesta;
        }
    }

    public void createTable(String query) {
        Pattern pattern = Pattern.compile("CREATE[\\s\\S]*?TABLE\\s+(\\w+)\\s*\\(([^;]+);\\)?", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            String tableName = matcher.group(1).trim();
            String columnsPart = matcher.group(2).trim();

            String[] columnDefinitions = columnsPart.split(",");
            List<String> columnNames = new ArrayList<>();

            for (String columnDefinition : columnDefinitions) {
                String[] columnInfo = columnDefinition.trim().split("\\s+");
                if (columnInfo.length != 2) {
                    System.out.println("Error: Sintaxis incorrecta para definición de columna.");
                    return;
                }
                columnNames.add(columnInfo[0]);
            }

            String tableFilePath = currentDatabase + File.separator + tableName + ".csv";

            try {
                File tableFile = new File(tableFilePath);
                if (tableFile.createNewFile()) {
                    // Crear el archivo CSV y escribir el encabezado
                    FileWriter fileWriter = new FileWriter(tableFile);
                    String header = join(",", columnNames);
                    fileWriter.write(header);
                    fileWriter.close();
                    System.out.println("Tabla '" + tableName + "' creada.");
                } else {
                    System.out.println("La tabla '" + tableName + "' ya existe.");
                }
            } catch (IOException e) {
                System.out.println("Error al crear la tabla: " + e.getMessage());
            }
        } else {
            System.out.println("Error: Sintaxis incorrecta para CREATE TABLE.");
        }
    }

    //DROP TABLE nombretabla;
    public void dropTable(String tableName) {
        String tableFilePath = currentDatabase + File.separator + tableName + ".csv";
        File tableFile = new File(tableFilePath);

        if (tableFile.exists()) {
            if (tableFile.delete()) {
                System.out.println("Tabla '" + tableName + "' eliminada.");
            } else {
                System.out.println("Error al eliminar la tabla '" + tableName + "'.");
            }
        } else {
            System.out.println("La tabla '" + tableName + "' no existe.");
        }
    }
    public String insertData(String query) {
        Pattern pattern = Pattern.compile("INSERT INTO (\\w+) \\((.*?)\\) VALUES \\((.*?)\\);", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            String tableName = matcher.group(1).trim();
            String columnNamesPart = matcher.group(2).trim();
            String valuesPart = matcher.group(3).trim();

            String[] columnNames = columnNamesPart.split(",");
            String[] values = valuesPart.split(",");

            if (columnNames.length != values.length) {
                return "Error: La cantidad de columnas no coincide con la cantidad de valores.";
            }

            String tableFilePath = currentDatabase + File.separator + tableName + ".csv";

            try {
                File tableFile = new File(tableFilePath);
                FileWriter fileWriter = new FileWriter(tableFile, true); // Abrir el archivo en modo de escritura (true para agregar)
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

                if (!tableFile.exists() || tableFile.length() == 0) {
                    // Si el archivo está vacío o no existe, escribir el encabezado
                    StringBuilder headerRow = new StringBuilder();
                    for (String columnName : columnNames) {
                        headerRow.append(columnName.trim()).append("¬");
                    }
                    //----------------------------------------------------------------------------
                    bufferedWriter.write(headerRow.toString());
                    bufferedWriter.newLine();
                }


                // Nueva sección para la escritura de datos
                if (tableFile.length() > 0) {
                    bufferedWriter.newLine(); // Salto de línea solo si ya hay datos en el archivo
                }

                StringBuilder dataRow = new StringBuilder();
                for (String value : values) {
                    dataRow.append(value.trim()).append("¬");
                }
                bufferedWriter.write(dataRow.toString());
                //bufferedWriter.newLine();

                bufferedWriter.close();

                // Una vez que hayas insertado los datos, puedes imprimir un mensaje de confirmación
                return "Datos insertados en la tabla '" + tableName + "'.";
            } catch (IOException e) {
                return  "Error al insertar datos en la tabla: " + e.getMessage();
            }
        } else {
            return "Error: Sintaxis incorrecta para INSERT INTO.";
        }
    }
    public void deleteData(String query) {
        Pattern pattern = Pattern.compile("DELETE FROM (\\w+)(?: WHERE (.*));", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            String tableName = matcher.group(1).trim();
            String whereClause = matcher.group(2).trim();
            String tableFilePath = currentDatabase + File.separator + tableName + ".csv";

            try {
                File tableFile = new File(tableFilePath);
                File tempFile = new File(currentDatabase + File.separator + tableName + "_temp.csv");

                if (!tableFile.exists()) {
                    System.out.println("La tabla '" + tableName + "' no existe.");
                    return;
                }

                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(tableFile));
                     BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile))) {

                    String headerLine = bufferedReader.readLine(); // Leer el encabezado
                    bufferedWriter.write(headerLine);
                    bufferedWriter.newLine();

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] columns = line.split(",");

                        if (matchesWhereCondition(headerLine, columns, whereClause)) {
                            // No escribe la línea en el archivo temporal si coincide con la condición
                            continue;
                        }

                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    }
                }

                if (tempFile.renameTo(tableFile)) {
                    System.out.println("Datos eliminados de la tabla '" + tableName + "'.");
                } else {
                    System.out.println("Error al eliminar datos de la tabla '" + tableName + "'.");
                }
            } catch (IOException e) {
                System.out.println("Error al eliminar datos de la tabla: " + e.getMessage());
            }
        } else {
            System.out.println("Error: Sintaxis incorrecta para DELETE FROM.");
        }
    }
    public boolean matchesWhereCondition(String headerLine, String[] columns, String whereClause) {
        String[] columnNames = headerLine.split(",");
        String[] orConditions = whereClause.split("OR", -1);

        boolean atLeastOneConditionSatisfied = false;

        for (String condition : orConditions) {
            String[] andConditions = condition.split("AND", -1);
            boolean allAndConditionsSatisfied = true;

            for (String andCondition : andConditions) {
                String[] conditionComponents = andCondition.trim().split("\\s+");

                if (conditionComponents.length != 3) {
                    System.out.println("Error: Sintaxis incorrecta para la condición WHERE.");
                    return false;
                }

                String leftOperand = conditionComponents[0];
                String operator = conditionComponents[1];
                String rightOperand = conditionComponents[2];

                int columnIndex = getColumnIndexDelete(columnNames, leftOperand);

                if (columnIndex == -1) {
                    System.out.println("Error: La columna especificada en la condición WHERE no existe.");
                    return false;
                }

                String columnValue = columns[columnIndex].trim();

                boolean operationResult = evaluateOperation(columnValue, operator, rightOperand);

                if (!operationResult && andConditions.length > 1) {
                    allAndConditionsSatisfied = false;
                    break;
                } else if (operationResult && andConditions.length == 1) {
                    atLeastOneConditionSatisfied = true;
                }
            }

            if (allAndConditionsSatisfied || andConditions.length == 1) {
                return true;
            }
        }

        return atLeastOneConditionSatisfied;
    }
    public int getColumnIndexDelete(String[] columns, String columnName) {
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].trim().equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;
    }
    public void updateData(String query) {
        Pattern pattern = Pattern.compile("UPDATE (\\w+) SET (.+) WHERE (.+);", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(query);

        if (matcher.find()) {
            String tableName = matcher.group(1).trim();
            String setClause = matcher.group(2).trim();
            String whereCondition = matcher.group(3).trim();
            String tableFilePath = currentDatabase + File.separator + tableName + ".csv";

            try {
                File tableFile = new File(tableFilePath);
                File tempFile = new File(currentDatabase + File.separator + tableName + "_temp.csv");

                if (!tableFile.exists()) {
                    System.out.println("La tabla '" + tableName + "' no existe.");
                    return;
                }

                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(tableFile));
                     BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tempFile))) {

                    String headerLine = bufferedReader.readLine();
                    bufferedWriter.write(headerLine);
                    bufferedWriter.newLine();

                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] columns = line.split(",");

                        if (evaluateWhereCondition(headerLine, columns, whereCondition)) {
                            applyUpdate(setClause, columns);
                        }

                        bufferedWriter.write(String.join(",", columns));
                        bufferedWriter.newLine();
                    }
                }

                if (tempFile.renameTo(tableFile)) {
                    System.out.println("Datos actualizados en la tabla '" + tableName + "'.");
                } else {
                    System.out.println("Error al actualizar datos en la tabla '" + tableName + "'.");
                }
            } catch (IOException e) {
                System.out.println("Error al actualizar datos en la tabla: " + e.getMessage());
            }
        } else {
            System.out.println("Error: Sintaxis incorrecta para UPDATE.");
        }
    }
    public void applyUpdate(String setClause, String[] columns) {
        String[] updateComponents = setClause.split(",");

        for (String updateComponent : updateComponents) {
            String[] parts = updateComponent.trim().split("=");

            if (parts.length != 2) {
                System.out.println("Error: Sintaxis incorrecta en la cláusula SET.");
                return;
            }

            String columnName = parts[0].trim();
            String newValue = parts[1].trim();

            // Check the condition to identify the column to update
            int columnIndex;
            if (columnName.equalsIgnoreCase("id")) {
                columnIndex = 0; // Assuming ID is the first column
            } else if (columnName.equalsIgnoreCase("nombre")) {
                columnIndex = 1; // Assuming 'nombre' is the second column
            } else if (columnName.equalsIgnoreCase("correo")) {
                columnIndex = 2; // Assuming 'correo' is the third column
            } else {
                columnIndex = getColumnIndex(columns, columnName);
            }

            if (columnIndex != -1) {
                columns[columnIndex] = newValue;
            } else {
                System.out.println("Error: La columna especificada en la cláusula SET no existe.");
            }
        }
    }
    public int getColumnIndex(String[] columns, String columnName) {
        for (int i = 0; i < columns.length; i++) {
            if (columns[i].trim().equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;
    }
    public boolean evaluateWhereCondition(String headerLine, String[] columns, String whereCondition) {
        String[] columnNames = headerLine.split(",");
        String[] orConditions = whereCondition.split("OR", -1);

        // Evaluar cada condición OR individualmente
        for (String orCondition : orConditions) {
            boolean orConditionResult = false;
            String[] andConditions = orCondition.split("AND", -1);
            boolean andConditionsPresent = andConditions.length > 1;

            if (andConditionsPresent) {
                // Evaluar cada condición AND individualmente dentro de la condición OR
                orConditionResult = evaluateAndConditions(columnNames, columns, andConditions);
            } else {
                // Solo hay una condición sin operador lógico AND
                String[] conditionComponents = orCondition.trim().split("\\s+");

                if (conditionComponents.length != 3) {
                    System.out.println("Error: Sintaxis incorrecta para la condición WHERE.");
                    return false;
                }

                orConditionResult = evaluateOperation(columnNames, columns, conditionComponents);
            }

            // Si alguna de las condiciones OR es verdadera, se cumple toda la condición WHERE
            if (orConditionResult) {
                return true;
            }
        }

        return false;
    }
    public boolean evaluateAndConditions(String[] columnNames, String[] columns, String[] andConditions) {
        boolean allConditionsResult = true;

        for (String andCondition : andConditions) {
            String[] conditionComponents = andCondition.trim().split("\\s+");

            if (conditionComponents.length != 3) {
                System.out.println("Error: Sintaxis incorrecta para la condición WHERE.");
                return false;
            }

            boolean conditionResult = evaluateOperation(columnNames, columns, conditionComponents);

            // Evaluar todas las condiciones AND
            allConditionsResult = allConditionsResult && conditionResult;
        }

        return allConditionsResult;
    }
    public boolean evaluateOperation(String[] columnNames, String[] columns, String[] conditionComponents) {
        String leftOperand = conditionComponents[0];
        String operator = conditionComponents[1];
        String rightOperand = conditionComponents[2];

        int columnIndex = getColumnIndex(columnNames, leftOperand);

        if (columnIndex == -1) {
            System.out.println("Error: La columna especificada en la condición WHERE no existe.");
            return false;
        }

        String columnValue = columns[columnIndex].trim();

        // Evaluar la condición de la operación (por ejemplo, "id = 27")
        return evaluateOperation(columnValue, operator, rightOperand);
    }
    public boolean evaluateOperation(String columnValue, String operator, String rightOperand) {
        if (operator.equals("=")) {
            return columnValue.equals(rightOperand);
        } else if (operator.equals("<>")) {
            return !columnValue.equals(rightOperand);
        } else {
            System.out.println("Error: Operador de comparación no válido en la condición WHERE.");
            return false;
        }
    }
    public boolean verificarUsuario(String query){

        return false;
    }
}