#include <stdlib.h>
#include <stdio.h>
#include "my_semaphore.h"

void *my_malloc(size_t size);

//Ptrhead wrappers
void pthread_create_wrapper(pthread_t *thread, const pthread_attr_t *attr, void *(*start_routine) (void*), void *arg);
void pthread_join_wrapper(pthread_t thread, void **retval);

// Mutex wrappers
void pthread_mutex_init_wrapper(pthread_mutex_t *mutex);
void mutex_destroy_wrapper(pthread_mutex_t *mutex);
void pthread_mutex_lock_wrapper(pthread_mutex_t *mutex);
void pthread_mutex_unlock_wrapper(pthread_mutex_t *mutex);

// my_sem wrapper
void my_sem_init_wrapper(my_semaphore *ms, unsigned int v);
void my_sem_destroy_wrapper(my_semaphore *ms);
void my_sem_wait_wrapper(my_semaphore *ms);
void my_sem_signal_wrapper(my_semaphore *ms);

// pthread_cond wrapper
void pthread_cond_init_wrapper(pthread_cond_t *restrict cond, const pthread_condattr_t *restrict attr);
void pthread_cond_destroy_wrapper(pthread_cond_t *cond);
void pthread_cond_wait_wrapper(pthread_cond_t *cond, pthread_mutex_t *mutex);
void pthread_cond_signal_wrapper(pthread_cond_t *cond);
void pthread_cond_broadcast_wrapper(pthread_cond_t *cond);