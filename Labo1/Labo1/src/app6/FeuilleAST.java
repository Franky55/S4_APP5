package app6;

/** @author Ahmed Khoumsi */

/** Classe representant une feuille d'AST
 */
public class FeuilleAST extends ElemAST {

  // Attribut(s)
  private String expression;


/**Constructeur pour l'initialisation d'attribut(s)
 */
  public FeuilleAST(String _expression) {  // avec arguments
    expression = _expression;
  }


  /** Evaluation de feuille d'AST
   * May throw error
   */
  public int EvalAST( ) {
      return Integer.parseInt(expression);
  }


 /** Lecture de chaine de caracteres correspondant a la feuille d'AST
  */
  public String LectAST( ) {
      return expression;
  }

}
