package com;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class Printer {
   public static int inext=20;
   public static int  iprev=0;

    private static String printText(Token token) {
      //  int tableNumber = token.getNumberOfTable();
      //  HashTable table = tables[tableNumber];
      //  int index = token.getIndex();
       // String lexeme = table.getByIndex(index);
       // return lexeme + ": " + getNameOfTableByState(token.getState()) + " (" + token.getState() + ")" + " INDEX " + token.getIndex();
        return "*";
    }

    public static void printTokenToFile(Token token, String filePath) {
        List<Token> list = new ArrayList<>();
        list.add(token);
        printTokensToFile(list, filePath);

    }

    public static void printTokensToFile(List<Token> list, String filePath) {

        try {
            PrintWriter file = getFile(filePath);
            for (Token token : list) {
                file.println(printText(token));
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printTables(HashSet<Token> tokens, String filePath)
    {

        List<Token> list = new ArrayList<Token>(tokens);
        list.sort(Comparator.comparingInt(Token::getState).reversed());


        for(Token n: list)
    {
        inext=WhatData(n);
        if(iprev!=inext)
            printNamesOfTables(n, filePath);
        if(n.getState()!=-15)
        Print(n.getToken());
        iprev=inext;
    }




    }
    public static int WhatData (Token token)
    {
        if (token.getState()>-500&&token.getState()<=-400)
        {
        return 1;
        }
        if (token.getState()>-400&&token.getState()<=-300)
        {
           return 2;
        }
        if (token.getState()>-300&&token.getState()<=-200)
        {
            return 3;
        }
        if (token.getState()>=-99&&token.getState()<=-19)
        {
          return 4;
        }
        if (token.getState()>=-200&&token.getState()<=-100)
        {
            return 5;
        }
        if (token.getState()==-6)
        {
           return 6;
        }
        if (token.getState()>=-5&&token.getState()<=-4)
        {
            return 7;
        }
        if (token.getState()>=-3&&token.getState()<=0)
        {
            return 8;
        }

return 0;

    }
    public static void printNamesOfTables(Token token, String filePath)
    {
        if (token.getState()>-500&&token.getState()<=-400)
        {
            System.out.println("==================================");
            System.out.println("||          OPERATORS :          ||");
            System.out.println("==================================");
        }
        if (token.getState()>-400&&token.getState()<=-300)
        {
            System.out.println("==================================");
            System.out.println("||          KEYWORDS :           ||");
            System.out.println("==================================");
        }
        if (token.getState()>-300&&token.getState()<=-200)
        {
            System.out.println("==================================");
            System.out.println("||            TYPES :            ||");
            System.out.println("==================================");
        }
        if (token.getState()>=-99&&token.getState()<=-19)
        {
            System.out.println("==================================");
            System.out.println("||            VALUES :           ||");
            System.out.println("==================================");
        }
        if (token.getState()>-200&&token.getState()<=-100)
        {
            System.out.println("==================================");
            System.out.println("||            SIGNS :            ||");
            System.out.println("==================================");
        }
        if (token.getState()==-6)
        {
            System.out.println("==================================");
            System.out.println("||        IDENTIFIERS :          ||");
            System.out.println("==================================");
        }
        if (token.getState()>=-5&&token.getState()<=-4)
        {
            System.out.println("==================================");
            System.out.println("||          COMMENTS :           ||");
            System.out.println("==================================");
        }
        if (token.getState()>=-3&&token.getState()<=0)
        {
            System.out.println("==================================");
            System.out.println("||            ERRORS :           ||");
            System.out.println("==================================");
        }



    }
    public static void Print(String st)
    {
        for(int i=0; i<st.length(); i++)
        {if (st.charAt(i)!='\r')
            System.out.print(st.charAt(i));
        }
        System.out.println("");
    }
    public static void createFile(String outputFilePath) {
        try {
            //rewriting old file
            new PrintWriter(outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PrintWriter getFile(String filePath) throws IOException {
        return new PrintWriter(new FileWriter(filePath, true));
    }

    public static String readFile(String filename) {
        File f = new File(filename);
        try {
            byte[] bytes = Files.readAllBytes(f.toPath());
            return new String(bytes, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getOutputFilePath(String fileName) {
        String currentWorkingDirectory = System.getProperty("user.dir");
        return currentWorkingDirectory + File.separator + fileName;
    }

    private static String getNameOfTableByState(int state) {
      /*  switch (state) {

            case Lexer.INT:
                return "INT";
            case Lexer.ID:
                return "ID";
            case Lexer.FLOAT_KEYWORD | Lexer.DOUBLE_KEYWORD:
            case Lexer.DOUBLE_KEYWORD:
            case Lexer.RETURN_KEYWORD:
                return "KEYWORD";
            case -1:
            case -2:
            case -3:
            case -4:
                return "FLOAT";
            case -5:
                return "DOUBLE FLOAT";
            case Lexer.SPACE:
                return "SPACE";
            case Lexer.CLOSING_CURLY_BRACE:
            case Lexer.OPENING_CURLY_BRACE:
            case Lexer.OPENING_ROUND_BRACE:
            case Lexer.CLOSING_ROUND_BRACE:
            case Lexer.SEMICOLON:
                return "SIGN";
            case Lexer.ERROR:
                return "ERROR";

        }

        */
      return "";
    }

    public static void printToFile(String info, String filePath) {
        try {
            PrintWriter file = getFile(filePath);
            file.println(info);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printLineToFile(String filepath) {
        printToFile("==================================================", filepath);
    }



}