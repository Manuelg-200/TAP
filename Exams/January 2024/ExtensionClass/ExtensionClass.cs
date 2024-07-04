namespace ExtensionClass {
    public static class ExtensionClass {
        // Auxiliary function for IntersectOn to use try-catch statement
        // ExceptionObserver is used to check if an exception was thrown
        private static T2? ApplyFunction<T1, T2>(Func<T1, T2> function, T1 value, ref bool exceptionObserver) {
            try {
                return function(value);
            }
            catch (Exception) {
                exceptionObserver = true;
                return default;
            }
        } 
        public static IEnumerable<bool?> IntersectOn<T1,T2>(this IEnumerable<Func<T1,T2>> source, IEnumerable<Func<T1,T2>> other, T1 p) {
            using var sourceEnumerator = source.GetEnumerator();
            using var otherEnumerator = other.GetEnumerator();
            while (true) { 
                var sourceHasNext = sourceEnumerator.MoveNext();
                var otherHasNext = otherEnumerator.MoveNext();
                if (!sourceHasNext ^ !otherHasNext) 
                    throw new ArgumentException("The sequences have different lengths");
                if (!sourceHasNext && !otherHasNext) // Reached the end of both sequences
                    yield break;

                var exceptionObserver = false;
                var sourceResult = ApplyFunction(sourceEnumerator.Current, p, ref exceptionObserver);
                if (exceptionObserver) {
                    yield return null;
                    continue;
                }

                var otherResult = ApplyFunction(otherEnumerator.Current, p, ref exceptionObserver);
                if (exceptionObserver) {
                    yield return null;
                    continue;
                }

                if (Equals(sourceResult, otherResult)) 
                    yield return true;
                else 
                    yield return false;
            }
        }
    }
}