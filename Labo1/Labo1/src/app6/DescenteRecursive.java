package app6;

/** @author Ahmed Khoumsi */

import static app6.AnalLex.OPERATORS_NO_P;
import static app6.AnalLex.Type;

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {

  // Attributs
  private String inDocument;
  ElemAST racine;
  AnalLex lexical;


/** Constructeur de DescenteRecursive :
      - recoit en argument le nom du fichier contenant l'expression a analyser
      - pour l'initalisation d'attribut(s)
 */
public DescenteRecursive(String in) {
  inDocument = in;
  racine = null;
}


/** AnalSynt() effectue l'analyse syntaxique et construit l'AST.
 *    Elle retourne une reference sur la racine de l'AST construit
 */
public ElemAST AnalSynt() {
  lexical = new AnalLex(new Reader(inDocument).toString());
  partieE();
  return racine;
}



// Methode pour chaque symbole non-terminal de la grammaire retenue
private Terminal partieE() {
  Terminal currentTerminal = readNext(lexical); // T
  ElemAST right = partieT(currentTerminal);

  Terminal op = readNext(lexical); // try to read + or -
  ErreurSynt(currentTerminal, op);

  while (op != null && (op.chaine.equals("+") || op.chaine.equals("-"))) {
    Terminal t2 = readNext(lexical); // next value
    ElemAST left = partieT(t2);

    NoeudAST node = new NoeudAST(op.chaine);
    node.elemASTLeft = left;
    node.elemASTRight = right;
    right = node;

    op = readNext(lexical);
  }

  // op is not + or -, bring back pointer
  lexical.pushBack(op);

  racine = right;
  return op;
}


  private ElemAST partieT(Terminal first) {
    ElemAST right = partieF(first);

    Terminal op = readNext(lexical); // read possible * or /
    ErreurSynt(first, op);

    while (op != null && (op.chaine.equals("*") || op.chaine.equals("/"))) {
      Terminal next = readNext(lexical); // next operand
      ElemAST left = partieF(next);

      NoeudAST node = new NoeudAST(op.chaine);
      node.elemASTLeft = left;
      node.elemASTRight = right;
      right = node;

      op = readNext(lexical); // read another * or /
    }

    // op is not * or /, go back to partieE
    lexical.pushBack(op);

    return right;
  }


  private ElemAST partieF(Terminal current) {
    if (current.chaine.equals(")")) {
      partieE(); // parse inside the parentheses
      Terminal closing = readNext(lexical); // consume ')'
      if (!closing.chaine.equals("(")) {
        ErreurSynt("Il manque une fermeture de parenthese");
      }
      return racine;
    } else if (Character.isLetterOrDigit(current.chaine.charAt(0))) {
      return new FeuilleAST(current.chaine); // id
    }

    return null;
  }

  private Terminal readNext(AnalLex lexical){
    Terminal t = lexical.prochainTerminal();
    if(!t.chaine.isEmpty())
      System.out.println(t.chaine + "\t" + Type(t.chaine) + "\n");

    return t;
  }

/** ErreurSynt() envoie un message d'erreur syntaxique
 */
  public void ErreurSynt(String s)
  {
      System.out.println("Erreur : " + s);
      System.exit(1);
  }

  public void ErreurSynt(Terminal current, Terminal nextOne)
  {
    if( current.chaine.isEmpty() || nextOne.chaine.isEmpty() )
      return;

    char c = current.chaine.charAt(0);
    char c2 = nextOne.chaine.charAt(0);

    if (OPERATORS_NO_P.contains(c) && OPERATORS_NO_P.contains(c2)) {
      System.out.println("Erreur de syntaxe, plusieurs operateur on ete mis un apres l'autre: " + current.chaine);
      System.exit(1);
    }
  }



  //Methode principale a lancer pour tester l'analyseur syntaxique 
  public static void main(String[] args) {
    String toWriteLect = "";
    String toWriteEval = "";

    System.out.println("Debut d'analyse syntaxique");
    if (args.length == 0){
      args = new String [2];
      args[0] = "ExpArith.txt";
      args[1] = "ResultatSyntaxique.txt";
    }

    DescenteRecursive dr = new DescenteRecursive(args[0]);

    try {
      ElemAST RacineAST = dr.AnalSynt();
      toWriteLect += "Lecture de l'AST trouve affichage :\n" + RacineAST.LectAST("") + "\n";
      toWriteLect += "Lecture de l'AST trouve : " + RacineAST.LectAST() + "\n";
      toWriteLect += "Postfix de l'AST trouve : " + RacineAST.ASTPostfix() + "\n";
      System.out.println(toWriteLect);
      toWriteEval += "Evaluation de l'AST trouve : " + RacineAST.EvalAST() + "\n";
      System.out.println(toWriteEval);
      Writer w = new Writer(args[1],toWriteLect+toWriteEval); // Ecriture de toWrite 
                                                              // dans fichier args[1]
    } catch (Exception e) {
      System.out.println(e);
      e.printStackTrace();
      System.exit(51);
    }
    System.out.println("Analyse syntaxique terminee");
  }

}

