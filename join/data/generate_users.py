#!/usr/bin/env python

"""Generate test users.

Usage:
  generate_users.py <number> [--separator=<separator>]
  generate_users.py (-h | --help)

Examples:
  ./generate_users.py 200 --separator=',
  ./generate_users.py 200 --separator=$'\\t'

Options:
  -h --help     Show this screen.
  --separator=<separator>   Separator between key and value [default: \t]

"""
from docopt import docopt
from faker import Faker
import json

def main(num, separator):
    fake = Faker()
    for id in range(1, num+1):
        print "%s%s%s" % (id, separator, json.dumps(create_user(id, fake)))

def create_user(id, fake):
    return {
        'id', id,
        'name': fake.name(),
        'email': fake.email(),
        'phone_number': fake.phone_number()
    }

if __name__ == '__main__':
    arguments = docopt(__doc__)
    num = int(arguments['<number>'])
    if num <= 0:
        raise Exception("Number of users must be positive")
    separator = arguments['--separator']
    main(num, separator)
