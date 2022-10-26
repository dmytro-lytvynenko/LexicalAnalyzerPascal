package com;

import com.exceptions.NoMoreLexemesException;



// every object is immutable and if you started parsing you need to create new one for changing position or text.
public class Lexer {

    public static final int LENGTH = 12;





    public static final int ERROR = -2;
    private static final int ERROR_IN_REAL = -3;


    public static final int COMENT = -4;
    public static final int COMENTBLOCK = -5;
    public static final int ID = -6;


    public static final int SPACE = -15;


    public static final int DOUBLEDOT = -108;
    public static final int DOT = -109;
    public static final int DOUBKOMA = -110;
    public static final int DOUBLE_SLASH = -111;
    public static final int KOMA = -113;
    public static final int OPENING_SQR_BRACE = -114;
    public static final int CLOSING_SQR_BRACE = -115;
    public static final int OPENING_ROUND_BRACE = -116;
    public static final int CLOSING_ROUND_BRACE = -117;
    public static final int SEMICOLON = -118;

    public static final int INT = -19;
    public static final int SIXTEEN_NUM = -20;
    public static final int DOUBLE= -21;
    public static final int STRING= -22;
    public static final int CHAR= -23;
    private static final int REAL = -24;






    public static final int INTEGER_KEYWORD = -201;
    public static final int STRING_KEYWORD = -202;
    public static final int BYTE_KEYWORD = -203;
    public static final int REAL_KEYWORD = -204;
    public static final int EXTENDED_KEYWORD = -205;
    public static final int CHAR_KEYWORD = -205;


    public static final int PROGRAM_KEYWORD = -300;
    public static final int AND_KEYWORD = -301;
    public static final int ARRAY_KEYWORD = -302;
    public static final int BEGIN_KEYWORD = -303;
    public static final int CASE_KEYWORD = -304;
    public static final int CONST_KEYWORD = -305;
    public static final int DIV_KEYWORD = -306;
    public static final int DO_KEYWORD = -307;
    public static final int DOWNTO_KEYWORD = -308;
    public static final int ELSE_KEYWORD = -309;
    public static final int END_KEYWORD = -310;
    public static final int FILE_KEYWORD = -311;
    public static final int FOR_KEYWORD = -312;
    public static final int FUNCTION_KEYWORD = -313;
    public static final int GOTO_KEYWORD = -314;
    public static final int IF_KEYWORD = -315;
    public static final int IN_KEYWORD = -316;
    public static final int LABEL_KEYWORD = -317;
    public static final int MOD_KEYWORD = -318;
    public static final int NIL_KEYWORD = -319;
    public static final int NOT_KEYWORD = -320;
    public static final int OF_KEYWORD = -321;
    public static final int OR_KEYWORD = -322;
    public static final int PACKED_KEYWORD = -323;
    public static final int PROCEDURE_KEYWORD = -324;
    public static final int RECORD_KEYWORD = -325;
    public static final int REPEAT_KEYWORD = -326;
    public static final int SET_KEYWORD = -327;
    public static final int THEN_KEYWORD = -328;
    public static final int TO_KEYWORD = -329;
    public static final int TYPE_KEYWORD = -330;
    public static final int UNTIL_KEYWORD = -331;
    public static final int VAR_KEYWORD = -332;
    public static final int WHILE_KEYWORD = -333;
    public static final int WITH_KEYWORD = -334;
    public static final int USES_KEYWORD = -335;
    public static final int RANDOMIZE_KEYWORD = -336;

    public static final int PLUS = -400;
    public static final int MINUS = -401;
    public static final int MULT = -402;
    public static final int DIL = -403;
    public static final int AWARDING = -404;
    public static final int GR = -405;
    public static final int GEQ = -406;
    public static final int EQUAL = -407;
    public static final int LESS = -408;
    public static final int LEQ = -409;
    public static final int NOTEQUALS = -410;
    public static final int EXP = -411;







    private int lastFinalState = 0;
    private int lastPositionWithFinalState = 0;
    private int startingPosition = 0;
    private int i = 0;
    private int[] states = initStates();
    private String code;
    private int length;




    public Lexer(String code, String outputFilePath) {

        this.code = code;
        this.length = code.length();



    }

