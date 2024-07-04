import matplotlib.pyplot as plt

with open("roundCount.txt", "r") as file:
    data = [int(line.strip()) for line in file.readlines()]
    plt.hist(data, bins=10)
    plt.show()