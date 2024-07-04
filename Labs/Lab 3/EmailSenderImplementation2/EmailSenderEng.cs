using EmailSenderInterfaces;

namespace EmailSenderImplementation2 {
    public class EmailSenderEng : IEmailSender {
        public bool SendEmail(string to, string body) {
            Console.WriteLine("Sending message...");
            Console.WriteLine($"To: {to}, body: {body}");
            return true;
        }
    }
}