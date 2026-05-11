// Bu sınıf uygulamanın giriş noktasıdır

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {      //  invokeLater kullanılarak arayüzün "Event Dispatch Thread" üzerinden başlatılması sağlanır.

            @Override
            public void run() {

                MainFrame mainFrame = new MainFrame();     // Ana pencereyi oluştur
                mainFrame.setVisible(true);                // Pencereyi kullanıcı için görünür yap
            }
        });
    }

}