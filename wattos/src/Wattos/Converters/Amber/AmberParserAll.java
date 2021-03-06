/* Generated By:JavaCC: Do not edit this line. AmberParserAll.java */
package Wattos.Converters.Amber;


import Wattos.Utils.*;
import Wattos.Converters.Amber.Utils;
import Wattos.Converters.Common.*;

import java.util.*;
import java.io.*;

/**
 * @author  Jurgen Doreleijers
 * @version 0.1
 */

public class AmberParserAll implements AmberParserAllConstants {

    // Type of data to parse
    static int data_type = Varia.DATA_TYPE_DISTANCE;

    //parser stack to store ASSI statement
    static Vector stack;
    //Store all comments
    static Vector cmtStack;
    //Store all error lines for second pass
    static Vector errLineStack;
    //Store all errors from second pass
    static Vector errStack;

    //Store most recently parsed Assi line and col
    //used by error recording later, for second pass
    //scanning for errors
    static int lastAssiLine;
    static int lastAssiCol;

    static int dataType;

    /**Constructor
     */
    public AmberParserAll() {
        init();
    }

    public void init() {
        stack           = new Vector();
        cmtStack        = new Vector();
        errLineStack    = new Vector();
        errStack        = new Vector();
        dataType = Varia.DATA_TYPE_DISTANCE; // To be reset later.
    }

