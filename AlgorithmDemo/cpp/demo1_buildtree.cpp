#include <iostream>
#include <cstring>
#include <algorithm>
#include <vector>
#include <queue>

using namespace std;

vector<int> nums;

struct TreeNode {
    int val;
    TreeNode *left;
    TreeNode *right;
    TreeNode(int val): val(val), left(nullptr), right(nullptr) {}
};

TreeNode * buildTree(int idx) {
    if (idx >= nums.size() || nums[idx] == -1) return nullptr;

    TreeNode* root = new TreeNode(nums[idx]);
    root -> left = buildTree(idx * 2 + 1);
    root -> right = buildTree(idx * 2 + 2);

    return root;
}

int main() {
    int x;
    while (cin >> x) {
        if (x == 0) break;
        nums.push_back(x);
    } 
    
    TreeNode * root = buildTree(0);

    return 0;
}