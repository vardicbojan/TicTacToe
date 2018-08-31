package TicTacToe;

import java.io.File;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;


/*
u ovom programu sam odlučio da prvi igrač UVIJEK ima znak "X", a drugi igrač 
UVIJEK ima znak "O"
*/
public class Glavna extends Application {
        static int igrac1 = 0;
        static int igrac2 = 0;
        static int tie = 0; //nerijeseno
        int x;
        final String prvi = "X";
        final String drugi = "O";
        static Glavna glavna = new Glavna();
        static Label status = new Label();
        static Label player1 = new Label("Igrač 1 ");
        static Label player2 = new Label("Igrač 2 ");
        static Label player1Score = new Label(String.valueOf(igrac1));
        static Label player2Score = new Label(String.valueOf(igrac2));
        static Label dvotocka = new Label(":");
        static Label nerijeseno = new Label("Nerijeseno: ");
        static Label nerijesenoScore = new Label(String.valueOf(tie));
        
        static Button reset = new Button("RESET");
        static Button b1 = new Button();
        static Button b2 = new Button();
        static Button b3 = new Button();
        static Button b4 = new Button();
        static Button b5 = new Button();
        static Button b6 = new Button();
        static Button b7 = new Button();
        static Button b8 = new Button();
        static Button b9 = new Button();
      
        static ArrayList<Label> labeli = new ArrayList<Label>(); //svi labeli na jednom mjestu
        static ArrayList<Button> buttoni = new ArrayList<Button>(); //svi buttoni na jednom mjestu
    
        /*
        statičke metode se izvršavaju prve
        svrha ove metode je da se odredi tko prvi igra tako što ćemo
        dobiti random broj od 1 - 0.
        Ako je 1, prvi igrač igra prvi, ako je 0, drugi igrač igra prvi
        */
        
        static int random(){ 
            return (int) (Math.random() * 2);
        }
    
    @Override
    public void start(Stage primaryStage) {
        x = random(); // prvo odluči tko igra prvi
        glavna.popuniListuButtonsima();
        glavna.popuniListuLabelsima();
        glavna.postaviFontove();
        //popuni sva polja
        
        Scene scene = new Scene(createBorder(), 700, 700); //postavi scenu (window)
        
        primaryStage.setTitle("Krizic kruzic"); //title programa
        primaryStage.setResizable(false); //mogućnost povećavanja/rastezanja prozora je false
        primaryStage.setScene(scene);
        primaryStage.show(); //prikaži scenu (window)
        
            if(x == 1){ //ako je random broj 1 pozovi metodu prviIgrac();
                prviIgrac();
            }else{
                drugiIgrac(); //ako je random broj 0 pozovi metodu drugiIgrac();
            }
            resetButton(); //metoda za action listener za reset button. Kada se stisne random button, ova metoda će se pozvati
    }

    public static void main(String[] args) {
        launch(args); //pokreni program
    }
    
