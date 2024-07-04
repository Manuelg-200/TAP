using IMessage;

namespace Censor {
    public static class Censorship {
        public static IEnumerable<I> Censor(IEnumerable<I>? sequence, string badWord) {
            if (sequence == null)
                throw new ArgumentNullException(nameof(sequence), "can't be null");
            foreach (var item in sequence) {
                if (item == null)
                    throw new ArgumentNullException(nameof(sequence), "can't have null elements");
                if (!item.Message.Contains(badWord))
                    yield return item;
            }
        }
    }
}