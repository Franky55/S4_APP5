package app6;

/** @author Ahmed Khoumsi */

import java.util.Set;

/** Cette classe effectue l'analyse syntaxique
 */
public class DescenteRecursive {

  // Attributs
  AnalLex lexical;
  ElemAST racine;
  ElemAST current;

  public static final Set<Character> SUM_OPERATORS = Set.of('+', '-');
  public static final Set<Character> MULTI_OPERATORS = Set.of('*', '/');

/** Constructeur de DescenteRecursive :
      - recoit en argument le nom du fichier contenant l'expression a analyser
      - pour l'initalisation d'attribut(s)
 */
public DescenteRecursive(String expression) {
  Reader reader = new Reader(expression);
  lexical = new AnalLex(reader.toString());
  racine = null;
  current = null;
}


/** AnalSynt() effectue l'analyse syntaxique et construit l'AST.
 *    Elle retourne une reference sur la racine de l'AST construit
 */
public ElemAST AnalSynt( ) {
  Terminal t1 = null, t2 = null;
    /*
    while(lexical.resteTerminal()){
      if (t1 == null) {
        t1 = lexical.prochainTerminal();
        t2 = lexical.prochainTerminal();
        lexical.currentPosition++;
        racine = new NoeudAST(t1);
        current = racine;
      } else {
        t1 = lexical.prochainTerminal();

        if (!lexical.resteTerminal()) t2 = new Terminal("+");
        else {
          t2 = lexical.prochainTerminal();
          lexical.currentPosition++;
        }
      }
      System.out.println(t1.chaine + "\n");

      if (isOperator(t2)) {
        if (isSumOperator(t2)) {
          current = new FeuilleAST(t1);
        }
        if (isMultiOperator(t2)) {
          current = new NoeudAST(t1);
        }
      } else {
        if (isSumOperator(t1)) {
          NoeudAST noeud = new NoeudAST(t1);
          noeud.elemASTRight = racine;
          racine = noeud;
          current = noeud.elemASTLeft;
        }
        if (isMultiOperator(t1)) {
          if (current instanceof NoeudAST noeud) {
            noeud.elemASTRight = new FeuilleAST(noeud.terminal);
            noeud.terminal = t1;
            current = noeud.elemASTLeft;
          }
        }
      }
    }
     */
  /*
  while (lexical.resteTerminal()) {
    if (t1 == null) {
      t1 = lexical.prochainTerminal();
      racine = new FeuilleAST(t1);
      current = racine;
    } else {
      t1 = lexical.prochainTerminal();
      if (isOperator(t1)) {
        if (isMultiOperator(t1)) {
          NoeudAST noeud = new NoeudAST(t1);
          noeud.right = current;
          noeud.left = new FeuilleAST(new Terminal("1"));
          current = noeud.right;
          if (racine == current) racine = noeud;
        }
      } else {
        current = new FeuilleAST(t1);
      }
    }
    System.out.println(t1.chaine);
  }
   */



  return racine;
}

public ElemAST recursive() {
  return null
}

public boolean isOperator(Terminal t) {
  if (t.chaine.length() == 1) {
      return SUM_OPERATORS.contains(t.chaine.charAt(0)) || MULTI_OPERATORS.contains(t.chaine.charAt(0));
  }
  return false;
}

public boolean isSumOperator(Terminal t) {
  return SUM_OPERATORS.contains(t.chaine.charAt(0));
}

public boolean isMultiOperator(Terminal t) {
  return MULTI_OPERATORS.contains(t.chaine.charAt(0));
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
      args[0] = "Allo.txt";
      args[1] = "SortieRecursive.txt";
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