    /*
    metoda koja će napraviti grid layout te ga returnati jer je ova metoda tipa GridPane
    */
    private GridPane createGrid() {
        GridPane grid = new GridPane(); //jedan od layouta gdje je sve kao u tablici/"rešetkama"
        grid.setAlignment(Pos.CENTER); //centrirano poravnanje
        grid.setHgap(5); //razmak između horizontalnih ćelija u gridu (da ne bude sve stisnuto)
        grid.setVgap(5); //razmak između vertikalnih ćelija u gridu (da ne bude sve stisnuto)
        grid.setPadding(new Insets(10,10,10,10)); //razmak grida od gornje, donje, lijeve i desne margine
        
        /*
        dohvaćanje buttona iz liste buttona i postavljanje na mjesta u grid layoutu
        ideja je da buttoni idu ovako
        1 2 3
        4 5 6
        7 8 9
        Lokacija buttona 1 je: 0 stupac, 0 red
        Lokacija buttona 2 je: 1 stupac, 0 red
        Lokacija buttona 7 je: 0 stupac, 2 red
        Lokacija buttona 6 je: 2 stupac, 1 red
        
        primjer: btn 6 grid.setConstraints(buttoni.get(5), 2, 1); broj 2 označava stupac, a 1 red
        prvo ide stupac onda red.
        Indeksiranje pocinje od nule (0).
        */
        grid.setConstraints(buttoni.get(0), 0, 0);  //button 1 
        grid.setConstraints(buttoni.get(1), 1, 0);  //button 2    
        grid.setConstraints(buttoni.get(2), 2, 0);  //button 3    
        grid.setConstraints(buttoni.get(3), 0, 1);  //button 4
        grid.setConstraints(buttoni.get(4), 1, 1);  //button 5
        grid.setConstraints(buttoni.get(5), 2, 1);  //button 6
        grid.setConstraints(buttoni.get(6), 0, 2);  //button 7
        grid.setConstraints(buttoni.get(7), 1, 2);  //button 8
        grid.setConstraints(buttoni.get(8), 2, 2);  //button 9
        
        grid.getChildren().addAll(0, buttoni); //u grid dodaj sve iz polja buttoni 
        //polje buttoni sadrži sve buttone
        return grid; //vrati grid
    }
    
    /*
    metoda koja će napraviti border layout te ga returnati jer je ova metoda tipa BorderPane
    */
    private BorderPane createBorder(){
        /*
        jedan od layouta koji umjesto tablica koristi sekcije (gore, lijevo, centar, desno, dolje)
        */
        BorderPane border = new BorderPane(); 
        border.setPadding(new Insets(40,10,10,10));
        
        border.setCenter(createVbox1()); //u središnji dio stavi metodu createVbox1 (metoda koja vraća Vbox1)
        border.setTop(status); //u gornji dio stavi label (text) 
        border.setBottom(createVbox2()); //u donji dio stavi metodu createVbox2 (metoda koja vraća Vbox2)
        
        border.setAlignment(status, Pos.TOP_CENTER); // poravnanje labela na gornjem dijelu neka bude centrirano

        return border;
    }
    
    /*
    još jedan od layouta u kojem su elementi sortirani vertikalno. Jedan ispod drugoga.
    VBox - Vertical box
    */
    private VBox createVbox1(){
        VBox v1 = new VBox(20); // 20 označava razmak između stavki u vbox-u. Namjera je da ne bude sve stisnuto
        v1.setAlignment(Pos.CENTER); //centriraj
        v1.setPadding(new Insets(10,10,10,10)); //razmak čitavog vboxa
        v1.getChildren().addAll(createGrid(), reset); //dodaj createGrid metodu i reset button
        //metoda createGrid vraća "grid" (tip GridPane) pa ga možemo dodati u Vbox
        return v1;
    }

    private VBox createVbox2(){
        VBox v2 = new VBox();
        v2.setAlignment(Pos.CENTER);
        v2.setPadding(new Insets(10,10,10,10));
        v2.getChildren().addAll(createHbox1(), createHbox2()); //ovdje dodajemo Hbox1 i Hbox2
        return v2;
    }
    
    /*
    još jedan layout koji je sličan VBoxu, samo postavlja elemente horizontalno, jedan DO drugoga
    a ne kao u VBoxu koji postavlja elemente jedan ISPOD drugoga
    HBox - horizontal box
    */
    private HBox createHbox1(){
        HBox h1 = new HBox(50); //50 je razmak između elemenata koji su jedan do drugoga
        h1.setPadding(new Insets(10,10,10,10));
        h1.getChildren().addAll(player1, player1Score, dvotocka, player2Score, player2); 
        h1.setAlignment(Pos.CENTER);
        return h1;
    }
    
    private HBox createHbox2(){
        HBox h2 = new HBox(50);
        h2.setAlignment(Pos.CENTER);
        h2.setPadding(new Insets(10,10,10,10));
        h2.getChildren().addAll(nerijeseno, nerijesenoScore);
        return h2;
    }
    
