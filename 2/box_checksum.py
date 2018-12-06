# AoC day 2, puzzle 1.

def get_input():
    out = []
    with open('input') as fh:
        for line in fh:
            out.append(int(line))
    return out

def eval_barcode(code):
    letter_counts = {}
    for l in code:
        if l not in letter_counts:
            letter_counts[l] = 0
        else:
            letter_counts[l] += 1
    
