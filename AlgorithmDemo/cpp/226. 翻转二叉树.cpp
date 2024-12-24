#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>

using namespace std;

vector<int> nums;
vector<int> res;

struct TreeNode {
    int val;
    TreeNode* left;
    TreeNode* right;
    TreeNode(int val): val(val), left(nullptr), right(nullptr) {}
};

TreeNode* buildTree(int idx) {
    if (idx >= nums.size() || nums[idx] == -1) return nullptr;

    TreeNode* root = new TreeNode(nums[idx]);
    root -> left = buildTree(2 * idx + 1);
    root -> right = buildTree(2 * idx + 2);

    return root;
}

void dfs(TreeNode* root) {
    if (root == nullptr) return;
    dfs(root -> left);
    dfs(root -> right);
    swap(root -> left, root -> right);
}

int main() {

    // [4,2,7,1,3,6,9]
    // [1, -1, 2]
    int x;
    while (cin >> x) {
        if (x == 0) break;
        nums.push_back(x);
    }

    TreeNode* root = buildTree(0);

    dfs(root);

    return 0;
}