    /*
    dodaj sve buttone u array listu
    */
    void popuniListuButtonsima(){
        buttoni.add(b1);
        buttoni.add(b2);
        buttoni.add(b3);
        buttoni.add(b4);
        buttoni.add(b5);
        buttoni.add(b6);
        buttoni.add(b7);
        buttoni.add(b8);
        buttoni.add(b9);
    }
    
    
    
    
    /*
    ova se metoda poziva kada je prvi igrac na potezu.
    Imamo konstruirane metode za sve buttone (listenere) i ovisno o tome koji se button stisne, taj "listener" će se pozvati
    i button više neće biti "prazan" nego će biti tekst na njemu "X" ili "O"
    Nakon sto kliknemo na button i nakon što button "dobije" tekst, taj button se onemogućuje za daljnje klikanje
    */
    void prviIgrac(){
        status.setText("Prvi igrač igra."); // u gornji dio (BorderPane) u label (tekst) postavi navedeni string
            b1.setOnMouseClicked(e -> { //koristimo lambda ekspresiju radi jednostavnosti i smanjenja linija koda
                b1.setText(prvi); //postavi tekst buttona b1 u "prvi". Prvi ovdje nije string nego varijabla
                b1.setDisable(true); //onemogući kliknuti button
                gameOver(jeLiIgraGotova()); // testira se je li igra gotova
                drugiIgrac(); //kada je prvi igrac napravio svoj potez, na redu je drugi igrac
            });
            b2.setOnMouseClicked(e -> {
                b2.setText(prvi);
                b2.setDisable(true);
                gameOver(jeLiIgraGotova());
                drugiIgrac();
            });
            b3.setOnMouseClicked(e -> {
                b3.setText(prvi);
                b3.setDisable(true);
                gameOver(jeLiIgraGotova());
                drugiIgrac();
            });
            b4.setOnMouseClicked(e -> {
                b4.setText(prvi);
                b4.setDisable(true);
                gameOver(jeLiIgraGotova());
                drugiIgrac();
            });
            b5.setOnMouseClicked(e -> {
                b5.setText(prvi);
                b5.setDisable(true);
                gameOver(jeLiIgraGotova());
                drugiIgrac();
            });
            b6.setOnMouseClicked(e -> {
                b6.setText(prvi);
                b6.setDisable(true);
                gameOver(jeLiIgraGotova());
                drugiIgrac();
            });
            b7.setOnMouseClicked(e -> {
                b7.setText(prvi);
                b7.setDisable(true);
                gameOver(jeLiIgraGotova());
                drugiIgrac();
            });
            b8.setOnMouseClicked(e -> {
                b8.setText(prvi);
                b8.setDisable(true);
                gameOver(jeLiIgraGotova());
                drugiIgrac();
            });
            b9.setOnMouseClicked(e -> {
                b9.setText(prvi);
                b9.setDisable(true);
                gameOver(jeLiIgraGotova());
                drugiIgrac();
            });
    }
    
    void drugiIgrac(){
        status.setText("Drugi igrač igra.");
            b1.setOnMouseClicked(e -> {
                b1.setText(drugi);
                b1.setDisable(true);
                gameOver(jeLiIgraGotova());
                prviIgrac();
            });
            b2.setOnMouseClicked(e -> {
                b2.setText(drugi);
                b2.setDisable(true);
                gameOver(jeLiIgraGotova());
                prviIgrac();
            });
            b3.setOnMouseClicked(e -> {
                b3.setText(drugi);
                b3.setDisable(true);
                gameOver(jeLiIgraGotova());
                prviIgrac();
            });
            b4.setOnMouseClicked(e -> {
                b4.setText(drugi);
                b4.setDisable(true);
                gameOver(jeLiIgraGotova());
                prviIgrac();
            });
            b5.setOnMouseClicked(e -> {
                b5.setText(drugi);
                b5.setDisable(true);
                gameOver(jeLiIgraGotova());
                prviIgrac();
            });
            b6.setOnMouseClicked(e -> {
                b6.setText(drugi);
                b6.setDisable(true);
                gameOver(jeLiIgraGotova());
                prviIgrac();
            });
            b7.setOnMouseClicked(e -> {
                b7.setText(drugi);
                b7.setDisable(true);
                gameOver(jeLiIgraGotova());
                prviIgrac();
            });
            b8.setOnMouseClicked(e -> {
                b8.setText(drugi);
                b8.setDisable(true);
                gameOver(jeLiIgraGotova());
                prviIgrac();
            });
            b9.setOnMouseClicked(e -> {
                b9.setText(drugi);
                b9.setDisable(true);
                gameOver(jeLiIgraGotova());
                prviIgrac();
            });
            
    }

