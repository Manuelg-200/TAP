namespace MyAttribute {

    [AttributeUsage(AttributeTargets.Method, AllowMultiple = true)]
    public class ExecuteMeAttribute : Attribute {
        public object?[]? Values { get; set; }

        public ExecuteMeAttribute(params object[] values) {
            Values = values;
        }

        public ExecuteMeAttribute() {}
    }
}