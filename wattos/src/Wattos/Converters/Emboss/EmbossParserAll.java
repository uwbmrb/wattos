/* Generated By:JavaCC: Do not edit this line. EmbossParserAll.java */
package Wattos.Converters.Emboss;

import Wattos.Utils.*;
import Wattos.Converters.Emboss.Utils;
import Wattos.Converters.Common.*;

import java.util.*;
import java.io.*;

/**
 * @author  Jurgen Doreleijers
 * @version 0.1
 */

public class EmbossParserAll implements EmbossParserAllConstants {

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

    /**Constructor
     */
    public EmbossParserAll() {
        init();
    }

    public void init() {
        stack           = new Vector();
        cmtStack        = new Vector();
        errLineStack    = new Vector();
        errStack        = new Vector();
    }

    /**Start parsing of Emboss file 
     */
    public void parse(String EmbossName, int type) {
        String inputFile = EmbossName;
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
                PrintWriter input = new PrintWriter(new BufferedWriter(new FileWriter(EmbossName, true)));
                input.print('\n');
                input.close();
            }
            else
                inputRead.close();

            ReInit( new java.io.FileInputStream(EmbossName) );
            getInput();

            //second pass processing errors
            Utils.saveErrors(inputFile, errLineStack, errStack);
        }
        catch (FileNotFoundException e) {
            System.out.println("FileNotFoundException opening " + EmbossName );
            General.showThrowable(e);
        }
        catch (ParseException e) {
            System.out.println("ParseException parsing " + EmbossName );
            General.showThrowable(e);
        }
        catch (IOException e) {
            System.out.println("IOException in parse method" );
            General.showThrowable(e);
        }
    }


    void getInput() throws ParseException {
        switch ( data_type ) {
            case Varia.DATA_TYPE_DISTANCE:
                getInputDistance();
                break;
            case Varia.DATA_TYPE_DIHEDRAL:
                getInputDihedral();
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

  static final public void getInputDihedral() throws ParseException {
    try {
      AssiListDihedral();

    } catch (ParserDoneException e) {
        if (getNextToken().kind == EOF) {
            ;
        } else {
            System.out.println("Caught ParserDone");
        }
    }
  }

  static final public void getInputDistance() throws ParseException {
    try {
      AssiListDistance();

    } catch (ParserDoneException e) {
        if (getNextToken().kind == EOF) {
            ;
        } else {
            System.out.println("Caught ParserDone");
        }
    }
  }

  static final public void AssiListDistance() throws ParseException, ParserDoneException {
    AssiStateDistance();
    AssiListDistance();

  }

  static final public void AssiListDihedral() throws ParseException, ParserDoneException {
    AssiStateDihedral();
    AssiListDihedral();

  }

  static final public void AssiStateDistance() throws ParseException, ParserDoneException {
    Token t, bogus;
    String d1, d2, dminus, dplus;
    ArrayList select1, select2;
    ArrayList select_list = null;
    Token assiStart = getToken(1);
    lastAssiLine = assiStart.beginLine;
    lastAssiCol = assiStart.beginColumn;
    assiStart = null;
    bogus = null;
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NUMBER:
        select1 = Selection();
        select2 = Selection();
        d1 = Number();
        d2 = Number();
        dminus = Number();
        dplus = Number();
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case WORD:
          bogus = jj_consume_token(WORD);
          break;
        default:
          jj_la1[0] = jj_gen;
          ;
        }
                Utils.saveComment(bogus,cmtStack);
                Utils.saveAssiStateDistance( stack, select1, select2, dminus, dplus, select_list );
        break;
      default:
        jj_la1[1] = jj_gen;
        error_skipto(NUMBER);
      }
    } catch (ParseException e) {
        error_skipto(NUMBER);
    }
  }

  static final public void AssiStateDihedral() throws ParseException, ParserDoneException {
    Token bogus;
    String c1, c2;
    String angle_low, angle_high;
    ArrayList select1, select2, select3, select4;
    Token assiStart = getToken(1);
    lastAssiLine = assiStart.beginLine;
    lastAssiCol = assiStart.beginColumn;
    assiStart = null;
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case NUMBER:
        select1 = Selection();
        select2 = SelectionSimple();
        // just residue number and atom name 
                    select3 = SelectionSimple();
        select4 = SelectionSimple();
        c1 = Number();
        c2 = Number();
        angle_low = Number();
        angle_high = Number();
        bogus = jj_consume_token(WORD);
                Utils.saveComment(bogus,cmtStack);
                // duplicate segi from 1 assumed.
                AtomNode atomnode = (AtomNode) select1.get(0);
                String segi = atomnode.info.getValue( "segi" );
                atomnode = (AtomNode) select2.get(0); atomnode.info.putValue("segi", segi);
                atomnode = (AtomNode) select3.get(0); atomnode.info.putValue("segi", segi);
                atomnode = (AtomNode) select4.get(0); atomnode.info.putValue("segi", segi);
                Utils.saveAssiStateDihedral( stack, select1, select2, select3, select4,
                    angle_low, angle_high  );
        break;
      default:
        jj_la1[2] = jj_gen;
        error_skipto(NUMBER);
      }
    } catch (ParseException e) {
        error_skipto(NUMBER);
    }
  }

