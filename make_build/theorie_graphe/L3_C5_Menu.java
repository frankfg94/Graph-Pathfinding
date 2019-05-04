/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package theorie_graphe;

/**
 *
 * @author franc
 */
public class L3_C5_Menu 
{
   public void start()
   {
       System.out.println(System.lineSeparator()+System.lineSeparator() + "/////////////// Choisissez vos options /////////////////////"+ System.lineSeparator() + System.lineSeparator());
       System.out.println("Graphe Actuel : Aucun");
       System.out.println(System.lineSeparator() + System.lineSeparator()+ "////////////////////////////////////////////////////////////");
       askMenuOption();
   }
   
   public void askMenuOption()
   {
       System.out.println("1) Afficher la liste des graphes :");
       System.out.println("2) Charger un graphe en particulier :");
       System.out.println("3) Quitter");
   }
}
