package carmenromano.capstone_project.enums;

public enum CategoryProduct {
    TREKKING("Zaini da trekking"),
    VIAGGIO("Zaini da viaggio"),
    GIORNO("Zaini da giorno"),
    CAMPEGGIO("Zaini da campeggio"),
    SPORT("Zaini sportivi"),
    LAPTOP("Zaini per laptop"),
    BAMBINO("Zaini per bambini"),
    MODA("Zaini da moda"),
    IMPERMEABILI("Zaini impermeabili"),
    ECO_SOSTENIBILI("Zaini eco-sostenibili");

    private final String displayName;

    CategoryProduct(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
