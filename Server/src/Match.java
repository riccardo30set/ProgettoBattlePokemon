import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
public class Match{
    private String name;
    private PlayerHandler[] players = new PlayerHandler[2];
    private int player1Kill;
    private int player2Kill;
    private int clash;
    private Pokemon[] pokemon = new Pokemon[2];
    public static final int one=0;
    public static final int two=1;
    //risposta da inviare a i client
    String player1Data;
    String player2Data;
    public Match(String name){
        System.out.println("Creata partita "+name);
        this.name = name;
        this.player1Kill=0;
        this.player2Kill=0;
        this.clash=0;
    }

    /**
     * Adds a player to the match in the specified position.
     * Use position 0 for game host
     * @param playerNumber
     * @param player
     */
    public void addPlayer(int playerNumber, PlayerHandler player){
        System.out.println(player.getPlayer().getName()+" partecipa alla partita "+name);
        players[playerNumber] = player;
    }

    /**
     * 
     * @param me The player asking for the opponent
     * @return
     */
    public PlayerHandler getOpponent(PlayerHandler me){
        if(me==players[one]){
            return players[two];
        }
        return players[one];
    }

    public void start(){
        for (PlayerHandler player : players) {
            player.socketOut.println(MessageType.GAME_STARTED);
        }
    }

    public void playerMove(){
        clash++;
        if(clash==2){
            try {
                startEvent();
            } catch (Exception e) {
                System.out.println("errore lettura json");
                e.printStackTrace();
            }
            
            clash=0;
        }
    }

