package GameBuilder;
import java.awt.*;
import java.util.Random;
import Model.Pion;
import Model.CouleurPion;
import Model.Table2D;

import javax.swing.*;

public class Table2DBuilder {
    private final int hight =2;
    private final int width =8;
    private Table2D table2D;


    ImageIcon BEIGE = new ImageIcon("sources/Images/BEIGE.png");
    ImageIcon BLANC = new ImageIcon("sources/Images/BLANC.png");
    ImageIcon BLEU = new ImageIcon("sources/Images/BLEU.png");
    ImageIcon JAUNE = new ImageIcon("sources/Images/JAUNE.png");
    ImageIcon NOIR = new ImageIcon("sources/Images/NOIR.png");
    ImageIcon ROUGE = new ImageIcon("sources/Images/ROUGE.png");
    ImageIcon VERT = new ImageIcon("sources/Images/VERT.png");
    ImageIcon VIDE = new ImageIcon("sources/Images/VIDE.png");

    public Table2DBuilder() {
        return;
    }


    public Table2D getTable2D() {
        return this.table2D;
    }


    public void RandomPions() {
        Random random = new Random();
        //random color from enum
        CouleurPion[] couleurs = CouleurPion.values();
        int k = 0;
        for (int i = 0; i < this.hight; i++) {
            for (int j = 0; j < this.width; j++) {
                k++;
                int randomIndex = random.nextInt(couleurs.length);
                CouleurPion couleur = couleurs[randomIndex];
                //switch case to get the right image
                if (k == 16) {
                    this.table2D.setCases(new Pion(null, Pion.TypePion.VIDE, VIDE, i, j,1), i, j);
                    //set pion inaccessible
                    Pion pion = table2D.getPion(i, j);
                    pion.setAccessible(false);
                    break;
                }
                switch (couleur) {
                    case BEIGE:
                        this.table2D.setCases(new Pion(couleur, Pion.TypePion.COLORED, BEIGE, i, j,1), i, j);
                        break;
                    case BLANC:
                        this.table2D.setCases(new Pion(couleur, Pion.TypePion.COLORED, BLANC, i, j,1), i, j);
                        break;
                    case BLEU:
                        this.table2D.setCases(new Pion(couleur, Pion.TypePion.COLORED, BLEU, i, j,1), i, j);
                        break;
                    case JAUNE:
                        this.table2D.setCases(new Pion(couleur, Pion.TypePion.COLORED, JAUNE, i, j,1), i, j);
                        break;
                    case NOIR:
                        this.table2D.setCases(new Pion(couleur, Pion.TypePion.COLORED, NOIR, i, j,1), i, j);
                        break;
                    case ROUGE:
                        this.table2D.setCases(new Pion(couleur, Pion.TypePion.COLORED, ROUGE, i, j,1), i, j);
                        break;
                    case VERT:
                        this.table2D.setCases(new Pion(couleur, Pion.TypePion.COLORED, VERT, i, j,1), i, j);
                        break;
                }
            }
        }
    }
}

