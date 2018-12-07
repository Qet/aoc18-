
def get_input():
    with open('input7') as fh:
        lines = list(fh)
        return map(lambda x: x.rstrip(), lines)

#Step L must be finished before step Z can begin.
def parse_line(line):
    spl = line.split(' ')
    return spl[1], spl[7]

def parse_data():
    # The map is a dict of: values is a list of steps to do before the key. 
    steps = set()
    mapping = {}
    lines = get_input()
    data = [parse_line(l) for l in lines]
    for pre, post in data:
        steps.add(pre)
        steps.add(post)
        if post not in mapping:
            mapping[post] = []
        mapping[post].append(pre)
    return mapping, steps


def generate_order():
    mapping, steps = parse_data()
    print mapping
    print steps
    
    cur_availables = list(steps.difference(mapping.keys()))
    
    print cur_availables

    done_steps = []

    while len(cur_availables) > 0:
        cur_availables.sort()
        next_step = cur_availables.pop(0)
        done_steps.append(next_step)

        for k, v in mapping.iteritems():
            if set(v).issubset(done_steps):
                if k not in cur_availables and k not in done_steps:
                    cur_availables += k

    print ''.join(done_steps)

generate_order()