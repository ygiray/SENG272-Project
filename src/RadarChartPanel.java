import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

// Bu sınıf, hesaplanan boyut puanlarını (Dimension Scores) dairesel bir koordinat düzlemi üzerinde poligon (çokgen) olarak görselleştirir.

class RadarChartPanel extends JPanel {
    // Görselleştirilecek boyutların listesi
    private List<Dimension> dims;

    public RadarChartPanel() {
        // Grafiğin daha net görünmesi için arka plan rengi ayarı
        setBackground(Color.WHITE);
    }

    // Güncel boyut verilerini panele yükler ve grafiğin yeniden çizilmesini sağlar.
    public void setDimensions(List<Dimension> dims) {
        this.dims = dims;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Veri yoksa veya yetersizse (Radar grafiği için en az 3 nokta idealdir) çizim yapma
        if (dims == null || dims.isEmpty()) return;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        // Panel merkezini ve yarıçapını hesapla
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        int radius = Math.min(cx, cy) - 50;
        int n = dims.size();

        Polygon p = new Polygon();
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;

            // Puan Normalizasyonu: 1-5 arası puanı 0-1 arasına çekerek yarıçapa oranla
            double val = dims.get(i).getCalculatedScore() / 5.0;

            // Trigonometrik Koordinat Hesaplama:
            int x = (int) (cx + Math.cos(angle) * radius * val);
            int y = (int) (cy + Math.sin(angle) * radius * val);
            p.addPoint(x, y);


            // Etiket Çizimi: Boyut isimlerini poligonun biraz dışına yazdır
            int labelX = (int) (cx + Math.cos(angle) * (radius + 25));
            int labelY = (int) (cy + Math.sin(angle) * (radius + 25));
            g2d.setColor(Color.BLACK);
            g2d.drawString(dims.get(i).getName(), labelX - 20, labelY);

        }

        // Poligonu Renklendir
        g2d.setColor(new Color(0, 100, 255, 100));
        g2d.fill(p);
        g2d.setColor(Color.BLUE);
        g2d.draw(p);
    }
}