import javax.swing.*;
import java.awt.*;


// Bu sınıf, kullanıcının kişisel bilgilerini (Ad, Okul, Seans) girdiği uygulamanın ilk aşamasını (Step 1) temsil eder.

public class ProfilePanel extends JPanel {
    // Kullanıcı giriş alanları
    private JTextField txtUsername = new JTextField(20);
    private JTextField txtSchool = new JTextField(20);
    private JTextField txtSession = new JTextField(20);

    public ProfilePanel() {
        // GridBagLayout: Bileşenlerin esnek ve hizalı bir şekilde yerleştirilmesi
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Bileşenler arası dış boşluklar

        // BAŞLIK
        JLabel lblTitle = new JLabel("Step 1: User Profile");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(lblTitle, gbc);

        // GİRİŞ ALANLARI
        gbc.gridwidth = 1;

        // Kullanıcı adı
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        add(txtUsername, gbc);

        // Okul Bilgisi
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("School:"), gbc);
        gbc.gridx = 1;
        add(txtSchool, gbc);

// Seans Adı
        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Session Name:"), gbc);
        gbc.gridx = 1;
        add(txtSession, gbc);
    }


    //  Formdaki tüm alanların dolu olup olmadığını kontrol etme
    public boolean isFormValid() {
        // trim() metodu ile sadece space karakteri girilmesi de engellenir
        return !txtUsername.getText().trim().isEmpty() &&
                !txtSchool.getText().trim().isEmpty() &&
                !txtSession.getText().trim().isEmpty();
    }

    // Girilen kullanıcı adını döndürür.
    public String getUsername() { return txtUsername.getText(); }
}