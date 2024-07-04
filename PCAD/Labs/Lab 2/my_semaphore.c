#include "my_semaphore.h"
#include <stdio.h>

int my_sem_init(my_semaphore *ms, unsigned int v) {
    ms->V = v;
    if (pthread_mutex_init(&ms->lock, NULL) != 0)
        return -1;
    if (pthread_cond_init(&ms->varcond, NULL) != 0)
        return -1;
    return 0;
}

int my_sem_wait(my_semaphore *ms) {
    if(pthread_mutex_lock(&ms->lock) != 0)
        return -1;
    while(ms->V == 0) {
        if(pthread_cond_wait(&ms->varcond, &ms->lock) != 0)
            return -1;
    }
    ms->V--;
    if(pthread_mutex_unlock(&ms->lock) != 0)
        return -1;
    return 0;
}

int my_sem_signal(my_semaphore *ms) {
    if(pthread_mutex_lock(&ms->lock) != 0)
        return -1;
    ms->V++;
    if(pthread_cond_signal(&ms->varcond) != 0)
        return -1;
    if(pthread_mutex_unlock(&ms->lock) != 0)
        return -1;
    return 0;
}

int my_sem_destroy(my_semaphore *ms) {
    if(pthread_mutex_destroy(&ms->lock) != 0)
        return -1;
    if(pthread_cond_destroy(&ms->varcond) != 0)
        return -1;
    return 0;
}