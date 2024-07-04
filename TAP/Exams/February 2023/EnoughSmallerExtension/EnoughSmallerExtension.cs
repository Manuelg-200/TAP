namespace EnoughSmallerExtension {
    public static class EnoughSmallerExtension {
        public static bool EnoughSmaller<T>(this IEnumerable<T> s, T threshold, int howMany) where T : IComparable<T> {
            if (s == null)
                throw new ArgumentNullException(nameof(s));
            if(Equals(threshold, null))
                throw new ArgumentNullException(nameof(threshold));
            if (howMany <= 0)
                throw new ArgumentOutOfRangeException(nameof(howMany));
            var counter = 0;
            foreach (var item in s) {
                if (item.CompareTo(threshold) < 0)
                    counter++;
                if (counter == howMany)
                    return true;
            }

            return counter >= howMany;
        }
    }
}