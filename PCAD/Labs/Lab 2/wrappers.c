#include "wrappers.h"

void *my_malloc(size_t size) {
    void *ptr = malloc(size);
    if(ptr == NULL) {
        perror("malloc");
        exit(EXIT_FAILURE);
    }
    return ptr;
}

// Pthread wrappers
void pthread_create_wrapper(pthread_t *thread, const pthread_attr_t *attr, void *(*start_routine) (void*), void *arg)  {
    if(pthread_create(thread, attr, start_routine, arg) != 0) {
        perror("pthread_create");
        exit(EXIT_FAILURE);
    }
}

void pthread_join_wrapper(pthread_t thread, void **retval) {
    if(pthread_join(thread, retval) != 0) {
        perror("pthread_join");
        exit(EXIT_FAILURE);
    }
}

// Mutex wrappers
void pthread_mutex_init_wrapper(pthread_mutex_t *mutex) {
    if(pthread_mutex_init(mutex, NULL) != 0) {
        perror("pthread_mutex_init");
        exit(EXIT_FAILURE);
    }
}

void mutex_destroy_wrapper(pthread_mutex_t *mutex) {
    if(pthread_mutex_destroy(mutex) != 0) {
        perror("pthread_mutex_destroy");
        exit(EXIT_FAILURE);
    }
}

void pthread_mutex_lock_wrapper(pthread_mutex_t *mutex) {
    if(pthread_mutex_lock(mutex) != 0) {
        perror("pthread_mutex_lock");
        exit(EXIT_FAILURE);
    }
}

void pthread_mutex_unlock_wrapper(pthread_mutex_t *mutex) {
    if(pthread_mutex_unlock(mutex) != 0) {
        perror("pthread_mutex_unlock");
        exit(EXIT_FAILURE);
    }
}

// my_sem wrappers
void my_sem_init_wrapper(my_semaphore *ms, unsigned int v) {
    if(my_sem_init(ms, v) != 0) {
        perror("my_sem_init");
        exit(EXIT_FAILURE);
    }
}

void my_sem_destroy_wrapper(my_semaphore *ms) {
    if(my_sem_destroy(ms) != 0) {
        perror("my_sem_destroy");
        exit(EXIT_FAILURE);
    }
}

void my_sem_wait_wrapper(my_semaphore *ms) {
    if(my_sem_wait(ms) != 0) {
        perror("my_sem_wait");
        exit(EXIT_FAILURE);
    }
}

void my_sem_signal_wrapper(my_semaphore *ms) {
    if(my_sem_signal(ms) != 0) {
        perror("my_sem_signal");
        exit(EXIT_FAILURE);
    }
}

// pthread_cond wrapper
void pthread_cond_init_wrapper(pthread_cond_t *restrict cond, const pthread_condattr_t *restrict attr) {
    if(pthread_cond_init(cond, attr) != 0) {
        perror("pthread_cond_init");
        exit(EXIT_FAILURE);
    }
}

void pthread_cond_destroy_wrapper(pthread_cond_t *cond) {
    if(pthread_cond_destroy(cond) != 0) {
        perror("pthread_cond_destroy");
        exit(EXIT_FAILURE);
    }
}

void pthread_cond_wait_wrapper(pthread_cond_t *cond, pthread_mutex_t *mutex) {
    if(pthread_cond_wait(cond, mutex) != 0) {
        perror("pthread_cond_wait");
        exit(EXIT_FAILURE);
    }
}

void pthread_cond_signal_wrapper(pthread_cond_t *cond) {
    if(pthread_cond_signal(cond) != 0) {
        perror("pthread_cond_signal");
        exit(EXIT_FAILURE);
    }
}

void pthread_cond_broadcast_wrapper(pthread_cond_t *cond) {
    if(pthread_cond_broadcast(cond) != 0) {
        perror("pthread_cond_broadcast");
        exit(EXIT_FAILURE);
    }
}