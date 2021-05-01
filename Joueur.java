public class Joueur{
    public int [][] grille;
    public int [][] coupsPrecedents;
    
    public joueur() {
        this.grille = new int [10][10];//chaque case va prendre une valeur entre 0 et 5 pour indiquer s'il n'y a rien ou le numéro du bateau
        this.coupsPrecedents = new char [10][10]; 
    }
    
    public void PlacementBateaux(Bateau b1){
        
    }
    
    public void AttaqueAdverse(int x , int y){
        if (grille[x][y]==0){
            grille[x][y] = 0;
            System.out.println("Loupé");
        }else if ()//autres conditions
    }
    
    public void initialiseCoupsPrecedents(){
        for(int i = 0 ; i<coupsPrecedents.length() ; i++){
            for(int j = 0 ; j<coupsPrecedents[i].length() ; j++){
                coupsPrecedents[i][j] = ".";
            }
        }
    }
    
    public void enregistreCoup(int x , int y){
        coupsPrecedents[x][y] = "X";
    }
    
    public void afficheCoupsPrecedents(){
        for(int i = 0 ; i<coupsPrecedents.length() ; i++){
            for(int j = 0 ; j<coupsPrecedents[i].length() ; j++){
                System.out.println(" "+coupsPrecedents[i][j]+" ");
            }
        }
    }
}