    /*
    reset button služi kada želimo izbrisati ukupne rezultate prvog i drugog igrača
    i vratiti program otprilike na sami početak (kao da smo ga ponovno pokrenuli)
    */
    void resetButton(){
        reset.setOnMouseClicked(e -> { //kada je reset button stisnut izvrši ispod navedene naredbe
               b1.setDisable(false); // omogući button b1
               b1.setText(""); //izbriši tekst koji je button b1 imao tako da nema teksta u njemu (prazan string)
               b2.setDisable(false);
               b2.setText("");
               b3.setDisable(false);
               b3.setText("");
               b4.setDisable(false);
               b4.setText("");
               b5.setDisable(false);
               b5.setText("");
               b6.setDisable(false);
               b6.setText("");
               b7.setDisable(false);
               b7.setText("");
               b8.setDisable(false);
               b8.setText("");
               b9.setDisable(false);
               b9.setText("");
               
               player1Score.setText("0"); //postavi rezultat prvog igrača na 0
               player2Score.setText("0"); // -||-
               nerijesenoScore.setText("0"); // -||-
               
               /*
               da ne moramo ponovno pokretati program, pozvali smo i ovdje metodu "random()" koja 
               će odlučiti tko igra prvi
               */
               x = random(); 
               
                if(x == 1){
                    prviIgrac();
                }else{
                    drugiIgrac();
                }
            });
    }
    
