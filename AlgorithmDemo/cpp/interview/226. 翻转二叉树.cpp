#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>
#include <queue>

using namespace std;

struct TreeNode {
    int val;
    TreeNode* left;
    TreeNode* right;
    TreeNode(int val): val(val), left(nullptr), right(nullptr) {}
};

int n;
vector<int> nums;

TreeNode * buildTree(int idx) {
    if (idx >= n || nums[idx] == -1) return nullptr;

    TreeNode* root = new TreeNode(nums[idx]);
    root -> left = buildTree(idx * 2 + 1);
    root -> right = buildTree(idx * 2 + 2);

    return root;
}

void reverseTree(TreeNode* root) {
    if (root == nullptr) return;

    reverseTree(root -> left);
    reverseTree(root -> right);
    swap(root -> left, root -> right);
}

void printTree(TreeNode* root) {
    if (root == nullptr) return;
    queue<TreeNode*> q;
    q.push(root);
    while (!q.empty()) {
        auto t = q.front(); q.pop();
        cout << t -> val << " ";
        if (t -> left) q.push(t -> left);
        if (t -> right) q.push(t -> right);
    }
}

int main() {
    // 输入：root = [4,2,7,1,3,6,9]     输出：[4,7,2,9,6,3,1]
    // 输入：root = [2,1,3]             输出：[2,3,1]
    // 输入：root = []                  输出：[]

    cin >> n;
    if (n == 0) return 0;

    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    TreeNode* root = buildTree(0);

    reverseTree(root);

    printTree(root);

    return 0;
}