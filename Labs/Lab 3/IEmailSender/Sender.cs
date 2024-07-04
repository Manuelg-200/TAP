using EmailSenderInterfaces;
using TinyDependencyInjectionContainer;

namespace Sender {
    internal class Program {
        static void Main(string[] args) {
            var resolver = new InterfaceResolver("configuration.txt");
            var sender = resolver.Instantiate<IEmailSender>();
            sender.SendEmail("pippo", "pluto");
        }
    }
}
