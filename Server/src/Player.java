import java.util.Collection;
import java.util.HashMap;

public class Player {
    private String name;
    private String description;
    private int ID;
    private HashMap<Integer, String> pokemons;

    
    public Player(String name, String description, int ID){
        this.name = name;
        this.description = description;
        this.ID = ID;
    }
    
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public int getID(){
        return this.ID;
    }

    /**
     * Sets a pokemon to a slot
     * @param slot
     * @param pokemon
     */
    public void setPokemon(int slot, String pokemon){
        this.pokemons.put(slot, pokemon);
    }

    /**
     * @return All player's pokemons as a String Collecion
     */
    public Collection<String> getPokemons(){
        return pokemons.values();
    }

}
