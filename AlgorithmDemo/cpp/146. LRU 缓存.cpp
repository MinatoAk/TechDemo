#include <iostream>
#include <cstring>
#include <algorithm>
#include <unordered_map>

using namespace std;

struct Node {
    int key;
    int val;
    Node* next;
    Node* pre;
    Node(int key, int val): key(key), val(val), pre(nullptr), next(nullptr) {}
}*head, *tail;

int n;
unordered_map<int, Node*> mp;

void init() {
    head = new Node(-1, -1);
    tail = new Node(-1, -1);
    head -> next = tail;
    tail -> pre = head;
}

void insert(Node* u) {
    u -> pre = head;
    u -> next = head -> next;
    head -> next -> pre = u;
    head -> next = u;
}

void remove(Node* u) {
    u -> next -> pre = u -> pre;
    u -> pre -> next = u -> next;
}

int get(int key) {
    if (mp.count(key)) {
        auto t = mp[key];
        int res = t -> val;
        remove(t);
        insert(t);
        return res;
    }
    return -1;
}

void put(int key, int val) {
    if (mp.count(key)) {
        auto t = mp[key];
        t -> val = val;
        remove(t);
        insert(t);

    } else {
        if (mp.size() == n) {
            auto toDelete = tail -> pre;
            remove(toDelete);
            mp.erase(toDelete -> key);
        }
        Node* node = new Node(key, val);
        mp[key] = node;
        insert(node);
    }
}

int main() {
    cin >> n;
    init();

    string op;
    int k, v;
    while (cin >> op) {
        if (op == "P") {
            cin >> k >> v;
            put(k, v);
        } else if (op == "G") {
            cin >> k;
            cout << get(k) << endl;
        }
    }

    return 0;
}