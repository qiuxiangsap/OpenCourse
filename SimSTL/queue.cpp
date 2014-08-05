#include <iostream>

template <class T>
class Queue 
{
public:
	Queue();
	~Queue();
	void enqueue(const T& elem);
	T dequeue();
private:
	struct node {
		T elem;
		node * prev, next;
	};

	node *head, tail;
	int sz;
};

template <typename T>
Queue<T>::Queue() {
	head = tail = null;
	sz = 0;
}

template <typename T>
Queue<T>::~Queue() {
}

int main(int argc, char* argv[]) {
		
}