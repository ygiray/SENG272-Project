import java.util.ArrayList;
import java.util.List;

//Bu sınıf, belirli bir kalite boyutunu temsil eder. İçerdiği metriklerin puanlarını kullanarak ağırlıklı ortalama yöntemiyle boyutun toplam kalite puanını hesaplar.

    public class Dimension {
        private String name;
        private int coefficient; // Boyutun senaryo içindeki ağırlığı (0-100)
        private List<Metric> metrics;
        private double calculatedScore; // Boyutun ağırlıklı ortalama puanı (1.0 - 5.0)


        // Boyut nesnesini temel parametrelerle ilklendirir
        public Dimension(String name, int coefficient) {
            this.name = name;
            this.coefficient = coefficient;
            this.metrics = new ArrayList<>();
        }

        // Boyuta yeni bir metrik nesnesi ekler.
        public void addMetric(Metric metric) {
            this.metrics.add(metric);
        }


        // Boyut altındaki tüm metriklerin puanlarını ve ağırlıklarını kullanarak toplam boyut puanını hesaplar
        public void calculateDimensionScore() {
            double totalWeightedScore = 0.0;
            int totalCoefficient = 0;

            // Her bir metriğin bireysel puanını ve kendi ağırlığını işleme alma
            for (Metric metric : metrics) {
                totalWeightedScore += (metric.getCalculatedScore() * metric.getCoefficient());
                totalCoefficient += metric.getCoefficient();
            }


            if (totalCoefficient > 0) {
                // Ağırlıklı ortalamayı hesapla ve sonuca ata
                this.calculatedScore = totalWeightedScore / totalCoefficient;
            }
            else {
                this.calculatedScore = 0.0;
            }
        }


        // Getters
        public String getName() { return name; }
        public int getCoefficient() { return coefficient; }
        public List<Metric> getMetrics() { return metrics; }
        public double getCalculatedScore() { return calculatedScore; }
    }








