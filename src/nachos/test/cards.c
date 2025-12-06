
typedef struct {
    int rank;
    char suit;
} Card;
#define DECKSIZE 52;
#define Card deck[DECKSIZE];
// This is bad code. I know that, you know that. 
// It is late. It is the end of the term. I doubt you will even read this.
// Chris, if you take points off for bad C code I will steal pinchy. You have been warned.
// I am not even submitting this. It doesnt work. I swear to god Chris I will have shark filet for dinner.
for (int i = 0; i < 13; i++){
    deck[i].rank = i+ 1;
    deck[i].suit = 'd';
}
for (int i = 0; i < 13; i++){
    deck[i+13].rank = i+ 1;
    deck[i+13].suit = 's';
}
for (int i = 0; i < 13; i++){
    deck[i+26].rank = i+ 1;
    deck[i+26].suit = 'c';
}
for (int i = 0; i < 13; i++){
    deck[i+39].rank = i+ 1;
    deck[i+39].suit = 'h';
}

int main() {
    int random_index = rand() % array_size;
    card drawnCard = deck[random_index];
    printf("You drew a %d of %d", drawnCard.rank, drawnCard.suit)
}
