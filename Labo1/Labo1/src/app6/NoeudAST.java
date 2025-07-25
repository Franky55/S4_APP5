package app6;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

  // Attributs
  public ElemAST left;
  public ElemAST right;

  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(Terminal terminal) {
    super(terminal);
	right = null;
	left = null;
  }

  public NoeudAST(Terminal terminal, ElemAST left, ElemAST right) {
      super(terminal);
      this.left = left;
      this.right = right;
  }

 
  /** Evaluation de noeud d'AST
   */
  public int EvalAST( ) {

	  return switch (terminal.chaine) {
		  case "+" -> left.EvalAST() + right.EvalAST();
		  case "-" -> left.EvalAST() - right.EvalAST();
		  case "*" -> left.EvalAST() * right.EvalAST();
		  case "/" -> left.EvalAST() / right.EvalAST();
		  default -> 0;
	  };

  }


  /** Lecture de noeud d'AST
   */
  public String LectAST(String prefix) {
      return prefix + terminal.chaine + "\n" + left.LectAST(prefix + "\t") + right.LectAST(prefix + "\t");
      //return " " + left.LectAST() + " " + terminal.chaine + " " + right.LectAST() + " ";
  }

}


