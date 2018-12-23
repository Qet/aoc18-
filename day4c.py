
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
        t = time_struct = time.strptime(timestamp, '%Y-%m-%d %H:%M')
        total_mins = t[4] + \
               t[3] * 60 + \
               t[2] * 24 * 60 + \
               t[1] * 32 * 24 * 60
        minute = t[4]

        # print t
        # print t[0], t[1], t[2], t[3], t[4]
        # print timestamp
        

        date_string = str(t[1]) + '-' + str(t[2])
        return total_mins, minute, time_struct, date_string


    line = line.replace('[', '')
    line = line.replace('] ', '~')

    spl = line.split('~')
    ts = spl[0]
    act = spl[1]

    ret = {}
    ret['total_mins'], ret['min'], ret['ts'], ret['date_string'] = parse_timestamp(ts)
        
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
        return a['total_mins'] - b['total_mins']
    data.sort(cmp=sort_cmp)

def calc_mins_asleep(data):
    cur_id = -1
    sleep_time = {}
    sleep_min_count = {}

    sleep_start = -1
    for d in data:
        if d['act'] == 'begin':
            cur_id = d['id']
        elif d['act'] == 'sleep':
            sleep_start = d['min']
        elif d['act'] == 'wake':
            if cur_id not in sleep_time:
                sleep_time[cur_id] = 0
            sleep_time[cur_id] += d['min'] - sleep_start

            if cur_id not in sleep_min_count:
                sleep_min_count[cur_id] = [0] * 60
            for m in xrange(sleep_start, d['min']):
                sleep_min_count[cur_id][m] += 1


    max_val = 0
    max_min = 0
    best_id = 0
    for id, min_count in sleep_min_count.iteritems():
        for min in xrange(60):
            if min_count[min] > max_val:
                max_val = min_count[min]
                max_min = min
                best_id = id

    print 'Guard #{} spent Min #{} asleep the most ({} mins)'.format(best_id, max_min, max_val)
    print 'ID x Min: {} x {} = {}'.format(best_id, max_min, best_id * max_min)


def go():
    input = get_input()
    data = []
    for line in input:
        data.append(parse_line(line))
    sort_entries(data)
    timestamps = set()
    calc_mins_asleep(data)

go()