#include <iostream>
#include <algorithm>
#include <cstring>
#include <vector>

using namespace std;

struct ListNode {
    int val;
    ListNode * next;
    ListNode(int val): val(val), next(nullptr) {}
};

int n;
vector<int> nums;

ListNode* buildList() {
    ListNode* dummy = new ListNode(-1), *cur = dummy;

    for (int i = 0; i < n; i ++ ) {
        ListNode* node = new ListNode(nums[i]);
        cur = cur -> next = node;
    }

    return dummy -> next;
}

ListNode* merge2Lists(ListNode* a, ListNode* b) {
    ListNode* dummy = new ListNode(-1), *cur = dummy;

    while (a && b) {
        if (a -> val <= b -> val) cur = cur -> next = a, a = a -> next;
        else cur = cur -> next = b, b = b -> next;
    }

    if (a) cur -> next = a;
    if (b) cur -> next = b;

    return dummy -> next;
}

ListNode* mergeSort(ListNode* head, ListNode* tail) {
    if (head == nullptr) return nullptr;

    if (head -> next == tail) {
        head -> next = nullptr;
        return head;
    }

    ListNode* fast = head, *slow = head;
    while (fast -> next != tail) {
        slow = slow -> next;
        fast = fast -> next;
        if (fast -> next != tail) fast = fast -> next;
    }

    return merge2Lists(mergeSort(head, slow), mergeSort(slow, tail));
}

void printList(ListNode* head) {
    while (head) {
        cout << head -> val << " ";
        head = head -> next;
    }
}

int main() {
    // 输入：head = [4,2,1,3]       输出：[1,2,3,4]
    // 输入：head = [-1,5,3,4,0]    输出：[-1,0,3,4,5]
    // 输入：head = []              输出：[]

    cin >> n;
    if (n == 0) return 0;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    ListNode* head = buildList();

    head = mergeSort(head, nullptr);

    printList(head);

    return 0;
}