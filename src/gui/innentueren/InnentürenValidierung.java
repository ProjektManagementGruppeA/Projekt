package gui.innentueren;

public class InnentuerenValidierung {

    // Methoden zur Überprüfung des Gebäudezustands
    public static boolean hasDachgeschoss(boolean hasDachgeschoss) {
        return hasDachgeschoss;    
    }

    public static boolean isObergeschoss(boolean isObergeschoss) {
        return isObergeschoss;    
    }

    public static boolean isErdgeschoss(boolean isErdgeschoss) {
        return isErdgeschoss;    
    }

    public static boolean isKeller(boolean isKeller) {
        return isKeller;    
    }

    public static boolean validiereGlasKlar(int i, boolean isKeller, boolean isDachgeschoss, boolean isErdgeschoss, boolean isObergeschoss) {
        boolean isValid = true;
        if(isKeller)
            isValid = isDachgeschoss ? i == 1 : i == 2;

        if(isErdgeschoss)
            isValid = i == 0 || i == 1;
        if(isObergeschoss)
            isValid = i == 3 || i == 4;

        if(isDachgeschoss)
            isValid = i >= 0 && i <= 2;
        return isValid;
    }
    
     public static boolean validiereGlasKlar{
       return true;
	}

    public static boolean validiereGlasMilch(int i, boolean isKeller, boolean isDachgeschoss, boolean isErdgeschoss, boolean isObergeschoss) {
        boolean isValid = true;
        if(isKeller)
            isValid = isDachgeschoss ? i == 1 : i == 2;

        if(isErdgeschoss)
            isValid = i == 0 || i == 1;
        if(isObergeschoss)
            isValid = i == 3 || i == 4;

        if(isDachgeschoss)
            isValid = i >= 0 && i <= 2;
        return isValid;
    }

       public static boolean validiereGlasMilch(int i){
        return true;
	  }

    public static boolean validiereGarage(int i) {
        return true;
    }
}

