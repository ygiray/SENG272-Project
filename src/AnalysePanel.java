import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

// Bu panel, toplanan verilerin analiz edildiği, boyut bazlı puanların hesaplandığı ve sonuçların görselleştirildiği (Gap Analysis & Radar Chart) kısımdır.


public class AnalysePanel extends JPanel {
    private JPanel progressContainer;
    private JLabel lblGapAnalysis;
    private RadarChartPanel radarChart;

    public AnalysePanel() {
        // Ana düzen olarak BorderLayout kullanılarak bileşenler organize edilir
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Boyut puanlarını gösteren Progress Bar alanı
        progressContainer = new JPanel();
        progressContainer.setLayout(new BoxLayout(progressContainer, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(progressContainer);
        scroll.setBorder(BorderFactory.createTitledBorder("Dimension Scores"));
        add(scroll, BorderLayout.NORTH);

        // Grafiksel gösterim alanı (Radar Chart)
        radarChart = new RadarChartPanel();
        add(radarChart, BorderLayout.CENTER);

        // Gap Analizi ve iyileştirme önerileri alanı
        lblGapAnalysis = new JLabel("<html><b>Gap Analysis:</b> Waiting for data...</html>");
        lblGapAnalysis.setBorder(BorderFactory.createTitledBorder("Improvement Suggestions"));
        add(lblGapAnalysis, BorderLayout.SOUTH);
    }

    // Seçilen senaryo üzerindeki tüm boyutların sonuçlarını hesaplar ve arayüzü günceller.
    public void calculateResults(Scenario scenario) {
        if (scenario == null) return;

        progressContainer.removeAll(); // Eski sonuçları temizleme
        Dimension lowestDim = null;
        double minScore = 6.0; // Karşılaştırma için başlangıç değeri

        for (Dimension dim : scenario.getDimensions()) {
            // Boyutun ağırlıklı ortalama puanını hesaplama
            dim.calculateDimensionScore();
            double score = dim.getCalculatedScore();

            // Arayüz için her boyuta özel bir panel ve progress bar oluşturma
            JPanel pnlDim = new JPanel(new BorderLayout());
            pnlDim.add(new JLabel(dim.getName() + ": " + score), BorderLayout.NORTH);

            // JProgressBar (Min: 1.0, Max: 5.0 -> Temsili olarak 10-50 arası değer kullanılır)
            JProgressBar bar = new JProgressBar(10, 50);
            bar.setValue((int)(score * 10));
            bar.setForeground(getColorForScore(score));
            pnlDim.add(bar, BorderLayout.CENTER);
            progressContainer.add(pnlDim);


            // Gap Analizi için en düşük puanlı boyutu belirleme
            if (score < minScore) {
                minScore = score;
                lowestDim = dim;
            }
        }

        // En düşük puanlı boyut üzerinden gap Analizi raporunu oluşturma
        if (lowestDim != null) {
            showGapAnalysis(lowestDim);
        }

        // Radar grafiğine güncel verileri gönder
        radarChart.setDimensions(scenario.getDimensions());

        // User interface'i yeniden çizme
        revalidate();
        repaint();
    }

         // En düşük puanlı boyut için fark (Gap) değerini hesaplar ve ekrana basar.
         private void showGapAnalysis(Dimension dim) {
         double score = dim.getCalculatedScore();

         // Gap Değeri Formülü: Gap = 5.0 - MevcutPuan
        double gap = 5.0 - score;
        String level = getQualityLevel(score);

        // Yazı düzenleme
        String text = "<html><b>Lowest Dimension:</b> " + dim.getName() + " (" + score + ")<br>" +
                "<b>Gap Value:</b> " + String.format("%.1f", gap) + "<br>" +
                "<b>Quality Level:</b> <font color='red'>" + level + "</font><br>" +
                "<i>This dimension has the lowest score and requires the most improvement.</i></html>";
        lblGapAnalysis.setText(text);
    }

    // Sayısal puanı sözel kalite raporuna dönüştürme
    private String getQualityLevel(double score) {
        if (score >= 4.5) return "Excellent";
        if (score >= 3.5) return "Good";
        if (score >= 2.5) return "Needs Improvement";
        return "Poor"; // [cite: 83]
    }

    // Puana göre görsel geri bildirim gönderme (Kırmızı, yeşil, turuncu)
    private Color getColorForScore(double score) {
        if (score >= 4.0) return Color.GREEN;
        if (score >= 3.0) return Color.ORANGE;
        return Color.RED;
    }
}