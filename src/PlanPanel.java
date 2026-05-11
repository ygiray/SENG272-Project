import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
// Sadece Okunabilir (Read-Only) bir özet ekranıdır.



public class PlanPanel extends JPanel {
    // Layout: Bileşenler arası boşluklar (10px) ile BorderLayout kullanıldı
    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblScenarioName;

    public PlanPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    // Başlık alanı. Seçilen senaryonun ismini dinamik olarak gösterecek
        lblScenarioName = new JLabel("Selected Scenario: None", JLabel.CENTER);
        lblScenarioName.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblScenarioName, BorderLayout.NORTH);

        // Tablo Sütun Başlıkları
        String[] columnNames = {"Metric", "Coefficient", "Direction", "Range", "Unit"};

        // Tablo Model Özelleştirmesi
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Kullanıcının bu aşamada verileri değiştirmesi engellenir. Read only requirement.
                return false;
            }
        };
        // JTable Ayarları
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setRowHeight(25);

        // ScrollPane. Tablo verileri ekrana sığmazsa kaydırma çubuğu ekleme
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }

    // DefinePanel (Step 2) aşamasında seçilen senaryo verilerini tabloya yansıtır.
    public void updateTable(Scenario scenario) {
        if (scenario == null) return;

        // Senaryo ismini güncelle
        lblScenarioName.setText("Selected Scenario: " + scenario.getScenarioName());

        // Tabloyu sıfırla
        tableModel.setRowCount(0);

    // Önce Boyut, altında ona bağlı Metrikler
        for (Dimension dim : scenario.getDimensions()) {

            // Boyut Başlığı Satırı
            tableModel.addRow(new Object[]{
                    "--- " + dim.getName() + " (Coeff: " + dim.getCoefficient() + ") ---",
                    "", "", "", ""
            });

            // Boyuta ait metrikleri alt satırlara ekle
            for (Metric m : dim.getMetrics()) {
                tableModel.addRow(new Object[]{
                        m.getName(),
                        m.getCoefficient(), // Ağırlık Katsayısı
                        m.getDirection(), // Yön (Higher/Lower)
                        m.getMinRange() + "-" + m.getMaxRange(), // Değer Aralığı
                        m.getUnit() // Ölçü Birimi
                });
            }
        }
    }
}