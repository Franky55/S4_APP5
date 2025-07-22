package app6;

/** @author Ahmed Khoumsi */

/** Classe Abstraite dont heriteront les classes FeuilleAST et NoeudAST
 */
public abstract class ElemAST {

  public Terminal terminal;

  public ElemAST(Terminal terminal) {
    this.terminal = terminal;
  }

  /** Evaluation d'AST
   */
  public abstract int EvalAST();


  /** Lecture d'AST
   */
  public abstract String LectAST(String prefix);


/** ErreurEvalAST() envoie un message d'erreur lors de la construction d'AST
 */  
  public void ErreurEvalAST(String s) {	
    System.out.println("Erreur element: " + s);

  }

}
