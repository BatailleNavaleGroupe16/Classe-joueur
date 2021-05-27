import java.util.Scanner;
public class Joueur{
    public byte [][] bateaux={
        {-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4},
        {-4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,-4},
        {-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4}
    };
        
        
        
        
    //public byte [][] bateaux;
    public byte type;
    public byte [][] coupsPrecedents;
    //public byte [][] coupsOrdi;
    public byte [] nbreCasesBateau = {2, 3, 3, 4, 5};
    //public byte nbBateaux = 5;
    //public byte totalCases = 17;
    public byte nbBateaux = 1;
    private byte ligne;
    private byte colonne;
    public byte caseL;
    public byte caseC;
    private byte touche;
    private Ordi ordi;
    Scanner sc = new Scanner(System.in);
    
    
    
    
    public Joueur(byte type) {
        
        this.type = type; 
        
        //1 pour un joueur humain qui cache ; 2 pour un humain qui cherche contre ordi ; 3 pour ordi qui cache juste ; 4 pour ordi qui joue
        /*
        1 jeu classique jvj ou contre ordi en mode attaque -> placement bateaux, tableau des coups
        2 joueur contre ordi qui cache -> pas de placement des bateaux, tableau des coups
        3 ordi qui cache juste -> placement des bateaux, pas de coups passés ni futurs
        4 ordi en mode attaque -> placement bateaux, coups passés et futurs
        */
        
        if (this.type == 1){
            this.coupsPrecedents = new byte [12][12];
            //placement bateaux
        }else if (this.type ==2){
            this.coupsPrecedents = new byte [12][12];
        }else if (this.type ==3){
            //placement bateaux
        }else if (this.type ==4){
            this.coupsPrecedents = new byte [12][12];
            //this.coupsOrdi = new byte [10][10]; pas besoin pour cette manière de jouer
            //placement bateaux
            ordi = new Ordi(this.coupsPrecedents);
        }
        
        if (this.type!=3){
            this.initialiseCoups();
        }
        
    }
    
    
    
    /**
     * Méthode pour la bonne création de la grille
     * Ne prend pas de paramètre
     * Ne retourne rien
     */
    public void initialiseCoups(){
        for (byte i = 0; i<this.coupsPrecedents.length; i+=1){
            this.coupsPrecedents[i][0] = -4;
            this.coupsPrecedents[0][i] = -4;
            this.coupsPrecedents[i][this.coupsPrecedents.length-1] = -4;
            this.coupsPrecedents[this.coupsPrecedents.length-1][i] = -4;
        }
    }
    
    /**
     * Méthode pour déterminer la manière d'attaquer selon ordi ou joueur
     * Prends la cible en paramètre
     * 
     */
    public void Attaque(Joueur cible){
        
        if ((this.type == 1) || (this.type ==2)){
            this.AttaqueJoueur(cible);
        }else if (this.type ==4){
            this.AttaqueOrdi(cible);
        }
        
    }
    
    
    /**
     * Méthode pour le choix de la case à attaquer pour l'ordi
     * Prend la cible en paramètre
     * 
     */
    public void AttaqueOrdi(Joueur cible){
        
        
        
        
        this.ordi.AttaqueOrdi(this);
        
        this.AttaqueCase(cible);
        
    }
    
    
    
    
    
    
    
    
    /**
     * Méthode pour le choix de la case à attaquer pour humain
     * Prends la cible en paramètre
     * 
     */
    public void AttaqueJoueur(Joueur cible){
        
        //Choix de l'attaque
        
        do{
            
            //choix endroit attaque
            do{
                System.out.print("Choisissez la ligne à attaquer ");
                this.ligne = sc.nextByte();
            }while( (this.ligne<1) || (this.ligne>this.bateaux.length-2) );
            //this.ligne-=1;
            
            do{
                System.out.print("Choisissez la colonne à attaquer ");
                this.colonne = sc.nextByte();
            }while( (this.colonne<1) || (this.colonne>this.bateaux[0].length-2) );
            //this.colonne -=1;
        }while(this.coupsPrecedents[this.ligne][this.colonne] != 0);
        
        this.caseL = this.ligne;
        this.caseC = this.colonne;
        System.out.println("Vous attaquez la case " + (this.ligne) + " " + (this.colonne));
        this.AttaqueCase(cible);
    }
    
    
    
    
    
