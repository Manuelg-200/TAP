import matplotlib.pyplot as plt

with open("results.txt", "r") as file:
    data = file.readlines()
    run_number = int(data[0])
    results = [int(i) for i in data[1:run_number]]
    plt.hist(results, bins=50)
    plt.show()