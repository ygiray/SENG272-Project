import java.util.ArrayList;
import java.util.List;


// Bu sınıf, ölçüm sürecinin en üst katmanını temsil eder.

public class Scenario {

    private String scenarioName; // Senaryonun adı
    private String mode; // Senaryonun ait olduğu mod

    // Composition: Senaryo, birden fazla kalite boyutundan oluşur
    private List<Dimension> dimensions;


    // Senaryo nesnesini başlatır ve boyut listesi için bellek ayırır.
    public Scenario(String scenarioName, String mode) {
        this.scenarioName = scenarioName;
        this.mode = mode;
        this.dimensions = new ArrayList<>();
    }

    // Senaryo hiyerarşisine yeni bir kalite boyutu ekler.
    public void addDimension(Dimension dimension) {
        this.dimensions.add(dimension);
    }

    // Getters
    public String getScenarioName() { return scenarioName; }
    public String getMode() { return mode; }
    public List<Dimension> getDimensions() { return dimensions; }

    @Override
    public String toString() {
        return scenarioName;
    }
}










