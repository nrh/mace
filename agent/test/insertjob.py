#!/usr/bin/env python

import kazoo.client
import json
import sys
from pprint import pprint

def sign_job():
    sig = None
    return sig

def main(argv):
    zk = kazoo.client.KazooClient()
    zk.start()

    children = zk.get_children('/')
    pprint(children)

    children = zk.get_children('/zookeeper')
    pprint(children)

    zk.stop()


if __name__ == '__main__':
    sys.exit(main(sys.argv))