/** "Selection" using Emboss syntax */
  static final public ArrayList Selection() throws ParseException {
    String t0, t1, t2, t3;
    ArrayList sel;
    t3 = null;
    t0 = Number();
    t1 = Number();
    t2 = Word();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case WORD:
      t3 = Word();
      break;
    default:
      jj_la1[3] = jj_gen;
      ;
    }
        sel = new ArrayList();
        AtomNode atomnode = new AtomNode();
        atomnode.info.putValue("segi", t0);
        atomnode.info.putValue("resi", t1);
// The confusion here is because of how to let javacc
// do non-greedy matching.
        if ( t3 != null ) {
            atomnode.info.putValue("resn", t2);
            atomnode.info.putValue("name", t3);
        } else {
            atomnode.info.putValue("resn", t3);
            atomnode.info.putValue("name", t2);
        }

        sel.add( atomnode );
        //System.out.println("Found arraylist for Selection:" + AtomNode.toString( sel ) );
        {if (true) return sel;}
    throw new Error("Missing return statement in function");
  }

/** "Selection" just residue number and atom name using Emboss syntax */
  static final public ArrayList SelectionSimple() throws ParseException {
    String t1, t2;
    ArrayList sel;
    t1 = Number();
    t2 = Word();
        sel = new ArrayList();
        AtomNode atomnode = new AtomNode();
        atomnode.info.putValue("segi", Wattos.Utils.NmrStar.STAR_EMPTY); // overriden later.
        atomnode.info.putValue("resi", t1);
        atomnode.info.putValue("resn", Wattos.Utils.NmrStar.STAR_EMPTY);
        atomnode.info.putValue("name", t2);

        sel.add( atomnode );
        //System.out.println("Found arraylist for simple Selection:" + AtomNode.toString( sel ) );
        {if (true) return sel;}
    throw new Error("Missing return statement in function");
  }

  static final public String Number() throws ParseException {
    Token t;
    t = jj_consume_token(NUMBER);
        Utils.saveComment(t,cmtStack);
        {if (true) return t.image;}
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
  static public EmbossParserAllTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  static public Token token, jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[4];
  static private int[] jj_la1_0;
  static {
      jj_la1_0();
   }
   private static void jj_la1_0() {
      jj_la1_0 = new int[] {0x4000,0x2000,0x2000,0x4000,};
   }

  public EmbossParserAll(java.io.InputStream stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  You must");
      System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new EmbossParserAllTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  static public void ReInit(java.io.InputStream stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  public EmbossParserAll(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  You must");
      System.out.println("       either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new EmbossParserAllTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  public EmbossParserAll(EmbossParserAllTokenManager tm) {
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
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  public void ReInit(EmbossParserAllTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  static final private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
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
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static final private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.Vector jj_expentries = new java.util.Vector();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  static public ParseException generateParseException() {
    jj_expentries.removeAllElements();
    boolean[] la1tokens = new boolean[18];
    for (int i = 0; i < 18; i++) {
      la1tokens[i] = false;
    }
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 4; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 18; i++) {
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
