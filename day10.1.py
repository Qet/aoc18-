
from collections import namedtuple
import time
import os


star = namedtuple('star', ['x', 'y', 'vx', 'vy'])
modelsize = namedtuple('modelsize', ['x', 'y'])

size = modelsize(65536, 65536)

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
    model = [['.'] * size.y for i in xrange(size.x)]
    return model

def populate_model(model, stars):
    #scale_factor =  1.0 / 65536.0
    for s in stars:
        x = int(s['curx'])# * scale_factor * size.x)
        y = int(s['cury'])# * scale_factor * size.y)
        model[x][y] = '#'


def iterate_stars(stars):
    for s in stars:
        s['curx'] += s['initial'].vx
        s['cury'] += s['initial'].vy

def print_model(model, start, win_size):
    import os
    os.system('clear')
    
    for row in model[start.y : start.y + win_size.y]:
        print ''.join(row[start.x : start.x + win_size.x])


def do_bins(stars):
    minval = -65536
    maxval = 65535
    divisions = 50
    step = (maxval - minval) / divisions
    bins = [[{}] * divisions for a in range(divisions)]
    for i in xrange(divisions):
        for j in xrange(divisions):
            curx = minval + i * step
            cury = minval + j * step
            bins[i][j]['x'] = curx
            bins[i][j]['y'] = cury
    print bins


def calc_bounding_box(stars):
    ymin = xmin = 0
    ymax = xmax = 0
    for cur_star in stars:
        xmin = min(xmin, cur_star['curx'])
        xmax = max(xmax, cur_star['curx'])
        ymin = min(ymin, cur_star['cury'])
        ymax = max(ymax, cur_star['cury'])
    return xmin, xmax, ymin, ymax

def bounding_box_area(box):
    xmin, xmax, ymin, ymax = box
    return (xmax - xmin) * (ymax - ymin)


def go():
    start = modelsize(14000, 28000)
    win_size = modelsize(1400, 1400)

    input = get_input()
    line_data = []
    for line in input:
        line_data.append(parse_line(line))
    stars = generate_stars(line_data)

    it = 0
    areas = []
    while True:
        box = calc_bounding_box(stars)
        areas.append(bounding_box_area(box))
        it = it + 1
        for i in xrange(100):
            iterate_stars(stars)

go()
