import javax.swing.*;
import java.awt.*;

// Bu sınıf, uygulamanın ana penceresini ve kontrol mantığını temsil eder.

public class MainFrame extends JFrame {
    // Navigasyon için kullanılan Layout ve Ana Konteynır
    private CardLayout cardLayout;
    private JPanel mainContainer;

    // Adımları temsil eden özel panel sınıfları
    private ProfilePanel profilePanel;
    private DefinePanel definePanel;
    private PlanPanel planPanel;
    private CollectPanel collectPanel;
    private AnalysePanel analysePanel;

    // UI Bileşenleri
    private JLabel lblStepIndicator;
    private JButton btnNext, btnBack;
    private int currentStep = 1;

    public MainFrame() {
        // Pencere Başlangıç Ayarları
        setTitle("ISO 15939 Measurement Process Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Kart Düzeni Başlatma
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // Panellerin belleğe alınması
        profilePanel = new ProfilePanel();
        definePanel = new DefinePanel();
        planPanel = new PlanPanel();
        collectPanel = new CollectPanel();
        analysePanel = new AnalysePanel();

        // Her paneli benzersiz bir isimle CardLayout'a ekle
        mainContainer.add(profilePanel, "Step1");
        mainContainer.add(definePanel, "Step2");
        mainContainer.add(planPanel, "Step3");
        mainContainer.add(collectPanel, "Step4");
        mainContainer.add(analysePanel, "Step5");

        // Üst durum çubuğu ve alt navigasyon butonlarını oluştur
        setupTopPanel();
        setupBottomPanel();

        add(mainContainer, BorderLayout.CENTER);


        // İlk ekranın durumunu güncelle
        updateUIState();
    }

    // Uygulamanın üst kısmında sürecin hangi aşamasında olunduğunu gösteren görsel göstergeyi (Step Indicator) oluşturur.
    private void setupTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBorder(BorderFactory.createTitledBorder("Measurement Process Status"));
        lblStepIndicator = new JLabel();
        topPanel.add(lblStepIndicator);
        add(topPanel, BorderLayout.NORTH);
    }

     // Back ve Next butonlarını içeren alt kontrol panelini oluşturur
    private void setupBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnBack = new JButton("Back");
        btnNext = new JButton("Next");

        // Buton olay dinleyicileri (Event Listeners)
        btnNext.addActionListener(e -> handleNext());
        btnBack.addActionListener(e -> handleBack());

        bottomPanel.add(btnBack);
        bottomPanel.add(btnNext);
        add(bottomPanel, BorderLayout.SOUTH);
    }


     // Next butonuna basıldığında adımlar arası geçişi ve veri transferini yönetir
    private void handleNext() {
        // STEP 1 Doğrulama
        if (currentStep == 1) {
            if (!profilePanel.isFormValid()) {
                JOptionPane.showMessageDialog(this,
                        "Please fill in all profile fields to continue.",
                        "Validation Error", JOptionPane.WARNING_MESSAGE);
                return; // Geçersiz form durumunda ilerlemeyi durdur
            }
        }

        // STEP 2 -> 3 Geçişi
        // Seçilen senaryo bilgilerini Plan ekranına aktar
        if (currentStep == 2) {
            planPanel.updateTable(definePanel.getSelectedScenario());
        }

        // STEP 3 -> 4 Geçişi
        // Planlanan metrikleri veri toplama ekranına (Collect) yükle
        if (currentStep == 3) {
            collectPanel.loadScenario(definePanel.getSelectedScenario());
        }
        // Adım İlerletme
        if (currentStep < 5) {
            currentStep++;
            updateUIState();
        }

        // Analiz Tetikleme
        // Veriler toplandıktan sonra analiz sonuçlarını hesapla
        if (currentStep == 4) {
            analysePanel.calculateResults(definePanel.getSelectedScenario());
        }


    }


    // Back butonuna basıldığında bir önceki adıma dönüşü yönetir.
    private void handleBack() {
        if (currentStep > 1) {
            currentStep--;
            updateUIState();
        }
    }
    // Arayüzün görsel durumunu (Aktif kart, buton metinleri ve üst gösterge) günceller.
    private void updateUIState() {

        // CardLayout üzerinden ilgili paneli göster
        cardLayout.show(mainContainer, "Step" + currentStep);

        // İlk adımda "Back" butonunu devre dışı bırak
        btnBack.setEnabled(currentStep > 1);

        // Son adımda "Next" butonunun metnini "Finish" yap
        btnNext.setText(currentStep == 5 ? "Finish" : "Next");

        // Üst kısımdaki akış şemasını (Breadcrumb) HTML kullanarak güncelle
        String[] steps = {"Profile", "Define", "Plan", "Collect", "Analyse"};
        StringBuilder sb = new StringBuilder("<html><div style='text-align: center;'>");
        for (int i = 0; i < steps.length; i++) {
            int stepNum = i + 1;
            if (stepNum < currentStep) {

                // Tamamlanan adımlar gri ve onay işaretli
                sb.append("<font color='gray'>").append(steps[i]).append(" (✓)</font>");
            } else if (stepNum == currentStep) {

                // Aktif adım mavi, kalın ve büyük punto
                sb.append("<b><font color='blue' size='5'>[").append(steps[i]).append("]</font></b>");
            } else {

                // Gelecek adımlar siyah
                sb.append("<font color='black'>").append(steps[i]).append("</font>");
            }
            if (i < steps.length - 1) sb.append(" &nbsp;&nbsp;→&nbsp;&nbsp; ");
        }
        sb.append("</div></html>");
        lblStepIndicator.setText(sb.toString());
    }
}