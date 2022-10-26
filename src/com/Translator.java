package com;

import com.exceptions.NoMoreLexemesException;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Translator {
    static HashSet<Token> lexemas = new HashSet<>();

    public static void main(String[] args) {

        long time = System.currentTimeMillis();
        String c = new String("C:\\Users\\Home\\Documents\\GitHub\\LexicalAnalyzerPascal\\code.txt");
        String r = new String("C:\\Users\\Home\\Documents\\GitHub\\LexicalAnalyzerPascal\\result.txt");

        Lexer lexer = new Lexer(Printer.readFile(c), c );
        List<Token> tokens = new ArrayList<>();

        try {
            while (true) {
                Token token = lexer.parseNext();


                lexemas.add(token);

            }
        } catch (NoMoreLexemesException e) {

            String filePath = Printer.getOutputFilePath(args[1]);

            Printer.createFile(filePath);

            System.out.println("Parsing done!");
            System.out.println("Printing results to file");


            Printer.printTables(lexemas, filePath);

        }

        Printer.printLineToFile(Printer.getOutputFilePath(args[1]));

        Printer.printToFile("Time in millis: " + (System.currentTimeMillis() - time), Printer.getOutputFilePath(args[1]));


    }
}