    public void startEvent(){
        try{
            for(int i=0;i<players.length;i++){
                assignValueFromResponse(i);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        createMessageData();
        System.out.println(player1Data);
        System.out.println(player2Data);
        if(player1Kill==6){
            players[one].socketOut.println(MessageType.WIN);
            players[two].socketOut.println(MessageType.LOSE);
        }else if(player2Kill==6){
            players[two].socketOut.println(MessageType.WIN);
            players[one].socketOut.println(MessageType.LOSE);
        }else{
            players[one].socketOut.println(MessageType.ACTION);
            players[two].socketOut.println(MessageType.ACTION);
        }
        players[one].socketOut.println(player1Data);
        players[two].socketOut.println(player2Data);
        /* 
        if(pokemon[one].getHpPost()==0 && player2Kill!=6){
            String rispostaK0=players[one].socketIn.nextLine();
            players[one].writeAction(rispostaK0);
            try {
                assignValueFromResponse(one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String newPokemon=MessageType.INCOMBAT+":"+MessageType.CHANGE_POKEMON+":"+player1WhenAttack()+":"+pokemon[one].getHpPre100()+":"+pokemon[one].getHpPost100()+":"+pokemon[two].getHpPost()+":"+pokemon[one].getPokedexId()+":"+pokemon[one].getPokemonName();
            players[two].socketOut.println(newPokemon);
        }
        if(pokemon[two].getHpPost()==0 && player1Kill!=6){
            String rispostaK0=players[two].socketIn.nextLine();
            players[two].writeAction(rispostaK0);
            try {
                assignValueFromResponse(one);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String newPokemon=MessageType.INCOMBAT+":"+MessageType.CHANGE_POKEMON+":"+player2WhenAttack()+":"+pokemon[two].getHpPre100()+":"+pokemon[two].getHpPost100()+":"+pokemon[two].getHpPost()+":"+pokemon[one].getPokedexId()+":"+pokemon[one].getPokemonName();
            players[two].socketOut.println(newPokemon);
        }
        */
    }

    public void pokemonFromKO(int playerID){
        for(int i=0;i<players.length;i++){
            if(players[i].getPlayerId()==playerID){
                try {
                    assignValueFromResponse(i);
                } catch (Exception e) {
                    System.out.println("impossibile leggere dal json");
                }
                int enemy=one;
                if(players[i]==players[enemy]){
                    enemy=two;
                }
                String pk=players[i].getPlayerAction().getPokemonName();
                String mes=MessageType.INCOMBAT+":"+MessageType.CHANGE_POKEMON+":"+MessageType.CHANGE_POKEMON+":"+pokemon[i].getHpPre100()+":"+pokemon[i].getHpPost100()+":"+pokemon[enemy].getHpPost()+":"+pokemon[i].getPokedexId()+":"+"nothing"+":"+pk;
                players[enemy].socketOut.println(MessageType.ONLY_ONE_ACT);
                System.out.println(mes);
                players[enemy].socketOut.println(mes);
            }
        }
    }

    public void assignValueFromResponse(int i )throws Exception{
            FileReader reader = new FileReader("src/moves.json");
            Action act=players[i].getPlayerAction();
            JSONObject all = new JSONObject(new JSONTokener(reader));
            JSONObject general=all.getJSONObject(act.getPokemonName());
            ArrayList<String> types=findTypes(general);
            HashMap<String,Integer> baseStats=getBaseStats(general);
            PokemonMove[] moves=findPokemonMoves(general);
            pokemon[i]=new Pokemon(act.getPokemonName(),general.getInt("pokedex_number"),types,baseStats,moves,act.getHp());
    }



     //     WIN|LOSE|INCOMBAT : E_CHANGEPK|E_U_MOVE : CHANGEPK|U_MOVE : E_HP_PREACTION% : E_HP_POSTACTION% : MY_HP : E_PK_ID : MV_NAME : PK_NAME
    public void createMessageData(){
        player1Data=""+MessageType.INCOMBAT;
        player2Data=""+MessageType.INCOMBAT;
        //mosse (se usate)
        String move1=(getPlayer2TypeAction()==MessageType.USED_MOVE)? players[two].getPlayerAction().getMoveName() : "nothing";
        String move2=(getPlayer1TypeAction()==MessageType.USED_MOVE)? players[one].getPlayerAction().getMoveName() : "nothing";
        //pokemon in campo
        String pk1=players[two].getPlayerAction().getPokemonName();
        String pk2=players[one].getPlayerAction().getPokemonName();
        //ordine eventi
        if(whoActFirst()==one){
            if(getPlayer1TypeAction()==MessageType.USED_MOVE){
                pokemon[two].setHpPost(defenderHp(one,two));
            }
            if(getPlayer2TypeAction()==MessageType.USED_MOVE && pokemon[two].getHpPost()!=0){
                pokemon[one].setHpPost(defenderHp(two,one));
            }
        }else{
            if(getPlayer2TypeAction()==MessageType.USED_MOVE){
                pokemon[one].setHpPost(defenderHp(two,one));
            }
            if(getPlayer1TypeAction()==MessageType.USED_MOVE && pokemon[one].getHpPost()!=0){
                pokemon[two].setHpPost(defenderHp(one,two));
            }
        }

        //conteggio KO
        if(pokemon[two].getHpPost()==0){
            player1Kill++;
        }
        if(pokemon[one].getHpPost()==0){
            player2Kill++;
        }

        //controllo vittoria e perdita

        if(player1Kill==6){
            player1Data=""+MessageType.WIN;
            player2Data=""+MessageType.LOSE;
        }
        if(player2Kill==6){
            player2Data=""+MessageType.WIN;
            player1Data=""+MessageType.LOSE;
        }
        //creazione del messaggio in base ai dati appena raccolti
        int typeAction2=players[1].getPlayerAction().getTypeAction();
        int typeAction1=players[0].getPlayerAction().getTypeAction();
        //     WIN|LOSE|INCOMBAT : E_CHANGEPK|E_U_MOVE : CHANGEPK|U_MOVE : E_HP_PREACTION% : E_HP_POSTACTION% : MY_HP : E_PK_ID : MV_NAME : PK_NAME
        player1Data+=":"+typeAction2+":"+typeAction1+":"+pokemon[two].getHpPre100()+":"+pokemon[two].getHpPost100()+":"+pokemon[one].getHpPost()+":"+pokemon[two].getPokedexId()+":"+move1+":"+pk1;
        player2Data+=":"+typeAction1+":"+typeAction2+":"+pokemon[one].getHpPre100()+":"+pokemon[one].getHpPost100()+":"+pokemon[two].getHpPost()+":"+pokemon[one].getPokedexId()+":"+move2+":"+pk2;
        System.out.println(player1Data);
        System.out.println(player2Data);
    }
    




    //ritorna il danno messo dal player 1 al player 2 o viceversa
    public int defenderHp(int attacker, int defender){
        String moveName=players[attacker].getPlayerAction().getMoveName();
        PokemonMove move=pokemon[attacker].getPokemonMoveByName(moveName);
        int powerMove=move.getPower();
        int ATK=move.getClassification().equals("special")? pokemon[attacker].getStat("special-attack"):pokemon[attacker].getStat("attack");
        int DEF=move.getClassification().equals("special")? pokemon[defender].getStat("special-defense"):pokemon[attacker].getStat("defense");
        int damage=moveDamage(powerMove, ATK, DEF, move.getType(), pokemon[attacker].getTypes(), pokemon[defender].getTypes());
        if(damage>pokemon[defender].getHpPre()){
            return 0;
        }else{
            return pokemon[defender].getHpPre()-damage;
        }
    }


    //calcolo danno effetuato da una mossa contro un determinato pokemon
    public int moveDamage(int power, int ATK, int DEF,String moveType, ArrayList<String> pokemonAttackerTypes,ArrayList<String> pokemonDefenderTypes){
        double otherEffects=1;
        for(int i=0;i<pokemonDefenderTypes.size();i++){
            otherEffects*=Moltiplicatore.getMultiplier(moveType, pokemonDefenderTypes.get(i));
        }
        //STAB
        otherEffects*=stab(moveType,pokemonAttackerTypes);
        //CRIT
        Random rand=new Random();
        if(rand.nextInt(24)==0)
            otherEffects*=1.5;
        otherEffects*= (((double)rand.nextInt(15)/100.0)+0.85);
        return (int)(((((28.57*ATK*power)/DEF)/50)+2)*otherEffects);
        //return (int) ((((42)*power*(ATK/DEF)/50)+2)*otherEffects);
    }



    //controlla la presenza dello stab e in caso fornisce il valore 1.5
    public double stab(String moveType, ArrayList<String> pokemonTypes){
        for(int i=0;i<pokemonTypes.size();i++){
            if(moveType.equalsIgnoreCase(pokemonTypes.get(i))){
                return 1.5;
            }
        }
        return 1;
        
    }



    //trova i tipi di un pokemon tramite il json
    public ArrayList<String> findTypes(JSONObject general)throws Exception{
        JSONArray temp=general.getJSONArray("types");
        ArrayList<String> types=new ArrayList<String>();
        for(int i=0;i<temp.length();i++){
            types.add(temp.getString(i));
        }
        return types;
    }



    //trova le statisitiche di un pokemon tramite il json
    public  HashMap<String, Integer> getBaseStats(JSONObject general)throws Exception{
        HashMap<String, Integer> baseStats= new HashMap<String,Integer>();
        JSONObject stats = general.getJSONObject("base_stats");
        baseStats.put("hp", stats.getInt("hp"));
        baseStats.put("attack", stats.getInt("attack"));
        baseStats.put("defense", stats.getInt("defense"));
        baseStats.put("special-attack", stats.getInt("special-attack"));
        baseStats.put("special-defense", stats.getInt("special-defense"));
        baseStats.put("speed", stats.getInt("speed"));
        return baseStats;
    }


    //trova le mosse di un pokemon tramite il json
    public PokemonMove[] findPokemonMoves(JSONObject general)throws Exception{
            JSONArray allMoves=general.getJSONArray("moves");
            PokemonMove[] moves=new PokemonMove[4];
            for(int i = 0; i < allMoves.length(); i++) {
                JSONObject move = allMoves.getJSONObject(i);
                int power = move.getInt("power");
                String type = move.getString("type");
                String classification = move.getString("classification");
                String moveName = move.getString("move_name");
                moves[i]=new PokemonMove(power,type,classification,moveName);
            }
            return moves;
    }


    public int whoActFirst(){
        if(players[one].getPlayerAction().getTypeAction()==MessageType.CHANGE_POKEMON){
            return one;
        }else if(players[two].getPlayerAction().getTypeAction()==MessageType.CHANGE_POKEMON){
            return two;
        }else if(player1WhenAttack()==MessageType.FIRST){
            return one;
        }else{
            return two;
        }
    }


    //ritorna un intero che rappresenta il MessageType.FIRST or MessageType.SECOND
    public int player1WhenAttack(){
        if(pokemon[one].getStat("speed")>=pokemon[two].getStat("speed")){
            return MessageType.FIRST;
        }else{
            return MessageType.SECOND;
        }     
    }



    //ritorna un intero che rappresenta il MessageType.FIRST or MessageType.SECOND
    public int player2WhenAttack(){
        if(pokemon[two].getStat("speed")>pokemon[one].getStat("speed")){
            return MessageType.FIRST;
        }else{
            return MessageType.SECOND;
        }     
    }



    public int getPlayer1TypeAction(){
        return players[one].getPlayerAction().getTypeAction();    
    }
    public int getPlayer2TypeAction(){
        return players[two].getPlayerAction().getTypeAction();    
    }



    
}