using System.Reflection;
using MyAttribute;

namespace Executer {
    class Executer {
        public static void Main(string[] args) {
            // Always recompile MyLibrary project if edited before executing this
            var loadedAssembly = Assembly.LoadFrom(@"C:\Users\manue\Documents\TAP\Laboratorio 2\MyLibrary\bin\Debug\net6.0\MyLibrary.dll");

            foreach (var type in loadedAssembly.GetTypes()) {
                if (type.IsClass) {
                    foreach (var method in type.GetMethods()) {

                        if (method.IsPublic) {
                            var attributeList = method.GetCustomAttributes();

                            foreach (var attribute in attributeList) {
                                if (attribute is ExecuteMeAttribute actualAttribute) {
                                    Console.WriteLine("Class: " + type.FullName);
                                    Console.WriteLine("Method: " + method.Name);

                                    if (type.GetConstructor(Type.EmptyTypes) == null) {
                                        Console.WriteLine("Missing default constructor: can't create class instance\n");
                                        continue;
                                    }

                                    // If the method has parameters they are passed directly on invocation
                                    if (method.GetParameters().Length != 0) {
                                        var instance = Activator.CreateInstance(type);
                                        try {
                                            method.Invoke(instance, actualAttribute.Values);
                                        }
                                        catch (Exception e) {
                                            Console.WriteLine(e + "\n");
                                            continue;
                                        }
                                    }
                                    // If the method doesn't have parameters they are first passed to the constructor
                                    else {
                                        try {
                                            var instance = Activator.CreateInstance(type, actualAttribute.Values);
                                            method.Invoke(instance, null);
                                        }
                                        catch (Exception e) {
                                            Console.WriteLine(e + "\n");
                                            continue;
                                        }
                                    }
                                    Console.WriteLine();
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}