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
        players[one].socketOut.println(MessageType.ACTION);
        players[two].socketOut.println(MessageType.ACTION);
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



    public void assignValueFromResponse(int i )throws Exception{
            FileReader reader = new FileReader("src/moves.json");
            Action act=players[i].getPlayerAction();
            JSONObject all = new JSONObject(new JSONTokener(reader));
            JSONObject general=all.getJSONObject(act.getPokemonName());
            ArrayList<String> types=findTypes(general);
            HashMap<String,Integer> baseStats=getBaseStats(general);
            PokemonMove[] moves=findPokemonMoves(general);
            System.out.println(findPokemonMoves(general));
            pokemon[i]=new Pokemon(act.getPokemonName(),general.getInt("pokedex_number"),types,baseStats,moves,act.getHp());
    }



     //     WIN|LOSE|INCOMBAT : 2_CHANGEPK|2_U_MOVE : 1_CHANGEPK|1_U_MOVE : E_HP_PREACTION% : E_HP_POSTACTION% : MY_HP : E_PK_ID : PK_NAME|MV_NAME
    public void createMessageData(){
        player1Data=""+MessageType.INCOMBAT;
        player2Data=""+MessageType.INCOMBAT;
        String moveOrPk1=(getPlayer2TypeAction()==MessageType.USED_MOVE)? players[two].getPlayerAction().getMoveName() : players[two].getPlayerAction().getPokemonName();
        String moveOrPk2=(getPlayer1TypeAction()==MessageType.USED_MOVE)? players[one].getPlayerAction().getMoveName() : players[one].getPlayerAction().getPokemonName();
        /*if(whoActFirst()==one){
            //player 1 primo
            if(getPlayer1TypeAction()==MessageType.USED_MOVE){
                //player usa mossa
                pokemon[two].setHpPost(defenderHp(one,two));
                if(pokemon[two].getHpPost()==0){
                    //aumento pokemon messi ko da playe1 
                    player1Kill++;
                    if(player1Kill==6){
                        player1Data=""+MessageType.WIN;
                        player2Data=""+MessageType.LOSE;
                    }
                }else{
                    //player 1 non mette ko player2 pokemon
                    if(getPlayer2TypeAction()==MessageType.USED_MOVE){
                        //player 2 usa mossa
                        pokemon[one].setHpPost(defenderHp(two,one));
                        if(pokemon[one].getHpPost()==0){
                            //aumento pokemon messi ko da player2
                            player2Kill++;
                            if(player2Kill==6){
                                player2Data=""+MessageType.WIN;
                                player1Data=""+MessageType.LOSE;
                            }
                        }
                    }
                    //player 2 cambia pokemon
                }
            }else{
                //player 1 cambia pokemon
                if(getPlayer2TypeAction()==MessageType.USED_MOVE){
                    //player attacca per secondo
                    pokemon[one].setHpPost(defenderHp(two,one));
                    if(pokemon[one].getHpPost()==0){
                        //aumento pokemon messi ko da player2
                        player2Kill++;
                        if(player2Kill==6){
                            player2Data=""+MessageType.WIN;
                            player1Data=""+MessageType.LOSE;
                        }
                    }
                }
                //player 2 cambia pokemon
            }
        }else{
            //player 2 primo
            if(getPlayer2TypeAction()==MessageType.USED_MOVE){
                //player 2 usa mossa
                pokemon[one].setHpPost(defenderHp(two,one));
                if(pokemon[one].getHpPost()==0){
                    //aumento pokemon messi ko da playe1
                    player2Kill++;
                    if(player2Kill==6){
                        player2Data=""+MessageType.WIN;
                        player1Data=""+MessageType.LOSE;
                    }
                }else{
                    //player 1 sopravvive
                    if(getPlayer1TypeAction()==MessageType.USED_MOVE){
                        //player 1 usa mossa
                        pokemon[two].setHpPost(defenderHp(one,two));
                        if(pokemon[two].getHpPost()==0){
                            //aumento pokemon messi ko da player1 
                            player1Kill++;
                            if(player1Kill==6){
                                player1Data=""+MessageType.WIN;
                                player2Data=""+MessageType.LOSE;
                            }
                        }
                    }
                }
            }else{
                if(getPlayer1TypeAction()==MessageType.USED_MOVE){
                    //player usa mossa
                    pokemon[two].setHpPost(defenderHp(one,two));
                    if(pokemon[two].getHpPost()==0){
                        //aumento pokemon messi ko da playe1 
                        player1Kill++;
                        if(player1Kill==6){
                            player1Data=""+MessageType.WIN;
                            player2Data=""+MessageType.LOSE;
                        }
                    }
                }
            }
        }*/
        int typeAction2=players[1].getPlayerAction().getTypeAction();
        int typeAction1=players[0].getPlayerAction().getTypeAction();
        //     WIN|LOSE|INCOMBAT : E_CHANGEPK|E_U_MOVE : FIRST|SECOND : E_HP_PREACTION% : E_HP_POSTACTION% : MY_HP : E_PK_ID : PK_NAME|MV_NAME
        player1Data+=":"+typeAction2+":"+typeAction1+":"+pokemon[two].getHpPre100()+":"+pokemon[two].getHpPost100()+":"+pokemon[one].getHpPost()+":"+pokemon[two].getPokedexId()+":"+moveOrPk1;
        player2Data+=":"+typeAction1+":"+typeAction2+":"+pokemon[one].getHpPre100()+":"+pokemon[one].getHpPost100()+":"+pokemon[two].getHpPost()+":"+pokemon[one].getPokedexId()+":"+moveOrPk2;
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
        return (int) ((((42)*power*(ATK/DEF)/50)+2)*otherEffects);
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
        System.out.println("hp      "+stats.getInt("hp"));
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
                System.out.println(power+type+classification+moveName);
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