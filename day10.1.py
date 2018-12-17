
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

def iterate_stars(stars):
    for s in stars:
        s['curx'] += s['initial'].vx
        s['cury'] += s['initial'].vy

def calc_bounding_box(stars):
    ymin = xmin = 999999999
    ymax = xmax = -999999999
    for cur_star in stars:
        xmin = min(xmin, cur_star['curx'])
        xmax = max(xmax, cur_star['curx'])
        ymin = min(ymin, cur_star['cury'])
        ymax = max(ymax, cur_star['cury'])
    return xmin, xmax, ymin, ymax

def bounding_box_area(box):
    xmin, xmax, ymin, ymax = box
    return (xmax - xmin) * (ymax - ymin)

def print_stars(stars, box):
    print box
    xmin, xmax, ymin, ymax = box
    buf = 10
    for j in xrange(ymin - buf, ymax + buf):
        line = ''
        for i in xrange(xmin - buf, xmax + buf):
            has_star = False 
            for s in stars:
                if s['curx'] == i and s['cury'] == j:
                    has_star = True
                    break
            line += '#' if has_star else '.'
        print line


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
    min_area = 99999999999999
    min_index = 0
    for it in xrange(12000):
        box = calc_bounding_box(stars)
        new_area = bounding_box_area(box)
        areas.append(new_area)
        if new_area < min_area:
            min_area = new_area
            min_index = it

        if it == 10619:
            print_stars(stars, box)

        it = it + 1
        iterate_stars(stars)
    print min_area, min_index

go()
