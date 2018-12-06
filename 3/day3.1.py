# AoC day 2, puzzle 2.

def get_input():
    with open('/home/rhett/git/aoc18-/2/input') as fh:
        lines = list(fh)
        return map(lambda x: x.rstrip(), lines)


def compare_barcodes(code1, code2):
    """ Return True if the barcodes are exactly 1 character different, false otherwise. """
    if len(code1) != len(code2): return False
    found_differing_char = False
    for i in xrange(len(code1)):
        if code1[i] != code2[i]:
            if not found_differing_char:
                found_differing_char = True
                differing_char_pos = i
            else:
                return False, ""
    
    if found_differing_char:
        substr = code1[0:differing_char_pos] + code1[differing_char_pos + 1:]
    else:
        substr = ""
    return found_differing_char, substr


def go():
    input = get_input()
    for i in xrange(len(input)):
        for j in xrange(i, len(input)):
            result, substr = compare_barcodes(input[i], input[j])
            if result:
                print 'Found barcodes that differ by 1 letter: [{}] and [{}].'.format(input[i], input[j])
                print 'Substring Result is: [{}]'.format(substr)
                return

go()

