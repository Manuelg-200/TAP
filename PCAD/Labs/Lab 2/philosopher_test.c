#include "wrappers.h"
#include <unistd.h>
#include <stdbool.h>

my_semaphore *waitingRoom;

typedef struct Stick {
    volatile bool taken;
    my_semaphore stickSem;
} Stick_s;

typedef enum philosopher_state {
    THINKING,
    HAS_LEFT_STICK,
    HAS_BOTH_STICKS,
    EATING,
    DONE_EATING
} philosopher_state;

typedef struct Philosopher {
    unsigned int id;
    Stick_s *leftStick;
    Stick_s *rightStick;
    philosopher_state state;
} Philosopher_s;

static void print_state(Philosopher_s *philosopher) {
    printf("Philosopher %d is ", philosopher->id);
    switch(philosopher->state) {
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

static void next_state(Philosopher_s *philosopher) {
    switch(philosopher->state) {
        case THINKING:
            my_sem_wait_wrapper(waitingRoom);
            my_sem_wait_wrapper(&philosopher->leftStick->stickSem);
            philosopher->leftStick->taken = true;
            philosopher->state = HAS_LEFT_STICK;
            break;
        case HAS_LEFT_STICK:
            my_sem_wait_wrapper(&philosopher->rightStick->stickSem);
            philosopher->rightStick->taken = true;
            philosopher->state = HAS_BOTH_STICKS;
            break;
        case HAS_BOTH_STICKS:
            philosopher->state = EATING;
            break;
        case EATING:
            philosopher->leftStick->taken = false;
            my_sem_signal_wrapper(&philosopher->leftStick->stickSem);
            philosopher->rightStick->taken = false;
            my_sem_signal_wrapper(&philosopher->rightStick->stickSem);
            my_sem_signal_wrapper(waitingRoom);
            philosopher->state = DONE_EATING;
            break;
        case DONE_EATING:
            philosopher->state = THINKING;
            break;
    }
}

void *philosopher_routine(void* arg) {
    Philosopher_s *philosopher = (Philosopher_s*) arg;
    // Each philosopher wants to eat 5 times
    for(unsigned int i=0; i<5; i++) {
        // 5 states to go thorugh
        for(unsigned int j=0; j<5; j++) {
            print_state(philosopher);
            next_state(philosopher);
        }
    }
    return NULL;    
}

int main() {
    // Structs and waiting room initialization
    Stick_s *sticks = my_malloc(sizeof(Stick_s) * 5);
    for(unsigned int i=0; i<5; i++) {
        sticks[i].taken = false;
        my_sem_init_wrapper(&sticks[i].stickSem, 1);
    }

    Philosopher_s *philosophers = my_malloc(sizeof(Philosopher_s) * 5);
    for(unsigned int i=0; i<5; i++) {
        philosophers [i].id = i;
        philosophers[i].leftStick = &sticks[(i+1) % 5];
        philosophers[i].rightStick = &sticks[i];
        philosophers[i].state = THINKING;
    }

    waitingRoom = my_malloc(sizeof(my_semaphore));
    my_sem_init_wrapper(waitingRoom, 4);
    
    // Threads initialization
    pthread_t *threads = my_malloc(sizeof(pthread_t) * 5);
    for(unsigned int i=0; i<5; i++) 
        pthread_create_wrapper(&threads[i], NULL, philosopher_routine, &philosophers[i]);

    // Wait and join all threads
    for(unsigned int i=0; i<5; i++) 
        pthread_join_wrapper(threads[i], NULL);

    printf("\nSuccess! Every philosopher ate 5 times\n");

    // Memory clean up
    free(threads);
    free(philosophers);

    for(unsigned int i=0; i<5; i++)
        my_sem_destroy_wrapper(&sticks[i].stickSem);
    free(sticks);

    my_sem_destroy_wrapper(waitingRoom);
    free(waitingRoom);

    return 0;
}
