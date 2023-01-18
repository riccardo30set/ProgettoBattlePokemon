class PokemonMove {
    private int power;
    private String type;
    private String classification;
    private String moveName;

    public PokemonMove(int power, String type, String classification, String moveName) {
        this.power = power;
        this.type = type;
        this.classification = classification;
        this.moveName = moveName;
        
    }

    public int getPower() {
        return power;
    }

    public String getType() {
        return type;
    }

    public String getClassification() {
        return classification;
    }

    public String getMoveName() {
        return moveName;
    }

}