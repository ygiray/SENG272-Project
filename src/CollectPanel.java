import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

// Bu panel, metrikler için ham verilerin (raw data) girildiği ve anlık olarak 1-5 arası puan hesaplamasının yapıldığı arayüzdür.

public class CollectPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private Scenario currentScenario;

    public CollectPanel() {
        // Layout ayarları: Kenar boşlukları ve dikey-yatay hizalama
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Kullanıcıyı yönlendiren HTML formatlı başlık etiketi
        add(new JLabel("<html><b>Step 4: Collect Raw Data & Calculate Scores</b><br>Double click the 'Value' column to enter data.</html>"), BorderLayout.NORTH);

        // Tablo başlıkları
        String[] columnNames = {"Metric", "Direction", "Range", "Value", "Score (1-5)", "Coeff/Unit"};

        // Tablo Model Özelleştirmesi. Kullanıcı yalnızcq value sütununu değiştirebilir.
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
        // Sadece 3. indexli (Value) sütun düzenlenebilir
                return column == 3;
            }
        };

        table = new JTable(tableModel);

        // Dinamik Hesaplama Dinleyicisi, tablodaki bir hücre değiştiğinde çalışır
        table.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int col = e.getColumn();
            // Eğer değişiklik value sütunundaysa hesaplamayı başlat
            if (col == 3) {
                calculateRowScore(row);
            }
        });
        // Tablonun kaydırılabilir olması için jscrollpane içine ekliyoruz
        add(new JScrollPane(table), BorderLayout.CENTER);
    }


        // Seçilen senaryodaki metrikleri tabloya yükler
    public void loadScenario(Scenario scenario) {
        this.currentScenario = scenario;
        tableModel.setRowCount(0); // Tabloyu temizle
        if (scenario == null) return;

        // Senaryo -> Dimension -> Metric hiyerarşisini gezerek tabloyu doldur
        for (Dimension dim : scenario.getDimensions()) {
            for (Metric m : dim.getMetrics()) {
                tableModel.addRow(new Object[]{
                        m.getName(),
                        m.getDirection(),
                        m.getMinRange() + "-" + m.getMaxRange(),
                        m.getRawValue(),
                        m.getCalculatedScore(),
                        m.getCoefficient() + " / " + m.getUnit()
                });
            }
        }
    }

    // Kullanıcının girdiği ham veriyi alıp, ilgili metrik üzerinden 1-5 puanına dönüştürür
    private void calculateRowScore(int row) {
        try {
            // Tablodaki String değeri sayıya dönüştür
            double value = Double.parseDouble(tableModel.getValueAt(row, 3).toString());
            String metricName = tableModel.getValueAt(row, 0).toString();
            // Metrik nesnesini senaryo içinden bulma
            Metric m = findMetricInScenario(metricName);

            if (m != null) {
                // Metrik sınıfındaki hesaplama mantığını (Higher or Lower is better) çalıştır
                m.calculateScore(value);
                // Hesaplanan puanı tabloya yansıt
                tableModel.setValueAt(m.getCalculatedScore(), row, 4);
            }
        } catch (NumberFormatException ex) {
            // Kullanıcı sayı yerine geçersiz karakter girerse hata ver

        }
    }

    // Tablo satırındaki metrik isminden orijinal Metrik nesnesine ulaşır.
    private Metric findMetricInScenario(String name) {
        for (Dimension d : currentScenario.getDimensions()) {
            for (Metric m : d.getMetrics()) {
                if (m.getName().equals(name)) return m;
            }
        }
        return null;
    }
}