#include <stdlib.h>
#include <stdio.h>
#include <pthread.h>
#include <unistd.h>
#include <stdbool.h>

void *my_malloc(size_t size) {
    void *ptr = malloc(size);
    if (!ptr) {
        perror("malloc");
        exit(EXIT_FAILURE);
    }
    return ptr;
}


typedef struct Stick {
    volatile bool taken;
    pthread_mutex_t stick_mutex;
} Stick_s;

typedef enum philosopher_state {
    THINKING, 
    HAS_LEFT_STICK,
    HAS_BOTH_STICKS,
    EATING,
    DONE_EATING
} philosopher_state;

typedef struct Philosopher {
    int id;
    Stick_s *leftStick;
    Stick_s *rightStick;
    philosopher_state state;
} Philosopher_s;

static void printState(Philosopher_s *philosopher) {
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

void pthread_mutex_lock_wrapper(pthread_mutex_t *mutex) {
    if (pthread_mutex_lock(mutex) != 0) {
        perror("pthread_mutex_lock");
        exit(EXIT_FAILURE);
    }
}

void pthread_mutex_unlock_wrapper(pthread_mutex_t *mutex) {
    if (pthread_mutex_unlock(mutex) != 0) {
        perror("pthread_mutex_unlock");
        exit(EXIT_FAILURE);
    }
}

static void nextState(Philosopher_s *philosopher) {
    switch(philosopher->state) {
        case THINKING:
            sleep(rand() % 5);
            pthread_mutex_lock_wrapper(&philosopher->leftStick->stick_mutex);
            pthread_mutex_lock_wrapper(&philosopher->rightStick->stick_mutex);
            philosopher->leftStick->taken = true;
            philosopher->state = HAS_LEFT_STICK;
            break;
        case HAS_LEFT_STICK:
            philosopher->rightStick->taken = true;
            philosopher->state = HAS_BOTH_STICKS;
            break;
        case HAS_BOTH_STICKS:
            philosopher->state = EATING;
            break;
        case EATING:
            philosopher->leftStick->taken = false;
            philosopher->rightStick->taken = false;
            pthread_mutex_unlock_wrapper(&philosopher->leftStick->stick_mutex);
            pthread_mutex_unlock_wrapper(&philosopher->rightStick->stick_mutex);
            philosopher->state = DONE_EATING;
            break;
        case DONE_EATING:
            philosopher->state = THINKING;
            break;
    }
}

void *philosopher_routine(void *arg) {
    Philosopher_s *philosopher = (Philosopher_s*) arg;
    srand(time(NULL) + philosopher->id); // Used to determine sleep time
    for(unsigned int i=0; i<5; i++) { // Philosphers want to eat 5 times
        for(unsigned int j=0; j<5; j++) { // 5 states to go through
            printState(philosopher);
            nextState(philosopher);
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
    Philosopher_s *philosophers = my_malloc(sizeof(Philosopher_s) * 5);
    Stick_s *sticks = my_malloc(sizeof(Stick_s) * 5);
    for (unsigned int i=0; i<5; i++) {
        philosophers[i].id = i;
        philosophers[i].state = THINKING;

        sticks[i].taken = false;
        if(pthread_mutex_init(&sticks[i].stick_mutex, NULL) != 0) {
            perror("pthread_mutex_init");
            exit(EXIT_FAILURE);
        }
    }

    for(int i=0; i<5; i++) {
        philosophers[i].leftStick = &sticks[(i + 1) % 5];
        philosophers[i].rightStick = &sticks[i];
    }

    for(unsigned int i = 0; i < 5; i++)
        pthread_create_wrapper(&threads[i], NULL, philosopher_routine, &philosophers[i]);

    for(unsigned int i = 0; i < 5; i++) 
        pthread_join_wrapper(threads[i], NULL);

    printf("\nSuccess! All the philosophers ate five times!\n");

    free(threads);
    free(philosophers);
    free(sticks);

    return 0;
}