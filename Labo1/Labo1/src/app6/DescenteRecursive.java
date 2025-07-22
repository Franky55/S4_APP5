package app6;

/** @author Ahmed Khoumsi */

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
  Terminal t = readNext(lexical);

  while(lexical.resteTerminal()){
    t = partieE(t);
  }
  partieE(t);

  return racine;
}


// Methode pour chaque symbole non-terminal de la grammaire retenue
  private Terminal partieE(Terminal current)
  {
    Terminal nextOne = readNext(lexical);

    if(nextOne.chaine.isEmpty() || AnalLex.OPERATORS_P_M.contains(nextOne.chaine.charAt(0)))
    {
      ElemAST elemAST = makeNewElemAST(current);
      if(racine == null)
      {
        racine = elemAST;
      }
      else {
        ((NoeudAST) racine).elemASTLeft = elemAST;
      }
    }
    else if(nextOne.chaine.isEmpty() || AnalLex.OPERATORS_M_D.contains(nextOne.chaine.charAt(0)))
    {
      ElemAST elemAST = makeNewElemAST(nextOne);

      if(racine == null)
      {
        racine = elemAST;
        ((NoeudAST) racine).elemASTRight = makeNewElemAST(current);
      }

      ((NoeudAST) racine).elemASTLeft = partieT(null, current, nextOne);
    }
    else if(!current.chaine.isEmpty() && AnalLex.OPERATORS_P_M.contains(current.chaine.charAt(0)))
    {
      ElemAST elemAST = makeNewElemAST(current);
      if(elemAST instanceof NoeudAST noeud) {
        noeud.elemASTRight = racine;
        racine = noeud;
      }
    }

    return nextOne;
  }

  private ElemAST partieT(ElemAST leftOperand, Terminal current, Terminal operator) {
    NoeudAST opNode = (NoeudAST) makeNewElemAST(operator);
    ElemAST rightOperand = makeNewElemAST(current);
    opNode.elemASTRight = rightOperand;

    // Read the next token (could be a number or another operator)
    Terminal nextToken = readNext(lexical);

    if (!nextToken.chaine.isEmpty() && AnalLex.OPERATORS_M_D.contains(nextToken.chaine.charAt(0))) {
      // Recursively process deeper
      Terminal nextOperand = readNext(lexical);
      ElemAST leftBranch = partieT(leftOperand, nextOperand, nextToken);
      opNode.elemASTLeft = leftBranch;
    } else if (!nextToken.chaine.isEmpty()) {
      // It must be a number
      opNode.elemASTLeft = makeNewElemAST(nextToken);
    }

    return opNode;
  }

  private void partieF()
  {

  }


  private ElemAST makeNewElemAST(Terminal t){
    if(AnalLex.OPERATORS.contains(t.chaine.charAt(0)))
      return new NoeudAST(t.chaine);
    return new FeuilleAST(t.chaine);
  }

  private Terminal readNext(AnalLex lexical){
    Terminal t = lexical.prochainTerminal();
    System.out.println(t.chaine + "\n");

    return t;
  }

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

