
from collections import namedtuple
import time
import os


def get_input():
    with open('input4') as fh:
        lines = list(fh)
        return map(lambda x: x.rstrip(), lines)

# [1518-11-01 00:00] Guard #10 begins shift
# [1518-11-01 00:05] falls asleep
# [1518-11-01 00:25] wakes up
def parse_line(line):
    def parse_id(act):
        act = act.replace(' #', '~')
        act = act.replace(' ', '~')
        spl = act.split('~')
        return int(spl[1])
    
    def parse_timestamp(timestamp):
        return time.strptime(timestamp, '%Y-%m-%d %H:%M')

    line = line.replace('[', '')
    line = line.replace('] ', '~')

    spl = line.split('~')
    ts = spl[0]
    act = spl[1]

    ret = {}
    ret['ts'] = parse_timestamp(ts)
    if 'begins shift' in act:
        ret['id'] = parse_id(act)
        ret['act'] = 'begin'
    elif 'falls asleep' in act:
        ret['act'] = 'sleep'
    elif 'wakes up' in act:
        ret['act'] = 'wake'
    else:
        assert False, 'Failed to parse line: ' + line

    return ret

def sort_entries(data):
    def sort_cmp(item):
        return item
    data.sort()


def go():
    input = get_input()
    for line in input:
        print parse_line(line)

go()