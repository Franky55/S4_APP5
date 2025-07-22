package app6;

/** @author Ahmed Khoumsi */

import java.util.Set;

/** Cette classe effectue l'analyse lexicale
 */
public class AnalLex {

// Attributs
  private String expression;
  private int expressionLength;
  private int currentPosition;

  public static final Set<Character> OPERATORS = Set.of('+', '-', '*', '/', '(', ')');
  public static final Set<Character> OPERATORS_NO_P = Set.of('+', '-', '*', '/');
  private Terminal lastTerminal = null;


/** Constructeur pour l'initialisation d'attribut(s)
 */
  public AnalLex(String string) {
    expression = removeSpaces(string);
    expressionLength = string.length();
    currentPosition = expressionLength-1;
  }

  public String removeSpaces(String s) {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (!Character.isWhitespace(c)) {
        sb.append(c);
      }
    }
    return sb.toString();
  }


/** resteTerminal() retourne :
      false  si tous les terminaux de l'expression arithmetique ont ete retournes
      true s'il reste encore au moins un terminal qui n'a pas ete retourne 
 */
  public boolean resteTerminal( ) {
      return currentPosition >= 0;
  }
  
  
/** prochainTerminal() retourne le prochain terminal
      Cette methode est une implementation d'un AEF
 */  
  public Terminal prochainTerminal( ) {
    if (lastTerminal != null) {
      Terminal temp = lastTerminal;
      lastTerminal = null;
      return temp;
    }

    StringBuilder temp = new StringBuilder();

    for (int i = currentPosition; i >= 0; i--, currentPosition--) {
      char c = expression.charAt(i);

      if (OPERATORS.contains(c)) {
        if (temp.isEmpty()) {
          temp.insert(0, c);
          currentPosition--;
        }
        return new Terminal(temp.toString());
      } else {
        temp.insert(0, c);
      }
    }

    ErreurLex(temp.toString());
    return new Terminal(temp.toString());
  }

  public void pushBack(Terminal t) {
    this.lastTerminal = t;
  }

  /** ErreurLex() envoie un message d'erreur lexicale
 */
  public void ErreurLex(String s) {
    if (s == null || s.isEmpty())
      return;

    Character c1 =  s.charAt(0);
    Character c2 = null;
    boolean error = false;

    if (Character.isLetter(c1)) {
      if (Character.isLowerCase(c1)) error = true;
    }

    for (int i = 0; i < s.length(); i++) {
      c1 = expression.charAt(i);
      if (c2 != null) {
        if (c1 == '_' && c2 == '_') error = true;
      }
      c2 =  c1;
    }
    if (s.charAt(s.length() - 1) == '_') error = true;
    if (error) {
      System.out.println("Erreur : " + s);
      System.exit(1);
    }

  }

  
  //Methode principale a lancer pour tester l'analyseur lexical
  public static void main(String[] args) {
    String toWrite = "";
    System.out.println("Debut d'analyse lexicale");
    if (args.length == 0){
    args = new String [2];
            args[0] = "ExpArith.txt";
            args[1] = "ResultatLexical.txt";
    }
    Reader r = new Reader(args[0]);

    AnalLex lexical = new AnalLex(r.toString()); // Creation de l'analyseur lexical

    // Execution de l'analyseur lexical
    Terminal t = null;
    while(lexical.resteTerminal()){
      t = lexical.prochainTerminal();
      toWrite +=t.chaine + "\n" ;  // toWrite contient le resultat
    }				   //    d'analyse lexicale
    System.out.println(toWrite); 	// Ecriture de toWrite sur la console
    Writer w = new Writer(args[1],toWrite); // Ecriture de toWrite dans fichier args[1]
    System.out.println("Fin d'analyse lexicale");
  }
}
