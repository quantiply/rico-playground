#!/usr/bin/env python

"""Generate test requests.

Usage:
  generate_requests.py <num_requests> <num_users>
  generate_requests.py (-h | --help)

Examples:
  ./generate_requests.py 2000 200

Options:
  -h --help     Show this screen.

"""
from docopt import docopt
import json
import random
import time

URLS = [
    '/foo/bar/blah',
    '/mumbo/jumbo',
    '/login',
    '/checkout',
    '/signup'
]

def main(num_requests, num_users):
    timestamp_ms = int(time.time()*1000.0)
    for ms_offset in range(0, num_requests):
        print json.dumps(create_request(num_users, timestamp_ms + ms_offset))

def create_request(num_users, timestamp_ms):
    user_id = random.randint(1, num_users)
    url = URLS[random.randint(0, len(URLS) - 1)]
    return {
        'user_id': user_id,
        'url': url,
        'timestamp_ms': timestamp_ms
    }

if __name__ == '__main__':
    arguments = docopt(__doc__)
    num_requests = int(arguments['<num_requests>'])
    if num_requests <= 0:
        raise Exception("num_requests must be positive")
    num_users = int(arguments['<num_users>'])
    if num_users <= 0:
        raise Exception("num_users must be positive")
    main(num_requests, num_users)
