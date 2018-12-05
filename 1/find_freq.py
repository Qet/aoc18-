# AoC day 1, puzzle 2

with open('input') as fh:
    sum = 0
    s = set()
    for line in fh:
        sum += int(line)
        if sum in s:
            print "Frequency {} detected twice! Stopping.".format(sum);
            break
        s.add(sum)

    print s
        


