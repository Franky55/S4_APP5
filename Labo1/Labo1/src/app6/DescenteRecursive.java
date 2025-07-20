package app6;

/** @author Ahmed Khoumsi */

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {

  // Attributs
  private String inDocument;
  ElemAST racine;


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
  AnalLex lexical = new AnalLex(r.toString());
  Terminal t = null;

  while(lexical.resteTerminal()){
    t = lexical.prochainTerminal();
    System.out.println(t.chaine + "\n");

    ElemAST elemAST;
    if(AnalLex.OPERATORS.contains(t.chaine.charAt(0))) {
      elemAST = new NoeudAST(t.chaine);
    }
    else {
      elemAST = new FeuilleAST(t.chaine);
    }

    if(racine == null)
    {
      racine = elemAST;
    }
    else if(elemAST instanceof NoeudAST noeud) {
      noeud.elemASTLeft = racine;
      racine = noeud;
    }
    else {
	    ((NoeudAST) racine).elemASTRight = elemAST;
    }
  }

  return racine;
}


// Methode pour chaque symbole non-terminal de la grammaire retenue
// ... 
// ...



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

