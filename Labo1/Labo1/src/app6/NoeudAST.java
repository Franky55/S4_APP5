package app6;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class NoeudAST extends ElemAST {

  // Attributs
  private String expression;

  public ElemAST elemASTLeft;
  public ElemAST elemASTRight;

  /** Constructeur pour l'initialisation d'attributs
   */
  public NoeudAST(String _expression) {
    expression = _expression;
	elemASTRight = null;
	elemASTLeft = null;
  }

 
  /** Evaluation de noeud d'AST
   */
  public int EvalAST( ) {

	  return switch (expression) {
		  case "+" -> elemASTLeft.EvalAST() + elemASTRight.EvalAST();
		  case "-" -> elemASTLeft.EvalAST() - elemASTRight.EvalAST();
		  case "*" -> elemASTLeft.EvalAST() * elemASTRight.EvalAST();
		  case "/" -> elemASTLeft.EvalAST() / elemASTRight.EvalAST();
		  default -> 0;
	  };

  }


  /** Lecture de noeud d'AST
   */
  public String LectAST( ) {
	  String left = (elemASTLeft != null) ? elemASTLeft.LectAST() : "null";
	  String right = (elemASTRight != null) ? elemASTRight.LectAST() : "null";
	  return "(" + left + " " + expression + " " + right + ")";
  }

}


