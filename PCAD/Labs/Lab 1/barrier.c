#include "my_barrier.h"
#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>

unsigned int pthread_my_barrier_init(my_barrier *mb, unsigned int v) {
    if(v == 0)
        return -1;
    mb->vinit = v;
    mb->val = 0;
    if(pthread_mutex_init(&mb->lock, NULL) != 0)
        return -1;
    if(pthread_cond_init(&mb->varcond, NULL) != 0)
        return -1;
    return 0;
}

unsigned int pthread_my_barrier_wait(my_barrier *mb) {
    if(pthread_mutex_lock(&mb->lock) != 0)
        return -1;
    mb->val++;
    printf("%d threads arrived\n", mb->val);
    if(mb->val != mb->vinit) {
        if(pthread_cond_wait(&mb->varcond, &mb->lock) != 0)
            return -1;
    }
    else {
        printf("Releasing\n");
        if(pthread_cond_broadcast(&mb->varcond) != 0)
            return -1;
        mb->val = 0;
    }
    if(pthread_mutex_unlock(&mb->lock) != 0)
        return -1;
    return 0;
}

void *barrier_test(void *arg) {
    my_barrier *mb = (my_barrier*) arg;
    srand(time(NULL) + pthread_self());
    sleep(rand() % 10);
    pthread_my_barrier_wait(mb);
    return NULL;
}

void *my_malloc(size_t size) {
    void *ptr = malloc(size);
    if (!ptr) {
        perror("malloc");
        exit(EXIT_FAILURE);
    }
    return ptr;
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
    my_barrier *mb = malloc(sizeof(my_barrier));
    if(pthread_my_barrier_init(mb, 5) < 0) {
        perror("pthread_my_barrier_init");
        return -1;
    }

    pthread_t *threads = my_malloc(sizeof(pthread_t) * 5);
    for(unsigned int i=0; i<5; i++)
        pthread_create_wrapper(&threads[i], NULL, barrier_test, mb);

    for(unsigned int i=0; i<5; i++)
        pthread_join_wrapper(threads[i], NULL);

    printf("All passed the barrier\n");
    
    free(threads);
    free(mb);
    return 0;
}