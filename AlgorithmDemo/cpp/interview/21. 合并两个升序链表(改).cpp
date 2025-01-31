#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

struct ListNode {
    int val;
    ListNode* next;
    ListNode(int val): val(val), next(nullptr) {}
    ListNode(int val, ListNode* next): val(val), next(next) {}
};

int n, m, x;
vector<int> nums1, nums2;

ListNode* buildList(vector<int> nums, int sz) {
    ListNode* dummy = new ListNode(-1), *cur = dummy;

    for (int i = 0; i < sz; i ++ ) {
        ListNode* node = new ListNode(nums[i]);
        cur = cur -> next = node;
    }

    return dummy -> next;
}

void printList(ListNode* head) {
    while (head) {
        cout << head -> val << " ";
        head = head -> next;
    }
}

ListNode* merge2List(ListNode* l1, ListNode* l2) {
    ListNode* dummy = new ListNode(-1), *cur = dummy;
    int pre = 0x3f3f3f3f;

    while (l1 && l2) {
        if (l1 -> val < l2 -> val) pre = l1 -> val, cur = cur -> next = l1, l1 = l1 -> next;
        else pre = l2 -> val, cur = cur -> next = l2, l2 = l2 -> next;

        while (l1 && l1 -> val == pre) l1 = l1 -> next;
        while (l2 && l2 -> val == pre) l2 = l2 -> next;
    }

    while (l1) {
        pre = l1 -> val, cur = cur -> next = l1, l1 = l1 -> next;
        while (l1 && l1 -> val == pre) l1 = l1 -> next;
    }

    while (l2) {
        pre = l2 -> val, cur = cur -> next = l2, l2 = l2 -> next;
        while (l2 && l2 -> val == pre) l2 = l2 -> next;
    }

    return dummy -> next;
}

int main() {
    // 改: 删除重复元素
    // 输入：l1 = [1,2,4], l2 = [1,3,4]     输出：[1,2,3,4]
    // 输入：l1 = [], l2 = []               输出：[]
    // 输入：l1 = [], l2 = [0]              输出：[0]

    cin >> n >> m;
    for (int i = 0; i < n; i ++ ) cin >> x, nums1.push_back(x);
    for (int i = 0; i < m; i ++ ) cin >> x, nums2.push_back(x);

    ListNode* l1 = buildList(nums1, n);
    ListNode* l2 = buildList(nums2, m);

    ListNode* res = merge2List(l1, l2);

    printList(res);

    return 0;
}