    public Token parseNext() throws NoMoreLexemesException {
        String p="";
        while (i <length)
        {

            identify(code.charAt(i), states);

            if (areAllBroken(states))
            {

                if (lastFinalState == 0)
                {


                    Token token = new Token(  0, p);

                    states = initStates();
                    i = startingPosition;
                    startingPosition++;
                    p="";
                    i++;
                    return token;

                } else
                {

                    if(lastFinalState==ERROR)
                        p+="   ERROR IN REAL;";

                    if(lastFinalState<=-100&&lastFinalState>-400)
                        p=p.toLowerCase();
                    Token token = new Token(  lastFinalState, p);

                    lastFinalState = 0;
                    i = lastPositionWithFinalState;
                    startingPosition = lastPositionWithFinalState + 1;
                    states = initStates();
                    p="";
                    i++;
                    return token;
                }
            }
            else
            {
                int gt=getFinalState(states);
                if (gt != 0) {

                    lastFinalState = gt;
                    lastPositionWithFinalState = i;

                }


                p += code.charAt(i);
                //  if(gt==-41)
                //  p.replace('\n','u' );
                i++;
            }

        }

        if (lastFinalState != 0 && i >= length) {


            Token token = new Token(  lastFinalState, p);
            lastFinalState = 0;
            return token;
        }

        throw new NoMoreLexemesException();

    }



