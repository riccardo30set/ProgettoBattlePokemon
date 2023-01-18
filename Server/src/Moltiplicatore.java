public class Moltiplicatore {
    // create an array that contains all possible types
    private static final String[] TYPES = {"normal", "fire", "water", "electric", "grass", "ice", "fighting", "poison", "ground", "flying", "psychic", "bug", "rock", "ghost", "dragon", "dark", "steel", "fairy"};

    // create a 2D array that contains all type matchups and their corresponding multipliers
    private static final double[][] MULTIPLIERS = {
        /*normal*/    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0.5, 0, 1, 1, 0.5, 1},
        /*fire*/      {1, 0.5, 0.5, 1, 2, 2, 1, 1, 1, 1, 1, 2, 0.5, 1, 0.5, 1, 2, 1},
        /*water*/     {1, 2, 0.5, 1, 0.5, 1, 1, 1, 2, 1, 1, 1, 2, 1, 0.5, 1, 1, 1},
        /*electric*/  {1, 1, 2, 0.5, 0.5, 1, 1, 1, 0, 2, 1, 1, 1, 1, 0.5, 1, 1, 1},
        /*grass*/     {1, 0.5, 2, 1, 0.5, 1, 1, 0.5, 2, 0.5, 1, 0.5, 2, 1, 0.5, 1, 0.5, 1},
        /*ice*/       {1, 0.5, 0.5, 1, 2, 0.5, 1, 1, 2, 2, 1, 1, 1, 1, 2, 1, 0.5, 1},
        /*fighting*/  {2, 1, 1, 1, 1, 2, 1, 0.5, 1, 0.5, 0.5, 0.5, 2, 0, 1, 2, 2, 0.5},
        /*poison*/    {1, 1, 1, 1, 2, 1, 1, 0.5, 0.5, 1, 1, 2, 1, 0.5, 1, 1, 0, 2},
        /*ground*/    {1, 2, 1, 2, 0.5, 1, 1, 2, 1, 0, 1, 0.5, 2, 1, 1, 1, 2, 1},
        /*flying*/    {1, 1, 1, 0.5, 2, 1, 2, 1, 1, 1, 1, 2, 0.5, 1, 1, 1, 0.5, 1},
        /*psychic*/   {1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 0.5, 1, 1, 1, 1, 0, 0.5, 1},
        /*bug*/       {1, 0.5, 1, 1, 2, 1, 0.5, 0.5, 1, 0.5, 2, 1, 1, 0.5, 1, 2, 0.5, 0.5},
        /*rock*/      {1, 2, 1, 1, 1, 2, 0.5, 1, 0.5, 2, 1, 2, 1, 1, 1, 2, 1},
        /*ghost*/     {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 0.5, 1, 1},
        /*dragon*/    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 0.5, 0},
        /*dark*/      {1, 1, 1, 1, 1, 1, 0.5, 1, 1, 1, 2, 1, 1, 2, 1, 0.5, 1, 0.5},
        /*steel*/    {1, 0.5, 0.5, 0.5, 1, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 0.5, 2},
        /*fairy*/    {1, 0.5, 1, 1, 1, 1, 2, 0.5, 1, 1, 1, 1, 1, 1, 2, 2, 0.5, 1}
    };
    public static double getMultiplier(String attackType, String defenseType) {
        // get the index of the attack type and defense type in the TYPES array
        int attackIndex = -1;
        int defenseIndex = -1;
        for (int i = 0; i < TYPES.length; i++) {
            if (TYPES[i].equals(attackType)) {
                attackIndex = i;
            }
            if (TYPES[i].equals(defenseType)) {
                defenseIndex = i;
            }
        }
        // return the multiplier based on the index
        return MULTIPLIERS[attackIndex][defenseIndex];
    }
}