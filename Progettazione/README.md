# PROGETTO POKEMON
## MEMBRI DEL GRUPPO:
Marco Mazzocchi, Fadda Emanuel, Filippo Sighinolfi e Riccardo D’esposito
## DESCRIZIONE DEL PROGETTO:
Il progetto consiste nella creazione di un’applicazione Android basata sui Pokemon. L’app avrà 2 funzioni principali, la prima si limiterà a mostrare un’enciclopedia di tutti i Pokemon attualmente creati con relative statistiche, tipi, sprite e descrizione. La seconda si concentrerà sulla creazione di un simulatore di battaglie Pokemon tra 2 utenti (nella stessa rete locale e internet). Ogni client avrà la possibilità di creare (o entrare in) una stanza di attesa, in cui sarà possibile attendere per l’arrivo del giocatore avversario. Ogni stanza sarà formata da un codice, necessario al secondo giocatore per entrare nella stanza; il codice è formato da un nome scelto dal creatore della stanza e un codice generato automaticamente dal server (ES.  GIANNI#1234). Il secondo giocatore per entrare dovrà soltanto digitare codice della stanza desiderata in un pop-up apposito. All’interno della stanza ci sarà una sezione dedicata per scegliere i 6 Pokémon del proprio team, con relative mosse. Quando entrambi i player avranno scelto i loro team sarà possibile cominciare la battaglia. La battaglia richiederà di avere il telefono in orizzontale, con gli sprite dei Pokémon che si affrontano e la possibilità di attaccare o cambiare Pokémon.
## LINGUAGGIO UTILIZZATO:
Java per client e server in locale e Javascript per server online.
## USER STORIES & ISSUES:
- l’app deve avere 3 sezioni con una navbar.
### Il programma deve avere una sezione Pokedex
- Creare una activity.
- Inserire un oggetto di tipo list
- Scaricare gli sprites
- Effettuare una rest Call
- Recuperare dalla rest call il nome e l’id dei pokemon 
- Modificare L’oggetto list in modo da avere 3 parametri per record (sprite,nome,id)
### Il pokedex deve avere una barra di ricerca
- Aggiungere una searchView
- Scollegare e ricollegare l’adapter alla list con la stringa presente nella searchView
### Cliccando su un pokemon deve aprirsi la sua scheda dati
- Creare una activity
- L’activity si dovrà aprire cliccando un record della list
- Effettuare una Rest call per prendere statistiche, tipo e descrizione
- Inserire le statistiche in delle progess-bar
- Aggiungere un imageView dove visualizzare il pokemon
- Al click sull’imageView si deve visualizzare il retro del pokemon
### Deve essere presente una sezione dedicata alla stanza di attesa pre-battaglia
- La seconda activity/sezione deve avere 3 bottoni (sezione dedicata alla battaglia).
- Primo bottone per creare una stanza Online
- Secondo Bottone per creare una stanza locale
- con Stanza Online necessario connetersi al Server
- In Locale se si è il creatore della stanza si crea un Thread che gestirà il server
- In Locale se si partecipa a una stanza si crea un Thread che si connetterà al server
- Il client creatore della stanza dovrà inserire in una textView il nome della stanza
#### Deve essere possibile per il secondo giocatore unirsi alla stanza
- premendo il terzo bottone (partecipa) si aprira la sezione per entrare nelle stanze
- Deve comparire una lista delle stanze Online
- Deve comparire una lista delle stanze locali
- Deve essere possibile scansionare un QR per giocare con un giocatore vicino senza rete locale
- una volta entrati in una stanza è possibile scegliere la squadra
- la squadra va scelta da una list
- per ogni pokemon deve essere presente una list con le sue mosse (ricavate da file locale o API)
- premendo un bottone battaglia si avvia il gioco
#### I 2 giocatori devono avere la possibilità di sfidarsi
- creare una activity
- avere il telefono in orizzontale
- devono essere presenti gli sprite dei 2 pokemon e uno sfondo
- entrambi i giocatori scelgono tra 4 bottoni una mossa o decidono di cambiare pokemon
- dopo aver scelto le mosse, si nascodono i menu e vengono mostrati i danni inflitti dalle mosse
- quando un giocatore rimane senza pokemon la battaglia termina e si ritorna alla stanza
### Deve essere presente una sezione di personalizzazione del profilo
- la terza sezione/activity permette di configurare il gioco e il profilo personale
- deve essere possibile inserire il nome utente
- deve essere possibile inserire un immagine profilo
- deve essere possibile inserire una descrizione
- si può modificare l'url del server online
## Valutazione dei rischi
### Client
- Scaricamento del pokédex
    - Rischio: 1
    - Impatto: 3
    - Danno complessivo: 3

- Barra di ricerca
    - Rischio: 2
    - Impatto: 1
    - Danno complessivo: 2

- Visualizzazione statistiche pokémon
    - Rischio: 1
    - Impatto: 3
    - Danno complessivo: 3

- Corretta comunicazione con il server:
    - Rischio: 3
    - Impatto: 3
    - Danno complessivo: 9

- Ricerca di partite in LAN:
    - Rischio: 3
    - Impatto: 3
    - Danno complessivo: 9

- Attivazione Local Only Hotspot:
    - Rischio: 3
    - Impatto: 3
    - Danno complessivo: 9

- Scansione QR code e connessione alla relativa rete:
    - Rischio: 3
    - Impatto: 3
    - Danno complessivo: 9
- Selezione squadra:
    - Rischio: 3
    - Impatto: 3
    - Danno complessivo: 9
- Creazione interfaccia battaglia:
    - Rischio: 2
    - Impatto: 3
    - Danno complessivo: 6
- Selezione ed invio al server dell'immagine profilo:
    - Rischio: 2
    - Impatto: 1
    - Danno complessivo: 2
### Server
- Gestire più stanze:
    - Rischio: 1
    - Impatto: 3
    - Danno complessivo: 3
- Corretta valutazione degli attacchi:
    - Rischio: 2
    - Impatto: 3
    - Danno complessivo: 6
- Corretta comunicazione con il client:
    - Rischio: 3
    - Impatto: 3
    - Danno complessivo: 9
- Creazione server basato su richieste HTTP:
    - Rischio: 3
    - Impatto: 3
    - Danno complessivo: 9
## Sprint
### Settimana 1
#### Client
- Creazione interfaccia grafica (eccetto partita)
- Scaricamento Pokédex
- Implementazione creazione partite in LAN
- Implementazione ricerca/unione partite in LAN
- Implementazione scelta squadra
#### Server
##### Server HTTP
- Implementazione download Pokédex
##### Server Socket
-  Gestione pre-partita
    - Scambio nome partita
    - Scambio squadre dei due giocatori
    - Scambio profili dei due giocatori
### Settimana 2
#### Client
- Creazione interfaccia grafica battaglia
- Implementazione Local Only Hotspot
- Implementazione scansione codice QR
- Implementazione battaglia funzionante tramite server socket
    - Invio azione in partita
    - Ricezione risultato
    - Visualizzazione risultato
#### Server Socket
- Gestione della partita:
    - Avvio partita
    - Ricezione azioni di entrambi i giocatori
    - Calcolo del risultato
    - Invio del risultato ai due client
### Settimana 3
- Gestione eventuale backlog
#### Client
- Gestione partita online
    - Scambio nome partita
    - Scambio squadre
    - Scambio giocatori
    - Invio azione in partita
    - Ricezione risultato
    - Visualizzazione risultato
#### Server HTTP
- Gestione pre partita:
    - Scambio nome partita
    - Scambio squadre
    - Scambio profili
- Gestione partita
    - Avvio partita
    - Ricezione azioni di entrambi i giocatori
    - Calcolo del risultato
    - Invio del risultato ai due client
### Settimana 4
- Gestione eventuale backlog
- Miglioramenti GUI nel client
- Risoluzione eventuali problemi
- Implementazione eventuali funzionalità extra
