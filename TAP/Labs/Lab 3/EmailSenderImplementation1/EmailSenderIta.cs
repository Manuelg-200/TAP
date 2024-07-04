using EmailSenderInterfaces;

namespace EmailSenderImplementation1 {
    public class EmailSenderIta : IEmailSender  {
        public bool SendEmail(string to, string body) {
            Console.WriteLine("Invio messaggio...");
            Console.WriteLine($"A: {to}, corpo: {body}");
            return true;
        }
    }
}