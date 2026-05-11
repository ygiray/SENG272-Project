// Bu sınıf, ölçüm sürecini gerçekleştiren kullanıcının oturum bilgilerini tutar.

public class UserSession {
    // Oturum Bilgileri
    private String username;
    private String school;
    private String sessionName;

    // Yeni bir kullanıcı oturumu oluşturur.
    public UserSession(String username, String school, String sessionName) {
        this.username = username;
        this.school = school;
        this.sessionName = sessionName;
    }

    // Getters
    public String getUsername() { return username; }
    public String getSchool() { return school; }
    public String getSessionName() { return sessionName; }


    // Statik Doğrulama Metodu, nesne oluşturulmadan önce verilerin boş olup olmadığını kontrol eder
    public static boolean isValid(String username, String school, String sessionName) {
        // null kontrolü ve trim() ile boşluk kontrolü bir arada yapılır
        return username != null && !username.trim().isEmpty() &&
                school != null && !school.trim().isEmpty() &&
                sessionName != null && !sessionName.trim().isEmpty();
    }
}




