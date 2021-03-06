# AoC day 3, puzzle 1.

#   #217 @ 897,812: 16x26
#    ID    x    y   w  h    

def get_input():
    with open('input') as fh:
        lines = list(fh)
        return map(lambda x: x.rstrip(), lines)

def parse_line(line):
    def multi_split(line, delim_list):
        for d in delim_list:
            line = line.replace(d,'~')
        return line.split('~')

    def remove_chars(line, char_list):
        for c in char_list:
            line = line.replace(c, '')
        return line

    line = remove_chars(line, [' ', '#'])
    return map(lambda x: int(x), multi_split(line, ['@', ',', ':', 'x']))
    
def collect_data():
    def process_point(d):
        x = d[1]
        y = d[2]
        w = d[3]
        h = d[4]
        for i in xrange(x, x+w):
            for j in xrange(y, y+h):
                fabric[i][j] += 1

    def count_contested_squares():
        contested_squares = 0
        for row in fabric:
            for cell in row:
                if cell >= 2:
                    contested_squares += 1
        return contested_squares

    fabric = [[0] * 1000 for i in xrange(1000)]
    lines = get_input()
    data = [parse_line(l) for l in lines]
    [process_point(d) for d in data]
    print count_contested_squares()


collect_data()

