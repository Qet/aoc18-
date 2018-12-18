
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
        t = time.strptime(timestamp, '%Y-%m-%d %H:%M')
        mins = t[4] + \
               t[3] * 60 + \
               t[2] * 24 * 60 + \
               t[1] * 30 * 24 * 60 + \
               t[0] * 365 * 24 * 60
        return mins, t


    line = line.replace('[', '')
    line = line.replace('] ', '~')

    spl = line.split('~')
    ts = spl[0]
    act = spl[1]

    ret = {}
    ret['mins'], ret['ts'] = parse_timestamp(ts)
        
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
    def sort_cmp(a, b):
        return a['mins'] - b['mins']
    data.sort(cmp=sort_cmp)

def make_table(data):
    

def go():
    input = get_input()
    data = []
    for line in input:
        data.append(parse_line(line))
    sort_entries(data)
    print data
    

go()