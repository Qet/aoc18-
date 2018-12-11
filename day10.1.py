
from collections import namedtuple

star = namedtuple('star', ['x', 'y', 'vx', 'vy'])
modelsize = namedtuple('modelsize', ['x', 'y'])

def get_input():
    with open('input10') as fh:
        lines = list(fh)
        return map(lambda x: x.rstrip(), lines)


#position=<-42304, -10427> velocity=< 4,  1>
def parse_line(line):
    line = line.replace('position=<', '')
    line = line.replace('velocity=<', '')
    line = line.replace(',', '~')
    line = line.replace('>', '~')
    data =  map(lambda x: int(x), line.split('~')[:4])
    # X, Y, vx, vy
    return star(*data)

def generate_model(data):
    size = modelsize(256, 256)
    model = [{}} * size.x for i in xrange(size.y)]
    stars = []
    for d in data:
        stars.append({'curx': d.x, 'cury': d.y, 'initial': d})
    


for l in get_input():
    print parse_line(l)

