namespace InfiniteComparisonSequenceExtension {
    public static class InfiniteComparisonSequenceExtension {
        public static IEnumerable<int> InfiniteComparisonSequence<T>(this IEnumerable<T> source) where T : IComparable<T>
        {
            using var current = source.GetEnumerator();
            using var next = source.GetEnumerator();
            if (!next.MoveNext())
                yield break;
            while (current.MoveNext() && next.MoveNext())
            {
                var result = current.Current.CompareTo(next.Current); // TEMP
                yield return result;
            }

            throw new ArgumentException("Reached the end of the sequence");
        }
    }
}