    /*
    u ovoj metodi provjeravamo da li je igra gotova (pobjeda igrača 1, pobjeda igrača 2, neriješeno)
    To provjeravamo logikom igre kružić - križić (3 ista uzastopna znaka ("X" ili "O" ) (vodoravno ili horizontalno) 
    ili obje dijagonale))
    
    U ovoj metodi imamo duple kodove za sve ishode jer moramo znati tko je pobijedio. Igrač 1 ili igrač 2.
    Jer da nemamo duplo, imali bismo pobjednika, ali ne bismo znali tko je pobijedio. 
    prvi kod za igrača 1 i drugi kod za igrača 2
    
    PS: neovisno tko je pobijedio, obojit ćemo dobitni red, stupac, dijagnolu u zelenu boju
    */
    String jeLiIgraGotova(){
        if(b1.getText().equals(prvi) && b4.getText().equals(prvi) && b7.getText().equals(prvi)){ //ako je igrač 1 pobijedio (1. stupac)
            b1.setStyle("-fx-base: #5ddf51;"); //button b1 - oboji pozadinu u zeleno (#5ddf51 predstavlja zelenu boju u HEX vrijednosti)
            b4.setStyle("-fx-base: #5ddf51;"); //učini isto za button b4
            b7.setStyle("-fx-base: #5ddf51;"); //učini isto za button b7
            return prvi; //vrati varijablu "prvi" da znamo tko je pobijedio
            
            /*
            isti kod, samo provjeravamo da li je drugi igrac pobijedio
            */
        }else if(b1.getText().equals(drugi) && b4.getText().equals(drugi) && b7.getText().equals(drugi)){
            b1.setStyle("-fx-base: #5ddf51;");
            b4.setStyle("-fx-base: #5ddf51;");
            b7.setStyle("-fx-base: #5ddf51;");
            return drugi; 
        }else if(b2.getText().equals(prvi) && b5.getText().equals(prvi) && b8.getText().equals(prvi)){
            b2.setStyle("-fx-base: #5ddf51;");
            b5.setStyle("-fx-base: #5ddf51;");
            b8.setStyle("-fx-base: #5ddf51;");
            return prvi;
        }else if(b2.getText().equals(drugi) && b5.getText().equals(drugi) && b8.getText().equals(drugi)){
            b2.setStyle("-fx-base: #5ddf51;");
            b5.setStyle("-fx-base: #5ddf51;");
            b8.setStyle("-fx-base: #5ddf51;");
            return drugi;
        }else if(b3.getText().equals(prvi) && b6.getText().equals(prvi) && b9.getText().equals(prvi)){
            b3.setStyle("-fx-base: #5ddf51;");
            b6.setStyle("-fx-base: #5ddf51;");
            b9.setStyle("-fx-base: #5ddf51;");
            return prvi;
        }else if(b3.getText().equals(drugi) && b6.getText().equals(drugi) && b9.getText().equals(drugi)){
            b3.setStyle("-fx-base: #5ddf51;");
            b6.setStyle("-fx-base: #5ddf51;");
            b9.setStyle("-fx-base: #5ddf51;");
            return drugi;
        }else if(b1.getText().equals(prvi) && b2.getText().equals(prvi) && b3.getText().equals(prvi)){
            b1.setStyle("-fx-base: #5ddf51;");
            b2.setStyle("-fx-base: #5ddf51;");
            b3.setStyle("-fx-base: #5ddf51;");
            return prvi;
        }else if(b1.getText().equals(drugi) && b2.getText().equals(drugi) && b3.getText().equals(drugi)){
            b1.setStyle("-fx-base: #5ddf51;");
            b2.setStyle("-fx-base: #5ddf51;");
            b3.setStyle("-fx-base: #5ddf51;");
            return drugi;
        }else if(b4.getText().equals(prvi) && b5.getText().equals(prvi) && b6.getText().equals(prvi)){
            b4.setStyle("-fx-base: #5ddf51;");
            b5.setStyle("-fx-base: #5ddf51;");
            b6.setStyle("-fx-base: #5ddf51;");
            return prvi;
        }else if(b4.getText().equals(drugi) && b5.getText().equals(drugi) && b6.getText().equals(drugi)){
            b4.setStyle("-fx-base: #5ddf51;");
            b5.setStyle("-fx-base: #5ddf51;");
            b6.setStyle("-fx-base: #5ddf51;");
            return drugi;
        }else if(b7.getText().equals(prvi) && b8.getText().equals(prvi) && b9.getText().equals(prvi)){
            b7.setStyle("-fx-base: #5ddf51;");
            b8.setStyle("-fx-base: #5ddf51;");
            b9.setStyle("-fx-base: #5ddf51;");
            return prvi;
        }else if(b7.getText().equals(drugi) && b8.getText().equals(drugi) && b9.getText().equals(drugi)){
            b7.setStyle("-fx-base: #5ddf51;");
            b8.setStyle("-fx-base: #5ddf51;");
            b9.setStyle("-fx-base: #5ddf51;");
            return drugi;
        }else if((b1.getText().equals(prvi) && b5.getText().equals(prvi) && b9.getText().equals(prvi))){
            b1.setStyle("-fx-base: #5ddf51;");
            b5.setStyle("-fx-base: #5ddf51;");
            b9.setStyle("-fx-base: #5ddf51;");
            return prvi;
        }else if(b1.getText().equals(drugi) && b5.getText().equals(drugi) && b9.getText().equals(drugi)){
            b1.setStyle("-fx-base: #5ddf51;");
            b5.setStyle("-fx-base: #5ddf51;");
            b9.setStyle("-fx-base: #5ddf51;");
            return drugi;
        }else if(b3.getText().equals(prvi) && b5.getText().equals(prvi) && b7.getText().equals(prvi)){
            b3.setStyle("-fx-base: #5ddf51;");
            b5.setStyle("-fx-base: #5ddf51;");
            b7.setStyle("-fx-base: #5ddf51;");
            return prvi;
        }else if(b3.getText().equals(drugi) && b5.getText().equals(drugi) && b7.getText().equals(drugi)){
            b3.setStyle("-fx-base: #5ddf51;");
            b5.setStyle("-fx-base: #5ddf51;");
            b7.setStyle("-fx-base: #5ddf51;");
            return drugi;
            
            /*
            da bismo odredili da nema pobjednika (neriješeno), moramo proći test da nema pobjednika i da su svi buttonsi onemogućeni
            tj. čim dođe do ovog "else if-a" --> nema pobjednika.
            */
        }else if(b1.isDisabled() && b2.isDisabled() && b3.isDisabled() && b4.isDisabled() && b5.isDisabled() && 
                b6.isDisabled() && b7.isDisabled() && b8.isDisabled() && b9.isDisabled()){
            return "nerijeseno"; // vrati string nerijeseno
        }else{
            return ""; // defaultni else (iako do ovoga nikada neće doći, moramo imati konačni else) te zato vraćamo prazan string
            /*
            morali smo staviti jos jedan else if gdje se testira jesu li svi buttoni onemogućeni, jer da nismo i da smo samo ostavili
            "else" svaki klik koji nije "dobitan" bi bio neriješen
            */           
        }
    }
    /*
    Metoda koja će izvršiti logiku kraja igre. Ova metoda prima string naziva ishod i taj će string "dobiti" 
    iz gornje metode "jeLiIgraGotova()" jer ta metoda vraća string
    Ovisno o ishodu, pri kraju igre, iskočit će mali "pop-up" te obavijestiti korisnika o ishodu igre.
    Ažurirat će se rezultat, resetirati tekst od svih buttona te maknuti pozadinska zelena boja pobjedničog niza
    */
    void gameOver(String ishod){
        //koristimo equals metodu jer uspoređujemo stringove
        if(ishod.equals(prvi)){ //ako je ishod jedank varijabli "prvi" tj ("X") tj. igrač 1 je pobijedio
            alertBox(ishod); // pozovi metodu naziva alertBox i proslijedi u nju string "ishod"
            igrac1++; //povećaj rezultat igrača 1
            player1Score.setText(String.valueOf(igrac1)); // ažuriraj label (tekst) i pretvori iz stringa u int
        }else if(ishod.equals(drugi)){
            alertBox(ishod);
            igrac2++;
            player2Score.setText(String.valueOf(igrac2));
        }else if(ishod.equals("nerijeseno")){
            alertBox(ishod);
            tie++; //povećaj neriješeno
            nerijesenoScore.setText(String.valueOf(tie));
        }else{
            
        }
        
        //bez obzira na ishod, ukloni pozadinsku zelenu boju iz svih buttona
        //setStyle je stil buttona, ovo ne utječe na tekst
        //za tekst koristimo setText() metodu
        if(ishod.equals(prvi) || ishod.equals(drugi) || ishod.equals("nerijeseno")){
            b1.setStyle(""); 
            b2.setStyle("");
            b3.setStyle("");
            b4.setStyle("");
            b5.setStyle("");
            b6.setStyle("");
            b7.setStyle("");
            b8.setStyle("");
            b9.setStyle("");
            
            /*
            budući da smo u dijelu programa koji se brine za kraj igre, potrebno je omogućiti
            novu igru, samim time moramo omogućiti sve buttone i maknuti sve križiće i kružiće iz njih
            pod omogućiti sve buttone, mislim na to da se mogu kliknuti
            */
            b1.setDisable(false);
            b1.setText("");
            b2.setDisable(false);
            b2.setText("");
            b3.setDisable(false);
            b3.setText("");
            b4.setDisable(false);
            b4.setText("");
            b5.setDisable(false);
            b5.setText("");
            b6.setDisable(false);
            b6.setText("");
            b7.setDisable(false);
            b7.setText("");
            b8.setDisable(false);
            b8.setText("");
            b9.setDisable(false);
            b9.setText("");
        }
    }
    
