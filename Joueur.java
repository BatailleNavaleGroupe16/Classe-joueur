public class Joueur{
    public int [][] grille;
    public int [][] coupsPrecedents;
    public int [] nbreCasesBateau;
    
    public joueur() {
        this.grille = new int [10][10];//chaque case va prendre une valeur entre 0 et 5 pour indiquer s'il n'y a rien ou le numéro du bateau
        this.coupsPrecedents = new int [10][10];
        this.nbreCasesBateau = {2, 3, 3, 4, 5};//nombre de cases par bateaux vivantes au début
    }
    
    public void PlacementBateaux(Bateau b1 , int numero){
        for(int i = x1 ; i<x2 ; i++){//remplacer x1, x2 ... par x1.b1() par exemple
            for(int j = y1 ; j<y2 ; j++){
                grille[i][j] = numero;
            }
        }
        
    }
    
    public void AttaqueAdverse(int x , int y){
        if (grille[x][y]==0){
            grille[x][y] = 0;
            System.out.println("Loupé");
        }else if ()
    }
    
    public void initialiseCoupsPrecedents(){
        for(int i = 0 ; i<coupsPrecedents.length ; i++){
            for(int j = 0 ; j<coupsPrecedents[i].length ; j++){
                coupsPrecedents[i][j] = 0;
            }
        }
    }
    
    public void enregistreCoup(int x , int y){
        coupsPrecedents[x][y] = 1;
    }
    
    public void afficheGrille(){
        System.out.println("Coups joués depuis le début de la partie par vous : ");
        for(int i = 0 ; i<coupsPrecedents.length ; i++){
            for(int j = 0 ; j<coupsPrecedents[i].length ; j++){
                if(coupsPrecedents[i][j] == 0){
                    System.out.print(" . ");
                }else{
                    System.out.print(" X ")
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
