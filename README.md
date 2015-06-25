# README #

    ______                              ____                        __  __            ___    ___                
   / ____/_____________ _____  ___     / __/________  ____ ___     / /_/ /_  ___     /   |  / (_)__  ____  _____
  / __/ / ___/ ___/ __ `/ __ \/ _ \   / /_/ ___/ __ \/ __ `__ \   / __/ __ \/ _ \   / /| | / / / _ \/ __ \/ ___/
 / /___(__  ) /__/ /_/ / /_/ /  __/  / __/ /  / /_/ / / / / / /  / /_/ / / /  __/  / ___ |/ / /  __/ / / (__  ) 
/_____/____/\___/\__,_/ .___/\___/  /_/ /_/   \____/_/ /_/ /_/   \__/_/ /_/\___/  /_/  |_/_/_/\___/_/ /_/____/  
                     /_/                                                                                        
    _          ____        __               _____                                                               
   (_)___     / __ \__  __/ /____  _____   / ___/____  ____ _________                                           
  / / __ \   / / / / / / / __/ _ \/ ___/   \__ \/ __ \/ __ `/ ___/ _ \                                          
 / / / / /  / /_/ / /_/ / /_/  __/ /      ___/ / /_/ / /_/ / /__/  __/                                          
/_/_/ /_/   \____/\__,_/\__/\___/_/      /____/ .___/\__,_/\___/\___/                                           
                                             /_/                                                                        

### CG_15 ###

Cg_15
Matteo Michele Piazzolla
Luca Maria Ritmo

##Istruzioni per avviare il gioco con interfaccia grafica:##
1) fare run su MainServer per far partire il server di gioco;
2) cliccare su start server sulla colonna di sinistra per fare partire il server;
3) fare run su MainClientGUI, c'è una piccola animazione di apertura, aspettare che termini.
4) nella lobby: scegliere socket o rmi attraverso il tastino di scelta in basso;
5) inserire un nome per la partita da creare, premere ok;
6) inserire il nome della mappa con cui si vuole giocare (le scelte disponibili sono: "fermi", "galilei", "galvani" oppure una mappa inviata al server creata con l'editor delle mappe), premere ok;
7) cliccare sulla partita appena creata nell'interfaccia della lobby;
8) premere "entra in una partita";
9) avviare di nuovo un MainClientGUI per il secondo giocatore;
10) cliccare "aggiorna la lista" per mostrare le partite disponibili;
11) cliccare sulla partita a cui si vuole accedere;
12) cliccare su "entra in una partita";
13) aspettare 10 secondi che il match inizi.
14) durante la partita se un giocatore non fa niente per un minuto si considera il suo turno finito e il giocatore corrente diventa il successivo.

##Istruzioni per avviare il gioco con interfaccia CLI:##
1) fare run su MainServer per far partire il server di gioco;
2) cliccare su start server sulla colonna di sinistra per fare partire il server;
3) fare run su MainClientCLI;
4) inserire 1 per socket e 2 per RMI;
5) fare create game;
6) Inserire un nome per la partita;
7) inserire il nome della mappa con cui si vuole giocare (le scelte disponibili sono: "fermi", "galilei", "galvani" oppure una mappa inviata al server creata con l'editor delle mappe), premere ok;
8) inserendo 2 mostra la lista delle partite disponibili, 3 per entrare in una partita;
9) inserire il nome della partita in cui si vuole giocare;
10) aspettare 10 secondi che il match inizi.
11) durante la partita se un giocatore non fa niente per un minuto si considera il suo turno finito e il giocatore corrente diventa il successivo.



##NOTE:##

1) è possibile chiudere in qualsiasi momento uno dei client e poi riprendere la partita, giocando sullo stesso computer solo l'ultimo client che entra nella partita ha questa possibilità:
-  chiudere il client;
-  fare partire un nuovo "MainClientGUI";
-  una volta aperta la lobby cliccare su "Resume Game"
-  cliccare su "Resume Last Game" nel menù a tendina;
-  analogamente in CLI nella console si può premere 4.

2) abbiamo creato anche un editor di mappe che permette al giocatore di creare la propria mappa personalizzata, per accedere:
-  il server deve essere aperto e attivato;
-  aprire MainClientGUI;
-  selezionare dal menù a tendina "MapEditor" e poi cliccare su "Open Map Editor";
-  cliccando più volte su una cella con il mouse posso cambiare il tipo do settore;
-  per creare una mappa giocabile è necessario inserire almeno un un settore di partenza per umani e alieni e uno di fuga.
-  è possibile salvarsi in locale la mappa creata o ricaricarla in un secondo momento dal menù "File";
-  una volta soddisfatti cliccare su "Server" e poi su "Send To Server";
-  inserire un nome per la mappa appena creata che sarà selezionabile quando si vuole creare una nuova partita.

3) l'utilizzo della carta spotlight ha una piccolo effetto grafico, lo si vede pescando una carta oggetto e usandola. Per la demo abbiamo inserito la carta spotlight ad ogni giocatore umano all'avvio della partita.