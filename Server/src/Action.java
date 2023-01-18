public class Action{
    
    private int typeAction;
    private String pokemonName;
    private int hp;
    private String moveName;
    // USED_MOVE|CHANGED_PK:PKNAME:HP:[MV_NAME]
    public Action (int typeAction,String pokemonName,String hp,String moveName){
        this.typeAction=typeAction;
        this.pokemonName=pokemonName;
        this.hp=Integer.parseInt(hp);
        this.moveName=moveName;
    }
    public Action (int typeAction,String pokemonName,String hp){
        this.typeAction=typeAction;
        this.pokemonName=pokemonName;
        this.hp=Integer.parseInt(hp);
    }
    public int getTypeAction() {
        return typeAction;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public int getHp() {
        return hp;
    }

    public String getMoveName() {
        return moveName;
    }
}