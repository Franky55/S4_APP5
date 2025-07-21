package app6;

/** @author Ahmed Khoumsi */

import java.util.Objects;

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
public ElemAST AnalSynt( ) {
  Reader r = new Reader(inDocument);
  lexical = new AnalLex(r.toString());
  Terminal t = null;

  ElemAST nextStartPoint;
  while(lexical.resteTerminal()){
    racine = makingTree(racine);
  }

  return racine;
}

  private Terminal readNext(AnalLex lexical){
    Terminal t = lexical.prochainTerminal();
    System.out.println(t.chaine + "\n");

    return t;
  }

  private ElemAST makingTree(ElemAST elemAST) {
    Terminal currentTerminal = readNext(lexical);
    Terminal aheadTerminal = null;
    ElemAST previousElemAST = elemAST;

    if(!AnalLex.OPERATORS.contains(currentTerminal.chaine.charAt(0))) {

      if(lexical.resteTerminal()){
         aheadTerminal = readNext(lexical);
      }

      if(lexical.resteTerminal() && AnalLex.OPERATORS_P_M.contains(Objects.requireNonNull(aheadTerminal).chaine.charAt(0))){       // operator +-
        if (previousElemAST != null) {
          elemAST = new NoeudAST(aheadTerminal.chaine);                           // Placing the first element
          ((NoeudAST) elemAST).elemASTLeft = previousElemAST;
          ((NoeudAST) previousElemAST).elemASTRight = makeNewElemAST(currentTerminal);;
        }
        else {
          elemAST = new NoeudAST(aheadTerminal.chaine);                           // Placing the first element
          ((NoeudAST) elemAST).elemASTLeft = makeNewElemAST(currentTerminal);     // Placing the 2nd element
        }
        return elemAST;
      }
      else if(lexical.resteTerminal() && AnalLex.OPERATORS_M_D.contains(Objects.requireNonNull(aheadTerminal).chaine.charAt(0))) { // operator */
        elemAST = new NoeudAST(aheadTerminal.chaine);                           // Placing the first element
        ((NoeudAST) elemAST).elemASTLeft = makeNewElemAST(currentTerminal);    // Placing the 2nd element
        ((NoeudAST) elemAST).elemASTRight = makingTree(elemAST);                   // placing the  element bottom right
        ((NoeudAST) previousElemAST).elemASTRight = elemAST;
        //Call function again to put right leaf
        return previousElemAST;
      }
      else {
        elemAST = makeNewElemAST(currentTerminal);
        ((NoeudAST) previousElemAST).elemASTRight = elemAST;
        return previousElemAST;
      }
    }

    return previousElemAST;
  }

  private ElemAST makeNewElemAST(Terminal t){
    if(AnalLex.OPERATORS.contains(t.chaine.charAt(0)))
      return new NoeudAST(t.chaine);
    return new FeuilleAST(t.chaine);
  }

// Methode pour chaque symbole non-terminal de la grammaire retenue


/** ErreurSynt() envoie un message d'erreur syntaxique
 */
public void ErreurSynt(String s)
{
    // trust
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
      toWriteLect += "Lecture de l'AST trouve : " + RacineAST.LectAST() + "\n";
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

