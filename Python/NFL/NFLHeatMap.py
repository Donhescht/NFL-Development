import operator
import random
import pylab
import colorsys
from treemap import Treemap



size_cache = {}



# the size of a node is the sum of the sizes of its leaf nodes
def size(thing):
    if isinstance(thing, int) or isinstance(thing, float):
        return thing
    if thing in size_cache:
        return size_cache[thing]
    else:
        size_cache[thing] = reduce(operator.add, [size(x) for x in thing])
        return size_cache[thing]

# random color (note r,g,b triples could be specified here)
def random_color(thing):
    return colorsys.hsv_to_rgb(random.random(), 0.5, 1)

# the tree is nested tuples
tree= ((5,(3,5,2,(0.1,0.1,0.6),1)), 4, (5,2,(2,3,(3,2, (2,5,2),2)),(3,3)), (3,2,(0.2,0.2,0.2)) )

# build the treemap object
tm = Treemap(tree, size, random_color)

# show it!
pylab.show()