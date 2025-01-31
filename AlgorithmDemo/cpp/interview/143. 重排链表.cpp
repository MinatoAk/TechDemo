#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

struct ListNode {
    int val;
    ListNode* next;
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

ListNode* findMid(ListNode* head) {
    ListNode* fast = head, *slow = head;

    while (fast -> next && fast -> next -> next) {
        slow = slow -> next;
        fast = fast -> next -> next;
    }

    return slow;
}

ListNode* reverseList(ListNode* head) {
    if (head == nullptr || head -> next == nullptr) return head;
    auto tail = reverseList(head -> next);

    head -> next -> next = head;
    head -> next = nullptr;

    return tail;
}

void mergeList(ListNode* l1, ListNode* l2) {
    while (l1 && l2) {
        auto l1Tmp = l1 -> next, l2Tmp = l2 -> next;
        l1 -> next = l2;
        l2 -> next = l1Tmp;
        l1 = l1Tmp, l2 = l2Tmp;
    }
}

void reorderList(ListNode* head) {
    if (head == nullptr) return;
    // 1) 找到中点
    ListNode* mid = findMid(head);
    ListNode* l2 = mid -> next;
    mid -> next = nullptr;

    // 2) 翻转后面的链表
    l2 = reverseList(l2);

    // 3) 重组链表
    mergeList(head, l2);
}

void printList(ListNode* head) {
    while (head) {
        cout << head -> val << " ";
        head = head -> next;
    }
}

int main() {
    // 输入：head = [1,2,3,4]       输出：[1,4,2,3]
    // 输入：head = [1,2,3,4,5]     输出：[1,5,2,4,3]
    cin >> n;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    ListNode* head = buildList();

    reorderList(head);

    printList(head);

    return 0;
}