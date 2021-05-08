//Version 8/05/21
import java.util.Scanner;
public class Joueur{
    public byte [][] bateaux;
    public byte [][] coupsPrecedents;
    public byte [] nbreCasesBateau = {2, 3, 3, 4, 5};
    public byte nbBateaux = 5;
    private byte ligne;
    private byte colonne;
    private byte caseL;
    private byte caseC;
    private byte touche;
    
    Scanner sc = new Scanner(System.in);
    
    
    public Joueur() {
        this.bateaux = new byte [10][10];//chaque case va prendre une valeur entre 0 et 5 pour indiquer s'il n'y a rien ou le numéro du bateau
        this.coupsPrecedents = new byte [10][10];
        //
        //Mettre le placement des bateaux ici 
    }
    
    public Joueur(boolean ordiJoue) {
        this.bateaux = new byte [10][10];//chaque case va prendre une valeur entre 0 et 5 pour indiquer s'il n'y a rien ou le numéro du bateau
        //
        //Mettre le placement des bateaux ici (aléatoire)
        /*si joue : créer coups précédents*/
        //this.coupsPrecedents = new byte [10][10]; inutile 
    }
    
    /*public void PlacementBateaux(Bateau b1 , int numero){
        for(int i = x1 ; i<x2 ; i++){//remplacer x1, x2 ... par x1.b1() par exemple
            for(int j = y1 ; j<y2 ; j++){
                bateaux[i][j] = numero;
            }
        }
        
    }*/
    
    
    public void Attaque(Joueur cible){
        
        //Choix de l'attaque
        
        do{
            
            //choix type attaque
            do{
                System.out.print("Choisissez la ligne à attaquer ");
                this.ligne = sc.nextByte();
            }while( (this.ligne<1) || (this.ligne>this.bateaux.length) );
            this.ligne-=1;
            
            do{
                System.out.print("Choisissez la colonne à attaquer ");
                this.colonne = sc.nextByte();
            }while( (this.colonne<1) || (this.colonne>this.bateaux[0].length) );
            this.colonne -=1;
        }while(this.coupsPrecedents[this.ligne][this.colonne] != 0);
        
        this.caseL = this.ligne;
        this.caseC = this.colonne;
        System.out.println("Vous attaquez la case " + (this.ligne+1) + " " + (this.colonne+1));
        this.AttaqueCase(cible); //distinction pour si nouvelles attaques : gestion de l'attaque case par case
    }
        
    
    
    public void AttaqueCase(Joueur cible){    
        
        //Gestion de l'attaque
        
        this.touche = cible.bateaux[this.caseL][this.caseC]; 
        
        if (this.touche ==0){
            System.out.println("Loupé");
            this.coupsPrecedents[this.caseL][this.caseC] = -1;
        }else{
            System.out.println("Touché");
            cible.nbreCasesBateau[touche-1] -=1;
            if (cible.nbreCasesBateau[touche-1] == 0){
                cible.nbBateaux -=1;
            }
            //cible.caseVie -=1;
            this.coupsPrecedents[this.caseL][this.caseC] = -2;
                
        }
        
        
    }
    
    
    
    
    
    
    
    
    
    /**
     * Méthode pour l'affichage des coups joués
     * 
     */
    public void afficheCoups(){
        System.out.print("      ");
        for(byte i = 0; i < this.coupsPrecedents[0].length ; i+=1){
            System.out.print(i+1 + "  ");
        }
        System.out.println("\n");
        
        for(int i = 0 ; i<this.coupsPrecedents.length ; i++){
            
            if (i+1<10){
                System.out.print(" ");
            }
            System.out.print(i+1 + "   ");
            
            for(int j = 0 ; j<this.coupsPrecedents[i].length ; j++){
                
                if(this.coupsPrecedents[i][j] == 0){
                    System.out.print(" . ");
                }else if (this.coupsPrecedents[i][j] == -1){// || matrice[i][j] == 1 || matrice[i][j] == 2 || matrice[i][j] == 3 || matrice[i][j] == 4){
                    System.out.print(" X ");
                }else if (this.coupsPrecedents[i][j] == -2){
                    System.out.print(" O ");
                }else{
                    System.out.print(" E ");
                }
                
            }
            System.out.println();
        }
    }
    
