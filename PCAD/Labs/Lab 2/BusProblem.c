#include "wrappers.h"
#include <stdbool.h>
#include <unistd.h>

#define PASSENGERS_NUMBER 50
#define BUS_CAPACITY 15

my_semaphore *bus_stop;

typedef struct Bus {
    unsigned int passengers_on_board;
    pthread_mutex_t passengers_count_lock;
    pthread_mutex_t bus_cond_lock;
    pthread_cond_t bus_cond;
} Bus_s;

void *bus_routine(void *arg) {
    Bus_s *bus = (Bus_s *) arg;

    while(true) {
        // Waiting for passengers
        pthread_mutex_lock_wrapper(&bus->bus_cond_lock);
        printf("Bus is waiting for passengers\n");
        pthread_cond_wait_wrapper(&bus->bus_cond, &bus->bus_cond_lock);
        pthread_mutex_unlock_wrapper(&bus->bus_cond_lock);

        // Bus starts tour
        printf("Bus is starting the tour\n");
        sleep(5); // Tour duration

        // Bus is back at the stop
        printf("The bus is back at the stop\n");
        pthread_mutex_lock_wrapper(&bus->bus_cond_lock);
        // Signal all passengers to get off the bus
        pthread_cond_broadcast_wrapper(&bus->bus_cond);
        pthread_mutex_unlock_wrapper(&bus->bus_cond_lock);
        pthread_mutex_lock_wrapper(&bus->passengers_count_lock);
        bus->passengers_on_board = 0;
        printf("All passengers got off the bus\n");
        pthread_mutex_unlock_wrapper(&bus->passengers_count_lock);
    }
    // Cancel the passenger threads to end the program
    return NULL;
}

void *passenger_routine(void *arg) {
    Bus_s *bus = (Bus_s *) arg;

    while (true) {
        // Passenger is waiting for the bus
        my_sem_wait_wrapper(bus_stop);

        // The bus is at the stop and there are seats available, so the passenger is boarding
        pthread_mutex_lock_wrapper(&bus->passengers_count_lock);
        bus->passengers_on_board++;
        printf("Passenger is boarding, %d seats left\n", BUS_CAPACITY - bus->passengers_on_board);
        pthread_mutex_unlock_wrapper(&bus->passengers_count_lock);
        if (bus->passengers_on_board == BUS_CAPACITY) {
            // The bus is full, so the passenger signals the bus to leave
            printf("The bus is full\n");
            pthread_mutex_lock_wrapper(&bus->bus_cond_lock);
            pthread_cond_signal_wrapper(&bus->bus_cond);
            pthread_mutex_unlock_wrapper(&bus->bus_cond_lock);
        }

        // The bus is moving, the passenger waits for the tour to end
        pthread_mutex_lock_wrapper(&bus->bus_cond_lock);
        pthread_cond_wait_wrapper(&bus->bus_cond, &bus->bus_cond_lock);
        pthread_mutex_unlock_wrapper(&bus->bus_cond_lock);

        // The bus is back at the stop, the passenger gets off and frees a place
        my_sem_signal_wrapper(bus_stop);
    }
    return NULL;
}

int main() {
    // Initialization
    pthread_t *bus_thread = my_malloc(sizeof(pthread_t));
    Bus_s *bus = my_malloc(sizeof(Bus_s));
    bus->passengers_on_board = 0;
    pthread_mutex_init_wrapper(&bus->bus_cond_lock);
    pthread_mutex_init_wrapper(&bus->passengers_count_lock);
    pthread_cond_init_wrapper(&bus->bus_cond, NULL);

    bus_stop = my_malloc(sizeof(my_semaphore));
    my_sem_init_wrapper(bus_stop, BUS_CAPACITY);

    pthread_t *passengers = my_malloc(sizeof(pthread_t) * PASSENGERS_NUMBER);

    // Thread creation
    pthread_create_wrapper(bus_thread, NULL, bus_routine, bus);
    for(unsigned int i=0; i<PASSENGERS_NUMBER; i++)
        pthread_create_wrapper(&passengers[i], NULL, passenger_routine, bus);

    // Thread joining
    pthread_join_wrapper(*bus_thread, NULL);
    for(unsigned int i=0; i<PASSENGERS_NUMBER; i++)
        pthread_join_wrapper(passengers[i], NULL);

    printf("The bus did 5 tours!\n");

    // Cleanup
    free(bus_thread);
    free(passengers);
    mutex_destroy_wrapper(&bus->bus_cond_lock);
    mutex_destroy_wrapper(&bus->passengers_count_lock);
    pthread_cond_destroy_wrapper(&bus->bus_cond);
    free(bus);
    my_sem_destroy_wrapper(bus_stop);
    free(bus_stop);

    return 0;
}