    /**
     * Méthode pour l'attaque de la case visée
     * Prend la cible en paramètre
     * 
     */
    public void AttaqueCase(Joueur cible){    
        
        //Gestion de l'attaque
        
        this.touche = cible.bateaux[this.caseL][this.caseC]; 
        
        System.out.println("\n\n\n\n\n");
        
        if (this.touche ==0){
            System.out.println("Loupé");
            this.coupsPrecedents[this.caseL][this.caseC] = -1;
        }else{
            System.out.println("Touché");
            cible.nbreCasesBateau[touche-1] -=1;
            this.coupsPrecedents[this.caseL][this.caseC] = -2;
            if (cible.nbreCasesBateau[touche-1] == 0){
                cible.nbBateaux -=1;
                //gestion coulé = changement des valeurs des coups précédents
                for (byte i = 0; i<cible.bateaux.length ; i +=1){
                    if (cible.bateaux[i][this.caseC] == this.touche){
                        this.coupsPrecedents[i][this.caseC] = -3;
                    }
                }
                
                for (byte j = 0; j<cible.bateaux[0].length ; j +=1){
                    if (cible.bateaux[this.caseL][j] == this.touche){
                        this.coupsPrecedents[this.caseL][j] = -3;
                    }
                }
                
                System.out.println("Coulé");
            }
            //cible.caseVie -=1;
                
        }
        
        
    }
    
    
    
    
    
    
    
    
    
    /**
     * Méthode pour l'affichage des coups joués
     * Prend le nombre de bateaux restant à l'autre en paramètre
     * 
     */
    public void afficheCoups(byte bateauxAdv){
        
        if (this.type!=3){
            System.out.print("      ");
            for(byte i = 1; i < this.coupsPrecedents[0].length-1 ; i+=1){
                System.out.print(i + "  ");
            }
            System.out.println("\n");
            
            for(int i = 1 ; i<this.coupsPrecedents.length-1 ; i++){
                
                if (i<10){
                    System.out.print(" ");
                }
                System.out.print(i + "   ");
                
                for(int j = 1 ; j<this.coupsPrecedents[i].length-1 ; j++){
                    
                    if(this.coupsPrecedents[i][j] == 0){
                        System.out.print(" . ");
                    }else if (this.coupsPrecedents[i][j] == -1){// || matrice[i][j] == 1 || matrice[i][j] == 2 || matrice[i][j] == 3 || matrice[i][j] == 4){
                        System.out.print(" X ");
                    }else if (this.coupsPrecedents[i][j] == -2){
                        System.out.print(" O ");
                    }else if (this.coupsPrecedents[i][j] == -3){
                        System.out.print(" C ");
                    }
                    else if (this.coupsPrecedents[i][j] == -4){
                        System.out.print(" B ");
                    }/*else{
                        System.out.print(" E ");
                    }*/
                    
                }
                System.out.println();
            }
            System.out.println("\n" + "Il reste " + bateauxAdv + " bateaux à trouver");
        }
    }
    
    public boolean fini(){
        return !(nbBateaux==0);
    }
    
    
    
    
    
    
}






/*
//Version 20/05
import java.util.Scanner;
public class Joueur{
    public byte [][] bateaux={
        {0,0,0,0,0,0,0,0,0,2},
        {0,1,1,0,0,0,0,0,0,2},
        {0,0,0,0,0,0,0,0,0,2},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
    };
        
        
        
        
    //public byte [][] bateaux;
    public byte type;
    public byte [][] coupsPrecedents;
    //public byte [][] coupsOrdi;
    public byte [] nbreCasesBateau = {2, 3, 3, 4, 5};
    //public byte nbBateaux = 5;
    //public byte totalCases = 17;
    public byte nbBateaux = 2;
    private byte ligne;
    private byte colonne;
    private byte caseL;
    private byte caseC;
    private byte touche;
    
    Scanner sc = new Scanner(System.in);
    
    
    
    
    public Joueur(byte type) {
        
        this.type = type; 
        
        //1 pour un joueur humain qui cache ; 2 pour un humain qui cherche contre ordi ; 3 pour ordi qui cache juste ; 4 pour ordi qui joue
        /*
        1 jeu classique jvj ou contre ordi en mode attaque -> placement bateaux, tableau des coups
        2 joueur contre ordi qui cache -> pas de placement des bateaux, tableau des coups
        3 ordi qui cache juste -> placement des bateaux, pas de coups passés ni futurs
        4 ordi en mode attaque -> placement bateaux, coups passés et futurs
        */
