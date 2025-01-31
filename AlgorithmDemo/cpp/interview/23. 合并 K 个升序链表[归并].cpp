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

int n, m, x;
vector<ListNode*> lists;

ListNode* buildList(vector<int> nums) {
    ListNode* dummy = new ListNode(-1), *cur = dummy;

    for (int i = 0; i < nums.size(); i ++ ) {
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

    while (l1 && l2) {
        if (l1 -> val < l2 -> val) cur = cur -> next = l1, l1 = l1 -> next;
        else cur = cur -> next = l2, l2 = l2 -> next;
    }

    if (l1) cur -> next = l1;
    if (l2) cur -> next = l2;

    return dummy -> next;
}

ListNode* mergeLists() {
    while (lists.size() > 1) {
        vector<ListNode*> tmp;

        for (int i = 0; i < lists.size(); i += 2) {
            ListNode* l1 = lists[i], *l2 = nullptr;
            if (i + 1 < lists.size()) l2 = lists[i + 1];

            ListNode* ans = merge2List(l1, l2);
            tmp.push_back(ans);
        }

        lists = tmp;
    }

    return lists[0];
}

int main() {
    // 输入：lists = [[1,4,5],[1,3,4],[2,6]]    
    // 输出：[1,1,2,3,4,4,5,6]

    cin >> n;
    for (int i = 0; i < n; i ++ ) {
        vector<int> tmp;
        cin >> m;
        for (int i = 0; i < m; i ++ ) cin >> x, tmp.push_back(x);
        ListNode* head = buildList(tmp);
        lists.push_back(head);
    }

    auto head = mergeLists();

    printList(head);

    return 0;
}