    /**Start parsing of Amber file
     */
    public void parse(String AmberName, int type) {
        String inputFile = AmberName;
        data_type = type;
        try{
            /* Check first if file ends with an end of line, if not,
                append an extra "\n" before EOF as this would cause problems later on
                */
            RandomAccessFile inputRead = new RandomAccessFile(inputFile, "r");
            long inputBytes = inputRead.length() - 1;
            //forward file pointer to last byte char in file
            inputRead.seek(inputBytes);
            char lastChar = (char)(inputRead.read());
            if (lastChar != '\n' && lastChar != '\r') {
                inputRead.close();
                // Open an appending writer
                PrintWriter input = new PrintWriter(new BufferedWriter(new FileWriter(AmberName, true)));
                input.print('\n');
                input.close();
            }
            else
                inputRead.close();

            ReInit( new java.io.FileInputStream(AmberName) );
            dataType = type;

            getInput();

            //second pass processing errors
            Utils.saveErrors(inputFile, errLineStack, errStack);
        }
        catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException opening " + AmberName );
            e.printStackTrace();
        }
        catch (ParseException e) {
            System.out.println("ParseException parsing " + AmberName );
            e.printStackTrace();
        }
        catch (IOException e) {
            System.out.println("IOException in parse method" );
            e.printStackTrace();
        }
    }

    void getInput() throws ParseException {
        switch ( data_type ) {
            case Varia.DATA_TYPE_DISTANCE:
                getInputDistanceOrDihedral();
                break;
            case Varia.DATA_TYPE_DIHEDRAL:
                getInputDistanceOrDihedral();
                break;
            case Varia.DATA_TYPE_DIPOLAR_COUPLING:
                getInputDipolarCoupling();
                break;
            case Varia.DATA_TYPE_CHEMICAL_SHIFT:
                System.out.println("WARNING: Not implemented yet: DATA_TYPE_CHEMICAL_SHIFT");
                break;
            case Varia.DATA_TYPE_COUPLING_CONSTANT:
                System.out.println("WARNING: Not implemented yet: DATA_TYPE_COUPLING_CONSTANT");
                break;
            case Varia.DATA_TYPE_PLANARITY:
                System.out.println("WARNING: Not implemented yet: DATA_TYPE_PLANARITY");
                break;
        }
    }


    /**return Vector storing all data
     * every element is a LogicalNode object
     */
    public Vector popResult() {
        return stack;
    }


    /**return Vector storing all comments
     * every element is a Comment object
     */
    public Vector popComment() {
        return cmtStack;
    }


    /**return Vector storing all errors
     * every element is an ErrorLine object
     */
    public Vector popError() {
        return errStack;
    }

  static final public void getInputDistanceOrDihedral() throws ParseException {
    try {
      AssiList();

    } catch (ParserDoneException e) {
        if (getNextToken().kind == EOF) {
            ;
        } else {
            System.out.println("Caught ParserDone");
        }
    }
  }

  static final public void getInputDipolarCoupling() throws ParseException {
    try {
      AssiListRDC();

    } catch (ParserDoneException e) {
        if (getNextToken().kind == EOF) {
            ;
        } else {
            System.out.println("Caught ParserDone");
        }
    }
  }

  static final public void AssiList() throws ParseException, ParserDoneException {
    AssiState();
    AssiList();

  }

  static final public void AssiListRDC() throws ParseException, ParserDoneException {
    AssiStateRDC();
    AssiListRDC();

  }

  static final public void AssiStateRDC() throws ParseException, ParserDoneException {
    Token assiStart = getToken(1);
    lastAssiLine = assiStart.beginLine;
    lastAssiCol = assiStart.beginColumn;
    assiStart = null;
    HashMap attributeSet;
    HashMap attrIdxx;
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDXX:
        attrIdxx = AttrIdxx();
        attributeSet = AttributeSet();
                attributeSet.putAll( attrIdxx );
                //System.out.println("Found attributes for AssiStateRDC:" + Strings.toString(attributeSet));
                Utils.saveAssiStateDipolarCoupling( stack, attributeSet);
        break;
      default:
        jj_la1[0] = jj_gen;
        error_skipto(IDXX);
      }
    } catch (ParseException e) {
        error_skipto(IDXX);
    }
  }

  static final public void AssiState() throws ParseException, ParserDoneException {
    Token t,s;
    Token assiStart = getToken(1);
    lastAssiLine = assiStart.beginLine;
    lastAssiCol = assiStart.beginColumn;
    assiStart = null;
    HashMap attributeSet;
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case RST:
        t = jj_consume_token(RST);
        attributeSet = AttributeSet();
        s = jj_consume_token(END);
                Utils.saveComment(t,cmtStack);
                Utils.saveComment(s,cmtStack);
                //System.out.println("Found attributes for AssiState:" + Strings.toString(attributeSet));
                switch ( dataType ) {
                    case Varia.DATA_TYPE_DISTANCE: {
                        Utils.saveAssiStateDistance( stack, attributeSet);
                        break;
                    }
                    case Varia.DATA_TYPE_DIHEDRAL: {
                        Utils.saveAssiStateDihedral( stack, attributeSet);
                        break;
                    }
                    default: {
                        General.showError("Wrong data type set in AmberParserAll");
                    }
                }
        break;
      default:
        jj_la1[1] = jj_gen;
        error_skipto(RST);
      }
    } catch (ParseException e) {
        error_skipto(RST);
    }
  }

  static final public HashMap AttributeSet() throws ParseException {
    HashMap single, set = null;
    single = Attr();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IXPK:
    case NXPK:
    case IALTD:
    case R1:
    case R2:
    case R3:
    case R4:
    case RK2:
    case RK3:
    case IR6:
    case IGR1:
    case IGR2:
    case IAT:
    case IRESID:
    case JD1:
    case DOBS1:
    case DOBSL1:
    case DOBSU1:
    case DIJ1:
    case ATNAM:
      set = AttributeSet();
      break;
    default:
      jj_la1[2] = jj_gen;
      ;
    }
        if ( set != null ) {
            single.putAll( set );
        }
        {if (true) return single;}
    throw new Error("Missing return statement in function");
  }

  static final public HashMap Attr() throws ParseException {
    Token t1,t2;
    String attr_name, attr_value;
    ArrayList attr_value_list = new ArrayList();
    HashMap attr = new HashMap();
    attr_name = AttributeName();
    t1 = jj_consume_token(EQUAL);
    attr_value_list = AttributeValueList();
        Utils.saveComment(t1, cmtStack);
        //General.showDebug("attr_name is: " + attr_name);
        //General.showDebug("attr_values are: " + Strings.toString(attr_value_list));
        attr.put(attr_name, attr_value_list);
        {if (true) return attr;}
    throw new Error("Missing return statement in function");
  }

  static final public HashMap AttrIdxx() throws ParseException {
    Token idxx,t1,t2;
    String attr_value;
    ArrayList attr_value_list = new ArrayList();
    HashMap attr = new HashMap();
    idxx = jj_consume_token(IDXX);
    t1 = jj_consume_token(EQUAL);
    attr_value_list = AttributeValueList();
        Utils.saveComment(t1, cmtStack);
        Utils.saveComment(idxx, cmtStack);
        attr.put("idxx", attr_value_list); // hardcoding the attribute key.
        {if (true) return attr;}
    throw new Error("Missing return statement in function");
  }

  static final public ArrayList AttributeValueList() throws ParseException {
    Token t1 = null;
    String attr_value;
    ArrayList attr_value_list = new ArrayList();
    attr_value = AttributeValue();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case COMMA:
      t1 = jj_consume_token(COMMA);
      break;
    default:
      jj_la1[3] = jj_gen;
      ;
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NUMBER:
    case WORD:
      attr_value_list = AttributeValueList();
      break;
    default:
      jj_la1[4] = jj_gen;
      ;
    }
        Utils.saveComment(t1, cmtStack);
        attr_value_list.add(0,attr_value);
        {if (true) return attr_value_list;}
    throw new Error("Missing return statement in function");
  }

  static final public String AttributeName() throws ParseException {
    Token t;
    String s;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IXPK:
      // simple attribute
          t = jj_consume_token(IXPK);
                    Utils.saveComment(t, cmtStack); {if (true) return t.image;}
      break;
    case NXPK:
      t = jj_consume_token(NXPK);
                    Utils.saveComment(t, cmtStack); {if (true) return t.image;}
      break;
    case IALTD:
      t = jj_consume_token(IALTD);
                    Utils.saveComment(t, cmtStack); {if (true) return t.image;}
      break;
    case R1:
      t = jj_consume_token(R1);
                    Utils.saveComment(t, cmtStack); {if (true) return t.image;}
      break;
    case R2:
      t = jj_consume_token(R2);
                    Utils.saveComment(t, cmtStack); {if (true) return t.image;}
      break;
    case R3:
      t = jj_consume_token(R3);
                    Utils.saveComment(t, cmtStack); {if (true) return t.image;}
      break;
    case R4:
      t = jj_consume_token(R4);
                    Utils.saveComment(t, cmtStack); {if (true) return t.image;}
      break;
    case RK2:
      t = jj_consume_token(RK2);
                    Utils.saveComment(t, cmtStack); {if (true) return t.image;}
      break;
    case RK3:
      t = jj_consume_token(RK3);
                    Utils.saveComment(t, cmtStack); {if (true) return t.image;}
      break;
    case IR6:
      t = jj_consume_token(IR6);
                    Utils.saveComment(t, cmtStack); {if (true) return t.image;}
      break;
    case IGR1:
      t = jj_consume_token(IGR1);
                    Utils.saveComment(t, cmtStack); {if (true) return t.image;}
      break;
    case IGR2:
      t = jj_consume_token(IGR2);
                    Utils.saveComment(t, cmtStack); {if (true) return t.image;}
      break;
    case IAT:
      t = jj_consume_token(IAT);
                    Utils.saveComment(t, cmtStack); {if (true) return t.image;}
      break;
    case IRESID:
      t = jj_consume_token(IRESID);
                    Utils.saveComment(t, cmtStack); {if (true) return t.image;}
      break;
    case JD1:
      // Hardcoding the changes.
          //t = <IDXX>     { Utils.saveComment(t, cmtStack); return t.image; } |
          t = jj_consume_token(JD1);
                    Utils.saveComment(t, cmtStack); {if (true) return "jdxx";}
      break;
    case DOBS1:
      t = jj_consume_token(DOBS1);
                    Utils.saveComment(t, cmtStack); {if (true) return "dobsxx";}
      break;
    case DOBSL1:
      t = jj_consume_token(DOBSL1);
                    Utils.saveComment(t, cmtStack); {if (true) return "dobslxx";}
      break;
    case DOBSU1:
      t = jj_consume_token(DOBSU1);
                    Utils.saveComment(t, cmtStack); {if (true) return "dobsuxx";}
      break;
    case DIJ1:
      t = jj_consume_token(DIJ1);
                    Utils.saveComment(t, cmtStack); {if (true) return "dijxx";}
      break;
    case ATNAM:
      // complex attribute
          s = AtNam();
                     {if (true) return s;}
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public String AttributeValue() throws ParseException {
    String v;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NUMBER:
      v = Number();
                   {if (true) return v;}
      break;
    case WORD:
      v = Word();
                   {if (true) return v;}
      break;
    default:
      jj_la1[6] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  static final public String Number() throws ParseException {
    Token t;
    t = jj_consume_token(NUMBER);
        Utils.saveComment(t,cmtStack);
        {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  static final public String AtNam() throws ParseException {
    Token t,t1,t2;
    String d;
    t = jj_consume_token(ATNAM);
    t1 = jj_consume_token(LPAREN);
    d = Number();
    t2 = jj_consume_token(RPAREN);
        Utils.saveComment(t1, cmtStack);
        Utils.saveComment(t,  cmtStack);
        Utils.saveComment(t2, cmtStack);

        {if (true) return t.image + d;} // should in fact be a single digit; easily digested later.

    throw new Error("Missing return statement in function");
  }

  static final public String Word() throws ParseException {
    Token t;
    t = jj_consume_token(WORD);
        Utils.saveComment(t,cmtStack);
        {if (true) return t.image;}
    throw new Error("Missing return statement in function");
  }

  static void error_skipto(int kind) throws ParseException, ParserDoneException {
    Utils.error_skipto(kind, errLineStack, cmtStack, lastAssiLine, lastAssiCol);
  }

  static private boolean jj_initialized_once = false;
  static public AmberParserAllTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  static public Token token, jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[7];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_0();
      jj_la1_1();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x0,0x100000,0xfe000000,0x800000,0x1000000,0xfe000000,0x1000000,};
   }
   private static void jj_la1_1() {
      jj_la1_1 = new int[] {0x80,0x0,0x3f7f,0x0,0x10000,0x3f7f,0x10000,};
   }

  public AmberParserAll(java.io.InputStream stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  You must");
      System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new AmberParserAllTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  static public void ReInit(java.io.InputStream stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    AmberParserAllTokenManager.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  public AmberParserAll(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  You must");
      System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new AmberParserAllTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    AmberParserAllTokenManager.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  public AmberParserAll(AmberParserAllTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  You must");
      System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  public void ReInit(AmberParserAllTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 7; i++) jj_la1[i] = -1;
  }

  static final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = AmberParserAllTokenManager.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = AmberParserAllTokenManager.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = AmberParserAllTokenManager.getNextToken();
    }
    return t;
  }

  static final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=AmberParserAllTokenManager.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.Vector jj_expentries = new java.util.Vector();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  static public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[52];
    for (int i = 0; i < 52; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 7; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 52; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.addElement(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = (int[])jj_expentries.elementAt(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  static final public void enable_tracing() {
  }

  static final public void disable_tracing() {
  }

}
