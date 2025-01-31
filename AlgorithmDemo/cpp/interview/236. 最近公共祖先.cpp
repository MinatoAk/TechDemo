#include <iostream>
#include <cstring>
#include <vector>
#include <algorithm>

using namespace std;

struct TreeNode {
    int val;
    TreeNode* left;
    TreeNode* right;
    TreeNode(int val): val(val), left(nullptr), right(nullptr) {}
};

int n, pp, qq;
vector<int> nums;
TreeNode* p, *q, *res = nullptr;

TreeNode* buildTree(int idx) {
    if (idx >= n || nums[idx] == -1) return nullptr;

    TreeNode* root = new TreeNode(nums[idx]);
    if (root -> val == pp) p = root;
    if (root -> val == qq) q = root;
    root -> left = buildTree(idx * 2 + 1);
    root -> right = buildTree(idx * 2 + 2);

    return root;
}

int findLCA(TreeNode* root) {
    if (root == nullptr) return 0;

    int state = 0;
    int l = findLCA(root -> left);
    int r = findLCA(root -> right);
    state = l | r;

    if (root == p) state |= 1;
    if (root == q) state |= 2;
    if (res == nullptr && state == 3) res = root;

    return state;
}

int main() {
    // 输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1     输出：3
    // 输入：root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4     输出：5
    // 输入：root = [1,2], p = 1, q = 2                             输出：1

    cin >> n >> pp >> qq;
    for (int i = 0; i < n; i ++ ) {
        int x; cin >> x;
        nums.push_back(x);
    }

    TreeNode* root = buildTree(0);

    findLCA(root);

    cout << res -> val << endl;

    return 0;
}