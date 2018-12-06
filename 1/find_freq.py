# AoC day 1, puzzle 2

def get_input():
    out = []
    with open('input') as fh:
        for line in fh:
            out.append(int(line))
    return out
    
def calc_dupe_freq():
    data = get_input()
    frequencies = set()
    frequencies.add(0)
    sum = 0
    loops = 0
    while True:
        loops += 1
        for d in data:
            sum += d
            if sum in frequencies:
                return sum, loops # Found a duplicate frequency level
            frequencies.add(sum)

answer, loops = calc_dupe_freq()
print "Duplicate frequency level: {}. Loops: {}".format(answer, loops)