    public boolean fini(){
        return !(nbBateaux==0);
    }
    
    
}


//Version avant 8/05/21
/*public class Joueur{
    public int [][] grille;
    public int [][] coupsPrecedents;
    public int [] nbreCasesBateau = {2, 3, 3, 4, 5};
    
    public joueur() {
        this.grille = new int [10][10];//chaque case va prendre une valeur entre -1 et 4 pour indiquer s'il n'y a rien ou le numéro du bateau
        this.coupsPrecedents = new int [10][10];
        //this.nbreCasesBateau = {2, 3, 3, 4, 5};//nombre de cases par bateaux vivantes au début, affectation selon les règles classiques du jeu
    }
    
    public void PlacementBateaux(Bateau b1 , int numero){
        for(int i = x1 ; i<x2 ; i++){//remplacer x1, x2 ... par x1.b1() par exemple
            for(int j = y1 ; j<y2 ; j++){
                grille[i][j] = numero;
            }
        }
        
    }
    
    public void AttaqueAdverse(Joueur j2 , int x , int y){//attaque du joueur adverse j2
        boolean trouve = false;
        int i = 0;
        while(!trouve && i<=nbreCasesBateau.length){//vérification pour chaque numéro de bateau
            if(grille[x][y]==i){
                trouve = true;
                grille[x][y] = -1;//la cellule de la grille devient morte 
                if(i == 0){
                    System.out.println("Loupé");
                    j2.coupsPrecedents[x][y] = 0;//0 pour affichage du tableau la méthode verra des bateaux 0 partout et donc affichera des croix pour indiquer une frappe à cet endroit
                }else{
                    System.out.println("Touché");
                    nbreCasesBateau[i]--;
                    j2.coupsPrecedents[x][y] = -2;//valeur différente pour la méthode affichage
                }
            }
            i++;
        }
        if(nbreCasesBateau[i-1]==0){
            System.out.println("Coulé");
        }
        /*if (grille[x][y]==0){
            grille[x][y] = 0;
            System.out.println("Loupé");
        }else if (grille[x][y]==1){
            
        }*/
    }
    
   /* public void initialiseMatrices(){//à faire marcher au début du code dans le main après création des classes des 2 joueurs
        for(int i = 0 ; i<grille.length ; i++){
            for(int j = 0 ; j<grille[i].length ; j++){
                grille[i][j] = -1;
            }
        }
        for(int i = 0 ; i<coupsPrecedents.length ; i++){
            for(int j = 0 ; j<coupsPrecedents[i].length ; j++){
                coupsPrecedents[i][j] = -1;
            }
        }
    }
    
    /*public void enregistreCoup(int x , int y){
        if(grille[x][y] = 0){
        coupsPrecedents[x][y] = 1; //
        }else{
            coupsPrecedents[x][y] = 2;//on a touché qq chose
        }
    }*/ //pas utile car on l'a déjà dans la méthode attaqueAdverse
    
  /*  public void afficheGrille(int [][] matrice){
        System.out.print("     ");
        for(byte i = 0; i < matrice[0].length ; i+=1){
            System.out.print(i+1 + "  ");
        }
        System.out.println("\n");
        if (i+1<10){
                System.out.print(" ");
            }
            System.out.print(i+1 + "   ");
            
        for(int i = 0 ; i<matrice.length ; i++){
            for(int j = 0 ; j<matrice[i].length ; j++){
                if(matrice[i][j] == -1){
                    System.out.print(" . ");
                }else if (matrice[i][j] == 0 || matrice[i][j] == 1 || matrice[i][j] == 2 || matrice[i][j] == 3 || matrice[i][j] == 4){
                    System.out.print(" X ");
                }else{
                    System.out.print(" O ");
                }
                
            }
            System.out.println();
        }
    }
    
    public boolean partieFinie(){//méthode pour savoir si la partie est finie
        int somme = 0;
        for(int i =0 ; i<nbreCasesBateau.length ; i++){
            if(nbreCasesBateau[i] = 0){
                somme++;
            }
        }
        if(somme == 5){
            return true;
        }else{
            return false;
        }
    }
}
*/
