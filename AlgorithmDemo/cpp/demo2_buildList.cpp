#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

vector<int> nums;

struct ListNode {
    int val;
    ListNode* next;
    ListNode(int val): val(val), next(nullptr) {}
};

ListNode* buildList() {
    ListNode* dummy = new ListNode(-1), *cur = dummy;

    for (int i = 0; i < nums.size(); i ++ ) {
        ListNode* newNode = new ListNode(nums[i]);
        cur = cur -> next = newNode;
    }

    return dummy -> next;
}

void getRes(ListNode* u) {
    while (u) {
        cout << u -> val << " ";
        u = u -> next;
    }
}

int main() {

    int x;
    while (cin >> x) {
        if (x == 0) break;
        nums.push_back(x);
    }

    ListNode* head = buildList();

    getRes(head);

    return 0;
}