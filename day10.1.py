
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

def generate_stars(data):
    stars = []
    for d in data:
        stars.append({'curx': d.x, 'cury': d.y, 'initial': d})
    return stars

def reset_model():
    size = modelsize(256, 256)
    model = [['.'] * size.x for i in xrange(size.y)]
    return model

def populate_model(model, stars):
    scale_factor =  1.0 / 65536.0 * 256.0
    for s in stars:
        x = int(stars['curx'] * scale_factor)
        y = int(stars['cury'] * scale_factor)
    model[x][y] = '#'


def iterate_stars(stars):
    for s in stars:
        stars['curx'] += stars['initial'].vx
        stars['cury'] += stars['initial'].velocity

def print_model(model):
    for row in model:
        print ''.join(row)

def go():
    input = get_input()
    
    for line in input:
        star = parse_line(line)

