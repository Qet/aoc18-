# AoC day 2, puzzle 1.

def get_input():
    with open('/home/rhett/git/aoc18-/2/input') as fh:
        lines = list(fh)
        return map(lambda x: x.rstrip(), lines)


def eval_barcode(code):
    # Returns a tuple of bools for whether or not there's a 2-of and a 3-of in the barcode. 
    letter_counts = {}
    counts = set()
    for l in code:
        if l not in letter_counts:
            letter_counts[l] = 1
        else:
            letter_counts[l] += 1
    [counts.add(x) for x in letter_counts.values()]
    return 2 in counts, 3 in counts

twos = 0
threes = 0
for line in get_input():
    has_two, has_three = eval_barcode(line)
    if has_two: twos += 1
    if has_three: threes += 1

print "Checksum is: {} x {} = {}".format(twos, threes, twos * threes)
