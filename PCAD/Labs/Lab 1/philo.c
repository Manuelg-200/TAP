#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <stdbool.h>

// False = on the table, true = taken by one of the philosphers
volatile bool sticks[5] = {false, false, false, false, false};

void *my_malloc(size_t size) {
    void *ptr = malloc(size);
    if (!ptr) {
        perror("malloc");
        exit(EXIT_FAILURE);
    }
    return ptr;
}

volatile bool* takeStick(volatile bool* stick) {
    while (true) {
        if(!*stick) {
            *stick = true;
            return stick;
        }
    }
}

typedef enum philosopher_state {
    THINKING, 
    HAS_LEFT_STICK,
    HAS_BOTH_STICKS,
    EATING,
    DONE_EATING
} philosopher_state;

typedef struct philosopher {
    int id;
    volatile bool* leftStick;
    volatile bool* rightStick;
    philosopher_state state;
} philosopher_s;

static void printState(philosopher_s *philosopher) {
    printf("Philosopher %d is ", philosopher->id);
    switch (philosopher->state) {
        case THINKING:
            printf("thinking\n");
            break;
        case HAS_LEFT_STICK:
            printf("holding left stick\n");
            break;
        case HAS_BOTH_STICKS:
            printf("holding both sticks\n");
            break;
        case EATING:
            printf("eating\n");
            break;
        case DONE_EATING:
            printf("done eating\n");
            break;
    }
}

static void nextState(philosopher_s *philosopher, unsigned int seed) {
    switch(philosopher->state) {
        case THINKING:
            sleep(rand_r(&seed) % 10);
            philosopher->leftStick = takeStick(&sticks[philosopher->id]);
            philosopher->state = HAS_LEFT_STICK;
            break;
        case HAS_LEFT_STICK:
            philosopher->rightStick = takeStick(&sticks[(philosopher->id - 1) % 5]);
            philosopher->state = HAS_BOTH_STICKS;
            break;
        case HAS_BOTH_STICKS:
            philosopher->state = EATING;
            break;
        case EATING:
            philosopher->leftStick = false;
            philosopher->rightStick = false;
            philosopher->state = DONE_EATING;
            break;
        case DONE_EATING:
            philosopher->state = THINKING;
            break;
    }
}

void *philosopher_routine(void *arg) {
    philosopher_s *philosopher = (philosopher_s*) arg;
    unsigned int seed = time(NULL) + philosopher->id; // Used to determine sleep time
    for(unsigned int i=0; i<5; i++) { // Philosphers want to eat 5 times
        for(unsigned int j=0; j<5; j++) { // 5 states to go through
            printState(philosopher);
            nextState(philosopher, seed);
        }
    }
    return NULL;
}

void pthread_create_wrapper(pthread_t *thread, const pthread_attr_t *attr, void *(*start_routine) (void *), void *arg) {
    if (pthread_create(thread, attr, start_routine, arg) != 0) {
        perror("pthread_create");
        exit(EXIT_FAILURE);
    }
}

void pthread_join_wrapper(pthread_t thread, void **retval) {
    if (pthread_join(thread, retval) != 0) {
        perror("pthread_join");
        exit(EXIT_FAILURE);
    }
}

int main() {
    pthread_t *threads = my_malloc(sizeof(pthread_t) * 5);
    philosopher_s *philosophers = my_malloc(sizeof(philosopher_s) * 5);
    for (unsigned int i=0; i<5; i++) {
        philosophers[i].id = i;
        philosophers[i].state = THINKING;
    }

    for(unsigned int i = 0; i < 5; i++)
        pthread_create_wrapper(&threads[i], NULL, philosopher_routine, &philosophers[i]);

    for(unsigned int i = 0; i < 5; i++) 
        pthread_join_wrapper(threads[i], NULL);

    free(threads);
    free(philosophers);

    return 0;
}