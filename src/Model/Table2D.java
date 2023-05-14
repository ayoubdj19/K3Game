package Model;

import javax.swing.*;

public class Table2D {
    private Pion[][] cases;
    // bag containes the number of colors of each pion

    // colors of pions
    ImageIcon BEIGE = new ImageIcon("../sources/Images/BEIGE.png");
    ImageIcon BLANC = new ImageIcon("../sources/Images/BLANC.png");
    ImageIcon BLEU = new ImageIcon("../sources/Images/BLEU.png");
    ImageIcon JAUNE = new ImageIcon("../sources/Images/JAUNE.png");
    ImageIcon NOIR = new ImageIcon("../sources/Images/NOIR.png");
    ImageIcon ROUGE = new ImageIcon("../sources/Images/ROUGE.png");
    ImageIcon VERT = new ImageIcon("../sources/Images/VERT.png");

    public Table2D(int hight, int width, int[] bag) {
        if (hight >1){

            this.cases = new Pion[hight][width];
            this.initTable2D(bag);
        }else {
            this.cases = new Pion [hight][width];
            this.initTable1D(width,bag);
        }
    }


    public Pion getPion(int x, int y) {
        return this.cases[x][y];
    }

    public boolean estVide(int x, int y) {
        return this.getPion(x, y) == null;
    }

    public void ajouterPion(Pion pion) {
        int x = pion.getX();
        int y = pion.getY();
        this.cases[x][y] = pion;
    }
    public void retirerPion(Pion pion) {
        int x = pion.getX();
        int y = pion.getY();
        this.cases[x][y] = null;
    }

    public int getHeight() {
        return this.cases.length;
    }

    public int getWidth() {
        return this.cases[0].length;
    }

    public Pion[][] getCases() {
        return this.cases;
    }

    public Pion getCases(int x, int y) {
        return this.cases[x][y];
    }
    public void setCases(Pion pion, int x, int y) {
        this.cases[x][y] = pion;
    }


    public CouleurPion RandomPions(int bag[]) { // Randomly choose a color of pion
        int random = (int) (Math.random() * 5);
        while (bag[random] == 0) {
            random = (int) (Math.random() * 5);
        }
        bag[random]--;
        switch (random) {
            case 0:
                return CouleurPion.ROUGE;
            case 1:
                return CouleurPion.BLEU;
            case 2:
                return CouleurPion.VERT;
            case 3:
                return CouleurPion.JAUNE;
            case 4:
                return CouleurPion.NOIR;
        }
        return null;
    }

    public Table2D initTable2D(int bag[]) {
        CouleurPion couleurPion;
        for (int i = 0; i < this.getHeight(); i++) {
            for (int j = 0; j < this.getWidth(); j++) {
                if (i == 0 && j == 0) {
                    this.setCases(new Pion(CouleurPion.BLANC, Pion.TypePion.COLORED, BLANC, i, j), i, j);
                } else if (i == 0 && j == 1) {
                    this.setCases(new Pion(CouleurPion.BEIGE, Pion.TypePion.COLORED, BEIGE, i, j), i, j);
                } else if (i == 0 && j == 2) {
                    this.setCases(new Pion(CouleurPion.BEIGE, Pion.TypePion.COLORED, BEIGE, i, j), i, j);
                } else {
                    //randomly generate the pions
                    if (i > 1 && j == this.getWidth()) {
                        return this;
                    } else {
                        couleurPion = RandomPions(bag);
                        switch (couleurPion) {
                            case ROUGE:
                                this.setCases(new Pion(couleurPion, Pion.TypePion.COLORED, ROUGE, i, j), i, j);
                                break;
                            case BLEU:
                                this.setCases(new Pion(couleurPion, Pion.TypePion.COLORED, BLEU, i, j), i, j);
                                break;
                            case VERT:
                                this.setCases(new Pion(couleurPion, Pion.TypePion.COLORED, VERT, i, j), i, j);
                                break;
                            case JAUNE:
                                this.setCases(new Pion(couleurPion, Pion.TypePion.COLORED, JAUNE, i, j), i, j);
                                break;
                            case NOIR:
                                this.setCases(new Pion(couleurPion, Pion.TypePion.COLORED, NOIR, i, j), i, j);
                                break;
                        }
                    }
                }
            }
        }
        return this;
    }


    public Table2D initTable1D(int width,int bag[]) {//K3 base
        CouleurPion couleurPion;
        for (int i = 0; i < width; i++) {
            couleurPion = RandomPions(bag);
            switch (couleurPion) {
                case ROUGE:
                    this.cases[0][i] = new Pion(couleurPion, Pion.TypePion.COLORED, ROUGE, 0, i);
                    break;
                case BLEU:
                    this.cases[0][i] = new Pion(couleurPion, Pion.TypePion.COLORED, BLEU, 0, i);
                    break;
                case VERT:
                    this.cases[0][i] = new Pion(couleurPion, Pion.TypePion.COLORED, VERT, 0, i);
                    break;
                case JAUNE:
                    this.cases[0][i] = new Pion(couleurPion, Pion.TypePion.COLORED, JAUNE, 0, i);
                    break;
                case NOIR:
                    this.cases[0][i] = new Pion(couleurPion, Pion.TypePion.COLORED, NOIR, 0, i);
                    break;
            }
        }
        return this;
    }






}