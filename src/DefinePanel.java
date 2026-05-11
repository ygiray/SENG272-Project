import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

//Bu panel, kullanıcının kalite tipini, çalışma modunu ve ölçüm senaryosunu seçtiği Tanımlama aşamasıdır.


public class DefinePanel extends JPanel {
    // Seçim Grupları: JRadioButton'ların mutlak seçim (single-selection) yapmasını sağlar
    private ButtonGroup typeGroup = new ButtonGroup();
    private ButtonGroup modeGroup = new ButtonGroup();
    private ButtonGroup scenarioGroup = new ButtonGroup();

    // Senaryo Veri Yapısı. Senaryo nesnelerini isimleriyle eşleştiren Koleksiyon (HashMap)
    private Map<String, Scenario> scenarioMap = new HashMap<>();
    private JPanel scenarioContainer;

    public DefinePanel() {
        // Layout: Bileşenlerin yukarıdan aşağıya sıralanması için BoxLayout kullanıldı
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Veri Tabanı Simülasyonu. Örnek senaryo verilerini belleğe yükle
        initializeHardCodedData();

        // Bölüm 1: Kalite Tipi Seçimi
        add(new JLabel("<html><b>1. Select Quality Type:</b></html>"));
        JPanel pnlType = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addOption(pnlType, typeGroup, "Product Quality", "Performance, security, usability, reliability");
        addOption(pnlType, typeGroup, "Process Quality", "Sprint efficiency, code quality, team collaboration");
        add(pnlType);

        add(Box.createVerticalStrut(20)); // Dikey boşluk

        // Bölüm 2: Çalışma Modu Seçimi
        add(new JLabel("<html><b>2. Select Mode:</b></html>"));
        JPanel pnlMode = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addModeRadioButton(pnlMode, "Health");
        addModeRadioButton(pnlMode, "Education");
        addModeRadioButton(pnlMode, "Custom");
        add(pnlMode);

        add(Box.createVerticalStrut(20));

        // Bölüm 3: Dinamik Senaryo Seçimi
        add(new JLabel("<html><b>3. Select Scenario:</b></html>"));
        scenarioContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(scenarioContainer);

        // Varsayılan senaryoları (Health mod) yükleyerek başlat
        updateScenarios("Health");
    }

   // MainFrame tarafından çağrılan bu metod, o an seçili olan radyo butonun karşılık geldiği scenario nesnesini map içinden bulup döndürür.
    public Scenario getSelectedScenario() {
        for (Enumeration<AbstractButton> buttons = scenarioGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                return scenarioMap.get(button.getText());
            }
        }
        return null;
    }

     // Kalite tipi seçenekleri için radyo butonu oluşturur ve gruba ekler
    private void addOption(JPanel pnl, ButtonGroup group, String text, String tooltip) {
        JRadioButton rb = new JRadioButton(text);
        rb.setToolTipText(tooltip);
        group.add(rb);
        pnl.add(rb);
        if (group.getButtonCount() == 1) rb.setSelected(true);
    }

    // Mod seçimi için radyo butonu oluşturur ve seçim yapıldığında senaryoları günceller
    private void addModeRadioButton(JPanel pnl, String modeName) {
        JRadioButton rb = new JRadioButton(modeName);
        rb.addActionListener(e -> updateScenarios(modeName));
        modeGroup.add(rb);
        pnl.add(rb);
        if (modeName.equals("Health")) rb.setSelected(true);
    }


    private void updateScenarios(String mode) {
        scenarioContainer.removeAll();
        scenarioGroup = new ButtonGroup(); // Yeni seçim grubu oluştur


        boolean first = true;
        for (Scenario s : scenarioMap.values()) {
            if (s.getMode().equals(mode)) {
                JRadioButton rb = new JRadioButton(s.getScenarioName());
                scenarioGroup.add(rb);
                scenarioContainer.add(rb);
                // İlk senaryoyu varsayılan olarak seçili yapma
                if (first) { rb.setSelected(true); first = false; }
            }
        }
        // Arayüzü tazele
        scenarioContainer.revalidate();
        scenarioContainer.repaint();
    }

    // ISO 15939 standardına uygun örnek verileri (Örn: Education Scenario C) belleğe yükler.
    private void initializeHardCodedData() {
        // SENARYO: Education Scenario C
        Scenario eduC = new Scenario("Scenario C - Team Alpha", "Education");

        // Boyut: Usability
        Dimension usability = new Dimension("Usability", 25);
        usability.addMetric(new Metric("SUS score", 50, "Higher↑", 0, 100, "points"));
        usability.addMetric(new Metric("Onboarding time", 50, "Lower↓", 0, 60, "min"));
        eduC.addDimension(usability);

        // Boyut: Perf. Efficiency
        Dimension perf = new Dimension("Perf. Efficiency", 20);
        perf.addMetric(new Metric("Video start time", 50, "Lower↓", 0, 15, "sec"));
        perf.addMetric(new Metric("Concurrent exams", 50, "Higher↑", 0, 600, "users"));
        eduC.addDimension(perf);

        // Boyut: Accessibility
        Dimension access = new Dimension("Accessibility", 20);
        access.addMetric(new Metric("WCAG compliance", 50, "Higher↑", 0, 100, "%"));
        access.addMetric(new Metric("Screen reader score", 50, "Higher↑", 0, 100, "%"));
        eduC.addDimension(access);

        // Senaryoyu koleksiyona kaydetme
        scenarioMap.put(eduC.getScenarioName(), eduC);

        // DİĞER ÖRNEK SENARYOLAR
        Scenario eduD = new Scenario("Scenario D - Team Beta", "Education");
        eduD.addDimension(new Dimension("Reliability", 20));
        scenarioMap.put(eduD.getScenarioName(), eduD);

        Scenario healthA = new Scenario("Scenario A - Hospital System", "Health");
        healthA.addDimension(new Dimension("Security", 30));
        scenarioMap.put(healthA.getScenarioName(), healthA);

        Scenario healthB = new Scenario("Scenario B - Clinic Management", "Health");
        healthB.addDimension(new Dimension("Usability", 20));
        scenarioMap.put(healthB.getScenarioName(), healthB);
    }
}