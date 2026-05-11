
// Bu sınıf, ölçüm sürecindeki her bir metriği temsil eder ve ham verilerin 1-5 puan aralığına normalizasyonunu gerçekleştirir.


public class Metric {

    // Metrik Özellikleri
    private String name;
    private int coefficient;
    private String direction; // "Higher is better" veya "Lower is better"
    private double minRange;
    private double maxRange;
    private String unit;

    // Hesaplanan Değerler
    private double rawValue;
    private double calculatedScore;

    // Metrik nesnesini temel parametrelerle ilklendirir.
    public Metric(String name, int coefficient, String direction, double minRange, double maxRange, String unit) {
        this.name = name;
        this.coefficient = coefficient;
        this.direction = direction;
        this.minRange = minRange;
        this.maxRange = maxRange;
        this.unit = unit;
    }

    // Ham veriyi 1 ile 5 arasında bir puana dönüştürür. (Normalizasyon Formülü: 1 + ((değer - min) / (max - min)) * 4)
    public void calculateScore(double value) {
        this.rawValue = value;
        double score;

        // 1. Normalizasyon: Ham değeri 1.0 - 5.0 aralığına haritala
        if (direction.equalsIgnoreCase("Higher is better") || direction.contains("↑")) {
            // Değer arttıkça puan artar
            score = 1 + ((value - minRange) / (maxRange - minRange)) * 4;
        } else {
            // Değer arttıkça puan azalır
            score = 5 - ((value - minRange) / (maxRange - minRange)) * 4;
        }
        // Puanın 1 ile 5 sınırları dışına çıkmamasını sağlama
        if (score > 5) score = 5;
        if (score < 1) score = 1;


        // 3. Rounding: Puanı en yakın 0.5 değerine yuvarla
        // Matematiksel mantık: (Değeri 2 ile çarp, yuvarla, tekrar 2'ye böl)
        this.calculatedScore = Math.round(score * 2) / 2.0;
    }

    // Getters
    public String getName() { return name; }
    public int getCoefficient() { return coefficient; }
    public String getDirection() { return direction; }
    public double getCalculatedScore() { return calculatedScore; }
    public String getUnit() { return unit; }
    public double getRawValue() { return rawValue; }
    public double getMinRange() { return minRange; }
    public double getMaxRange() { return maxRange; }
}