/*        
        if (this.type == 1){
            this.coupsPrecedents = new byte [10][10];
            //placement bateaux
        }else if (this.type ==2){
            this.coupsPrecedents = new byte [10][10];
        }else if (this.type ==3){
            //placement bateaux
        }else if (this.type ==4){
            this.coupsPrecedents = new byte [10][10];
            //this.coupsOrdi = new byte [10][10]; pas besoin pour cette manière de jouer
            //placement bateaux
        }
        
    }
    
    
    
    
    
    /**
     * Méthode pour déterminer la manière d'attaquer selon ordi ou joueur
     * Prends la cible en paramètre
     * 
     */
/*    public void Attaque(Joueur cible){
        
        if ((this.type == 1) || (this.type ==2)){
            this.AttaqueJoueur(cible);
        }else if (this.type ==4){
            this.AttaqueOrdi(cible);
        }
        
    }
    
    
    /**
     * Méthode pour le choix de la case à attaquer pour l'ordi
     * Prend la cible en paramètre
     * 
     */
/*    public void AttaqueOrdi(Joueur cible){
        
        
        boolean attaque = false;//pour savoir si on a déjà attaqué ce tour-ci
        byte i = 0;
        /*byte bord = 0; // 1 pour le haut, 2 pour droite, 3 pour bas, 4 pour gauche
        byte coin = 0; // sens horaire en partant du haut gauche
        */
 /*       byte[][] dirAleat = { {0,-1}, {0,1}, {-1,0}, {1,0} };//sert pour jouer aléatoirement autour d'une case
        // indice 0=colonne d'avant, 1=colonne d'après, 2=ligne d'avant, 3=ligne d'après
        byte aleat;
        while ((attaque==false) && (i<this.coupsPrecedents.length*this.coupsPrecedents[0].length)){
            
            
            //On parcourt les cases touchées de la grille et on détermine la meilleure façon d'attaquer à proximité
            
            
            if (this.coupsPrecedents[i/10][i%10]==-2){
                this.ligne = (byte)(i/10);
                this.colonne = (byte)(i%10);
                
                //Permet de connaître les coordonnées de la dernière case touchée visitée. (utile en cas de bateaux collés)
                //virer les system.out.print
                if ((this.ligne>0) && (this.ligne<this.coupsPrecedents.length-1) && (this.colonne>0) && (this.colonne<this.coupsPrecedents[0].length-1)){ //si la case considérée n'est pas sur un bord
                    
                    System.out.println("case");
                    if ((this.coupsPrecedents[this.ligne+1][this.colonne]==-2) && (this.coupsPrecedents[this.ligne-1][this.colonne]==0)){//si case dessous touchée, attaque au dessus
                        this.caseL = (byte)(this.ligne-1);
                        this.caseC = this.colonne;
                        attaque = true;
                        System.out.println("att haut");
                        
                    }else if ((this.coupsPrecedents[this.ligne-1][this.colonne]==-2) && (this.coupsPrecedents[this.ligne+1][this.colonne]==0)){//si case au dessus touchée, attaque en dessous
                        this.caseL = (byte)(this.ligne+1);
                        this.caseC = this.colonne;
                        attaque = true;
                        System.out.println("att bas");
                        
                    }else if ((this.coupsPrecedents[this.ligne][this.colonne+1]==-2) && (this.coupsPrecedents[this.ligne][this.colonne-1]==0)){//si case à droite touchée, attaque à gauche
                        this.caseL = this.ligne;
                        this.caseC = (byte)(this.colonne-1);
                        attaque = true;
                        System.out.println("att gauche");
                        
                    }else if ((this.coupsPrecedents[this.ligne][this.colonne-1]==-2) && (this.coupsPrecedents[this.ligne][this.colonne+1]==0)){//si case à gauche touchée, attaque à droite
                        this.caseL = this.ligne;
                        this.caseC = (byte)(this.colonne+1);
                        attaque = true;
                        System.out.println("att droite");
                        
                    }else if ((this.coupsPrecedents[this.ligne+1][this.colonne]!=-2) && (this.coupsPrecedents[this.ligne-1][this.colonne]!=-2) && (this.coupsPrecedents[this.ligne][this.colonne+1]!=-2) && (this.coupsPrecedents[this.ligne][this.colonne-1]!=-2)){//si la case est isolée
                        //Attaque aléatoire sur une case autour
                        do{
                            //Tirage de la case aléatoire pour 4 cases potentielles
                            aleat = (byte)(4*Math.random());
                            this.caseL = (byte)(this.ligne + dirAleat[aleat][0]);
                            this.caseC = (byte)(this.colonne + dirAleat[aleat][1]);
                        }while(this.coupsPrecedents[this.caseL][this.caseC]!=0);//case visée déjà jouée
                        attaque = true;
                    }
                    
                    //Si toutes les cases autour ont déjà été attaquées, on ne fait rien 
                    
                    
                
                    
                }else if (((this.ligne==0) || (this.ligne==this.coupsPrecedents.length-1))&&((this.colonne==0) || (this.colonne==this.coupsPrecedents[0].length-1))){//si la case est dans un angle
                    
                    //case dans un angle
                    if ((this.ligne == 0) && (this.colonne == 0) && (this.coupsPrecedents[this.ligne+1][this.colonne]!=-2) && (this.coupsPrecedents[this.ligne][this.colonne+1]!=-2)){// angle hg ajouter et que y'a pas de -2 à coté
                    
                        do{//choix de l'indice : 1 ou 3
                            //Tirage de la case aléatoire pour 2 cases potentielles
                            aleat = (byte)(1 + 2*((byte)(2*Math.random())));
                            this.caseL = (byte)(this.ligne + dirAleat[aleat][0]);
                            this.caseC = (byte)(this.colonne + dirAleat[aleat][1]);
                        }while(this.coupsPrecedents[this.caseL][this.caseC]!=0);
                            attaque = true;
                        
                    }else if ((this.ligne == this.coupsPrecedents.length-1) && (this.colonne == 0) && (this.coupsPrecedents[this.ligne-1][this.colonne]!=-2) && (this.coupsPrecedents[this.ligne][this.colonne+1]!=-2)){// angle bg
                    
                        do{//choix de l'indice : 1 ou 2
                            //Tirage de la case aléatoire pour 2 cases potentielles
                            aleat = (byte)(1 + (byte)(2*Math.random()));
                            this.caseL = (byte)(this.ligne + dirAleat[aleat][0]);
                            this.caseC = (byte)(this.colonne + dirAleat[aleat][1]);
                        }while(this.coupsPrecedents[this.caseL][this.caseC]!=0);
                            attaque = true;
                        
                    }else if ((this.ligne == this.coupsPrecedents.length-1) && (this.colonne == this.coupsPrecedents[0].length-1) && (this.coupsPrecedents[this.ligne-1][this.colonne]!=-2) && (this.coupsPrecedents[this.ligne][this.colonne-1]!=-2)){// angle bd
                    
                        do{//choix de l'indice : 0 ou 2
                            //Tirage de la case aléatoire pour 2 cases potentielles
                            aleat = (byte)(2*(byte)(2*Math.random()));
                            this.caseL = (byte)(this.ligne + dirAleat[aleat][0]);
                            this.caseC = (byte)(this.colonne + dirAleat[aleat][1]);
                        }while(this.coupsPrecedents[this.caseL][this.caseC]!=0);
                            attaque = true;
                        
                    }else if ((this.ligne == 0) && (this.colonne == this.coupsPrecedents[0].length-1) && (this.coupsPrecedents[this.ligne+1][this.colonne]!=-2) && (this.coupsPrecedents[this.ligne][this.colonne-1]!=-2)){// angle hd
                    
                        do{//choix de l'indice : 0 ou 2
                            //Tirage de la case aléatoire pour 2 cases potentielles
                            aleat = (byte)(3*(byte)(2*Math.random()));
                            this.caseL = (byte)(this.ligne + dirAleat[aleat][0]);
                            this.caseC = (byte)(this.colonne + dirAleat[aleat][1]);
                        }while(this.coupsPrecedents[this.caseL][this.caseC]!=0);
                            attaque = true;
                        
                    }
                
                
                    
                }else{//la case est sur un bord mais pas dans un angle
                    
                    if (this.ligne==0){//si case sur bord haut
                    
                        if ((this.coupsPrecedents[this.ligne][this.colonne+1]==-2) && (this.coupsPrecedents[this.ligne][this.colonne-1]==0)){//si case à droite touchée, attaque à gauche
                            this.caseL = this.ligne;
                            this.caseC = (byte)(this.colonne-1);
                            attaque = true;
                            System.out.println("att gauche");
                            
                        }else if ((this.coupsPrecedents[this.ligne][this.colonne-1]==-2) && (this.coupsPrecedents[this.ligne][this.colonne+1]==0)){//si case à gauche touchée, attaque à droite
                            this.caseL = this.ligne;
                            this.caseC = (byte)(this.colonne+1);
                            attaque = true;
                            System.out.println("att droite");
                            
                        }else if((this.coupsPrecedents[this.ligne+1][this.colonne]!=-2) && (this.coupsPrecedents[this.ligne][this.colonne+1]!=-2) && (this.coupsPrecedents[this.ligne][this.colonne-1]!=-2)){//si rien autour
                            //attaque aléatoire sur une case autour
                            do{
                                //tirage de la case aléatoire pour 3 cases
                            }while(this.coupsPrecedents[this.caseL][this.caseC]!=0);//case visée déjà jouée
                            attaque = true;
                        }
                        
                    }else if (this.ligne==this.coupsPrecedents.length-1){//si case sur bord bas
                    
                        if ((this.coupsPrecedents[this.ligne][this.colonne+1]==-2) && (this.coupsPrecedents[this.ligne][this.colonne-1]==0)){//si case à droite touchée, attaque à gauche
                            this.caseL = this.ligne;
                            this.caseC = (byte)(this.colonne-1);
                            attaque = true;
                            System.out.println("att gauche");
                            
                        }else if ((this.coupsPrecedents[this.ligne][this.colonne-1]==-2) && (this.coupsPrecedents[this.ligne][this.colonne+1]==0)){//si case à gauche touchée, attaque à droite
                            this.caseL = this.ligne;
                            this.caseC = (byte)(this.colonne+1);
                            attaque = true;
                            System.out.println("att droite");
                            
                        }else if((this.coupsPrecedents[this.ligne-1][this.colonne]!=-2) && (this.coupsPrecedents[this.ligne][this.colonne+1]!=-2) && (this.coupsPrecedents[this.ligne][this.colonne-1]!=-2)){//si rien autour
                            //attaque aléatoire sur une case autour
                            do{
                                //tirage de la case aléatoire pour 3 cases
                            }while(this.coupsPrecedents[this.caseL][this.caseC]!=0);//case visée déjà jouée
                            attaque = true;
                        }
                        
                    }else if (this.colonne==0){//si case sur bord gauche
                    
                        if ((this.coupsPrecedents[this.ligne+1][this.colonne]==-2) && (this.coupsPrecedents[this.ligne-1][this.colonne]==0)){//si case en dessous touchée, attaque au dessus
                            this.caseL = (byte)(this.ligne-1);
                            this.caseC = this.colonne;
                            attaque = true;
                            System.out.println("att haut");
                            
                        }else if ((this.coupsPrecedents[this.ligne-1][this.colonne]==-2) && (this.coupsPrecedents[this.ligne+1][this.colonne]==0)){//si case au dessus touchée, attaque en dessous
                            this.caseL = (byte)(this.ligne+1);
                            this.caseC = this.colonne;
                            attaque = true;
                            System.out.println("att bas");
                            
                        }else if((this.coupsPrecedents[this.ligne+1][this.colonne]!=-2) && (this.coupsPrecedents[this.ligne-1][this.colonne]!=-2) && (this.coupsPrecedents[this.ligne][this.colonne+1]!=-2)){//si rien autour
                            //attaque aléatoire sur une case autour
                            do{
                                //tirage de la case aléatoire pour 3 cases
                            }while(this.coupsPrecedents[this.caseL][this.caseC]!=0);//case visée déjà jouée
                            attaque = true;
                        }
                        
                    }else if (this.colonne==this.coupsPrecedents[0].length-1){//si case sur bord droit
                    
                        if ((this.coupsPrecedents[this.ligne+1][this.colonne]==-2) && (this.coupsPrecedents[this.ligne-1][this.colonne]==0)){//si case en dessous touchée, attaque au dessus
                            this.caseL = (byte)(this.ligne-1);
                            this.caseC = this.colonne;
                            attaque = true;
                            System.out.println("att haut");
                            
                        }else if ((this.coupsPrecedents[this.ligne-1][this.colonne]==-2) && (this.coupsPrecedents[this.ligne+1][this.colonne]==0)){//si case au dessus touchée, attaque en dessous
                            this.caseL = (byte)(this.ligne+1);
                            this.caseC = this.colonne;
                            attaque = true;
                            System.out.println("att bas");
                            
                        }else if((this.coupsPrecedents[this.ligne+1][this.colonne]!=-2) && (this.coupsPrecedents[this.ligne-1][this.colonne]!=-2) && (this.coupsPrecedents[this.ligne][this.colonne-1]!=-2)){//si rien autour
                            //attaque aléatoire sur une case autour
                            do{
                                //tirage de la case aléatoire pour 3 cases
                            }while(this.coupsPrecedents[this.caseL][this.caseC]!=0);//case visée déjà jouée
                            attaque = true;
                        }
                    }
                }
                
                
                
                
                
            }
            
            
            i +=1;
        }
        
        
        if((attaque==false) && this.coupsPrecedents[this.ligne][this.colonne]==-2){//si on a bien trouvé une case touchée mais qu'on a pas pu attaquer (si bateaux collés)
            //attaque aléatoire autour
            //attaque = true;
        }
        
        
        //Si aucune attaque n'a pu être menée, c'est qu'il n'y a plus de case touchée. On repasse en mode chasse : Attaque aléatoire sur cases paires.
        if (attaque==false){
            do{
                //Tirage d'une case paire
                this.ligne = (byte)(10*Math.random());
                this.colonne = (byte)(2*(byte)(5*Math.random()) + this.ligne%2);//pour n'attaquer qu'une case sur 2
                System.out.print(this.ligne + " " + this.colonne);
            }while(this.coupsPrecedents[this.ligne][this.colonne] != 0);
            
            this.caseL = this.ligne;
            this.caseC = this.colonne;
            //this.caseC=9;
            //this.caseL=0;
            
        }
        
        
        this.AttaqueCase(cible);
        
    }
    
    
    
    
    
    
    
    
    /**
     * Méthode pour le choix de la case à attaquer pour humain
     * Prends la cible en paramètre
     * 
     */
  /*  public void AttaqueJoueur(Joueur cible){
        
        //Choix de l'attaque
        
        do{
            
            //choix endroit attaque
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
        this.AttaqueCase(cible);
    }
    
    
    
    
    
    /**
     * Méthode pour l'attaque de la case visée
     * Prend la cible en paramètre
     * 
     */
 /*   public void AttaqueCase(Joueur cible){    
        
        //Gestion de l'attaque
        
        this.touche = cible.bateaux[this.caseL][this.caseC]; 
        
        System.out.println("\n\n\n\n\n");
        
        if (this.touche ==0){
            System.out.println("Loupé");
            this.coupsPrecedents[this.caseL][this.caseC] = -1;
        }else{
            System.out.println("Touché");
            cible.nbreCasesBateau[touche-1] -=1;
            this.coupsPrecedents[this.caseL][this.caseC] = -2;
            if (cible.nbreCasesBateau[touche-1] == 0){
                cible.nbBateaux -=1;
                //gestion coulé = changement des valeurs des coups précédents
                for (byte i = 0; i<cible.bateaux.length ; i +=1){
                    if (cible.bateaux[i][this.caseC] == this.touche){
                        this.coupsPrecedents[i][this.caseC] = -3;
                    }
                }
                
                for (byte j = 0; j<cible.bateaux.length ; j +=1){
                    if (cible.bateaux[this.caseL][j] == this.touche){
                        this.coupsPrecedents[this.caseL][j] = -3;
                    }
                }
                
                System.out.println("Coulé");
            }
            //cible.caseVie -=1;
                
        }
        
        
    }
    
    
    
    
    
    
    
    
    
    /**
     * Méthode pour l'affichage des coups joués
     * Prend le nombre de bateaux restant à l'autre en paramètre
     * 
     */
  /*  public void afficheCoups(byte bateauxAdv){
        
        if (this.type!=3){
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
                    }else if (this.coupsPrecedents[i][j] == -3){
                        System.out.print(" C ");
                    }else{
                        System.out.print(" E ");
                    }
                    
                }
                System.out.println();
            }
            System.out.println("\n" + "Il reste " + bateauxAdv + " bateaux à trouver");
        }
    }
    
    public boolean fini(){
        return !(nbBateaux==0);
    }
    
    
    
}








/*
//Version 15/05
import java.util.Scanner;
public class Joueur{
    public byte [][] bateaux={
        {1,1,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0},
    };
        
        
        
        
    //public byte [][] bateaux;
    public byte type;
    public byte [][] coupsPrecedents;
    public byte [][] coupsOrdi;
    public byte [] nbreCasesBateau = {2, 3, 3, 4, 5};
    //public byte nbBateaux = 5;
    public byte nbBateaux = 1;
    private byte ligne;
    private byte colonne;
    private byte caseL;
    private byte caseC;
    private byte touche;
    
    Scanner sc = new Scanner(System.in);
    
    
    /*public Joueur() {
        this.coupsPrecedents = new byte [10][10];
        //
        //Mettre le placement des bateaux ici 
    }*/
    
 /*   public Joueur(byte type) {
        
        this.type = type; 
        
        //1 pour un joueur humain qui cache ; 2 pour un humain qui cherche contre ordi ; 3 pour ordi qui cache juste ; 4 pour ordi qui joue
        /*
        1 jeu classique jvj ou contre ordi en mode attaque -> placement bateaux, tableau des coups
        2 joueur contre ordi qui cache -> pas de placement des bateaux, tableau des coups
        3 ordi qui cache juste -> placement des bateaux, pas de coups passés ni futurs
        4 ordi en mode attaque -> placement bateaux, coups passés et futurs
        */
        
  /*      if (this.type == 1){
            this.coupsPrecedents = new byte [10][10];
            //placement bateaux
        }else if (this.type ==2){
            this.coupsPrecedents = new byte [10][10];
        }else if (this.type ==3){
            //placement bateaux
        }else if (this.type ==4){
            this.coupsPrecedents = new byte [10][10];
            this.coupsOrdi = new byte [10][10];
            //placement bateaux
        }
        
    }
    
    
    
    
    //Gère la manière d'attaquer selon si humain ou ordinateur
    /**
     * Méthode pour déterminer la manière d'attaquer selon ordi ou joueur
     * Prends la cible en paramètre
     * 
     */
 /*   public void Attaque(Joueur cible){
        
        if ((this.type == 1) || (this.type ==2)){
            this.AttaqueJoueur(cible);
        }else if (this.type ==4){
            this.AttaqueOrdi(cible);
        }
        
    }
    
    
    /**
     * Méthode pour le choix de la case à attaquer pour l'ordi
     * Prend la cible en paramètre
     * 
     */
 /*   public void AttaqueOrdi(Joueur cible){
        // jeu pseudo aléatoire sur cases paires
        //quand touche, va chercher à toucher autour -> augmentation du score des cases voisines de 2 ou 3 points. si rate, diminue de 1 ou 2 scores cases voisines => voir équilibrage score
        // if en mode recherche, 
    }
    
    
    
    
    
    
    
    
    /**
     * Méthode pour le choix de la case à attaquer pour humain
     * Prends la cible en paramètre
     * 
     */
  /*  public void AttaqueJoueur(Joueur cible){
        
        //Choix de l'attaque
        
        do{
            
            //choix endroit attaque
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
        this.AttaqueCase(cible);
    }
    
    
    
    
    
    /**
     * Méthode pour l'attaque de la case visée
     * Prend la cible en paramètre
     * 
     */
 /*   public void AttaqueCase(Joueur cible){    
        
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
     * Prend le nombre de bateaux restant à l'autre en paramètre
     * 
     */
  /*  public void afficheCoups(byte bateauxAdv){
        
        if (this.type<=2){
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
            System.out.println("\n" + "Il reste " + bateauxAdv + " bateaux à trouver");
        }
    }
    
    public boolean fini(){
        return !(nbBateaux==0);
    }
    
    
    
}





//Version 8/05/21
/*import java.util.Scanner;
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
    
    
 /*   public void Attaque(Joueur cible){
        
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
 /*   public void afficheCoups(){
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
    
    
}*/


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
