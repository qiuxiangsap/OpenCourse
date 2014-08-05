#include <iostream>

using namespace std;

template <class T>
class Stack {
public:
	Stack();
	~Stack();

	void push(const T& elem);
	int pop();
	bool empty();
	int size();

private:
	struct Node {
		T elem;
		Node *next;
		Node(){}
	};
	Node *head;
	int sz;
};

template <class T>
Stack<T>::Stack() {
	head = NULL;
	sz = 0;
}

template <class T>
Stack<T>::~Stack() {
	for (;head != NULL;) {
		Node *s = head;
		head = head->next;
		delete s;
	}
	head = NULL;
}

template <class T>
bool Stack<T>::empty() {
	return sz == 0;
}

template <class T>
void Stack<T>::push(const T &elem) {
	Node *node = new Node();
	node->elem = elem;
	node->next = head;
	head = node; 
	sz++;
}

template <class T>
int Stack<T>::pop() {
	if (this->empty()) {
		throw "Empty Stack";
	}

	int el = head->elem;
	head = head->next;
	sz--;
	return el;


}

template <class T>
int Stack<T>::size() {
	return sz;
}

int main(int argc, char*argv[])  {
	Stack<int> *s = new Stack<int>();
	s->push(10);
	s->push(9);
	s->push(8);
	s->push(7);

	cout << "Size of S: " << s->size() << endl;
	cout << "Head of S:" << s->pop() << endl;

}