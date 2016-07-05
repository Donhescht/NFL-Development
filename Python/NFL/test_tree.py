from collections import deque
import unittest
from Tree import Tree


class TestTree(unittest.TestCase):
    def test_nodes(self):
        __tree = Tree()
        queue = deque(["Harry", "Jane", "Joe","Diane","George","Jill", "Carol","Mary","Mark","Bill","Grace"])

        (_ROOT, _DEPTH, _BREADTH) = range(3)

        __tree.add_node("Harry")  # root node
        __tree.add_node("Jane", "Harry")
        __tree.add_node("Bill", "Harry")
        __tree.add_node("Joe", "Jane")
        __tree.add_node("Diane", "Jane")
        __tree.add_node("George", "Diane")
        __tree.add_node("Mary", "Diane")
        __tree.add_node("Jill", "George")
        __tree.add_node("Carol", "Jill")
        __tree.add_node("Grace", "Bill")
        __tree.add_node("Mark", "Jane")

        for node in __tree.traverse("Harry"):
            self.assertEqual(node, queue[0])
            queue.popleft()

        self.assertFalse(queue)

if __name__ == '__main__':
    unittest.main()
