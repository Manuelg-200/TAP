using System.Reflection;

namespace TinyDependencyInjectionContainer {
    public class InterfaceResolver {
        private Dictionary<Type, Type>? Dictionary { get; }

        public InterfaceResolver(string configFileName) {
            var configRaw = File.ReadAllLines(configFileName);
            foreach (var line in configRaw) {
                if (line.Length == 0 || line.StartsWith('#'))
                    continue;
                var substrings = line.Split("*");
                if (substrings.Length != 4)
                    throw new ArgumentException("Config file: wrong format");
                var interfaceAssembly = Assembly.LoadFrom(substrings[0]);
                var interfaceType = interfaceAssembly.GetType(substrings[1]);
                var classAssembly = Assembly.LoadFrom(substrings[2]);
                var classType = classAssembly.GetType(substrings[3]);
                if (interfaceType is null || classType is null)
                    throw new ArgumentException("Config file: could not find path");
                Dictionary = new Dictionary<Type, Type> {
                    { interfaceType, classType }
                };
            }
        }

        public T? Instantiate<T>() where T : class {
            var sender = Dictionary[typeof(T)];
            return Activator.CreateInstance(sender) as T;
        }
    }
}