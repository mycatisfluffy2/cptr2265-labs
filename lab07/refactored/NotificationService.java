interface NotificationService {
    void sendEmail(String to, String subject, String body);
}

class EmailNotificationService implements NotificationService {
    public void sendEmail(String to, String subject, String body) {
        System.out.println("[EMAIL to " + to + "] " + subject);
        System.out.println(body);
    }
}