    /*
    popunjavamo array listu naziva "labeli" sa svim labelima kako bismo lakše manipulirali svima 
    umjesto jednog po jednog
    */
    void popuniListuLabelsima(){
        labeli.add(player1);
        labeli.add(player1Score);
        labeli.add(player2Score);
        labeli.add(player2);
        labeli.add(nerijeseno);
        labeli.add(nerijesenoScore);
        labeli.add(dvotocka);
    }
    
    /*
    sada kada imamo sve labele i buttone u array listi, lakše je postaviti 
    stil (font, bold/italic, veličine buttona itd...) 
    za sve odjednom umjesto jedan po jedan
    */
    void postaviFontove(){
        status.setFont(Font.font(25)); //veličina fonta
        
        reset.setMinSize(250, 50); //minimalna veličina RESET buttona
        reset.setFont(Font.font(30)); //veličina fonta teksta koji se nalazi u buttonu RESET
        
        /*
        prolazimo kroz čitavu array listu buttona i postavljamo njihov font i veličinu
        */
        for(int i = 0; i < buttoni.size(); i++){
            buttoni.get(i).setMinSize(100, 100);
            buttoni.get(i).setFont(Font.font(40));    
        }
        /*
        ista stvar, samo ovaj put za labele
        */
        for(int i = 0; i < labeli.size(); i++){
            labeli.get(i).setMinSize(50, 20);
            labeli.get(i).setFont(Font.font(30));
        }
    }
    
