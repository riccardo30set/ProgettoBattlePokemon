import java.util.ArrayList;
import java.util.HashMap;

class Pokemon {
    private String pokemonName;
    private int pokedexId;
    private int hpPre;
    private int hpPost;
    private ArrayList<String> types;
    private HashMap<String, Integer> baseStats;
    private PokemonMove[] moves;
    public Pokemon(String name, int id, ArrayList<String> types, HashMap<String, Integer> baseStats,PokemonMove[] moves, int hpPre) {
        this.pokemonName = name;
        this.pokedexId = id;
        this.types = types;
        this.baseStats = baseStats;
        this.hpPre=hpPre;
        this.hpPost=hpPre;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public int getPokedexId() {
        return pokedexId;
    }

    public ArrayList<String> getTypes() {
        return types;
    }
    public int getHpPre() {
        return hpPre;
    }
    public void setHpPost(int hp) {
        this.hpPre=hp;
    }
    public int getHpPost() {
        return hpPost;
    }
    public HashMap<String, Integer> getBaseStats() {
        return baseStats;
    }
    public int getBaseStat(String stat) {
        return baseStats.get(stat);
    }
    public int getStat(String stat) {
        return calcoloPureStats(baseStats.get(stat));
    }
    public int calcoloPureStats(int base){
        return  (int) (Math.floor(0.01*(2*base+31+Math.floor(0.25*85))*100)+5);
    }
    public PokemonMove getPokemonMoveByName(String name){
        for(int i=0;i<moves.length;i++){
            if(moves[i].getMoveName().equals(name)){
                return moves[i];
            }
        }
        return moves[0];
    }
}