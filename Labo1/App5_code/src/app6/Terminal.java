package app6;

/** @author Ahmed Khoumsi */

/** Cette classe identifie les terminaux reconnus et retournes par
 *  l'analyseur lexical
 */
public class Terminal {
  public String chaine;

/** Un ou deux constructeurs (ou plus, si vous voulez)
  *   pour l'initalisation d'attributs 
 */	
  public Terminal() {   // arguments possibles
    chaine = "";
  }

  public Terminal(String _info) {   // arguments possibles
    chaine = _info;
  }

}