    /*
    metoda koja će se pobrinuti po završetku igre da obavijesti korisnika tko je pobijedio
    na temelju argumenta tipa string. Potreban nam je taj argument da "pop-up" zna tko je pobijedio
    Dodatak "pop-upu" će biti i zvuk koji će se reproducirati odmah nakon "pop-upa"
    */
    void alertBox(String tkoJePobijedio){
        String musicFile = "alertSound.wav"; // String zvučne datoteke koju ćemo koristiti

        /*
        stvaranje objekta "zvuka" i pretvorba gore navedenog stringa u url
        navedeni zvuk je u mapi projekta pa ne moramo koristiti puni path (C:\Users.....)
        */
        Media zvuk = new Media(new File(musicFile).toURI().toString()); 
        MediaPlayer mediaPlayer = new MediaPlayer(zvuk); //stvaranje objekta mediaPlayera kako bismo mogli reproducirati zvuk
        mediaPlayer.play(); //reproduciraj
        
        Stage window = new Stage(); //kako bi "pop-up" iskočio, potreban nam je novi prozorčić (window)
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Rezultat"); //title "pop-upa"
        window.setMinWidth(250); //minimalna veličina "pop-upa"
        
        
        Label rezultat = new Label(); //potreban na je label za prikaz teksta
        rezultat.setFont(Font.font(30)); //veličina fonta teksta
        
        /*
        ovisno o tome tko je pobijedio, obavijesti korisnika, tj postavi tekst
        */
        if(tkoJePobijedio.equals(prvi)){
            rezultat.setText(player1.getText() + " je pobijedio!");
        }else if(tkoJePobijedio.equals(drugi)){
            rezultat.setText(player2.getText() + " je pobijedio!");
        }else if(tkoJePobijedio.equals("nerijeseno")){
            rezultat.setText("Zavrsilo je nerijeseno!");
        }
        
        //potreban nam je barem neki layout kako bismo dodali lable u kojem obaviještavamo korisnika
        VBox v3 = new VBox(20);
        v3.setPadding(new Insets(10,10,10,10));
        v3.setAlignment(Pos.CENTER);
        v3.getChildren().add(rezultat);
        
        Scene scene = new Scene(v3);
        window.setScene(scene);
        window.showAndWait(); //prikaži i pričekaj naredbu korisnika 
        //ta naredba je obično zatvaranje "pop-upa"
        
    }
}
    