    private boolean areAllBroken(int[] states) {
        //temporary code
        for (int i : states) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    private int getFinalState(int[] states) {

        //ORDER MATTERS
        //=============

        //keyword
        if (states[2] < 0) {
            return states[2];
        }
        if (states[11] < 0) {
            return states[11];
        }


        //int
        if (states[0] < 0) {
            return states[0];
        }

        //identifier
        if (states[1] < 0) {
            return states[1];
        }



        //double
        if (states[4] < 0) {
            return states[4];
        }

        //float
        if (states[3] < 0) {
            return states[3];
        }
        //sixteen numeric
        if (states[8] < 0) {
            return states[8];
        }

        //space
        if (states[5] < 0) {
            return states[5];
        }

        //sign
        if (states[6] < 0) {
            return states[6];
        }
        //operators
        if (states[10] < 0) {
            return states[10];
        }
        //operators
        /*if (states[] < 0) {
            return states[9];
        }
        */
        //string
        if (states[9] < 0) {
            return states[9];
        }
        if (states[7] < 0) {
            return states[7];
        }


        return 0;
    }

    private void identify(char c, int[] states) {

        states[0] = identifyInt(c, states[0]);
        states[1] = identifyIdentifier(c, states[1]);
        states[2] = identifyKeyWord(c, states[2]);
        states[3] = identifyReal(c, states[3]);
        states[4] = identifyDouble(c, states[4]);
        states[5] = identifySpace(c, states[5]);
        states[6] = identifySign(c, states[6]);
        states[7]=identifyComments(c, states[7]);
        states[8]=identifySixteenNumeric(c,states[8]);
        states[9]=identifyString(c,states[9]);
        states[10]=identifyOPERATORS(c,states[10]);
        states[11]=identifyTYPES(c,states[11]);

    }


    private int identifyTYPES(char c, int state)
    {
        switch (state)
        {
            case 1:
                if(c=='i'||c=='I')
                {
                    return 2;
                }
                if(c=='s'||c=='S')
                {
                    return 8;
                }
                if(c=='b'||c=='B')
                {
                    return 13;
                }
                if(c=='r'||c=='R')
                {
                    return 16;
                }
                if(c=='e'||c=='E')
                {
                    return 19;
                }
                if(c=='c'||c=='C')
                {
                    return 26;
                }
                return 0;
            case 2:
                if(c=='n'||c=='N')
                {
                    return 3;
                }
                return 0;
            case 3:
                if(c=='t'||c=='T')
                {
                    return 4;
                }
                return 0;
            case 4:
                if(c=='e'||c=='E')
                {
                    return 5;
                }
                return 0;
            case 5:
                if(c=='g'||c=='G')
                {
                    return 6;
                }
                return 0;
            case 6:
                if(c=='e'||c=='E')
                {
                    return 7;
                }
                return 0;
            case 7:
                if(c=='r'||c=='R')
                {
                    return INTEGER_KEYWORD;
                }
                return 0;
            case 8:
                if(c=='t'||c=='T')
                {
                    return 9;
                }
                return 0;
            case 9:
                if(c=='r'||c=='R')
                {
                    return 10;
                }
                return 0;
            case 10:
                if(c=='i'||c=='I')
                {
                    return 11;
                }
                return 0;
            case 11:
                if(c=='n'||c=='N')
                {
                    return 12;
                }
                return 0;
            case 12:
                if(c=='g'||c=='G')
                {
                    return STRING_KEYWORD;
                }
                return 0;
            case 13:
                if(c=='y'||c=='Y')
                {
                    return 14;
                }
                return 0;
            case 14:
                if(c=='t'||c=='T')
                {
                    return 15;
                }
                return 0;
            case 15:
                if(c=='e'||c=='E')
                {
                    return BYTE_KEYWORD;
                }
                return 0;
            case 16:
                if(c=='e'||c=='E')
                {
                    return 17;
                }
                return 0;
            case 17:
                if(c=='a'||c=='A')
                {
                    return 18;
                }
                return 0;
            case 18:
                if(c=='l'||c=='L')
                {
                    return REAL_KEYWORD;
                }
                return 0;
            case 19:
                if(c=='x'||c=='X')
                {
                    return 20;
                }
                return 0;
            case 20:
                if(c=='t'||c=='T')
                {
                    return 21;
                }
                return 0;
            case 21:
                if(c=='e'||c=='E')
                {
                    return 22;
                }
                return 0;
            case 22:
                if(c=='n'||c=='N')
                {
                    return 23;
                }
                return 0;
            case 23:
                if(c=='d'||c=='D')
                {
                    return 24;
                }
                return 0;
            case 24:
                if(c=='e'||c=='E')
                {
                    return 25;
                }
                return 0;
            case 25:
                if(c=='d'||c=='D')
                {
                    return EXTENDED_KEYWORD;
                }
                return 0;
            case 26:
                if(c=='h'||c=='H')
                {
                    return 27;
                }
                return 0;
            case 27:
                if(c=='a'||c=='A')
                {
                    return 28;
                }
                return 0;
            case 28:
                if(c=='r'||c=='R')
                {
                    return CHAR_KEYWORD;
                }
                return 0;



        }
        return 0;
    }


    private int identifyOPERATORS(char c, int state)
    {switch(state){
        case 1:
            if(c=='+')
            {
                return PLUS;
            }
            if(c=='-')
            {
                return MINUS;
            }
            if(c=='*')
            {
                return MULT;
            }
            if(c=='/')
            {
                return DIL;
            }
            if(c==':')
            {
                return 2;
            }
            if(c=='=')
            {
                return EQUAL;
            }
            if(c=='>')
            {
                return GR;
            }
            if(c=='<')
            {
                return LESS;
            }
            if(c=='^')
            {
                return EXP;
            }

            return 0;
        case 2:
            if(c=='=')
            {
                return AWARDING;
            }
            return 0;
        case GR:
            if(c=='=')
            {
                return GEQ;
            }
            return 0;
        case LESS:
            if(c=='=')
            {
                return LEQ;
            }
            if(c=='>')
            {
                return NOTEQUALS;
            }
            return 0;


    }

        return 0;
    }



    private int identifyString(char c, int state)
    {
        switch(state)

        {
            case 1:
                if(c=='\'')
                {
                    return 2;
                }
                return 0;
            case 2:
                if(c=='\'')
                {
                    return CHAR;
                }

                else return 3;
            case 3:
                if(c=='\'')
                {
                    return CHAR;
                }
                else return 4;
            case 4:
                if(c=='\'')
                {return STRING;

                }
                return 4;

        }
        return 0;
    }

    private int[] initStates() {
        int[] arr = new int[LENGTH];
        for (int i=0; i< LENGTH; i++) {
            arr[i] = 1;
        }
        return arr;
    }

    private int identifyInt(char ch, int state) {
        switch (state) {
            //11234d
            case 1:
                if (ch=='-') {
                    return INT;
                }
                if (Character.isDigit(ch)) {
                    return INT;
                }
                return 0;
            case INT:
                if (Character.isDigit(ch)) {
                    return INT;
                }
                return 0;
        }
        return 0;
    }

    private int identifySixteenNumeric(char ch, int state) {
        switch (state) {
            //11234d
            case 1:
                if (ch=='$') {
                    return 2;
                }

                return 0;
            case 2:
                if (Character.isDigit(ch)) {
                    return SIXTEEN_NUM;
                }
                if (ch=='A'||ch=='B'||ch=='C'||ch=='D'||ch=='E'||ch=='F'||ch=='a'||ch=='b'||ch=='c'||ch=='d'||ch=='e'||ch=='f') {
                    return SIXTEEN_NUM;
                }
                return 0;
            case SIXTEEN_NUM:
                if (Character.isDigit(ch)) {
                    return SIXTEEN_NUM;
                }
                if (ch=='A'||ch=='B'||ch=='C'||ch=='D'||ch=='E'||ch=='F'||ch=='a'||ch=='b'||ch=='c'||ch=='d'||ch=='e'||ch=='f') {
                    return SIXTEEN_NUM;
                }
                return 0;

        }
        return 0;
    }

    private int identifyIdentifier(char ch, int state) {
        switch (state) {
            case 1:
                if (Character.isLetter(ch)) {
                    return ID;
                }
            case ID:
                if (Character.isLetter(ch)||Character.isDigit(ch)||ch=='_') {
                    return ID;
                }
                //-7 - -12 reserved by this FSM
        }
        return 0;
    }





    //todo fill keywords
    private int identifyKeyWord(char ch, int state) {
        switch (state)
        {
            case 1:
                if(ch=='p'||ch=='P')
                {return 2;
                }
                if(ch=='a'||ch=='A')
                {return 8;
                }
                if(ch=='b'||ch=='B')
                {return 13;
                }
                if(ch=='c'||ch=='C')
                {return 17;
                }
                if(ch=='d'||ch=='D')
                {return 23;
                }
                if(ch=='e'||ch=='E')
                {return 28;
                }
                if(ch=='f'||ch=='F')
                {return 32;
                }
                if(ch=='g'||ch=='G')
                {return 42;
                }
                if(ch=='i'||ch=='I')
                {return 45;
                }
                if(ch=='l'||ch=='L')
                {return 46;
                }
                if(ch=='m'||ch=='M')
                {return 50;
                }
                if(ch=='n'||ch=='n')
                {return 52;
                }
                if(ch=='o'||ch=='O')
                {return 55;
                }
                if(ch=='r'||ch=='R')
                {return 65;
                }
                if(ch=='s'||ch=='S')
                {return 73;
                }
                if(ch=='t'||ch=='T')
                {return 75;
                }
                if(ch=='u'||ch=='U')
                {return 80;
                }
                if(ch=='v'||ch=='V')
                {return 84;
                }
                if(ch=='w'||ch=='W')
                {return 86;
                }
                return 0;
            case 2:
                if(ch=='r'||ch=='R')
                {return 3;
                }
                if(ch=='a'||ch=='A')
                {return 56;
                }

                return 0;
            case 3:
                if(ch=='o'||ch=='O')
                {return 4;
                }
                return 0;
            case 4:
                if(ch=='g'||ch=='G')
                {return 5;
                }
                if(ch=='c'||ch=='C')
                {return 60;
                }

                return 0;
            case 5:
                if(ch=='r'||ch=='R')
                {return 6;
                }
                return 0;
            case 6:
                if(ch=='a'||ch=='A')
                {return 7;
                }
                return 0;
            case 7:
                if(ch=='m'||ch=='M')
                {return PROGRAM_KEYWORD;
                }
                return 0;
            case 8:
                if(ch=='n'||ch=='N')
                {return 9;
                }
                if(ch=='r'||ch=='R')
                {return 10;
                }

                return 0;
            case 9:
                if(ch=='d'||ch=='D')
                {return AND_KEYWORD;
                }
                return 0;
            case 10:
                if(ch=='r'||ch=='R')
                {return 11;
                }
                return 0;
            case 11:
                if(ch=='a'||ch=='A')
                {return 12;
                }
                return 0;
            case 12:
                if(ch=='y'||ch=='Y')
                {return ARRAY_KEYWORD;
                }
                return 0;
            case 13:
                if(ch=='e'||ch=='E')
                {return 14;
                }
                return 0;
            case 14:
                if(ch=='g'||ch=='G')
                {return 15;
                }
                return 0;
            case 15:
                if(ch=='i'||ch=='I')
                {return 16;
                }
                return 0;
            case 16:
                if(ch=='n'||ch=='N')
                {return BEGIN_KEYWORD;
                }
                return 0;
            case 17:
                if(ch=='a'||ch=='A')
                {return 18;
                }
                if(ch=='o'||ch=='O')
                {return 20;
                }
                return 0;
            case 18:
                if(ch=='s'||ch=='S')
                {return 19;
                }

                return 0;
            case 19:
                if(ch=='e'||ch=='E')
                {return CASE_KEYWORD;
                }
                return 0;
            case 20:
                if(ch=='n'||ch=='N')
                {return 21;
                }
                return 0;
            case 21:
                if(ch=='s'||ch=='S')
                {return 22;
                }
                return 0;
            case 22:
                if(ch=='t'||ch=='t')
                {return CONST_KEYWORD;
                }
                return 0;
            case 23:
                if(ch=='i'||ch=='I')
                {return 24;
                }
                if(ch=='o'||ch=='O')
                {return DO_KEYWORD;
                }
                return 0;
            case 24:
                if(ch=='v'||ch=='V')
                {return DIV_KEYWORD;
                }
                return 0;
            case DO_KEYWORD:
                if(ch=='w'||ch=='W')
                {return 25;
                }
                return 0;
            case 25:
                if(ch=='n'||ch=='N')
                {return 26;
                }
                return 0;
            case 26:
                if(ch=='t'||ch=='T')
                {return 27;
                }
                return 0;
            case 27:
                if(ch=='o'||ch=='O')
                {return DOWNTO_KEYWORD;
                }
                return 0;
            case 28:
                if(ch=='l'||ch=='L')
                {return 29;
                }
                if(ch=='n'||ch=='N')
                {return 31;
                }

                return 0;
            case 29:
                if(ch=='s'||ch=='S')
                {return 30;
                }
                return 0;

            case 30:
                if(ch=='e'||ch=='E')
                {return ELSE_KEYWORD;
                }
                return 0;
            case 31:
                if(ch=='d'||ch=='D')
                {return END_KEYWORD;
                }
                return 0;
            case 32:
                if(ch=='i'||ch=='I')
                {return 33;
                }
                if(ch=='o'||ch=='O')
                {return 35;
                }
                if(ch=='u'||ch=='U')
                {return 36;
                }

                return 0;
            case 33:
                if(ch=='l'||ch=='L')
                {return 34;
                }
                return 0;
            case 34:
                if(ch=='e'||ch=='E')
                {return FILE_KEYWORD;
                }
                return 0;
            case 35:
                if(ch=='r'||ch=='R')
                {return FOR_KEYWORD;
                }
                return 0;
            case 36:
                if(ch=='n'||ch=='N')
                {return 37;
                }
                return 0;
            case 37:
                if(ch=='c'||ch=='C')
                {return 38;
                }
                return 0;
            case 38:
                if(ch=='t'||ch=='T')
                {return 39;
                }
                return 0;
            case 39:
                if(ch=='i'||ch=='I')
                {return 40;
                }
                return 0;
            case 40:
                if(ch=='o'||ch=='O')
                {return 41;
                }
                return 0;
            case 41:
                if(ch=='n'||ch=='N')
                {return FUNCTION_KEYWORD;
                }
                return 0;
            case 42:
                if(ch=='o'||ch=='O')
                {return 43;
                }
                return 0;
            case 43:
                if(ch=='t'||ch=='T')
                {return 44;
                }
                return 0;
            case 44:
                if(ch=='o'||ch=='O')
                {return GOTO_KEYWORD;
                }
                return 0;
            case 45:
                if(ch=='f'||ch=='F')
                {return IF_KEYWORD;
                }
                if(ch=='n'||ch=='N')
                {return IN_KEYWORD;
                }
                return 0;
            case 46:
                if(ch=='a'||ch=='A')
                {return 47;
                }
                return 0;
            case 47:
                if(ch=='b'||ch=='B')
                {return 48;
                }
                return 0;
            case 48:
                if(ch=='e'||ch=='E')
                {return 49;
                }
                return 0;
            case 49:
                if(ch=='l'||ch=='L')
                {return LABEL_KEYWORD;
                }
                return 0;
            case 50:
                if(ch=='o'||ch=='O')
                {return 51;
                }
                return 0;
            case 51:
                if(ch=='d'||ch=='D')
                {return MOD_KEYWORD;
                }
                return 0;
            case 52:
                if(ch=='i'||ch=='I')
                {return 53;
                }
                if(ch=='o'||ch=='O')
                {return 54;
                }
                return 0;
            case 53:
                if(ch=='l'||ch=='L')
                {return NIL_KEYWORD;
                }
                return 0;
            case 54:
                if(ch=='t'||ch=='T')
                {return NOT_KEYWORD;
                }
                return 0;
            case 55:
                if(ch=='f'||ch=='F')
                {return OF_KEYWORD;
                }
                if(ch=='r'||ch=='R')
                {return OR_KEYWORD;
                }
                return 0;
            case 56:
                if(ch=='c'||ch=='C')
                {return 57;
                }
                return 0;
            case 57:
                if(ch=='k'||ch=='K')
                {return 58;
                }
                return 0;
            case 58:
                if(ch=='e'||ch=='E')
                {return 59;
                }
                return 0;
            case 59:
                if(ch=='d'||ch=='D')
                {return PACKED_KEYWORD;
                }
                return 0;
            case 60:
                if(ch=='e'||ch=='E')
                {return 61;
                }
                return 0;
            case 61:
                if(ch=='d'||ch=='D')
                {return 62;
                }
                return 0;
            case 62:
                if(ch=='u'||ch=='U')
                {return 63;
                }
                return 0;
            case 63:
                if(ch=='r'||ch=='R')
                {return 64;
                }
                return 0;
            case 64:
                if(ch=='e'||ch=='E')
                {return PROCEDURE_KEYWORD;
                }
                return 0;
            case 65:
                if(ch=='e'||ch=='E')
                {return 66;
                }
                if(ch=='a'||ch=='A')
                {return 95;
                }
                return 0;
            case 66:
                if(ch=='c'||ch=='C')
                {return 67;
                }
                if(ch=='p'||ch=='P')
                {return 70;
                }
                return 0;
            case 67:
                if(ch=='o'||ch=='O')
                {return 68;
                }
                return 0;
            case 68:
                if(ch=='r'||ch=='R')
                {return 69;
                }
                return 0;
            case 69:
                if(ch=='d'||ch=='D')
                {return RECORD_KEYWORD;
                }
                return 0;
            case 70:
                if(ch=='e'||ch=='E')
                {return 71;
                }
                return 0;
            case 71:
                if(ch=='a'||ch=='A')
                {return 72;
                }
                return 0;
            case 72:
                if(ch=='t'||ch=='T')
                {return REPEAT_KEYWORD;
                }
                return 0;
            case 73:
                if(ch=='e'||ch=='E')
                {return 74;
                }
                return 0;
            case 74:
                if(ch=='t'||ch=='T')
                {return SET_KEYWORD;
                }
                return 0;
            case 75:
                if(ch=='h'||ch=='H')
                {return 76;
                }
                if(ch=='o'||ch=='O')
                {return TO_KEYWORD;
                }
                if(ch=='y'||ch=='Y')
                {return 78;
                }

                return 0;
            case 76:
                if(ch=='e'||ch=='E')
                {return 77;
                }
                return 0;
            case 77:
                if(ch=='n'||ch=='N')
                {return THEN_KEYWORD;
                }
                return 0;
            case 78:
                if(ch=='p'||ch=='P')
                {return 79;
                }
                return 0;
            case 79:
                if(ch=='e'||ch=='E')
                {return TYPE_KEYWORD;
                }
                return 0;
            case 80:
                if(ch=='n'||ch=='N')
                {return 81;
                }
                if(ch=='s'||ch=='S')
                {return 93;
                }

                return 0;
            case 81:
                if(ch=='t'||ch=='T')
                {return 82;
                }
                return 0;
            case 82:
                if(ch=='i'||ch=='I')
                {return 83;
                }
                return 0;
            case 83:
                if(ch=='l'||ch=='L')
                {return UNTIL_KEYWORD;
                }
                return 0;
            case 84:
                if(ch=='a'||ch=='A')
                {return 85;
                }
                return 0;
            case 85:
                if(ch=='R'||ch=='r')
                {return VAR_KEYWORD;
                }
                return 0;
            case 86:
                if(ch=='h'||ch=='H')
                {return 87;
                }
                if(ch=='i'||ch=='I')
                {return 91;
                }

                return 0;
            case 87:
                if(ch=='i'||ch=='I')
                {return 89;
                }
                return 0;
            case 89:
                if(ch=='l'||ch=='L')
                {return 90;
                }
                return 0;
            case 90:
                if(ch=='e'||ch=='E')
                {return WHILE_KEYWORD;
                }
                return 0;
            case 91:
                if(ch=='t'||ch=='T')
                {return 92;
                }
                return 0;
            case 92:
                if(ch=='h'||ch=='H')
                {return WITH_KEYWORD;
                }
                return 0;

            case 93:
                if(ch=='e'||ch=='E')
                {return 94;
                }
                return 0;

            case 94:
                if(ch=='s'||ch=='S')
                {return USES_KEYWORD;
                }
                return 0;
            case 95:
                if(ch=='n'||ch=='N')
                {return 96;
                }
                return 0;
            case 96:
                if(ch=='d'||ch=='D')
                {return 97;
                }
                return 0;
            case 97:
                if(ch=='o'||ch=='O')
                {return 98;
                }
                return 0;
            case 98:
                if(ch=='m'||ch=='M')
                {return 99;
                }
                return 0;
            case 99:
                if(ch=='i'||ch=='I')
                {return 100;
                }
                return 0;
            case 100:
                if(ch=='z'||ch=='Z')
                {return 101;
                }
                return 0;
            case 101:
                if(ch=='e'||ch=='E')
                {return RANDOMIZE_KEYWORD;
                }
                return 0;

        }
        return 0;
    }
    private int identifyDouble(char ch, int state)
    {
        switch (state)
        {
            case 1:
                if (ch == '-')
                {
                    return 2;
                }
                if (Character.isDigit(ch))
                {
                    return 3;
                }
                return 0;

            case 2:
                if (Character.isDigit(ch))
                {
                    return 3;
                }
                return 0;
            case 3:
                if (Character.isDigit(ch))
                {
                    return 3;
                }
                if(ch=='.')
                {
                    return 4;
                }
                return 0;
            case 4:
                if (Character.isDigit(ch))
                {
                    return DOUBLE;

                }
                return 0;
            case DOUBLE:
                if (Character.isDigit(ch))
                {
                    return DOUBLE;

                }
                return 0;


        }
        return 0;

    }

    private int identifyReal(char ch, int state) {
        switch (state) {
            case 1:
                if (ch == '-') {
                    return 2;
                }
                if (ch == '0') {
                    return 4;
                }
                if (Character.isDigit(ch)) {
                    return 2;
                }
                if (ch == '.') {
                    return 3;
                }
                return 0;
            case 2:
                if (Character.isDigit(ch)) {
                    return 2;
                }
                if (ch == '.')
                    return -1;
                return 0;
            case -1:
                if (Character.isDigit(ch)) {
                    return -2;
                }
                if (ch=='E')
                {
                    return 7;
                }
                if (ch=='.') {
                    return ERROR;
                }
                return 0;
            case -2:
                if (Character.isDigit(ch)) {
                    return -2;
                }
                if(ch=='E')
                {
                    return 7;
                }
                return 0;
            case 3:
                if (Character.isDigit(ch)) {
                    return -5;
                }
                return 0;
            case -5:
                if (Character.isDigit(ch)) {
                    return -5;
                }
                return 0;
            case 4:
                if (ch == '.') {
                    return 5;
                }
                return 0;
            case 5:
                if (Character.isDigit(ch)) {
                    return 6;
                }
                return 0;
            case 6:
                if (Character.isDigit(ch)) {
                    return 6;
                }
                if (ch == 'E') {
                    return 7;
                }
                return 0;
            case 7:
                if (ch == '+' || ch == '-') {
                    return 8;
                }
                return 0;
            case 8:
                if (Character.isDigit(ch)) {
                    return ERROR_IN_REAL;
                }
                return 0;
            case ERROR_IN_REAL:
                if (Character.isDigit(ch)) {
                    return REAL; //
                }
                else return ERROR;
            case REAL:
                if (Character.isDigit(ch)) {
                    return ERROR;
                }
                return 0;
        }
        return 0;
    }

    private int identifyDoubleFloat(char ch, int state) {
        switch (state) {
            case 1:
                if (ch == '0') {
                    return 2;
                }
                return 0;
            case 2:
                if (ch == '.') {
                    return 3;
                }
                return 0;
            case 3:
                if (Character.isDigit(ch)) {
                    return 4;
                }
                return 0;
            case 4:
                if (Character.isDigit(ch)) {
                    return 4;
                }
                if (ch == 'd') {
                    return 5;
                }
                return 0;
            case 5:
                if (ch == '+' || ch == '-') {
                    return 6;
                }
                return 0;
            case 6:
                if (Character.isDigit(ch)) {
                    return -5;
                }
                return 0;
            case -5:
                if (Character.isDigit(ch)) {
                    return -5;
                }
                return 0;
        }
        return 0;
    }

    private int identifyComments(char ch, int state)
    {
        switch (state)
        {
            case 1:

                if (ch == '/')
                {
                    return 2 ;
                }
                if(ch=='{')
                {
                    return 3;
                }
                if(ch=='(')
                {
                    return 4;
                }
                return 0;
            case 2:
                if (ch == '/')
                {
                    return DOUBLE_SLASH;
                }
                return 0;
            case 3:
                if(ch=='}')
                {
                    return COMENTBLOCK;
                }
                else return 3;
            case(DOUBLE_SLASH):
                if(ch!='\n')               //
                {return COMENT;
                }
                //else return DOUBLE_SLASH;
                return 0;
            case 4:
                if(ch=='*')
                {
                    return 5;
                }
                return 0;
            case 5:
                if(ch=='*')
                {
                    return 6;
                }
                else return 5;
            case 6:
                if(ch==')')
                {
                    return COMENTBLOCK;
                }
                else return 0;
            case COMENT:
                if(ch!='\n')               //
                {return COMENT;
                }
                //else return DOUBLE_SLASH;
                return 0;


        }
        return 0;
    }
    private int identifySpace(char ch, int state) {
        switch (state) {
            case 1:
                if (ch == ' ') {
                    return SPACE;
                }
                if (ch == '\t') {
                    return SPACE;
                }
                if (ch == '\r') {
                    return SPACE;
                }
                if (ch == '\n') {
                    return SPACE;
                }
                return 0;
            case SPACE:
                if (ch == ' ') {
                    return SPACE;
                }
                if (ch == '\t') {
                    return SPACE;
                }
                if (ch == '\r') {
                    return SPACE;
                }
                if (ch == '\n') {
                    return SPACE;
                }
                return 0;
        }
        return 0;
    }

    private int identifySign(char ch, int state) {
        switch (state) {
            case 1:
                if (ch == ';') {
                    return SEMICOLON;
                }
                if (ch == ',') {
                    return KOMA;
                }
                if (ch == ':') {
                    return DOUBKOMA;
                }
                if (ch == '[') {
                    return OPENING_SQR_BRACE;
                }
                if (ch == ']') {
                    return CLOSING_SQR_BRACE;
                }
                if (ch == '(') {
                    return OPENING_ROUND_BRACE;
                }
                if (ch == ')')
                {
                    return CLOSING_ROUND_BRACE;
                }
                if (ch == '.')
                {
                    return DOT;
                }
                return 0;
            case DOT:
                if (ch == '.')
                {
                    return DOUBLEDOT;
                }
                return 0;

        }
        return 0;
    }

}

